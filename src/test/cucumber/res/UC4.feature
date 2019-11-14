Feature: the clerk deletes a course for the current term
  Scenario: clerk delete a course with correct course number and before registration
    Given init system
    And clerk login
    And clerk login with correct password
    Then clerk create course
      | course1 | 101010 | 10 | n | 1 | 1 | n | n |
    Then clerk create course
      | course2 | 101011 | 10 | n | 1 | 1 | n | n |
    Then clerk delete course 101010
    Then university has course 1
  Scenario: clerk delete a course with incorrect course number and before registration
    Given init system
    And clerk login
    And clerk login with correct password
    Then clerk create course
      | course1 | 101010 | 10 | n | 1 | 1 | n | n |
    Then clerk create course
      | course2 | 101011 | 10 | n | 1 | 1 | n | n |
    Then clerk delete course 10101010
    Then university has course 2
  Scenario: clerk delete a course with correct course number and after registration
    Given init system
    And clerk login
    And clerk login with correct password
    Then clerk create course
      | course1 | 101010 | 10 | n | 1 | 1 | n | n |
    Then clerk create course
      | course2 | 101011 | 10 | n | 1 | 1 | n | n |
    Then wait until registration starts
    Then clerk delete course 101010
    Then university has course 2