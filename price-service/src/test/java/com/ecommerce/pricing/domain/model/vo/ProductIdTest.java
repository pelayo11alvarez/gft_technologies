package com.ecommerce.pricing.domain.model.vo;

import com.ecommerce.pricing.domain.exception.DomainValidationException;
import org.junit.jupiter.api.Test;

import static com.ecommerce.pricing.domain.exception.constant.ExceptionConstant.NULL_PRODUCT_ID_DESC;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProductIdTest {

    @Test
    void givenValidId_whenCreateProductId_thenSuccess() {
        //given
        final var expectedValue = 35455L;

        //when
        final var productId = new ProductId(expectedValue);

        //then
        assertNotNull(productId);
        assertEquals(expectedValue, productId.value());
    }

    @Test
    void givenNullId_whenCreateProductId_thenThrowDomainValidationException() {
        //when /then
        final var exception = assertThrows(DomainValidationException.class, () -> new ProductId(null));
        assertEquals(NULL_PRODUCT_ID_DESC, exception.getMessage());
    }

    @Test
    void givenProductsIds_whenCompareHashCodeAndEquals_thenCompare() {
        //given
        final var id1 = new ProductId(35455L);
        final var id2 = new ProductId(35455L);
        final var id3 = new ProductId(1L);

        //when /then
        assertEquals(id1, id2);
        assertEquals(id1.hashCode(), id2.hashCode());
        assertNotEquals(id1, id3);
        assertNotEquals(id1.hashCode(), id3.hashCode());
    }
}