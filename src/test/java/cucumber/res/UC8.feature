Feature: a student logs into the ATC
  Scenario: student login with correct student number and name combination
    Given init system
    And clerk login
    And clerk login with correct password
    And clerk create student
      | 101010101 | s1 | y |
    And university has student 1
    Then logs out
    Then student login
      | 101010101 | s1 |