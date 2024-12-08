Feature: Table price calculation

  Scenario: Order with a few larger pizzas
    Given an order with the following items
      | name       | size   |
      | Margherita | Large  |
      | BBQ        | Medium |
    When the order price is calculated
    And the subtotal should be 40
    And the delivery costs should be 5
    And the total price should be 45

  Scenario: Order with extra cheese
    Given an order with the following items
      | name       | size   | extraCheese |
      | Margherita | Large  | true        |
      | BBQ        | Medium | false       |
    When the order price is calculated
    And the subtotal should be 41
    And the delivery costs should be 0
    And the total price should be 41
