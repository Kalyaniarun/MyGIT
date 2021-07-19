package tests;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import constants.Constants;
import pageObjects.SpiceJetGiftCardPage;
import pageObjects.SpiceJetHomePage;
import resources.Base;

public class Concepts extends Base {

	public WebDriver driver;
	public static Logger log = LogManager.getLogger(Concepts.class.getName());
	SpiceJetHomePage spiceJetHomePg;
	Actions action;
	HashMap<Object,Object> testdata = new HashMap<Object,Object>();
	String departureCity;
	String arrivalCity;

	@BeforeClass
	public void initialize() throws IOException {
		
		testdata = getDataFromExcel("Concepts");
		departureCity = (String) testdata.get("DepartureCity");
		arrivalCity = (String) testdata.get("ArrivalCity");
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + Constants.CONCEPTS);
		driver = initializeDriver(fis, "");
		driver.get(prop.getProperty("url"));
		driver.manage().window().maximize();
		log.info("==== Initialized Driver ====");
	}
	@Test
	public void handleDynamicDropdown() {
		spiceJetHomePg = new SpiceJetHomePage(driver);
		spiceJetHomePg.departureCityDropdown().click();
		selectDynamicWebElement(spiceJetHomePg.departureCity(),departureCity);
		selectDynamicWebElement(spiceJetHomePg.arrivalCity(), arrivalCity);
		log.info("==== Dynamic dropdown selected ====");

	}

	@Test(dependsOnMethods = { "handleDynamicDropdown" })
	public void softAssert() {

		SoftAssert a = new SoftAssert();
		a.assertFalse(spiceJetHomePg.friendsAndFamily().isSelected());
		action = new Actions(driver);
		action.moveToElement(spiceJetHomePg.friendsAndFamily()).build().perform();
		a.assertAll();
	}

	@Test(dependsOnMethods = { "softAssert" })
	public void countLinks() {

		log.info("==== Total number of links in the page: " + spiceJetHomePg.links().size() + " ====");
		log.info("==== Total number of links in the footer section: " + spiceJetHomePg.footerLinks().size() + " ====");

	}

	@Test(dependsOnMethods = { "countLinks" })
	public void multipleWindows() {

		SpiceJetGiftCardPage spiceJetGiftCardPg = spiceJetHomePg.giftCard();
		Set<String> ids = driver.getWindowHandles();
		Iterator<String> it = ids.iterator();
		String parentID = it.next();
		driver.switchTo().window(it.next());
		action.moveToElement(spiceJetGiftCardPg.search()).click().keyDown(Keys.SHIFT).sendKeys("wedding").doubleClick()
				.build().perform();
		driver.close();
		driver.switchTo().window(parentID);

	}

	@AfterClass
	public void close() {
		driver.close();
		log.info("======= Driver Closed ========");
	}

}
