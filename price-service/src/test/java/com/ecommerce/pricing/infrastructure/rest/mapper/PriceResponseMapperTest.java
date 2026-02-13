package com.ecommerce.pricing.infrastructure.rest.mapper;

import com.ecommerce.pricing.domain.port.in.dto.out.PriceResult;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class PriceResponseMapperTest {

    @InjectMocks
    private PriceResponseMapperImpl mapper;

    @Test
    void givenValidPriceResult_whenToPriceResponse_thenReturnPriceResponse() {
        //given
        final var result = Instancio.create(PriceResult.class);

        //when
        final var response = mapper.toPriceResponse(result);

        //then
        assertThat(response).isNotNull();
        assertThat(response.getProductId()).isEqualTo(result.productId());
        assertThat(response.getBrandId()).isEqualTo(result.brandId());
        assertThat(response.getPriceList()).isEqualTo(result.priceList());
        assertThat(response.getPrice()).isEqualTo(result.price().doubleValue());
        assertThat(response.getStartDate()).isEqualTo(toExpectedOffset(result.startDate()));
        assertThat(response.getEndDate()).isEqualTo(toExpectedOffset(result.endDate()));
    }

    @Test
    void givenNullPriceResult_whenToPriceResponse_thenReturnNull() {
        //when
        final var response = mapper.toPriceResponse(null);

        //then
        assertThat(response).isNull();
    }

    /**
     * Replica la lógica del método default 'toOffsetDateTime' del mapper
     */
    private OffsetDateTime toExpectedOffset(LocalDateTime ldt) {
        return ldt.atOffset(ZoneOffset.UTC);
    }
}