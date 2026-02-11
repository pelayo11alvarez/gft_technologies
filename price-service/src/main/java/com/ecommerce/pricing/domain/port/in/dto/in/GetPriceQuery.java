package com.ecommerce.pricing.domain.port.in.dto.in;

import java.time.LocalDateTime;
import java.util.Objects;

public record GetPriceQuery(Long brandId, Long productId, LocalDateTime date) {
    public GetPriceQuery {
        if (Objects.isNull(brandId) || Objects.isNull(productId) || Objects.isNull(date)) {
            throw new IllegalArgumentException("Query parameters cannot be null");
        }
    }
}
