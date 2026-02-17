package com.ecommerce.pricing.domain.port.in;

import com.ecommerce.pricing.domain.port.in.dto.in.GetPriceRequest;
import com.ecommerce.pricing.domain.port.in.dto.out.PriceResult;

public interface GetApplicablePriceUseCase {
    PriceResult execute(GetPriceRequest request);
}
