Feature: Sequential running multi student registration
  Scenario: First student register course then the rest three student try to take the last spot
    Given init system
    And clerk login
    And clerk login with correct password
    And clerk create student
      | 101010101 | s1 | y |
    And clerk create student
      | 101010102 | s2 | y |
    And clerk create student
      | 101010103 | s3 | y |
    Then clerk create course
      | course1 | 101010 | 2 | n | 1 | 1 | n | n |
    Then log out
    Then student login on port {int}

