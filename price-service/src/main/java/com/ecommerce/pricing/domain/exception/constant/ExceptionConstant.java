package com.ecommerce.pricing.domain.exception.constant;

public final class ExceptionConstant {

    private ExceptionConstant() {}

    public static final String NULL_BRAND_ID_DESC = "BrandId cannot be null";
    public static final String NULL_PRODUCT_ID_DESC = "ProductId cannot be null";
    public static final String NULL_PRICE_LIST_ID_DESC = "PriceListId cannot be null";
    public static final String NULL_DATES_DESC = "Dates cannot be null";
    public static final String INVALID_DATE_RANGE_DESC = "End date cannot be before start date";
    public static final String INVALID_AMOUNT_DESC = "Invalid amount";
    public static final String NULL_CURRENCY_DESC = "Currency cannot be null";
    public static final String PRICE_NOT_FOUND_DESC = "No price found for the given criteria";
}
