package com.ecommerce.pricing.domain.model;

import com.ecommerce.pricing.domain.model.vo.BrandId;
import com.ecommerce.pricing.domain.model.vo.DateRange;
import com.ecommerce.pricing.domain.model.vo.Money;
import com.ecommerce.pricing.domain.model.vo.PriceListId;
import com.ecommerce.pricing.domain.model.vo.ProductId;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PriceTest {

    @Test
    void givenValidProperties_whenCreatePrice_thenSuccess() {
        //given
        final var brandId = new BrandId(1L);
        final var productId = new ProductId(35455L);
        final var priceListId = new PriceListId(1L);
        final var start = LocalDateTime.now();
        final var end = start.plusDays(1);
        final var range = new DateRange(start, end);
        final var money = new Money(new BigDecimal("35.50"), "EUR");
        final var priority = 1;

        //when
        final var price = new Price(brandId, productId, priceListId, range, priority, money);

        //then
        assertAll(
                () -> assertEquals(brandId, price.getBrandId()),
                () -> assertEquals(productId, price.getProductId()),
                () -> assertEquals(priceListId, price.getPriceListId()),
                () -> assertEquals(range, price.getRange()),
                () -> assertEquals(priority, price.getPriority()),
                () -> assertEquals(money, price.getPrice())
        );
    }

    @Test
    void givenPrices_whenCompareHashCodeAndEquals_thenCompare() {
        //given
        final var now = LocalDateTime.now();
        final var range = new DateRange(now, now.plusDays(1));
        final var money = new Money(new BigDecimal("10.00"), "EUR");

        final var price1 = new Price(new BrandId(1L), new ProductId(1L), new PriceListId(1L), range, 0, money);
        final var price2 = new Price(new BrandId(1L), new ProductId(1L), new PriceListId(1L), range, 0, money);
        final var price3 = new Price(new BrandId(2L), new ProductId(1L), new PriceListId(1L), range, 0, money);

        //when /then
        assertEquals(price1, price2);
        assertEquals(price1.hashCode(), price2.hashCode());
        assertNotEquals(price1, price3);
        assertNotEquals(price1.hashCode(), price3.hashCode());
    }

    @Test
    void givenValidPrice_whenCheckIfDateIsWithinPriceRange_thenCheck() {
        //given
        final var start = LocalDateTime.of(2020, 6, 14, 0, 0);
        final var end = LocalDateTime.of(2020, 6, 14, 23, 59);
        final var range = new DateRange(start, end);
        final var price = new Price(new BrandId(1L), new ProductId(1L), new PriceListId(1L), range, 0,
                new Money(BigDecimal.TEN, "EUR"));

        //when /then
        assertTrue(price.getRange().includes(LocalDateTime.of(2020, 6, 14, 12, 0)));
        assertFalse(price.getRange().includes(LocalDateTime.of(2020, 6, 15, 0, 0)));
    }
}