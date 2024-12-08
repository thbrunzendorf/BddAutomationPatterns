@webapi
Feature: My Order

  # This is an alternative to the @login tag
  # Background:
  #  Given the client is logged in

Rule: The pizzas the client has put into the basket are listed on the My Order page

  Scenario: Customer has a few different pizzas in the basket
    Given the client is logged in
    And the client has the following items in the basket
      | name       | size   |
      | Margherita | Small  |
      | BBQ        | Medium |
    When the client checks the my order page
    Then the ordered items should be listed on the my order page

  Scenario: Customer has a few different pizzas in the basket - ensure version
      Note: the login is performed implicitly by the step 'Given the client has the following items in the basket - ensure version'
      The default ensure behavior can be overridden by explicitly fulfilling the prerequisite,
      e.g. by the commented Given step
    #Given the client is logged in with user name "Ford" and password "1423"
    Given the client has the following items in the basket - ensure version
      | name       | size   |
      | Margherita | Small  |
      | BBQ        | Medium |
    When the client checks the my order page
    Then the ordered items should be listed on the my order page

Rule: Additional pizzas can be added to the basket

  @login
  Scenario: Customer added a pizza to the basket
    Given the client has the following items in the basket
      | name       | size   |
      | BBQ        | Medium |
    When the client adds a Small Margherita pizza to the basket
    Then the following items should be listed on the my order page
      | name       | size   |
      | BBQ        | Medium |
      | Margherita | Small  |

Rule: The details of the ordered pizzas can be changed on the My Order page

  @login
  Scenario: Customer changed the size of the pizza
    Given the client has the following items in the basket
      | name       | size   |
      | BBQ        | Medium |
    And the client has added a Small Margherita pizza to the basket
    When the client changes its size to Large
    Then the following items should be listed on the my order page
      | name       | size   |
      | BBQ        | Medium |
      | Margherita | Large  |

Rule: The order can be placed

    @login
    Scenario: Customer places an order
      When the client attempts to place the order
      Then the order placement should be successful
