Feature: the clerk cancels a course for the current term
  Scenario: clerk cancel a course with correct course number and after registration ends
    Given init system
    And clerk login
    And clerk login with correct password
    Then clerk create course
      | course1 | 101010 | 10 | n | 1 | 1 | n | n |
    Then clerk create course
      | course2 | 101011 | 10 | n | 1 | 1 | n | n |
    Then wait until registration ends
    Then clerk cancel course 101010
    Then print output

  Scenario: clerk cancel a course with incorrect course number and after registration ends
    Given init system
    And clerk login
    And clerk login with correct password
    Then clerk create course
      | course1 | 101010 | 10 | n | 1 | 1 | n | n |
    Then clerk create course
      | course2 | 101011 | 10 | n | 1 | 1 | n | n |
    Then wait until registration ends
    Then clerk cancel course 1010101010
    Then print output


  Scenario: clerk cancel a course with correct course number and after term ends
    Given init system
    And clerk login
    And clerk login with correct password
    Then clerk create course
      | course1 | 101010 | 10 | n | 1 | 1 | n | n |
    Then clerk create course
      | course2 | 101011 | 10 | n | 1 | 1 | n | n |
    Then wait until term ends
    Then clerk cancel course 101010
    Then print output


  Scenario: clerk cancel a course with correct course number and before registration starts
    Given init system
    And clerk login
    And clerk login with correct password
    Then clerk create course
      | course1 | 101010 | 10 | n | 1 | 1 | n | n |
    Then clerk create course
      | course2 | 101011 | 10 | n | 1 | 1 | n | n |
    Then clerk cancel course 101010
    Then print output


