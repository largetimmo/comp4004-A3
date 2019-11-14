Feature: the clerk creates a student for the current term

  Scenario: clerk create a student for current term with correct information and before registration starts
    Given init system
    And clerk login
    And clerk login with correct password
    And clerk create student
      | 101010101 | s1 | y |
    And university has student 1

  Scenario: clerk create a student for current term with correct information and after registration starts
    Given init system
    And clerk login
    And clerk login with correct password
    Then wait until registration starts
    And clerk create student
      | 101010101 | s1 | y |
    And university has student 0

  Scenario: clerk create a student for current term with incorrect information and before registration starts
    Given init system
    And clerk login
    And clerk login with correct password
    And clerk create student
      | 10101010110 | s1 | y |
    And university has student 0