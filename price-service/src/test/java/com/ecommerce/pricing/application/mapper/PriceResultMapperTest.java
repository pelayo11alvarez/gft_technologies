package com.ecommerce.pricing.application.mapper;

import com.ecommerce.pricing.domain.model.Price;
import com.ecommerce.pricing.domain.model.vo.DateRange;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.instancio.Select.field;

@ExtendWith(MockitoExtension.class)
class PriceResultMapperTest {

    @InjectMocks
    private PriceResultMapperImpl mapper;

    @Test
    void givenValidPrice_whenToResult_thenReturnPriceResult() {
        //given
        final var dateRange = dateRangeProvider();
        final var domainPrice = Instancio.of(Price.class)
                .set(field("range"), dateRange)
                .create();

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

    private static DateRange dateRangeProvider() {
        final var startDate = LocalDateTime.of(2020, 6, 1, 0, 0);
        final var endDate = LocalDateTime.of(2020, 7, 14, 0, 0);
        return new DateRange(startDate, endDate);
    }

    @Test
    void givenNullPrice_whenToResult_thenReturnNull() {
        //when
        final var result = mapper.toResult(null);

        //then
        assertThat(result).isNull();
    }
}