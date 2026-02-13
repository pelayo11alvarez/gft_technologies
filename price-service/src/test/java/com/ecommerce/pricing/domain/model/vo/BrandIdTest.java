package com.ecommerce.pricing.domain.model.vo;

import com.ecommerce.pricing.domain.exception.DomainValidationException;
import org.junit.jupiter.api.Test;

import static com.ecommerce.pricing.domain.exception.constant.ExceptionConstant.NULL_BRAND_ID_DESC;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BrandIdTest {

    @Test
    void givenValidId_whenCreateBrandId_thenSuccess() {
        //given
        final var expectedValue = 1L;

        //when
        final var brandId = new BrandId(expectedValue);

        //then
        assertNotNull(brandId);
        assertEquals(expectedValue, brandId.value());
    }

    @Test
    void givenNullId_whenCreateBrandId_thenThrowDomainValidationException() {
        //when /then
        final var exception = assertThrows(DomainValidationException.class, () -> new BrandId(null));
        assertEquals(DomainValidationException.class, exception.getClass());
        assertEquals(NULL_BRAND_ID_DESC, exception.getMessage());
    }

    @Test
    void givenBrandIds_whenCompareHashCodeAndEquals_thenCompare() {
        //given
        final var brandId1 = new BrandId(1L);
        final var brandId2 = new BrandId(1L);
        final var brandId3 = new BrandId(2L);

        //when /then
        assertEquals(brandId1, brandId2);
        assertEquals(brandId1.hashCode(), brandId2.hashCode());
        assertNotEquals(brandId1, brandId3);
        assertNotEquals(brandId1.hashCode(), brandId3.hashCode());
    }
}