package com.ecommerce.pricing.domain.model.vo;

import com.ecommerce.pricing.domain.exception.DomainValidationException;

import java.time.LocalDateTime;
import java.util.Objects;

import static com.ecommerce.pricing.domain.exception.constant.ExceptionConstant.INVALID_DATE_RANGE_DESC;
import static com.ecommerce.pricing.domain.exception.constant.ExceptionConstant.NULL_DATES_DESC;

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
            throw new DomainValidationException(NULL_DATES_DESC);
        }
    }

    private void validateRange(LocalDateTime start, LocalDateTime end) {
        if (end.isBefore(start)) {
            throw new DomainValidationException(INVALID_DATE_RANGE_DESC);
        }
    }

    public boolean includes(LocalDateTime date) {
        return !date.isBefore(start) && !date.isAfter(end);
    }
}
