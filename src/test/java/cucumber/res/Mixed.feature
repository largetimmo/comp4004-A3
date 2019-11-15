Feature: Mixed testing
  Scenario: student deregister and register the same course
    Given init system
    And clerk login
    And clerk login with correct password
    And clerk create student
      | 101010101 | s1 | y |
    Then clerk create course
      | course1 | 101010 | 10 | n | 1 | 1 | n | n |
    Then clerk create course
      | course2 | 101011 | 10 | n | 1 | 1 | n | n |
    Then clerk create course
      | course2 | 101012 | 10 | n | 1 | 1 | n | n |
    Then clerk create course
      | course2 | 101013 | 10 | n | 1 | 1 | n | n |
    Then clerk create course
      | course2 | 101014 | 10 | n | 1 | 1 | n | n |
    Then clerk create course
      | course2 | 101015 | 10 | n | 1 | 1 | n | n |
    Then logs out
    Then student login
      | 101010101 | s1 |
    Then student select course 101010
    Then student select course 101011
    Then student select course 101012
    Then wait until registration starts
    Then student register course 101010
    Then student 101010101 has 1 course
    Then student deregister course 101010
    Then student 101010101 has 0 course
    Then student select course 101010
    Then student register course 101010
    Then student register course 101011
    Then student register course 101012
    Then student 101010101 has 3 course

    Scenario: Student register a course but the course got cancelled
    cenario: student deregister and register the same course
      Given init system
      And clerk login
      And clerk login with correct password
      And clerk create student
        | 101010101 | s1 | y |
      Then clerk create course
        | course1 | 101010 | 10 | n | 1 | 1 | n | n |
      Then clerk create course
        | course2 | 101011 | 10 | n | 1 | 1 | n | n |
      Then clerk create course
        | course2 | 101012 | 10 | n | 1 | 1 | n | n |
      Then clerk create course
        | course2 | 101013 | 10 | n | 1 | 1 | n | n |
      Then clerk create course
        | course2 | 101014 | 10 | n | 1 | 1 | n | n |
      Then clerk create course
        | course2 | 101015 | 10 | n | 1 | 1 | n | n |
      Then logs out
      Then student login
        | 101010101 | s1 |
      Then student select course 101010
      Then wait until registration starts
      Then student register course 101010
      Then student 101010101 has 1 course
      Then logs out
      And clerk login
      And clerk login with correct password
      Then wait until registration ends
      Then clerk cancel course 101010
      Then student 101010101 has 0 course
