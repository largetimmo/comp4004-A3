Feature: a student drops a course
  Scenario: student drops before registration ends
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
    Then student drop course 101010
    Then student 101010101 has 1 course
  Scenario: student drops after registration ends
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
    Then student drop course 101010
    Then print output
    Then student 101010101 has 0 course
  Scenario: student drops after term ends
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
    Then wait until term ends
    Then student drop course 101010
    Then student 101010101 has 1 course