package com.ecommerce.pricing.application.usecase;

import com.ecommerce.pricing.application.mapper.PriceResultMapper;
import com.ecommerce.pricing.domain.model.Price;
import com.ecommerce.pricing.domain.model.vo.BrandId;
import com.ecommerce.pricing.domain.model.vo.ProductId;
import com.ecommerce.pricing.domain.port.in.GetApplicablePriceUseCase;
import com.ecommerce.pricing.domain.port.in.dto.in.GetPriceQuery;
import com.ecommerce.pricing.domain.port.in.dto.out.PriceResult;
import com.ecommerce.pricing.domain.port.out.PriceRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.NoSuchElementException;

@Service
public class GetApplicablePriceUseCaseImpl implements GetApplicablePriceUseCase {
    private final PriceRepository repository;
    private final PriceResultMapper mapper;

    public GetApplicablePriceUseCaseImpl(PriceRepository repository, PriceResultMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public PriceResult execute(GetPriceQuery query) {
        final var brandId = new BrandId(query.brandId());
        final var productId = new ProductId(query.productId());

        final var winningPrice = repository.findByBrandAndProduct(brandId, productId)
                .stream()
                .filter(price -> price.getRange().includes(query.date()))
                .max(Comparator.comparing(Price::getPriority))
                .orElseThrow(() -> new NoSuchElementException("No price found for the given criteria"));

        return mapper.toResult(winningPrice);
    }
}
