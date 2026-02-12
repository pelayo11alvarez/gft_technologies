package com.ecommerce.pricing.infrastructure.rest.mapper;

import com.ecommerce.pricing.domain.port.in.dto.out.PriceResult;
import com.ecommerce.pricing.infrastructure.rest.api.model.PriceResponse;
import org.mapstruct.Mapper;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Mapper(componentModel = "spring")
public interface PriceResponseMapper {

    PriceResponse toPriceResponse(PriceResult priceResult);

    default OffsetDateTime toOffsetDateTime(LocalDateTime localDateTime) {
        return localDateTime.atOffset(ZoneOffset.UTC);
    }
}
