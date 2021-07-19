package pageObjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class IPLHomePage {

	public WebDriver driver;

	public IPLHomePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//div[contains(text(),'')]")
	private List<WebElement> column;
	
	@FindBy(xpath = "//a[contains(text(),'By Season')]")
	private WebElement bySeason;

	public List<WebElement> column() {
		return column;
	}
	
	public IPLStatsPage bySeason() {
		bySeason.click();
		IPLStatsPage iplStatsPg = new IPLStatsPage(driver);
		return iplStatsPg;
	}


}
