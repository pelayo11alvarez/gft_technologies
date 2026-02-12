package com.ecommerce.pricing.domain.model.vo;

import com.ecommerce.pricing.domain.exception.DomainValidationException;

import static com.ecommerce.pricing.domain.exception.constant.ExceptionConstant.NULL_BRAND_ID_DESC;

public record BrandId(Long value) {
    public BrandId {
        if (value == null) {
            throw new DomainValidationException(NULL_BRAND_ID_DESC);
        }
    }
}
