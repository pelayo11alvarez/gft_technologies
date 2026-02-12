package com.ecommerce.pricing.infrastructure.rest.mapper;

import com.ecommerce.pricing.domain.model.vo.BrandId;
import com.ecommerce.pricing.domain.model.vo.ProductId;
import com.ecommerce.pricing.domain.port.in.dto.in.GetPriceQuery;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Mapper(componentModel = "spring")
public interface QueryMapper {

    GetPriceQuery toGetPriceQuery(Long productId, Long brandId, OffsetDateTime applicationDate);

    @Named("mapBrandId")
    default BrandId mapBrandId(Long brandId) {
        return new BrandId(brandId);
    }

    @Named("mapProductId")
    default ProductId mapProductId(Long productId) {
        return new ProductId(productId);
    }

    @Named("mapLocalDateTime")
    default LocalDateTime mapLocalDateTime(OffsetDateTime offsetDateTime) {
        return offsetDateTime.toLocalDateTime();
    }
}
