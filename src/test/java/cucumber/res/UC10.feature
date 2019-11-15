Feature: a student selects and registers in one a course of the current term
  Scenario: Student selects courser with correct course number and register course with correct course number after registration start
    Given init system
    And clerk login
    And clerk login with correct password
    And clerk create student
      | 101010101 | s1 | y |
    Then clerk create course
      | course1 | 101010 | 10 | n | 1 | 1 | n | n |
    Then clerk create course
      | course2 | 101011 | 10 | n | 1 | 1 | n | n |
    Then logs out
    Then student login
      | 101010101 | s1 |
    Then student select course 101010
    Then wait until registration starts
    Then student register course 101010
    Then student 101010101 has 1 course

  Scenario: Student selects courser with correct course number and register full course with correct course number after registration start
    Given init system
    And clerk login
    And clerk login with correct password
    And clerk create student
      | 101010101 | s1 | y |
    And clerk create student
      | 101010102 | s2 | y |
    Then clerk create course
      | course1 | 101010 | 1 | n | 1 | 1 | n | n |
    Then clerk create course
      | course2 | 101011 | 10 | n | 1 | 1 | n | n |
    Then logs out
    Then student login
      | 101010101 | s1 |
    Then student select course 101010
    Then wait until registration starts
    Then student register course 101010
    Then student 101010101 has 1 course
    Then logs out
    Then student login
      | 101010102 | s2 |
    Then student select course 101010

    Then student register course 101010
    Then student 101010102 has 0 course

  Scenario: Student selects courser with incorrect course number
    Given init system
    And clerk login
    And clerk login with correct password
    And clerk create student
      | 101010101 | s1 | y |
    Then clerk create course
      | course1 | 101010 | 10 | n | 1 | 1 | n | n |
    Then clerk create course
      | course2 | 101011 | 10 | n | 1 | 1 | n | n |
    Then logs out
    Then student login
      | 101010101 | s1 |
    Then student select course 101012

  Scenario: Student selects courser with correct course number and register course with incorrect course number after registration start
    Given init system
    And clerk login
    And clerk login with correct password
    And clerk create student
      | 101010101 | s1 | y |
    Then clerk create course
      | course1 | 101010 | 10 | n | 1 | 1 | n | n |
    Then clerk create course
      | course2 | 101011 | 10 | n | 1 | 1 | n | n |
    Then logs out
    Then student login
      | 101010101 | s1 |
    Then student select course 101010
    Then wait until registration starts
    Then student register course 101011
    Then student 101010101 has 0 course
  Scenario: Student selects courser with correct course number and register course with correct course number before registration start
    Given init system
    And clerk login
    And clerk login with correct password
    And clerk create student
      | 101010101 | s1 | y |
    Then clerk create course
      | course1 | 101010 | 10 | n | 1 | 1 | n | n |
    Then clerk create course
      | course2 | 101011 | 10 | n | 1 | 1 | n | n |
    Then logs out
    Then student login
      | 101010101 | s1 |
    Then student select course 101010
    Then student register course 101010
    Then student 101010101 has 0 course
  Scenario: Student selects courser with correct course number and register course with correct course number after registration ends
    Given init system
    And clerk login
    And clerk login with correct password
    And clerk create student
      | 101010101 | s1 | y |
    Then clerk create course
      | course1 | 101010 | 10 | n | 1 | 1 | n | n |
    Then clerk create course
      | course2 | 101011 | 10 | n | 1 | 1 | n | n |
    Then logs out
    Then student login
      | 101010101 | s1 |
    Then student select course 101010
    Then wait until registration ends
    Then student register course 101010
    Then student 101010101 has 0 course