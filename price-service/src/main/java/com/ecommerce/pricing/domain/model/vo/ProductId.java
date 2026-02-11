package com.ecommerce.pricing.domain.model.vo;

public record ProductId(Long value) {
    public ProductId {
        if (value == null) {
            throw new IllegalArgumentException("ProductId cannot be null");
        }
    }
}
