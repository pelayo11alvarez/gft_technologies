package com.ecommerce.pricing.domain.model.vo;

public record PriceListId(Long value) {
    public PriceListId {
        if (value == null) {
            throw new IllegalArgumentException("PriceListId cannot be null");
        }
    }
}
