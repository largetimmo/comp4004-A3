package test.java.cucumber;

import io.cucumber.datatable.DataTable;
import io.cucumber.java8.En;
import main.server.logic.handler.InputHandler;
import main.server.logic.handler.model.ServerOutput;
import main.server.logic.model.University;
import main.utilities.Config;
import org.junit.Assert;

import java.util.*;

public class StepDef implements En {

    static final InputHandler inputHandler = new InputHandler();
    ServerOutput serverOutput = new ServerOutput("", 0);
    int currentState;
    String output;
    static Map<String, Integer> multiStudentState;
    static final List<Thread> threads = new LinkedList<>();
    static boolean asyncFlag;
    static Integer ex2Ready = 0;

    public StepDef() {
        Given("init system", () -> {
            University.getInstance().Reset();
            serverOutput = inputHandler.processInput("", InputHandler.WAITING, "1");
            currentState = serverOutput.getState();
            output = serverOutput.getOutput();
            multiStudentState = new HashMap<>();
            asyncFlag = false;
        });
        Then("registration does not start", () -> {
            Assert.assertFalse(Config.REGISTRATION_STARTS);
        });
        And("clerk login", () -> {
            serverOutput = inputHandler.processInput("clerk", currentState, "1");
            currentState = serverOutput.getState();
            output = serverOutput.getOutput();
        });
        And("clerk login with correct password", () -> {
            serverOutput = inputHandler.processInput(Config.CLERK_PASSWORD, currentState, "1");
            currentState = serverOutput.getState();
            output = serverOutput.getOutput();
        });
        And("clerk login with incorrect password", () -> {
            serverOutput = inputHandler.processInput("1234567", currentState, "1");
            currentState = serverOutput.getState();
            output = serverOutput.getOutput();
        });

        Then("clerk enters system", () -> {
            Assert.assertTrue(output.startsWith("What can I do for you?"));
        });
        Then("clerk does not enters system", () -> {
            Assert.assertFalse(output.startsWith("What can I do for you?"));
        });
        Then("logs out", () -> {
            serverOutput = inputHandler.processInput("log out", currentState, "student");
            currentState = serverOutput.getState();
            output = serverOutput.getOutput();
            serverOutput = inputHandler.processInput("", currentState, "student");
            currentState = serverOutput.getState();
            output = serverOutput.getOutput();
        });
        Then("wait until registration starts", () -> {
            while (!Config.REGISTRATION_STARTS) {
                Thread.sleep(1);
            }
        });
        Then("wait until registration ends", () -> {
            while (!Config.REGISTRATION_ENDS) {
                Thread.sleep(20);
            }
        });
        Then("wait until term ends", () -> {
            while (!Config.TERM_ENDS) {
                Thread.sleep(20);
            }
        });
        Then("university has course {int}", (Integer number) -> {
            Assert.assertEquals((int) number, University.getInstance().getCourses().size());
        });
        Then("clerk create course", (DataTable dt) -> {
            StringBuffer sb = new StringBuffer();
            for (String data : dt.asList()) {
                sb.append(data);
                sb.append(",");
            }

            sb.deleteCharAt(sb.lastIndexOf(","));
            serverOutput = inputHandler.processInput("create course", currentState, "1");
            currentState = serverOutput.getState();
            output = serverOutput.getOutput();
            serverOutput = inputHandler.processInput(sb.toString(), currentState, "1");
            currentState = serverOutput.getState();
            output = serverOutput.getOutput();
        });

        Then("clerk delete course {int}", (Integer courseNumber) -> {
            serverOutput = inputHandler.processInput("delete course", currentState, "1");
            currentState = serverOutput.getState();
            output = serverOutput.getOutput();
            serverOutput = inputHandler.processInput(Integer.toString(courseNumber), currentState, "1");
            currentState = serverOutput.getState();
            output = serverOutput.getOutput();
        });
        Then("clerk cancel course {int}", (Integer courseNumber) -> {
            serverOutput = inputHandler.processInput("cancel course", currentState, "1");
            currentState = serverOutput.getState();
            output = serverOutput.getOutput();
            serverOutput = inputHandler.processInput(Integer.toString(courseNumber), currentState, "1");
            currentState = serverOutput.getState();
            output = serverOutput.getOutput();
        });
        Then("print output", () -> {
            System.out.println(output);
        });

        Then("clerk delete student {int}", (Integer studentNumber) -> {
            serverOutput = inputHandler.processInput("delete student", currentState, "1");
            currentState = serverOutput.getState();
            output = serverOutput.getOutput();
            serverOutput = inputHandler.processInput(Integer.toString(studentNumber), currentState, "1");
            currentState = serverOutput.getState();
            output = serverOutput.getOutput();
        });
        Then("university has student {int}", (Integer studentCount) -> {
            Assert.assertEquals((int) studentCount, University.getInstance().getStudents().size());
        });

        Then("clerk create student", (DataTable dt) -> {
            StringBuffer sb = new StringBuffer();
            for (String data : dt.asList()) {
                sb.append(data);
                sb.append(",");
            }
            sb.deleteCharAt(sb.lastIndexOf(","));
            serverOutput = inputHandler.processInput("create student", currentState, "1");
            currentState = serverOutput.getState();
            output = serverOutput.getOutput();
            serverOutput = inputHandler.processInput(sb.toString(), currentState, "1");
            currentState = serverOutput.getState();
            output = serverOutput.getOutput();
        });

        Then("student login", (DataTable dt) -> {
            StringBuffer sb = new StringBuffer();
            for (String data : dt.asList()) {
                sb.append(data);
                sb.append(",");
            }
            sb.deleteCharAt(sb.lastIndexOf(","));
            serverOutput = inputHandler.processInput("student", currentState, "student");
            currentState = serverOutput.getState();
            output = serverOutput.getOutput();
            serverOutput = inputHandler.processInput(sb.toString(), currentState, "student");
            currentState = serverOutput.getState();
            output = serverOutput.getOutput();
        });
        Then("student {int} has {int} course", (Integer studentNumber, Integer courseCount) -> {
            Assert.assertEquals(University.getInstance().getStudents().stream()
                    .filter(s -> s.getStudentNumber() == studentNumber)
                    .findAny()
                    .get()
                    .getRegisteredCourses().size(), (int) courseCount);
        });

        Then("student register course {int}", (Integer courseNumber) -> {
            serverOutput = inputHandler.processInput("register for course", currentState, "student");
            currentState = serverOutput.getState();
            output = serverOutput.getOutput();
            serverOutput = inputHandler.processInput(Integer.toString(courseNumber), currentState, "student");
            currentState = serverOutput.getState();
            output = serverOutput.getOutput();
        });
        Then("student select course {int}", (Integer courseNumber) -> {
            serverOutput = inputHandler.processInput("select course", currentState, "student");
            currentState = serverOutput.getState();
            output = serverOutput.getOutput();
            serverOutput = inputHandler.processInput(Integer.toString(courseNumber), currentState, "student");
            currentState = serverOutput.getState();
            output = serverOutput.getOutput();
        });

        Then("student deregister course {int}", (Integer courseNumber) -> {
            serverOutput = inputHandler.processInput("deregister course", currentState, "student");
            currentState = serverOutput.getState();
            output = serverOutput.getOutput();
            serverOutput = inputHandler.processInput(Integer.toString(courseNumber), currentState, "student");
            currentState = serverOutput.getState();
            output = serverOutput.getOutput();
        });
        Then("student drop course {int}", (Integer courseNumber) -> {
            serverOutput = inputHandler.processInput("drop course", currentState, "student");
            currentState = serverOutput.getState();
            output = serverOutput.getOutput();
            serverOutput = inputHandler.processInput(Integer.toString(courseNumber), currentState, "student");
            currentState = serverOutput.getState();
            output = serverOutput.getOutput();
        });

        Then("student complete course {int}", (Integer courseNumber) -> {
            serverOutput = inputHandler.processInput("complete course", currentState, "student");
            currentState = serverOutput.getState();
            output = serverOutput.getOutput();
            serverOutput = inputHandler.processInput(Integer.toString(courseNumber), currentState, "student");
            currentState = serverOutput.getState();
            output = serverOutput.getOutput();
        });
        Then("student login on {string}", (String uid, DataTable dt) -> {
            synchronized (inputHandler) {
                StringBuffer sb = new StringBuffer();
                for (String data : dt.asList()) {
                    sb.append(data);
                    sb.append(",");
                }
                sb.deleteCharAt(sb.lastIndexOf(","));
                serverOutput = inputHandler.processInput("student", 1, uid);
                multiStudentState.put(uid, serverOutput.getState());
                output = serverOutput.getOutput();
                serverOutput = inputHandler.processInput(sb.toString(), multiStudentState.get(uid), uid);
                multiStudentState.put(uid, serverOutput.getState());
                output = serverOutput.getOutput();
            }
        });
        Then("student register course {int} on {string} in {string}", (Integer courseNumber, String uid,String example) -> {
            synchronized (inputHandler) {

                serverOutput = inputHandler.processInput("register for course", multiStudentState.get(uid), uid);
                multiStudentState.put(uid, serverOutput.getState());
                output = serverOutput.getOutput();
                serverOutput = inputHandler.processInput(Integer.toString(courseNumber), multiStudentState.get(uid), uid);
                multiStudentState.put(uid, serverOutput.getState());
                output = serverOutput.getOutput();
                System.out.println("Example"+example+"Register:" + uid + ",course count:"+University.getInstance().getStudents().stream()
                        .filter(s -> s.getStudentNumber() == University.getInstance().getPortStudentNumberMap().get(uid))
                        .findAny()
                        .get()
                        .getRegisteredCourses().size());
            }
        });
        Then("student select course {int} on {string}", (Integer courseNumber, String uid) -> {
            synchronized (inputHandler) {

                serverOutput = inputHandler.processInput("select course", multiStudentState.get(uid), uid);
                multiStudentState.put(uid, serverOutput.getState());
                output = serverOutput.getOutput();
                serverOutput = inputHandler.processInput(Integer.toString(courseNumber), multiStudentState.get(uid), uid);
                multiStudentState.put(uid, serverOutput.getState());
                output = serverOutput.getOutput();
            }
        });

        Then("student deregister course {int} on {string} in {string}", (Integer courseNumber, String uid,String example) -> {
            synchronized (inputHandler) {

                serverOutput = inputHandler.processInput("deregister course", multiStudentState.get(uid), uid);
                multiStudentState.put(uid, serverOutput.getState());
                output = serverOutput.getOutput();
                serverOutput = inputHandler.processInput(Integer.toString(courseNumber), multiStudentState.get(uid), uid);
                multiStudentState.put(uid, serverOutput.getState());
                output = serverOutput.getOutput();
                System.out.println("Example"+example+"Deregister:" + uid + ",course count:"+University.getInstance().getStudents().stream()
                        .filter(s -> s.getStudentNumber() == University.getInstance().getPortStudentNumberMap().get(uid))
                        .findAny()
                        .get()
                        .getRegisteredCourses().size());
            }
        });
        Then("student drop course {int} on {string}", (Integer courseNumber, String uid) -> {
            synchronized (inputHandler) {

                serverOutput = inputHandler.processInput("drop course", multiStudentState.get(uid), uid);
                multiStudentState.put(uid, serverOutput.getState());
                output = serverOutput.getOutput();
                serverOutput = inputHandler.processInput(Integer.toString(courseNumber), multiStudentState.get(uid), uid);
                multiStudentState.put(uid, serverOutput.getState());
                output = serverOutput.getOutput();
            }
        });

        Then("student complete course {int} on {string}", (Integer courseNumber, String uid) -> {
            synchronized (inputHandler) {

                serverOutput = inputHandler.processInput("complete course", multiStudentState.get(uid), uid);
                multiStudentState.put(uid, serverOutput.getState());
                output = serverOutput.getOutput();
                serverOutput = inputHandler.processInput(Integer.toString(courseNumber), multiStudentState.get(uid), uid);
                multiStudentState.put(uid, serverOutput.getState());
                output = serverOutput.getOutput();
            }
        });
        Then("student register course {int} on {string} async", (Integer courseNum, String uid) -> {
            Thread t = new Thread(() -> {
                while (!Config.REGISTRATION_STARTS && !asyncFlag) {
                    try {
                        Thread.sleep(threads.size());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (inputHandler) {
                    serverOutput = inputHandler.processInput("register for course", multiStudentState.get(uid), uid);
                    multiStudentState.put(uid, serverOutput.getState());
                    output = serverOutput.getOutput();
                    serverOutput = inputHandler.processInput(Integer.toString(courseNum), multiStudentState.get(uid), uid);
                    multiStudentState.put(uid, serverOutput.getState());
                    output = serverOutput.getOutput();
                }
                int studentNumber;
                studentNumber = University.getInstance().getPortStudentNumberMap().get(uid);
                System.out.println("Register" + uid + University.getInstance().getStudents().stream()
                        .filter(s -> s.getStudentNumber() == studentNumber)
                        .findAny()
                        .get()
                        .getRegisteredCourses().size());
            });
            threads.add(t);
            t.start();
        });
        Then("student deregister course {int} on {string} async", (Integer courseNum, String uid) -> {
            Thread t = new Thread(() -> {
                while (!Config.REGISTRATION_STARTS && !asyncFlag) {
                    try {
                        Thread.sleep(threads.size());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (inputHandler) {
                    serverOutput = inputHandler.processInput("deregister course", multiStudentState.get(uid), uid);
                    multiStudentState.put(uid, serverOutput.getState());
                    output = serverOutput.getOutput();
                    serverOutput = inputHandler.processInput(Integer.toString(courseNum), multiStudentState.get(uid), uid);
                    multiStudentState.put(uid, serverOutput.getState());
                    output = serverOutput.getOutput();
                }
                int studentNumber;
                studentNumber = University.getInstance().getPortStudentNumberMap().get(uid);
                System.out.println("Deregister:" + uid + University.getInstance().getStudents().stream()
                        .filter(s -> s.getStudentNumber() == studentNumber)
                        .findAny()
                        .get()
                        .getRegisteredCourses().size());
            });
            threads.add(t);
            t.start();
        });

        Then("async ready", () -> {
            asyncFlag = true;
        });
        Then("wait until async ready", () -> {
            while (!asyncFlag) {
                Thread.sleep(1);
            }
        });
        Then("print {string}", (String msg) -> {
            System.out.println(msg);
        });
        Then("wait ex2",()->{
            while (ex2Ready<3){
                Thread.sleep(1);
            }
        });
        Then("set ex2 ready",()->{
            synchronized (ex2Ready){
                ex2Ready +=1;
            }
            asyncFlag = false;
        });
        Then ("wait until ex2 and async ready",()->{
            while (ex2Ready<3 || !asyncFlag){
                Thread.sleep(1);
            }
        });
    }
}
