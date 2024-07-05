package org.example.likelion.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class DateTimeUtils {
    public boolean isOverlapUsingLocalDateAndDuration(LocalDate start1, LocalDate end1, LocalDate start2, LocalDate end2) {
        long overlap = Math.min(end1.toEpochDay(), end2.toEpochDay()) -
                Math.max(start1.toEpochDay(), start2.toEpochDay());

        return overlap >= 0;
    }

    public boolean isDateGreaterThan1Date(LocalDate start, LocalDate end) {
        long overlap = end.toEpochDay() - start.toEpochDay();

        return overlap >= 0;
    }
}
