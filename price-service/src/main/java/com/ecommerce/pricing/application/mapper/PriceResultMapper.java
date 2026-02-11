package com.ecommerce.pricing.application.mapper;

import com.ecommerce.pricing.domain.model.Price;
import com.ecommerce.pricing.domain.port.in.dto.out.PriceResult;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PriceResultMapper {

    @Mapping(target = "brandId", source = "brandId.value")
    @Mapping(target = "productId", source = "productId.value")
    @Mapping(target = "priceList", source = "priceListId.value")
    @Mapping(target = "startDate", source = "range.start")
    @Mapping(target = "endDate", source = "range.end")
    @Mapping(target = "price", source = "price.amount")
    PriceResult toResult(Price price);
}
