Feature: a student deregister from a course
  Scenario: student deregister course after registration starts
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
    Then student deregister course 101010
    Then student 101010101 has 0 course
  Scenario: student deregister course after registration ends
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
    Then wait until registration ends
    Then student deregister course 101010
    Then student 101010101 has 1 course

  Scenario: student deregister course after term ends
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
    Then wait until registration ends
    Then student deregister course 101010
    Then student 101010101 has 1 course