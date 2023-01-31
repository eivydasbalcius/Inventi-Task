package com.inventi.app.formatter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class DateFormatter {


    public LocalDateTime getTo(LocalDate to) {
        if (to == null) {
            return LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
        }
        return to.atTime(23, 59, 59);
    }

    public LocalDateTime getFrom(LocalDate from) {
        if (from == null) {
            return LocalDate.EPOCH.atStartOfDay();
        }
        return from.atStartOfDay();
    }
}
