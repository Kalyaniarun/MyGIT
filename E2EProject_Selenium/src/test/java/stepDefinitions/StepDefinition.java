package stepDefinitions;

import java.io.FileInputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;

import constants.Constants;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import pageObjects.IPLHomePage;
import pageObjects.IPLStatsPage;
import resources.Base;

public class StepDefinition extends Base {

	public static Logger log = LogManager.getLogger(StepDefinition.class.getName());
	IPLStatsPage iplStatsPg;

	@Given("^Initialize browser with chrome$")
	public void initialize_browser_with_chrome() throws Throwable {
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + Constants.IPL_STATS);
		driver = initializeDriver(fis, "");
		log.info("==== Initialized Driver =====");
	}

	@When("^User navigates to (.+) and select (.+)$")
	public void user_navigates_to_and_select(String menu, String option) throws Throwable {
		IPLHomePage iplHomePg = new IPLHomePage(driver);
		selectDynamicWebElement(iplHomePg.column(), menu);
		iplStatsPg = iplHomePg.bySeason();
		log.info("==== Navigated to menu: " + menu + " and selected option: " + option + " ====");

	}

	@Then("^Verify player with highest runs$")
	public void verify_player_with_highest_runs() throws Throwable {
		int max = 0;
		String firstName = null;
		String lastName = null;
		for (WebElement run : iplStatsPg.runs()) {
			if (Integer.parseInt(run.getText()) > max) {
				max = Integer.parseInt(run.getText());
				int index = iplStatsPg.runs().indexOf(run);
				firstName = iplStatsPg.names().get(index).getText().trim().split("\n")[0];
				lastName = iplStatsPg.names().get(index).getText().trim().split("\n")[1];
			}

		}
		log.info("==== " + firstName + " " + lastName + " has the highest runs: " + max + " ====");
		driver.close();
	}

	@And("^navigate to \"([^\"]*)\" site$")
	public void navigate_to_something_site(String strArg1) throws Throwable {
		driver.get(strArg1);
		driver.manage().window().maximize();
		log.info("==== Navigated to url ====");
	}
}
