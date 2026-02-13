package com.ecommerce.pricing.application.usecase;

import com.ecommerce.pricing.application.mapper.PriceResultMapper;
import com.ecommerce.pricing.domain.exception.PriceNotFoundException;
import com.ecommerce.pricing.domain.model.Price;
import com.ecommerce.pricing.domain.model.vo.BrandId;
import com.ecommerce.pricing.domain.model.vo.ProductId;
import com.ecommerce.pricing.domain.port.in.GetApplicablePriceUseCase;
import com.ecommerce.pricing.domain.port.in.dto.in.GetPriceQuery;
import com.ecommerce.pricing.domain.port.in.dto.out.PriceResult;
import com.ecommerce.pricing.domain.port.out.PriceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;

import static com.ecommerce.pricing.domain.exception.constant.ExceptionConstant.PRICE_NOT_FOUND_DESC;

@Service
@RequiredArgsConstructor
public class GetApplicablePriceUseCaseImpl implements GetApplicablePriceUseCase {

    private final PriceRepository priceRepository;
    private final PriceResultMapper priceResultMapper;

    @Override
    public PriceResult execute(GetPriceQuery query) {
        final var brandId = new BrandId(query.brandId());
        final var productId = new ProductId(query.productId());

        final var winningPrice = priceRepository.findByBrandAndProduct(brandId, productId)
                .stream()
                .filter(price -> price.getRange().includes(query.date()))
                .max(Comparator.comparing(Price::getPriority))
                .orElseThrow(() -> new PriceNotFoundException(PRICE_NOT_FOUND_DESC));

        return priceResultMapper.toResult(winningPrice);
    }
}
