package com.ecommerce.pricing.acceptance.steps;

import com.ecommerce.pricing.infrastructure.rest.api.model.PriceResponse;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import static org.assertj.core.api.Assertions.assertThat;

public class PriceStep {

    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;
    private ResponseEntity<PriceResponse> response;

    @Given("the system has the initial price tariffs loaded")
    public void the_system_has_the_initial_price_tariffs_loaded() {
    }

    @When("I request the price for product {int}, brand {int}, and date {string}")
    public void i_request_the_price_for_product_brand_and_date(Integer productId, Integer brandId, String date) {
        String url = UriComponentsBuilder.fromHttpUrl("http://localhost:" + port + "/price")
                .queryParam("productId", productId)
                .queryParam("brandId", brandId)
                .queryParam("applicationDate", date)
                .toUriString();
        response = restTemplate.getForEntity(url, PriceResponse.class);
    }

    @Then("the system responds with status code {int}")
    public void the_system_responds_with_status_code(Integer expectedStatusCode) {
        assertThat(response.getStatusCode().value()).isEqualTo(expectedStatusCode);
    }

    @And("the applied price is {double} EUR")
    public void the_applied_price_is(Double expectedPrice) {
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getPrice()).isEqualTo(expectedPrice);
    }

    @And("the applied tariff identifier is {int}")
    public void the_applied_tariff_identifier_is(Integer expectedTariff) {
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getPriceList()).isEqualTo(expectedTariff.longValue());
    }

    @And("the response contains a not found error")
    public void the_response_contains_a_not_found_error() {
        assertThat(response).isNotNull();
    }
}
