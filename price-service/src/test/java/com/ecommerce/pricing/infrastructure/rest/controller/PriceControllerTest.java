package com.ecommerce.pricing.infrastructure.rest.controller;

import com.ecommerce.pricing.domain.port.in.GetApplicablePriceUseCase;
import com.ecommerce.pricing.domain.port.in.dto.in.GetPriceRequest;
import com.ecommerce.pricing.domain.port.in.dto.out.PriceResult;
import com.ecommerce.pricing.infrastructure.rest.api.model.PriceResponse;
import com.ecommerce.pricing.infrastructure.rest.mapper.PriceRequestMapper;
import com.ecommerce.pricing.infrastructure.rest.mapper.PriceResponseMapper;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.OffsetDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PriceController.class)
class PriceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private GetApplicablePriceUseCase getApplicablePriceUseCase;

    @MockitoBean
    private PriceRequestMapper priceRequestMapper;

    @MockitoBean
    private PriceResponseMapper priceResponseMapper;

    @Test
    void givenValidRequestParams_whenGetApplicablePrice_thenSuccess() throws Exception {
        //given
        final var productId = 35455L;
        final var brandId = 1L;
        final String dateStr = "2020-06-14T16:00:00Z";

        final var request = Instancio.create(GetPriceRequest.class);
        final var result = Instancio.create(PriceResult.class);
        final var response = Instancio.create(PriceResponse.class);
        response.setProductId(productId);
        response.setPrice(35.50);

        when(priceRequestMapper.toGetPriceRequest(eq(productId), eq(brandId), any(OffsetDateTime.class)))
                .thenReturn(request);
        when(getApplicablePriceUseCase.execute(request)).thenReturn(result);
        when(priceResponseMapper.toPriceResponse(result)).thenReturn(response);

        //when /then
        mockMvc.perform(get("/price")
                        .param("applicationDate", dateStr)
                        .param("productId", String.valueOf(productId))
                        .param("brandId", String.valueOf(brandId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value(productId))
                .andExpect(jsonPath("$.price").value(35.50));

        verify(priceRequestMapper).toGetPriceRequest(eq(productId), eq(brandId), any(OffsetDateTime.class));
        verify(getApplicablePriceUseCase).execute(request);
    }

    @Test
    void givenInvalidRequestParams_whenGetApplicablePrice_thenReturnBadRequest() throws Exception {
        //when /the
        mockMvc.perform(get("/price")
                        .param("productId", "35455")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }
}