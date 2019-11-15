Feature: the clerk creates a course for the current term

  Scenario: clerk create course with correct course information and before registration
    Given init system
    And clerk login
    And clerk login with correct password
    Then clerk create course
      | course1 | 101010 | 10 | n | 1 | 1 | n | n |
    Then clerk create course
      | course2 | 101011 | 10 | n | 1 | 1 | n | n |
    Then university has course 2

  Scenario: clerk create course with incorrect course information and before registration
    Given init system
    And clerk login
    And clerk login with correct password
    Then clerk create course
      | course1 | 10101011 | 10 | n | 1 | 1 | n | n |
    Then university has course 0

  Scenario: clerk create course with correct course information and after registration
    Given init system
    And clerk login
    And clerk login with correct password
    Then wait until registration starts
    Then clerk create course
      | course1 | 101010 | 10 | n | 1 | 1 | n | n |
    Then university has course 0