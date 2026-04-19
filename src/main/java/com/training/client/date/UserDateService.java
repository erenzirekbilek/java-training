package com.training.client.date;

import java.time.*;
import java.time.format.*;

public class UserDateService {
    
    public LocalDateTime getCurrentTime() {
        return LocalDateTime.now();
    }

    public LocalDate getCurrentDate() {
        return LocalDate.now();
    }

    public LocalTime getCurrentTimeOnly() {
        return LocalTime.now();
    }

    public ZonedDateTime getCurrentTimeWithZone() {
        return ZonedDateTime.now();
    }

    public String formatDateTime(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public LocalDateTime parseDateTime(String dateStr) {
        return LocalDateTime.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public LocalDate addDays(LocalDate date, int days) {
        return date.plusDays(days);
    }

    public long daysBetween(LocalDate start, LocalDate end) {
        return ChronoUnit.DAYS.between(start, end);
    }

    public LocalDateTime convertToUtc(ZonedDateTime zonedDateTime) {
        return zonedDateTime.withZoneSameInstant(ZoneOffset.UTC).toLocalDateTime();
    }
}