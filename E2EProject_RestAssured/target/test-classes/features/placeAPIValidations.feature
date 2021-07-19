Feature: Validating Place API's
@AddPlace
Scenario Outline: Verify place is added successfully using AddPlaceAPI
Given Add place payload with "<name>" "<language>" "<address>"
When User calls the "addPlaceAPI" with "POST" http request
Then API call got success with status code 200
And "status" in response body is "OK"
And "scope" in response body is "APP"
And Verify placeid created maps to "<name>" using "getPlaceAPI"

Examples:
|name   |language    |address    |
|Ram    |Hindi       |Hounslow   |
#|John   |Spanish     |Spain      |

@DeletePlace
Scenario: Verify place is deleted successfully using DeletPlaceAPI
Given Delete place payload
When User calls the "deletePlaceAPI" with "POST" http request
Then API call got success with status code 200
And "status" in response body is "OK"
