package cucumber.Options;

import org.junit.runner.RunWith;

import io.cucumber.junit.CucumberOptions;

import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
		features="src/test/java/features",
		plugin ="json:target/jsonReports/cucumber-reports.json",
		glue= {"stepDefinition"},
		tags= {"@DeletePlace"})
public class TestRunner {

}
