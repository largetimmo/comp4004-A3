Feature: Sequential running multi student registration Ex2

  Scenario: Create course
    Then wait ex2
    Then print "ex2 start"
    Given init system
    And clerk login
    And clerk login with correct password
    And clerk create student
      | 101010101 | s1 | y |
    And clerk create student
      | 101010102 | s2 | y |
    And clerk create student
      | 101010103 | s3 | y |
    And clerk create student
      | 101010104 | s4 | y |
    Then clerk create course
      | course1 | 101010 | 2 | n | 1 | 1 | n | n |
    Then logs out
    Then student login on "student1ip"
      | 101010101 | s1 |
    Then student login on "student2ip"
      | 101010102 | s2 |
    Then student login on "student3ip"
      | 101010103 | s3 |
    Then student login on "student4ip"
      | 101010104 | s4 |
    Then student select course 101010 on "student1ip"
    Then student select course 101010 on "student2ip"
    Then student select course 101010 on "student3ip"
    Then student select course 101010 on "student4ip"
    Then wait until registration starts
    Then student register course 101010 on "student1ip" in "EX2"
    Then student 101010101 has 1 course
    Then student register course 101010 on "student2ip" in "EX2"
    Then student 101010102 has 1 course
    Then async ready

  Scenario:  Student 2 deregister course
    Then wait until ex2 and async ready
    Then student deregister course 101010 on "student2ip" in "EX2"


  Scenario:  Student 3 register course
    Then wait until ex2 and async ready
    Then student register course 101010 on "student3ip" in "EX2"

  Scenario:  Student 4 register course
    Then wait until ex2 and async ready
    Then student register course 101010 on "student4ip" in "EX2"

