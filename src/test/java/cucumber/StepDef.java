package test.java.cucumber;

import io.cucumber.datatable.DataTable;
import io.cucumber.java8.En;
import main.server.logic.handler.InputHandler;
import main.server.logic.handler.model.ServerOutput;
import main.server.logic.model.University;
import main.utilities.Config;
import org.junit.Assert;

public class StepDef implements En {

    InputHandler inputHandler = new InputHandler();
    ServerOutput serverOutput = new ServerOutput("", 0);
    int currentState;
    String output;

    public StepDef(){
        Given("init system",()->{
            University.getInstance().Reset();
            serverOutput = inputHandler.processInput("", InputHandler.WAITING,9999);
            currentState = serverOutput.getState();
            output = serverOutput.getOutput();
        });
        Then("registration does not start",()->{
            Assert.assertFalse(Config.REGISTRATION_STARTS);
        });
        And("clerk login",()->{
            serverOutput = inputHandler.processInput("clerk", currentState,6666);
            currentState = serverOutput.getState();
            output = serverOutput.getOutput();
        });
        And("clerk login with correct password",()->{
            serverOutput = inputHandler.processInput(Config.CLERK_PASSWORD, currentState,6666);
            currentState = serverOutput.getState();
            output = serverOutput.getOutput();
        });
        And("clerk login with incorrect password",()->{
            serverOutput = inputHandler.processInput("1234567", currentState,6666);
            currentState = serverOutput.getState();
            output = serverOutput.getOutput();
        });

        Then("clerk enters system",()->{
            Assert.assertTrue(output.startsWith("What can I do for you?"));
        });
        Then("clerk does not enters system",()->{
            Assert.assertFalse(output.startsWith("What can I do for you?"));
        });
        Then("logs out",()->{
            serverOutput = inputHandler.processInput("log out", currentState,9999);
            currentState = serverOutput.getState();
            output = serverOutput.getOutput();
            serverOutput = inputHandler.processInput("", currentState,9999);
            currentState = serverOutput.getState();
            output = serverOutput.getOutput();
        });
        Then("wait until registration starts",()->{
            while (!Config.REGISTRATION_STARTS){
                Thread.sleep(200);
            }
        });
        Then("wait until registration ends",()->{
            while (!Config.REGISTRATION_ENDS){
                Thread.sleep(200);
            }
        });
        Then("wait until term ends",()->{
            while (!Config.TERM_ENDS){
                Thread.sleep(200);
            }
        });
        Then("university has course {int}",(Integer number)->{
            Assert.assertEquals((int) number, University.getInstance().getCourses().size());
        });
        Then("clerk create course",(DataTable dt)->{
            StringBuffer sb = new StringBuffer();
            for (String data: dt.asList()){
                sb.append(data);
                sb.append(",");
            }

            sb.deleteCharAt(sb.lastIndexOf(","));
            serverOutput = inputHandler.processInput("create course", currentState,6666);
            currentState = serverOutput.getState();
            output = serverOutput.getOutput();
            serverOutput = inputHandler.processInput(sb.toString(), currentState,6666);
            currentState = serverOutput.getState();
            output = serverOutput.getOutput();
        });

        Then("clerk delete course {int}",(Integer courseNumber)->{
            serverOutput = inputHandler.processInput("delete course", currentState,6666);
            currentState = serverOutput.getState();
            output = serverOutput.getOutput();
            serverOutput = inputHandler.processInput(Integer.toString(courseNumber), currentState,6666);
            currentState = serverOutput.getState();
            output = serverOutput.getOutput();
        });
        Then("clerk cancel course {int}",(Integer courseNumber)->{
            serverOutput = inputHandler.processInput("cancel course", currentState,6666);
            currentState = serverOutput.getState();
            output = serverOutput.getOutput();
            serverOutput = inputHandler.processInput(Integer.toString(courseNumber), currentState,6666);
            currentState = serverOutput.getState();
            output = serverOutput.getOutput();
        });
        Then("print output",()->{
            System.out.println(output);
        });

        Then("clerk delete student {int}",(Integer studentNumber)->{
            serverOutput = inputHandler.processInput("delete student", currentState,6666);
            currentState = serverOutput.getState();
            output = serverOutput.getOutput();
            serverOutput = inputHandler.processInput(Integer.toString(studentNumber), currentState,6666);
            currentState = serverOutput.getState();
            output = serverOutput.getOutput();
        });
        Then("university has student {int}" ,(Integer studentCount)->{
            Assert.assertEquals((int)studentCount,University.getInstance().getStudents().size());
        });

        Then("clerk create student",(DataTable dt)->{
            StringBuffer sb = new StringBuffer();
            for (String data:dt.asList()){
                sb.append(data);
                sb.append(",");
            }
            sb.deleteCharAt(sb.lastIndexOf(","));
            serverOutput = inputHandler.processInput("create student", currentState,6666);
            currentState = serverOutput.getState();
            output = serverOutput.getOutput();
            serverOutput = inputHandler.processInput(sb.toString(), currentState,6666);
            currentState = serverOutput.getState();
            output = serverOutput.getOutput();
        });

        Then("student login",(DataTable dt)->{
            StringBuffer sb = new StringBuffer();
            for (String data:dt.asList()){
                sb.append(data);
                sb.append(",");
            }
            sb.deleteCharAt(sb.lastIndexOf(","));
            serverOutput = inputHandler.processInput("student", currentState,9999);
            currentState = serverOutput.getState();
            output = serverOutput.getOutput();
            serverOutput = inputHandler.processInput(sb.toString(), currentState,9999);
            currentState = serverOutput.getState();
            output = serverOutput.getOutput();
        });
        Then("student {int} has {int} course",(Integer studentNumber, Integer courseCount)->{
            Assert.assertEquals(University.getInstance().getStudents().stream()
                    .filter(s->s.getStudentNumber()== studentNumber)
                    .findAny()
                    .get()
                    .getRegisteredCourses().size(),(int)courseCount);
        });

        Then("student register course {int}",(Integer courseNumber)->{
            serverOutput = inputHandler.processInput("register for course", currentState,9999);
            currentState = serverOutput.getState();
            output = serverOutput.getOutput();
            serverOutput = inputHandler.processInput(Integer.toString(courseNumber), currentState,9999);
            currentState = serverOutput.getState();
            output = serverOutput.getOutput();
        });
        Then("student select course {int}",(Integer courseNumber)->{
            serverOutput = inputHandler.processInput("select course", currentState,9999);
            currentState = serverOutput.getState();
            output = serverOutput.getOutput();
            serverOutput = inputHandler.processInput(Integer.toString(courseNumber), currentState,9999);
            currentState = serverOutput.getState();
            output = serverOutput.getOutput();
        });

        Then("student deregister course {int}",(Integer courseNumber)->{
            serverOutput = inputHandler.processInput("deregister course", currentState,9999);
            currentState = serverOutput.getState();
            output = serverOutput.getOutput();
            serverOutput = inputHandler.processInput(Integer.toString(courseNumber), currentState,9999);
            currentState = serverOutput.getState();
            output = serverOutput.getOutput();
        });
        Then("student drop course {int}",(Integer courseNumber)->{
            serverOutput = inputHandler.processInput("drop course", currentState,9999);
            currentState = serverOutput.getState();
            output = serverOutput.getOutput();
            serverOutput = inputHandler.processInput(Integer.toString(courseNumber), currentState,9999);
            currentState = serverOutput.getState();
            output = serverOutput.getOutput();
        });

        Then("student complete course {int}",(Integer courseNumber)->{
            serverOutput = inputHandler.processInput("complete course", currentState,9999);
            currentState = serverOutput.getState();
            output = serverOutput.getOutput();
            serverOutput = inputHandler.processInput(Integer.toString(courseNumber), currentState,9999);
            currentState = serverOutput.getState();
            output = serverOutput.getOutput();
        });
        Then("student login on port {int}",(Integer p,DataTable dt)->{
            StringBuffer sb = new StringBuffer();
            for (String data:dt.asList()){
                sb.append(data);
                sb.append(",");
            }
            sb.deleteCharAt(sb.lastIndexOf(","));
            serverOutput = inputHandler.processInput("student", currentState,p);
            currentState = serverOutput.getState();
            output = serverOutput.getOutput();
            serverOutput = inputHandler.processInput(sb.toString(), currentState,p);
            currentState = serverOutput.getState();
            output = serverOutput.getOutput();
        });
        Then("student register course {int} on {int}",(Integer courseNumber,Integer port)->{
            serverOutput = inputHandler.processInput("register for course", currentState,port);
            currentState = serverOutput.getState();
            output = serverOutput.getOutput();
            serverOutput = inputHandler.processInput(Integer.toString(courseNumber), currentState,port);
            currentState = serverOutput.getState();
            output = serverOutput.getOutput();
        });
        Then("student select course {int} on {int}",(Integer courseNumber,Integer port)->{
            serverOutput = inputHandler.processInput("select course", currentState,port);
            currentState = serverOutput.getState();
            output = serverOutput.getOutput();
            serverOutput = inputHandler.processInput(Integer.toString(courseNumber), currentState,port);
            currentState = serverOutput.getState();
            output = serverOutput.getOutput();
        });

        Then("student deregister course {int} on {int}",(Integer courseNumber,Integer port)->{
            serverOutput = inputHandler.processInput("deregister course", currentState,port);
            currentState = serverOutput.getState();
            output = serverOutput.getOutput();
            serverOutput = inputHandler.processInput(Integer.toString(courseNumber), currentState,port);
            currentState = serverOutput.getState();
            output = serverOutput.getOutput();
        });
        Then("student drop course {int} on {int}",(Integer courseNumber,Integer port)->{
            serverOutput = inputHandler.processInput("drop course", currentState,port);
            currentState = serverOutput.getState();
            output = serverOutput.getOutput();
            serverOutput = inputHandler.processInput(Integer.toString(courseNumber), currentState,port);
            currentState = serverOutput.getState();
            output = serverOutput.getOutput();
        });

        Then("student complete course {int} on {int}",(Integer courseNumber,Integer port)->{
            serverOutput = inputHandler.processInput("complete course", currentState,port);
            currentState = serverOutput.getState();
            output = serverOutput.getOutput();
            serverOutput = inputHandler.processInput(Integer.toString(courseNumber), currentState,port);
            currentState = serverOutput.getState();
            output = serverOutput.getOutput();
        });

    }
}
