package com.inventi.app.formatter;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@AllArgsConstructor
class DateFormatterTest {

    private final DateFormatter dateFormatter = new DateFormatter();

    @Test
    void when_LocalDateIsNotNull_then_ShouldReturnLocalDateWithAtTheEndOfTheDayTime() {
        LocalDate localDate = LocalDate.of(2022, 2, 19);

        LocalDateTime localDateTime = dateFormatter.getTo(localDate);

        Assertions.assertEquals(LocalDateTime.of(2022, 2, 19, 23, 59, 59), localDateTime);
    }


    @Test
    void when_LocalDateIsNotNull_then_ShouldReturnLocalDateWithAtTheStartOfTheDayTime() {
        LocalDate localDate = LocalDate.of(2022, 2, 19);

        LocalDateTime localDateTime = dateFormatter.getFrom(localDate);

        Assertions.assertEquals(LocalDateTime.of(2022, 2, 19, 0, 0, 0), localDateTime);
    }

    @Test
    void when_LocalDateIsNull_then_ShouldReturnLocalDateTimeAsLocalTimeAtTheEndOfTheDay() {
        LocalDateTime localDateTime = dateFormatter.getTo(null);

        Assertions.assertEquals(LocalDateTime.of(LocalDate.now(), LocalTime.MAX), localDateTime);
    }

    @Test
    void when_LocalDateIsNull_then_ShouldReturnLocalDateTimeAsEpocStartOfTheDayTime() {
        LocalDateTime localDateTime = dateFormatter.getFrom(null);

        Assertions.assertEquals(LocalDate.EPOCH.atStartOfDay(), localDateTime);
    }
}