Feature: As a logged in user can log out

  Scenario: user can log out after registering
    Given user has just registered and is on main page
    When  logout is clicked
    Then  user is logged out