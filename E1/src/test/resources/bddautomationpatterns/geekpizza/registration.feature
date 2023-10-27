@webapi
Feature: Registration

  Describes the behavior of the registration process.

  There are many rules involved (e.g. related to user name and password validity
  or password and re-entered password match). For the sake of the workshop we only
  work with a single rule: Should be able to register with user name and password.

Rule: Should be able to register with user name and password

  Scenario: Customer registers successfully
    When the client attempts to register with user name "Trillian" and password "139139"
    Then the registration should be successful

Rule: The user name is mandatory

    Scenario: User name was not provided
      When the client attempts to register with
        | user name |
        |           |
      Then the registration should fail with "Name must be provided"


Rule: The password should be valid and verified

    Scenario Outline: The password should be valid and verified
      When the client attempts to register with
        | password   | password re-enter   |
        | <password> | <password re-enter> |
      Then the registration should fail with "<error message>"
      Examples:
        | description                          | password | password re-enter | error message                                   |
        | Password was not provided            |          |                   | Password and password re-enter must be provided |
        | The re-entered password is different | 139139   | different         | Re-entered password is different                |
        | Password is too short                | 123      | 123               | Password must be at least 4 characters long     |
