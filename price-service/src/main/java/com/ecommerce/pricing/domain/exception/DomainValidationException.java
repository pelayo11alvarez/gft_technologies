package com.ecommerce.pricing.domain.exception;

public class DomainValidationException extends RuntimeException {

    public DomainValidationException(String description) {
        super(description);
    }
}
