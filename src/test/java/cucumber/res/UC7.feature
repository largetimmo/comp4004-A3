Feature: the clerk deletes a student for the current term
  Scenario: clerk deletes student with correct student number and before registration
    Given init system
    And clerk login
    And clerk login with correct password
    And clerk create student
      | 101010101 | s1 | y |
    And clerk create student
      | 101010102 | s2 | y |
    And clerk delete student 101010102
    And university has student 1

  Scenario: clerk deletes student with incorrect student number and before registration
    Given init system
    And clerk login
    And clerk login with correct password
    And clerk create student
      | 101010101 | s1 | y |
    And clerk create student
      | 101010102 | s2 | y |
    And clerk delete student 1000
    And university has student 2

  Scenario: clerk deletes student with correct student number and after registration ends

    Given init system
    And clerk login
    And clerk login with correct password
    And clerk create student
      | 101010101 | s1 | y |
    And clerk create student
      | 101010102 | s2 | y |
    Then wait until registration starts
    And clerk delete student 101010102
    And university has student 2
