package com.ecommerce.pricing.infrastructure.persistence;

import com.ecommerce.pricing.domain.model.Price;
import com.ecommerce.pricing.domain.model.vo.BrandId;
import com.ecommerce.pricing.domain.model.vo.ProductId;
import com.ecommerce.pricing.domain.port.out.PriceRepository;
import com.ecommerce.pricing.infrastructure.persistence.mapper.PriceEntityMapper;
import com.ecommerce.pricing.infrastructure.persistence.repository.DataPriceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PricePersistenceAdapter implements PriceRepository {

    private final DataPriceRepository dataPriceRepository;
    private final PriceEntityMapper priceEntityMapper;

    @Override
    public List<Price> findByBrandAndProduct(BrandId brandId, ProductId productId) {
        return dataPriceRepository.findByBrandIdAndProductId(brandId.value(), productId.value())
                .stream()
                .map(priceEntityMapper::toDomain)
                .toList();
    }
}
