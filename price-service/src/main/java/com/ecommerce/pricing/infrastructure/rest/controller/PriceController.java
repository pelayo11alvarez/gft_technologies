package com.ecommerce.pricing.infrastructure.rest.controller;

import com.ecommerce.pricing.domain.port.in.GetApplicablePriceUseCase;
import com.ecommerce.pricing.infrastructure.rest.api.PriceApi;
import com.ecommerce.pricing.infrastructure.rest.api.model.PriceResponse;
import com.ecommerce.pricing.infrastructure.rest.mapper.PriceQueryMapper;
import com.ecommerce.pricing.infrastructure.rest.mapper.PriceResponseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;

@RestController
@RequiredArgsConstructor
public class PriceController implements PriceApi {

    private final GetApplicablePriceUseCase getApplicablePriceUseCase;
    private final PriceQueryMapper priceQueryMapper;
    private final PriceResponseMapper priceResponseMapper;

    @Override
    public ResponseEntity<PriceResponse> getApplicablePrice(OffsetDateTime applicationDate, Long productId, Long brandId) {
        final var getPriceQuery = priceQueryMapper.toGetPriceQuery(productId, brandId, applicationDate);
        final var priceResult = getApplicablePriceUseCase.execute(getPriceQuery);
        final var priceResponse = priceResponseMapper.toPriceResponse(priceResult);
        return ResponseEntity.ok(priceResponse);
    }
}
