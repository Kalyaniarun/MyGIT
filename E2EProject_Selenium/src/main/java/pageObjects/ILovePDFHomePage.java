package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ILovePDFHomePage {

	public WebDriver driver;

	public ILovePDFHomePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//span[text()='Select JPG images']")
	private WebElement selectJPG;

	@FindBy(xpath = "//span[text()='Convert to PDF']")
	private WebElement convertToPDF;

	public WebElement selectJPG() {
		return selectJPG;
	}

	public WebElement convertToPDF() {
		return convertToPDF;
	}

}
