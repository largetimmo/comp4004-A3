Feature: the clerk logs into the ATC
  Scenario: clerk logs into ATC with correct password
    Given init system
    And clerk login
    And clerk login with correct password
    Then clerk enters system

    Scenario:  clerk logs into ATC with incorrect password
      Given init system
      And clerk login
      And clerk login with incorrect password
      Then clerk does not enters system