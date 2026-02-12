package com.ecommerce.pricing.domain.model.vo;

import com.ecommerce.pricing.domain.exception.DomainValidationException;

import static com.ecommerce.pricing.domain.exception.constant.ExceptionConstant.NULL_PRODUCT_ID_DESC;

public record ProductId(Long value) {
    public ProductId {
        if (value == null) {
            throw new DomainValidationException(NULL_PRODUCT_ID_DESC);
        }
    }
}
