package com.ecommerce.pricing.application.usecase;

import com.ecommerce.pricing.application.mapper.PriceResultMapper;
import com.ecommerce.pricing.domain.exception.PriceNotFoundException;
import com.ecommerce.pricing.domain.model.Price;
import com.ecommerce.pricing.domain.model.vo.BrandId;
import com.ecommerce.pricing.domain.model.vo.DateRange;
import com.ecommerce.pricing.domain.model.vo.ProductId;
import com.ecommerce.pricing.domain.port.in.dto.in.GetPriceQuery;
import com.ecommerce.pricing.domain.port.in.dto.out.PriceResult;
import com.ecommerce.pricing.domain.port.out.PriceRepository;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static com.ecommerce.pricing.domain.exception.constant.ExceptionConstant.PRICE_NOT_FOUND_DESC;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.instancio.Select.field;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetApplicablePriceUseCaseImplTest {

    @InjectMocks
    private GetApplicablePriceUseCaseImpl getApplicablePriceUseCase;
    @Mock
    private PriceRepository priceRepository;
    @Mock
    private PriceResultMapper priceResultMapper;

    @Test
    void shouldReturnHighestPriorityPrice() {
        //given
        final var dateRange = dateRangeProvider();
        final var query = getPriceQueryProvider();
        final var lowPriorityPrice = Instancio.of(Price.class)
                .set(field("priority"), 0)
                .set(field("range"), dateRange)
                .create();
        final var highPriorityPrice = Instancio.of(Price.class)
                .set(field("priority"), 1)
                .set(field("range"), dateRange)
                .create();
        final var expectedResult = Instancio.create(PriceResult.class);

        ArgumentCaptor<Price> captor = ArgumentCaptor.forClass(Price.class);

        when(priceRepository.findByBrandAndProduct(any(BrandId.class), any(ProductId.class)))
                .thenReturn(List.of(lowPriorityPrice, highPriorityPrice));
        when(priceResultMapper.toResult(captor.capture())).thenReturn(expectedResult);

        //when
        final var result = getApplicablePriceUseCase.execute(query);

        //then
        assertThat(captor.getValue()).isEqualTo(highPriorityPrice);
        assertThat(result).isEqualTo(expectedResult);
        verify(priceRepository).findByBrandAndProduct(any(), any());
        verify(priceResultMapper).toResult(highPriorityPrice);
    }

    private static DateRange dateRangeProvider() {
        final var startDate = LocalDateTime.of(2020, 6, 1, 0, 0);
        final var endDate = LocalDateTime.of(2020, 7, 14, 0, 0);
        return new DateRange(startDate, endDate);
    }

    private static GetPriceQuery getPriceQueryProvider() {
        final var targetDate = LocalDateTime.of(2020, 6, 14, 16, 0);
        return new GetPriceQuery(1L, 35455L, targetDate);
    }

    @Test
    void shouldThrowExceptionWhenNoPriceIsApplicable() {
        //given
        final var query = Instancio.create(GetPriceQuery.class);

        when(priceRepository.findByBrandAndProduct(any(), any())).thenReturn(List.of());

        //when /then
        assertThatThrownBy(() -> getApplicablePriceUseCase.execute(query))
                .isInstanceOf(PriceNotFoundException.class)
                .hasMessage(PRICE_NOT_FOUND_DESC);

        verify(priceResultMapper, never()).toResult(any());
    }
}