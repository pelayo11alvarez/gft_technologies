package com.ecommerce.pricing.domain.port.in.dto.out;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PriceResult(
        Long productId,
        Long brandId,
        Long priceList,
        LocalDateTime startDate,
        LocalDateTime endDate,
        BigDecimal price
) {}
