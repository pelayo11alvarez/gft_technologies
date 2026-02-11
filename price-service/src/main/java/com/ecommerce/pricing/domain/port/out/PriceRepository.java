package com.ecommerce.pricing.domain.port.out;

import com.ecommerce.pricing.domain.model.Price;
import com.ecommerce.pricing.domain.model.vo.BrandId;
import com.ecommerce.pricing.domain.model.vo.ProductId;

import java.util.List;

public interface PriceRepository {
    List<Price> findByBrandAndProduct(BrandId brandId, ProductId productId);
}
