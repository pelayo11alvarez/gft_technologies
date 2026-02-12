package com.ecommerce.pricing.domain.model.vo;

import com.ecommerce.pricing.domain.exception.DomainValidationException;

import static com.ecommerce.pricing.domain.exception.constant.ExceptionConstant.NULL_PRICE_LIST_ID_DESC;

public record PriceListId(Long value) {
    public PriceListId {
        if (value == null) {
            throw new DomainValidationException(NULL_PRICE_LIST_ID_DESC);
        }
    }
}
