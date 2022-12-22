package com.ITCube.Booking.service;

import com.ITCube.Booking.exception.BookingNotFoundException;
import com.ITCube.Booking.repository.BookingRepository;
import com.ITCube.Data.model.Booking;
import com.ITCube.Data.model.Desk;
import com.ITCube.Data.model.Room;
import com.ITCube.Data.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * @author Matteo Rosso
 */

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class BookingServiceUnitTest {
    @Mock
    private BookingRepository rep;
    @Mock
    private Clock clock;
    @InjectMocks
    private BookingServiceImpl underTest;

    private static final ZonedDateTime NOW= ZonedDateTime.of(
            2023,
            6,
            15,
            12,
            30,
            0,
            0,
            ZoneId.of("GMT")
    );

    @BeforeEach
    void setUp() {
        when(clock.getZone()).thenReturn(NOW.getZone());
        when(clock.instant()).thenReturn(NOW.toInstant());
    }

    @Test
    void findAllBookingsTest() {
        // When
        User user = new User("Matteo", "Rosso", "Junior");
        Desk desk=new Desk("A1", new Room("Stanza 1", "Via Roma 15", 99));
        LocalDateTime start=LocalDateTime.now();
        LocalDateTime end=LocalDateTime.now();
        Booking expected=new Booking(start, end, user, desk);
        when(rep.findAll()).thenReturn(List.of(expected));

        // Action
        List<Booking> result=underTest.findAllBookings();

        // Assert
        assertFalse(result.isEmpty());
        assertThat(result.size(), equalTo(1));
        assertThat(result.get(0).getStartDate(), equalTo(expected.getStartDate()));
        verify(rep,times(1)).findAll();
        verifyNoMoreInteractions(rep);
    }

    @Test
    void findAllBookingsByUserTest() {
        // When
        User user = new User("Matteo", "Rosso", "Junior");
        Desk desk=new Desk("A1", new Room("Stanza 1", "Via Roma 15", 99));
        Booking expected=new Booking(LocalDateTime.now(), LocalDateTime.now(), user, desk);
        when(rep.findAllBookingsByUser(anyLong())).thenReturn(List.of(expected));

        // Action
        List<Booking> result=underTest.findAllBookingsByUser(anyLong());

        // Assert
        assertNotNull(result);
        assertThat(result.size(), equalTo(1));
        assertThat(result.get(0).getUser().getFirstName(),equalTo(user.getFirstName()));
        verify(rep,times(1)).findAllBookingsByUser(anyLong());
        verifyNoMoreInteractions(rep);
    }

    @Test
    void findAllBookingByDeskTest() {
        // When
        User user = new User("Matteo", "Rosso", "Junior");
        Desk desk=new Desk("A1", new Room("Stanza 1", "Via Roma 15", 99));
        Booking expected=new Booking(LocalDateTime.now(), LocalDateTime.now(), user, desk);
        when(rep.findAllBookingsByDesk(anyLong())).thenReturn(List.of(expected));

        // Action
        List<Booking> result=underTest.findAllBookingByDesk(anyLong());

        // Assert
        assertNotNull(result);
        assertThat(result.size(), equalTo(1));
        assertThat(result.get(0).getDesk().getDeskName(),equalTo(desk.getDeskName()));
        verify(rep,times(1)).findAllBookingsByDesk(anyLong());
        verifyNoMoreInteractions(rep);
    }

    @Test
    void findBookingByIdTest() {
        // When
        User user = new User("Matteo", "Rosso", "Junior");
        Desk desk=new Desk("A1", new Room("Stanza 1", "Via Roma 15", 99));
        Booking expected=new Booking(LocalDateTime.now(), LocalDateTime.now(), user, desk);
        when(rep.findById(anyLong())).thenReturn(Optional.of(expected));

        // Action
        Booking result=underTest.findBookingById(anyLong());

        // Assert
        assertNotNull(result);
        verify(rep,times(1)).findById(anyLong());
        verifyNoMoreInteractions(rep);
    }

    @Test
    void findBookingByIdFailTest(){
        // When
        when(rep.findById(anyLong())).thenThrow(BookingNotFoundException.class);

        // Assert
        assertThrows(BookingNotFoundException.class, ()-> underTest.findBookingById(anyLong()));
    }

    @Test
    void findAllDeskAvailableTest() {
        // When
        Desk d1=new Desk("A1", new Room("Stanza 1", "Via Roma 15", 99));
        Desk d2=new Desk("A2", new Room("Stanza 1","Via Roma 15", 99));
        LocalDateTime okStart=LocalDateTime.of(
                2023,
                6,
                15,
                12,
                30,
                1
        );
        when(rep.findDeskAvailable(okStart,okStart))
                .thenReturn(List.of(d1,d2));

        // Action
        List<Desk> result=underTest.findAllDeskAvailable(okStart,okStart);

        // Assert
        assertFalse(result.isEmpty());
        assertThat(result.size(), equalTo(2));
        verify(rep,times(1)).findDeskAvailable(okStart,okStart);
        verifyNoMoreInteractions(rep);
    }

    @Test
    void createBookingTest() {
        // When
        User user = new User("Matteo", "Rosso", "Junior");
        Desk desk=new Desk("A1", new Room("Stanza 1", "Via Roma 15", 99));
        LocalDateTime okStart=LocalDateTime.of(
                2023,
                6,
                15,
                12,
                30,
                1
        );
        Booking expected=new Booking(okStart, okStart, user, desk);

        when(rep.save(any(Booking.class))).thenReturn(expected);

        // Action
        Booking result=underTest.createBooking(expected);

        // Assert
        assertNotNull(result);
        assertThat(result,equalTo(expected));
        verify(rep,times(1)).save(any(Booking.class));
        verifyNoMoreInteractions(rep);
    }

    @Test
    void deleteBookingByIdTest() {
        // When
        User user = new User("Matteo", "Rosso", "Junior");
        Desk desk=new Desk("A1", new Room("Stanza 1", "Via Roma 15", 99));
        Booking expected=new Booking(LocalDateTime.now(), LocalDateTime.now(), user, desk);
        when(rep.findById(anyLong())).thenReturn(Optional.of(expected));
        doNothing().when(rep).deleteById(anyLong());

        // Action
        underTest.deleteBookingById(anyLong());

        // Assert
        verify(rep,times(1)).findById(anyLong());
        verify(rep,times(1)).deleteById(anyLong());
        verifyNoMoreInteractions(rep);
    }
}