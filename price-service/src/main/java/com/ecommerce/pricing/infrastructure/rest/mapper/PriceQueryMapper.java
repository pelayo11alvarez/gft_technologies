package com.ecommerce.pricing.infrastructure.rest.mapper;

import com.ecommerce.pricing.domain.port.in.dto.in.GetPriceQuery;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Objects;

@Mapper(componentModel = "spring")
public interface PriceQueryMapper {

    @Mapping(target = "date", source = "applicationDate")
    GetPriceQuery toGetPriceQuery(Long productId, Long brandId, OffsetDateTime applicationDate);

    default LocalDateTime toLocalDateTime(OffsetDateTime offsetDateTime) {
        return Objects.nonNull(offsetDateTime) ? offsetDateTime.toLocalDateTime() : null;
    }
}
