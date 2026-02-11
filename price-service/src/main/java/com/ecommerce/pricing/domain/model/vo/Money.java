package com.ecommerce.pricing.domain.model.vo;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.Objects;

public record Money(BigDecimal amount, String currency) {
    public Money {
        validate(amount, currency);
    }

    private void validate(BigDecimal amount, String currency) {
        validateAmount(amount);
        validateCurrency(currency);
    }

    private void validateAmount(BigDecimal amount) {
        if (Objects.isNull(amount) || amount.signum() < 0) {
            throw new IllegalArgumentException("Invalid amount");
        }
    }

    private void validateCurrency(String currency) {
        if (StringUtils.isEmpty(currency)) {
            throw new IllegalArgumentException("Currency cannot be null");
        }
    }
}
