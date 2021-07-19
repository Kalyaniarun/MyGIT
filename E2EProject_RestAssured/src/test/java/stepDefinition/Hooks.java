package stepDefinition;

import cucumber.api.java.Before;

public class Hooks {

	@Before("@DeletePlace")
	public void beforeScenario() throws Throwable {
		MyStepDefinition m = new MyStepDefinition();
		if(MyStepDefinition.placeId==null) {
		m.Add_place_payload_with("Sita", "Sanskrit", "Delhi");
		m.User_calls_the_with_http_request("addPlaceAPI", "POST");
		m.Verify_placeid_created_maps_to_using("Sita","getPlaceAPI");
		}
	}
}
