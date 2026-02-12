package com.ecommerce.pricing.domain.exception;

public class PriceNotFoundException extends RuntimeException {

    public PriceNotFoundException(String description) {
        super(description);
    }
}
