package com.ecommerce.pricing.infrastructure.rest.mapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class PriceQueryMapperTest {

    @InjectMocks
    private PriceQueryMapperImpl mapper;

    @Test
    void givenValidArguments_whenToGetPriceQuery_thenReturnGetQueryPrice() {
        //given
        final var productId = 35455L;
        final var brandId = 1L;
        final var applicationDate = OffsetDateTime.of(2020, 6, 14, 16,
                0, 0, 0, ZoneOffset.UTC);
        final var expectedDate = applicationDate.toLocalDateTime();

        //when
        final var query = mapper.toGetPriceQuery(productId, brandId, applicationDate);

        //then
        assertThat(query).isNotNull();
        assertThat(query.productId()).isEqualTo(productId);
        assertThat(query.brandId()).isEqualTo(brandId);
        assertThat(query.date()).isEqualTo(expectedDate);
    }

    @Test
    void givenNullArguments_whenToGetPriceQuery_thenReturnGetQueryPrice() {
        //when
        final var query = mapper.toGetPriceQuery(null, null, null);

        //then
        assertThat(query).isNull();
    }
}