package com.ecommerce.pricing.infrastructure.rest.mapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class PriceRequestMapperTest {

    @InjectMocks
    private PriceRequestMapperImpl mapper;

    @Test
    void givenValidArguments_whenToGetPriceQuery_thenReturnGetQueryPrice() {
        //given
        final var productId = 35455L;
        final var brandId = 1L;
        final var applicationDate = OffsetDateTime.of(2020, 6, 14, 16,
                0, 0, 0, ZoneOffset.UTC);
        final var expectedDate = applicationDate.toLocalDateTime();

        //when
        final var request = mapper.toGetPriceRequest(productId, brandId, applicationDate);

        //then
        assertThat(request).isNotNull();
        assertThat(request.productId()).isEqualTo(productId);
        assertThat(request.brandId()).isEqualTo(brandId);
        assertThat(request.date()).isEqualTo(expectedDate);
    }

    @Test
    void givenNullArguments_whenToGetPriceQuery_thenReturnGetQueryPrice() {
        //when
        final var request = mapper.toGetPriceRequest(null, null, null);

        //then
        assertThat(request).isNull();
    }
}