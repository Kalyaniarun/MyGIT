package tests;

import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.util.HashMap;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import resources.Utils;

public class DynamicJson extends Utils{
	
	public static void main(String[] args) throws IOException {
		
		RestAssured.baseURI="https://rahulshettyacademy.com";
		HashMap<String,Object> testdata = new HashMap<>();
		testdata = getDataFromExcel("DynamicJson","RestAssured");
		
		Response response = given().log().all().header("Content-Type","application/json").body(testdata)
		.when().post("/Library/Addbook.php")
		.then().log().all().assertThat().statusCode(200)
		.extract().response();
		
		System.out.println(getJsonPath(response,"ID"));
		
	}

}
