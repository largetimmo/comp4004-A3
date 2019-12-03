package test.java.cucumber;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
        strict = true,
        features = {"/Users/chenjunhao/Documents/GitHub/comp4004-A3-JunhaoChen/src/test/java/cucumber/res/scenarios.feature:48"},
        plugin = {"json:/Users/chenjunhao/Documents/GitHub/comp4004-A3-JunhaoChen/target/cucumber-parallel/json/8.json"},
        monochrome = true,
        glue = {"test.java.cucumber"})
public class Parallel08IT {
}
