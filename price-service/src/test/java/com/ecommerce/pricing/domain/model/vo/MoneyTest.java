package com.ecommerce.pricing.domain.model.vo;

import com.ecommerce.pricing.domain.exception.DomainValidationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static com.ecommerce.pricing.domain.exception.constant.ExceptionConstant.INVALID_AMOUNT_DESC;
import static com.ecommerce.pricing.domain.exception.constant.ExceptionConstant.NULL_CURRENCY_DESC;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MoneyTest {

    @Test
    void givenValidProperties_whenCreateMoney_thenSuccess() {
        //given
        final var amount = new BigDecimal("35.50");
        final var currency = "EUR";

        //when
        final var money = new Money(amount, currency);

        //then
        assertNotNull(money);
        assertEquals(amount, money.amount());
        assertEquals(currency, money.currency());
    }

    @ParameterizedTest
    @MethodSource("invalidAmountsProvider")
    void givenInvalidAmount_whenCreateMoney_thenThrowDomainValidationException(BigDecimal invalidAmount) {
        //when /then
        final var exception = assertThrows(DomainValidationException.class,
                () -> new Money(invalidAmount, "EUR"));
        assertEquals(INVALID_AMOUNT_DESC, exception.getMessage());
    }

    private static Stream<BigDecimal> invalidAmountsProvider() {
        return Stream.of(
                null,
                new BigDecimal("-1.00"),
                new BigDecimal("-0.01")
        );
    }

    @ParameterizedTest
    @NullAndEmptySource
    void givenInvalidCurrency_whenCreateMoney_thenThrowDomainValidationException(String invalidCurrency) {
        //when /then
        final var  exception = assertThrows(DomainValidationException.class,
                () -> new Money(new BigDecimal("10.00"), invalidCurrency));
        assertEquals(NULL_CURRENCY_DESC, exception.getMessage());
    }

    @Test
    void givenMoney_whenCompareHashCodeAndEquals_thenCompare() {
        //given
        final var money1 = new Money(new BigDecimal("10.00"), "EUR");
        final var money2 = new Money(new BigDecimal("10.00"), "EUR");
        final var money3 = new Money(new BigDecimal("20.00"), "USD");

        //when /then
        assertEquals(money1, money2);
        assertEquals(money1.hashCode(), money2.hashCode());
        assertNotEquals(money1, money3);
        assertNotEquals(money1.hashCode(), money3.hashCode());
    }
}