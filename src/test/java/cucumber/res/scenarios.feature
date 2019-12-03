Feature: Parallel running multi student registration Ex1

  Scenario Outline: init
    Then print "<print_out>"
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
    Then student register course 101010 on "student1ip"
    Then student 101010101 has 1 course
    Then async ready
    Examples:
      | print_out |
      | I'm ready |

  Scenario Outline: async_register_course
    Then print "<print_out>"
    Then wait until async ready
    Then student register course 101010 on "<ipaddr>"
    Examples:
      | print_out | ipaddr     |
      | 2 start   | student2ip |
      | 3 start   | student3ip |
      | 4 start   | student4ip |