package com.ecommerce.pricing.application.mapper;

import com.ecommerce.pricing.domain.model.Price;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class PriceResultMapperTest {

    @InjectMocks
    private PriceResultMapperImpl mapper;

    @Test
    void givenValidPrice_whenToResult_thenReturnPriceResult() {
        //given
        final var domainPrice = Instancio.create(Price.class);

        //when
        final var result = mapper.toResult(domainPrice);

        //then
        assertThat(result).isNotNull();
        assertThat(result.brandId()).isEqualTo(domainPrice.getBrandId().value());
        assertThat(result.productId()).isEqualTo(domainPrice.getProductId().value());
        assertThat(result.priceList()).isEqualTo(domainPrice.getPriceListId().value());
        assertThat(result.startDate()).isEqualTo(domainPrice.getRange().start());
        assertThat(result.endDate()).isEqualTo(domainPrice.getRange().end());
        assertThat(result.price()).isEqualTo(domainPrice.getPrice().amount());
    }

    @Test
    void givenNullPrice_whenToResult_thenReturnNull() {
        //when
        final var result = mapper.toResult(null);

        //then
        assertThat(result).isNull();
    }
}