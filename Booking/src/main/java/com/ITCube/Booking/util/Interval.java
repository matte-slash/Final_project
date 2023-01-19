package com.ITCube.Booking.util;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public class Interval {
    @NotBlank
    private final LocalDateTime start;
    @NotBlank
    private final LocalDateTime end;

    public Interval(LocalDateTime start, LocalDateTime end) {
        this.start = start;
        this.end = end;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }
}
