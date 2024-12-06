@webapi
Feature: Order Details

  @login @reset
  Scenario: Pizza is ordered for today by default
    Given the client has items in the basket
    When the client checks the my order page
    Then the order should indicate that the delivery date is today

  @login @reset
  Scenario Outline: Pizza is ordered for different dates and times
    Given the client has items in the basket
    When the client specifies <date> at <time> as delivery time
    Then the order should indicate that the delivery date is <date>
    # Exercise: Write the following step
    #And the delivery time should be <time>

    Examples:
      | description         | date         | time  |
      | later today         | today        | 17:11 |
      | tomorrow lunch      | tomorrow     | 12:20 |
      | meeting in two days | 2 days later | 13:30 |
