package com.ecommerce.pricing.domain.model.vo;

import java.time.LocalDateTime;
import java.util.Objects;

public record DateRange(LocalDateTime start, LocalDateTime end) {

    public DateRange {
        validate(start, end);
    }

    private void validate(LocalDateTime start, LocalDateTime end) {
        validateNull(start, end);
        validateRange(start, end);
    }

    private void validateNull(LocalDateTime start, LocalDateTime end) {
        if (Objects.isNull(start) || Objects.isNull(end)) {
            throw new IllegalArgumentException("Dates cannot be null");
        }
    }

    private void validateRange(LocalDateTime start, LocalDateTime end) {
        if (end.isBefore(start)) {
            throw new IllegalArgumentException("End date cannot be before start date");
        }
    }

    public boolean includes(LocalDateTime date) {
        return !date.isBefore(start) && !date.isAfter(end);
    }
}
