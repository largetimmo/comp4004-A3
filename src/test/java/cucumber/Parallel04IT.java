package test.java.cucumber;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
        strict = true,
        features = {"/Users/chenjunhao/Documents/GitHub/comp4004-A3-JunhaoChen/src/test/java/cucumber/res/SequentialRunningEx2.feature:55"},
        plugin = {"json:/Users/chenjunhao/Documents/GitHub/comp4004-A3-JunhaoChen/target/cucumber-parallel/json/4.json"},
        monochrome = true,
        glue = {"test.java.cucumber"})
public class Parallel04IT {
}
