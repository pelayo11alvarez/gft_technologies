package com.ecommerce.pricing.infrastructure.persistence.mapper;

import com.ecommerce.pricing.domain.model.Price;
import com.ecommerce.pricing.domain.model.vo.*;
import com.ecommerce.pricing.infrastructure.persistence.entity.PriceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface PriceEntityMapper {

    @Mapping(target = "brandId", source = "brandId")
    @Mapping(target = "productId", source = "productId")
    @Mapping(target = "priceListId", source = "priceList")
    @Mapping(target = "priority", source = "priority")
    @Mapping(target = "range", expression = "java(mapDateRange(entity))")
    @Mapping(target = "price", expression = "java(mapMoney(entity))")
    Price toDomain(PriceEntity entity);

    default BrandId mapBrandId(Long brandId) {
        return new BrandId(brandId);
    }

    default ProductId mapProductId(Long productId) {
        return new ProductId(productId);
    }

    default PriceListId mapPriceListId(Long priceList) {
        return new PriceListId(priceList);
    }

    default DateRange mapDateRange(PriceEntity entity) {
        return new DateRange(entity.getStartDate(), entity.getEndDate());
    }

    default Money mapMoney(PriceEntity entity) {
        return new Money(entity.getPrice(), entity.getCurr());
    }
}
