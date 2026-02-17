package com.ecommerce.pricing.domain.port.in.dto.in;

import java.time.LocalDateTime;
import java.util.Objects;

import static com.ecommerce.pricing.domain.exception.constant.ExceptionConstant.NULL_GET_PRICE_REQUEST_DESC;

public record GetPriceRequest(Long brandId, Long productId, LocalDateTime date) {
    public GetPriceRequest {
        if (Objects.isNull(brandId) || Objects.isNull(productId) || Objects.isNull(date)) {
            throw new IllegalArgumentException(NULL_GET_PRICE_REQUEST_DESC);
        }
    }
}
