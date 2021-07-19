package pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SpiceJetHomePage {

	public WebDriver driver;

	public SpiceJetHomePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath="//input[@value='Departure City']")
	private WebElement departureCityDropdown;
	
	@FindBy(xpath="//div[@class='dropdownDiv']//li")
	private List<WebElement> departureCity;
	
	@FindBy(xpath="//div[contains(@id,'destination')]//div[@class='dropdownDiv']//li")
	private List<WebElement> arrivalCity;
	
	@FindBy(xpath="//input[contains(@id,'friendsandfamily')]")
	private WebElement friendsAndFamily;
	
	@FindBy(xpath="//li[@id='gift-card']")
	private WebElement giftCard;
	
	@FindBy(xpath="//a[@href]")
	private List<WebElement> links;
	
	@FindBy(xpath="//table[@id='footerTable']")
	private WebElement footer;
	
	
	public SpiceJetGiftCardPage giftCard() {
		giftCard.click();
		SpiceJetGiftCardPage spiceJetGiftCardPg = new SpiceJetGiftCardPage(driver);
		return spiceJetGiftCardPg;
	}
	
	public WebElement departureCityDropdown() {
		return departureCityDropdown;
	}
	
	public List<WebElement> departureCity() {
		return departureCity;
	}
	
	public List<WebElement> arrivalCity() {
		return arrivalCity;
	}
	
	public WebElement friendsAndFamily() {
		return friendsAndFamily;
	}
	
	public List<WebElement> links() {
		return links;
	}
	
	public List<WebElement> footerLinks() {
		return footer.findElements(By.tagName("a"));
	}
	
}
