package pageObjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class IPLStatsPage {

	public WebDriver driver;

	public IPLStatsPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//td[contains(@class,'top-players__r')]")
	private List<WebElement> runs;

	@FindBy(xpath = "//tr/td[2]/div[2]//a")
	private List<WebElement> names;

	public List<WebElement> runs() {
		return runs;
	}

	public List<WebElement> names() {
		return names;
	}

}
