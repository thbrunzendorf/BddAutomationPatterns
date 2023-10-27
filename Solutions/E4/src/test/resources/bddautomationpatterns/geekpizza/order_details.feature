@webapi
Feature: Order Details

  @login
  Scenario: Pizza is ordered for today by default
    Given the client has items in the basket
    When the client checks the my order page
    Then the order should indicate that the delivery date is today

  @login
  Scenario Outline: Pizza is ordered for different dates and times
    Given the client has items in the basket
    When the client specifies <date> at <time> as delivery time
    Then the order should indicate that the delivery date is <date>
    And the delivery time should be <time>

    Examples:
      | description         | date         | time  |
      | later today         | today        | 17:00 |
      | tomorrow lunch      | tomorrow     | 12:00 |
      | meeting in two days | 2 days later | 13:30 |
