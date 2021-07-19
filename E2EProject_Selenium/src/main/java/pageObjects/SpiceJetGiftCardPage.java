package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SpiceJetGiftCardPage {

	public WebDriver driver;

	public SpiceJetGiftCardPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//input[contains(@class,'Search')]")
	private WebElement search;

	public WebElement search() {
		return search;
	}

}
