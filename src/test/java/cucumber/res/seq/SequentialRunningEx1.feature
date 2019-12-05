Feature: Sequential running multi student registration Ex1
#This is the feature file
  #Since everything is running sequential, I just put the racing scenario into threads
  #This feature is going to run the Create course scenario first which init courses
  # and students
  # Expected output is one of the s2,s3,s4 are going to register the course.
  #Cucumber version 4.7.2
  Scenario: Create course
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

    #Then create three threads than whit for the asyncFlag to be true before start
  # register course
  Scenario:  Student 2 register course
    Then student register course 101010 on "student2ip" async

  Scenario:  Student 3 register course
    Then student register course 101010 on "student3ip" async

  Scenario:  Student 4 register course
    Then student register course 101010 on "student4ip" async

    #Then student 1 register course
  Scenario: Student 1 register course
    Then wait until registration starts
    Then student register course 101010 on "student1ip"
    Then student 101010101 has 1 course

    # then make asyncFlag to true so the three racing threads are going to
  #register course
  Scenario:  async ready
    Then async ready

  Scenario:  check course
    Then wait until registration ends
    Then 2 students register course 101010




