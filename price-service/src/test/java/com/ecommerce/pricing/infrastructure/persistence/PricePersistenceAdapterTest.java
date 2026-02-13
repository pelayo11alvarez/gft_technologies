package com.ecommerce.pricing.infrastructure.persistence;

import com.ecommerce.pricing.domain.model.Price;
import com.ecommerce.pricing.domain.model.vo.BrandId;
import com.ecommerce.pricing.domain.model.vo.ProductId;
import com.ecommerce.pricing.infrastructure.persistence.entity.PriceEntity;
import com.ecommerce.pricing.infrastructure.persistence.mapper.PriceEntityMapper;
import com.ecommerce.pricing.infrastructure.persistence.repository.DataPriceRepository;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PricePersistenceAdapterTest {

    @InjectMocks
    private PricePersistenceAdapter pricePersistenceAdapter;
    @Mock
    private DataPriceRepository dataPriceRepository;
    @Mock
    private PriceEntityMapper priceEntityMapper;
    @Mock
    private BrandId brandId;
    @Mock
    private ProductId productId;

    @Test
    void givenValidBrandIdAndProductId_whenFindByBrandAndProduct_thenReturnPrices() {
        //given
        final var entities = Instancio.ofList(PriceEntity.class).size(3).create();

        when(dataPriceRepository.findByBrandIdAndProductId(brandId.value(), productId.value()))
                .thenReturn(entities);
        when(priceEntityMapper.toDomain(any(PriceEntity.class))).thenReturn(Instancio.create(Price.class));

        //when
        final var result = pricePersistenceAdapter.findByBrandAndProduct(brandId, productId);

        //then
        assertThat(result).hasSize(3);
        verify(dataPriceRepository, times(1))
                .findByBrandIdAndProductId(brandId.value(), productId.value());
        verify(priceEntityMapper, times(3)).toDomain(any(PriceEntity.class));
    }

    @Test
    void givenNotExistingBrandIdAndProductId_whenFindByBrandAndProduct_thenReturnEmptyList() {
        //given
        when(dataPriceRepository.findByBrandIdAndProductId(brandId.value(), productId.value()))
                .thenReturn(List.of());

        //when
        final var result = pricePersistenceAdapter.findByBrandAndProduct(brandId, productId);

        //then
        assertThat(result).isEmpty();
        verify(priceEntityMapper, never()).toDomain(any());
    }
}