Feature: Applicable price consultation
  As an e-commerce system
  I want to consult the final price of a product for a brand on a specific date
  So that I can apply the correct tariff to the customer at checkout

  Scenario Outline: Consult price with parameters that do not match any active tariff
    Given the system has the initial price tariffs loaded
    When I request the price for product <product_id>, brand <brand_id>, and date "<application_date>"
    Then the system responds with status code 404
    And the response contains a not found error

    Examples:
      | scenario                  | product_id | brand_id | application_date     |
      | Non-existent product      | 99999      | 1        | 2020-06-14T10:00:00Z |
      | Non-existent brand        | 35455      | 99       | 2020-06-14T10:00:00Z |
      | Date before minimum range | 35455      | 1        | 2019-01-01T10:00:00Z |
      | Date after maximum range  | 35455      | 1        | 2025-01-01T10:00:00Z |

  Scenario Outline: Consult the price of a product on different dates
    Given the system has the initial price tariffs loaded
    When I request the price for product <product_id>, brand <brand_id>, and date "<application_date>"
    Then the system responds with status code 200
    And the applied price is <expected_price> EUR
    And the applied tariff identifier is <expected_tariff>

    Examples:
      | product_id | brand_id | application_date     | expected_price | expected_tariff |
      | 35455      | 1        | 2020-06-14T10:00:00Z | 35.50          | 1               |
      | 35455      | 1        | 2020-06-14T16:00:00Z | 25.45          | 2               |
      | 35455      | 1        | 2020-06-14T21:00:00Z | 35.50          | 1               |
      | 35455      | 1        | 2020-06-15T10:00:00Z | 30.50          | 3               |
      | 35455      | 1        | 2020-06-16T21:00:00Z | 38.95          | 4               |