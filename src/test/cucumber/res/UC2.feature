Feature: the clerk logs out of the ATC
  Scenario: clerk logs out
    Given init system
    And clerk login
    And clerk login with correct password
    Then clerk enters system
    Then logs out
