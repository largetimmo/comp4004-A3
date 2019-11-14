Feature: a student logs out of the ATC
  Scenario: student logs out from ATC
    Given init system
    And clerk login
    And clerk login with correct password
    And clerk create student
      | 101010101 | s1 | y |
    And university has student 1
    Then logs out
    Then student login
      | 101010101 | s1 |
    Then logs out