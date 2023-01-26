package com.ITCube.Booking;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@EntityScan(basePackages = "com.ITCube.Data.model")
class BookingApplicationTests {

	@Test
	void contextLoads() {
	}

}


