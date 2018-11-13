Feature: As a user can register

  Scenario: user can register with valid username and password
    Given register is selected
    When  a valid username and password is given
    Then  user is shown the welcome page
