package com.ecommerce.pricing.domain.model;

import com.ecommerce.pricing.domain.model.vo.*;

import java.util.Objects;

public class Price {
    private final BrandId brandId;
    private final ProductId productId;
    private final PriceListId priceListId;
    private final DataRange range;
    private final Integer priority;
    private final Money price;

    public Price(BrandId brandId, ProductId productId, PriceListId priceListId,
                 DataRange range, Integer priority, Money price) {
        this.brandId = brandId;
        this.productId = productId;
        this.priceListId = priceListId;
        this.range = range;
        this.priority = priority;
        this.price = price;
    }

    public BrandId getBrandId() {
        return brandId;
    }

    public ProductId getProductId() {
        return productId;
    }

    public PriceListId getPriceListId() {
        return priceListId;
    }

    public DataRange getRange() {
        return range;
    }

    public Integer getPriority() {
        return priority;
    }

    public Money getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Price price1 = (Price) o;
        return Objects.equals(brandId, price1.brandId) &&
                Objects.equals(productId, price1.productId) &&
                Objects.equals(priceListId, price1.priceListId) &&
                Objects.equals(range, price1.range) &&
                Objects.equals(priority, price1.priority) &&
                Objects.equals(price, price1.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(brandId, productId, priceListId, range, priority, price);
    }

    @Override
    public String toString() {
        return "Price{" +
                "brandId=" + brandId +
                ", productId=" + productId +
                ", priceListId=" + priceListId +
                ", range=" + range +
                ", priority=" + priority +
                ", price=" + price +
                '}';
    }
}
