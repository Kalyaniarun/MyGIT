package stepDefinition;

import java.util.ArrayList;
import java.util.List;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import static org.junit.Assert.assertEquals;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import resources.APIResources;
import resources.TestDataBuild;
import resources.Utils;

public class MyStepDefinition extends Utils{
	RequestSpecification res;
	Response response;
	TestDataBuild data=new TestDataBuild();
	static String placeId;
	
	@Given("^Add place payload with \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
	public void Add_place_payload_with(String name, String language, String address) throws Throwable {
		
		res=given().spec(requestSpecification()).body(data.addPlacePayload(name,language,address));

	}

	@When("^User calls the \"([^\"]*)\" with \"([^\"]*)\" http request$")
	public void User_calls_the_with_http_request(String resourceName, String method) throws Throwable {

		APIResources resource = APIResources.valueOf(resourceName);
		if (method.equals("POST"))
			response = res.when().post(resource.getResource());
		else if (method.equals("GET"))
			response = res.when().get(resource.getResource());
	}

	@Then("^API call got success with status code (\\d+)$")
	public void API_call_got_success_with_status_code(int arg1) throws Throwable {
		
		assertEquals(response.getStatusCode(),200);
	}

	@Then("^\"([^\"]*)\" in response body is \"([^\"]*)\"$")
	public void in_response_body_is(String key, String value) throws Throwable {
		
		assertEquals(getJsonPath(response,key),value);
	}
	
	@Then("^Verify placeid created maps to \"([^\"]*)\" using \"([^\"]*)\"$")
	public void Verify_placeid_created_maps_to_using(String name,String resource) throws Throwable {

		placeId=getJsonPath(response,"place_id");
		res=given().spec(requestSpecification()).queryParam("place_id",placeId);
		User_calls_the_with_http_request(resource,"GET");
		assertEquals(getJsonPath(response,"name"),name);
		
	}

	@Given("^Delete place payload$")
	public void Delete_place_payload() throws Throwable {
		res=given().spec(requestSpecification()).body(data.deletePlacePayload(placeId));
	}

}
