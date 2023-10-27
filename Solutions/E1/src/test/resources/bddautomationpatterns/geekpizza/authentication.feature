@webapi
Feature: Authentication

Rule: Can login with valid credentials

  Scenario: Client logs in with valid credentials
    Given there is a user registered with user name 'Trillian' and password '139139'
    When the client attempts to log in with user name "Trillian" and password "139139"
    Then the login attempt should be successful

Rule: Cannot login with invalid credentials

  Scenario: Client logs in with invalid password
    Given there is a user registered with user name 'Trillian' and password '139139'
    When the client attempts to log in with user name "Trillian" and password "wrong"
    Then the login attempt should fail with "Invalid user name or password"
