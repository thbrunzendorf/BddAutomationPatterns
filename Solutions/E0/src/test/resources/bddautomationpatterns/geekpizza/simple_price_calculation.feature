Feature: Simple price calculation

  Scenario: Empty order
    Given a new order
    When the order price is calculated
    Then the subtotal should be 0
    And the delivery costs should be 0
    And the total price should be 0

  Scenario: Order with a few different pizzas
    Given a new order
    And it contains a Small "Margherita"
    And it contains a Medium "BBQ"
    When the order price is calculated
    Then the subtotal should be 25
    And the delivery costs should be 5
    And the total price should be 30
