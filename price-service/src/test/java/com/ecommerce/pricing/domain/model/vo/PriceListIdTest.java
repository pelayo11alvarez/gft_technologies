package com.ecommerce.pricing.domain.model.vo;

import com.ecommerce.pricing.domain.exception.DomainValidationException;
import org.junit.jupiter.api.Test;

import static com.ecommerce.pricing.domain.exception.constant.ExceptionConstant.NULL_PRICE_LIST_ID_DESC;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PriceListIdTest {

    @Test
    void givenValidId_whenCreatePriceListId_thenSuccess() {
        //given
        final var expectedValue = 1L;

        //when
        final var priceListId = new PriceListId(expectedValue);

        //then
        assertNotNull(priceListId);
        assertEquals(expectedValue, priceListId.value());
    }

    @Test
    void givenNullId_whenCreatePriceListId_thenThrowDomainValidationException() {
        //when /then
        final var exception = assertThrows(DomainValidationException.class, () -> new PriceListId(null));
        assertEquals(NULL_PRICE_LIST_ID_DESC, exception.getMessage());
    }

    @Test
    void givenPriceListIds_whenCompareHashCodeAndEquals_thenCompare() {
        //given
        final var id1 = new PriceListId(1L);
        final var id2 = new PriceListId(1L);
        final var id3 = new PriceListId(2L);

        //when /then
        assertEquals(id1, id2);
        assertEquals(id1.hashCode(), id2.hashCode());
        assertNotEquals(id1, id3);
        assertNotEquals(id1.hashCode(), id3.hashCode());
    }
}