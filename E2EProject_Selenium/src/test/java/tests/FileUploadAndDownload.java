package tests;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import constants.Constants;
import pageObjects.ILovePDFHomePage;
import resources.Base;

public class FileUploadAndDownload extends Base { 

	public WebDriver driver;
	public static Logger log = LogManager.getLogger(FileUploadAndDownload.class.getName());
	ILovePDFHomePage iLovePDFHomePg;
	String path = System.getProperty("user.dir");
	
	@BeforeClass
	public void initialize() throws IOException {

		FileInputStream fis = new FileInputStream(path + Constants.FILE_UPLOAD_AND_DOWNLOAD);
		driver = initializeDriver(fis, Constants.YES);
		driver.get(prop.getProperty("url"));
		driver.manage().window().maximize();
		log.info("==== Initialized Driver ====");
	}

	@Test
	public void fileUpload() throws InterruptedException, IOException {

		iLovePDFHomePg = new ILovePDFHomePage(driver);
		iLovePDFHomePg.selectJPG().click();
		Thread.sleep(3000); // Takes time for the file upload window to appear
		Runtime.getRuntime().exec("C:\\Users\\Kalyani\\eclipse-workspace\\Introduction\\fileupload.exe");
	}

	@Test(dependsOnMethods= {"fileUpload"})
	public void fileDownloadAndVerify() throws InterruptedException, IOException {

		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOf(iLovePDFHomePg.convertToPDF()));
		iLovePDFHomePg.convertToPDF().click();
		Thread.sleep(10000);  // Takes time for the file to be downloaded
		File f = new File(path + "/Capture.pdf");
		Assert.assertTrue(f.exists());
		log.info("file exists");
		f.delete();
		log.info("file deleted");
	}
	
	@AfterClass
	public void close() {
		driver.close();
		log.info("======= Driver Closed ========");
	}

}
