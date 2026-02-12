package com.ecommerce.pricing.domain.model.vo;

import com.ecommerce.pricing.domain.exception.DomainValidationException;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.Objects;

import static com.ecommerce.pricing.domain.exception.constant.ExceptionConstant.INVALID_AMOUNT_DESC;
import static com.ecommerce.pricing.domain.exception.constant.ExceptionConstant.NULL_CURRENCY_DESC;

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
            throw new DomainValidationException(INVALID_AMOUNT_DESC);
        }
    }

    private void validateCurrency(String currency) {
        if (StringUtils.isEmpty(currency)) {
            throw new DomainValidationException(NULL_CURRENCY_DESC);
        }
    }
}
