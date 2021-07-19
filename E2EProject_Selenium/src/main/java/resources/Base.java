package resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import constants.Constants;

public class Base {

	public WebDriver driver;
	public Properties prop;
	static String path = System.getProperty("user.dir");

	public WebDriver initializeDriver(FileInputStream fis, String fileUpload) throws IOException {

		prop = new Properties();
		prop.load(fis);
		// String browser = System.getProperty("browser");
		String browser = prop.getProperty("browser");
		ChromeOptions options = new ChromeOptions();

		if (!fileUpload.isEmpty()) {
			HashMap<String, Object> chromeprefs = new HashMap<String, Object>();
			chromeprefs.put("profile.default_content_settings.popups", 0);
			chromeprefs.put("download.default_directory", path);
			options.setExperimentalOption("prefs", chromeprefs);
		}

		if (browser.contains(Constants.CHROME)) {
			System.setProperty("webdriver.chrome.driver", path + Constants.CHROMEDRIVER);
			if (browser.contains(Constants.HEADLESS)) {
				options.addArguments(Constants.HEADLESS);
			}
			driver = new ChromeDriver(options);
		} else if (browser.equals(Constants.FIREFOX)) {
			System.setProperty("webdriver.gecko.driver", path + Constants.GECKODRIVER);
			driver = new FirefoxDriver();
		} else if (browser.equals(Constants.IE)) {
			System.setProperty("webdriver.ie.driver", path + Constants.IEDRIVER);
			driver = new InternetExplorerDriver();
		}

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		return driver;
	}

	public String getScreenshot(String name, WebDriver driver) throws IOException {

		String destination = path + "\\reports\\" + name + ".png";
		File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(src, new File(destination));

		return destination;

	}

	public static void selectDynamicWebElement(List<WebElement> element, String text) {

		for (int i = 0; i < element.size(); i++) {
			if (element.get(i).getText().contains(text)) {
				element.get(i).click();
				break;
			}
		}
	}

	public static HashMap<Object,Object> getDataFromExcel(String testCase) throws IOException { 

		ArrayList<String> data = new ArrayList<String>();
		ArrayList<String> columnNames = new ArrayList<String>();
		HashMap<Object,Object> hm = new HashMap<Object,Object>();
		FileInputStream fis = new FileInputStream(path + "\\src\\test\\resources\\TestData.xlsx");
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		int sheetCount = workbook.getNumberOfSheets();
		for (int i = 0; i < sheetCount; i++) {
			if (workbook.getSheetName(i).equalsIgnoreCase("Sheet1")) {
				XSSFSheet sheet = workbook.getSheetAt(i);
				Iterator<Row> rows = sheet.iterator();
				Row firstRow = rows.next();
				Iterator<Cell> cells = firstRow.cellIterator();
				int j = 0;
				int column = 0;
				while (cells.hasNext()) {
					Cell cell = cells.next();
					if (cell.getStringCellValue().equalsIgnoreCase("TestCase")) {
						column = j;
					}
					columnNames.add(cell.getStringCellValue());
					j++;
				}
				while (rows.hasNext()) {
					Row row = rows.next();
					if (row.getCell(column).getStringCellValue().equalsIgnoreCase(testCase)) {
						cells = row.cellIterator();
						while (cells.hasNext()) {
							Cell c = cells.next();
							if (c.getCellTypeEnum() == CellType.STRING) {
								data.add((c.getStringCellValue()));
							} else {
								data.add(NumberToTextConverter.toText(c.getNumericCellValue()));
							}
						}
					}
				}

			}
		}
		for (int i=0;i<columnNames.size();i++) {
			hm.put(columnNames.get(i), data.get(i));
			
		}
		return hm;
	}

}
