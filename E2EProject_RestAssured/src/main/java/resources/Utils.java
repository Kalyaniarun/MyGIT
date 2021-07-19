package resources;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Utils {

	public static RequestSpecification req;

	public RequestSpecification requestSpecification() throws IOException {

		if (req == null) {
			PrintStream log = new PrintStream(new FileOutputStream("logs.txt"));
			req = new RequestSpecBuilder().setBaseUri(getGlobalValue("baseURI")).addQueryParam("key", "qaclick123")
					.setContentType(ContentType.JSON).addFilter(RequestLoggingFilter.logRequestTo(log))
					.addFilter(ResponseLoggingFilter.logResponseTo(log)).build();
		}
		return req;
	}

	public static String getGlobalValue(String key) throws IOException {

		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(
				System.getProperty("user.dir") + "/src/main/java/resources/global.properties");
		prop.load(fis);
		String value = prop.getProperty(key);
		return value;
	}

	public static String getJsonPath(Response response, String key) {

		String resp = response.asString();
		JsonPath js = new JsonPath(resp);
		String value = js.getString(key);
		return value;
	}

	public static HashMap<String, Object> getDataFromExcel(String testCase, String sheetName) throws IOException {

		ArrayList<String> data = new ArrayList<String>();
		ArrayList<String> columnNames = new ArrayList<String>();
		HashMap<String, Object> hm = new HashMap<>();
		String path = System.getProperty("user.dir");
		FileInputStream fis = new FileInputStream(path + "\\src\\test\\resources\\TestData.xlsx");
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		int sheetCount = workbook.getNumberOfSheets();
		for (int i = 0; i < sheetCount; i++) {
			if (workbook.getSheetName(i).equalsIgnoreCase(sheetName)) {
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
						int count = columnNames.size();
						for (int k = 0; k < count; k++) {
							Cell c = row.getCell(k);
							if (c == null) {
								data.add(null);
							} else if (c.getCellTypeEnum() == CellType.STRING) {
								data.add((c.getStringCellValue()));
							} else {
								data.add(NumberToTextConverter.toText(c.getNumericCellValue()));
							}
						}
					}
				}

			}
		}
		for (int i = 0; i < columnNames.size(); i++) {
			hm.put(columnNames.get(i), data.get(i));
		}
		hm.remove("TestCase");
		hm.values().removeAll(Collections.singleton(null));
		return hm;
	}
}
