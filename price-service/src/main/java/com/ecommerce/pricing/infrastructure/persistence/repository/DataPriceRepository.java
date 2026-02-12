package com.ecommerce.pricing.infrastructure.persistence.repository;

import com.ecommerce.pricing.infrastructure.persistence.entity.PriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DataPriceRepository extends JpaRepository<PriceEntity, Long> {
    List<PriceEntity> findByBrandIdAndProductId(Long brandId, Long productId);
}
