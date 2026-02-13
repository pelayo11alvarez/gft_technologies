package com.ecommerce.pricing.infrastructure.persistence.mapper;

import com.ecommerce.pricing.infrastructure.persistence.entity.PriceEntity;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.instancio.Select.field;

@ExtendWith(MockitoExtension.class)
class PriceEntityMapperTest {

    @InjectMocks
    private PriceEntityMapperImpl mapper;

    @Test
    void givenValidPriceEntity_whenToDomain_thenReturnPrice() {
        //given
        final var startDate = LocalDateTime.of(2020, 6, 1, 0, 0);
        final var endDate = LocalDateTime.of(2020, 7, 14, 0, 0);
        final var entity = Instancio.of(PriceEntity.class)
                .set(field("startDate"), startDate)
                .set(field("endDate"), endDate)
                .create();

        //when
        final var domain = mapper.toDomain(entity);

        //then
        assertThat(domain).isNotNull();
        assertThat(domain.getBrandId().value()).isEqualTo(entity.getBrandId());
        assertThat(domain.getProductId().value()).isEqualTo(entity.getProductId());
        assertThat(domain.getPriceListId().value()).isEqualTo(entity.getPriceList());
        assertThat(domain.getPriority()).isEqualTo(entity.getPriority());
        assertThat(domain.getRange().start()).isEqualTo(entity.getStartDate());
        assertThat(domain.getRange().end()).isEqualTo(entity.getEndDate());
        assertThat(domain.getPrice().amount()).isEqualTo(entity.getPrice());
        assertThat(domain.getPrice().currency()).isEqualTo(entity.getCurr());
    }

    @Test
    void givenNullPriceEntity_whenToDomain_thenReturnNull() {
        //when
        final var domain = mapper.toDomain(null);

        //then
        assertThat(domain).isNull();
    }
}