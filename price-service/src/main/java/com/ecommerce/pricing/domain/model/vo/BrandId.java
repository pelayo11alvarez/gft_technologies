package com.ecommerce.pricing.domain.model.vo;

public record BrandId(Long value) {
    public BrandId {
        if (value == null) {
            throw new IllegalArgumentException("BrandId cannot be null");
        }
    }
}
