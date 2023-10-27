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

Rule: Successful login authorizes for member-only services

  Scenario: Client uses member-only services after login
    Given there is a user registered with user name 'Trillian' and password '139139'
    When the client logs in with user name "Trillian" and password "139139"
    #TODO: Replace 'When' step to this: When the client logs in with the registered user credentials
    Then the client should be able to access member-only services

