package Authentication.authentications;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

public class OAuth2APITest {
	
	
	@Test
	public void checkOAuth2_APITest(){
		
		
	RequestSpecification request =
			RestAssured.
			given()
				.auth()
					.oauth2("f8153116a86253eaebe3c9146e2b8e0d49c6d47b");
		
		Response response = request.post("http://coop.apps.symfonycasts.com/api/396/chickens-feed");
		
		System.out.println(response.getStatusCode());
		System.out.println(response.prettyPrint());

		
	}
	
	//1. Generate a token at the run time by using token api
	//2. Get String tokenID from the response
	//3. hit the next api with this tokenID
	
	@Test
	public void getAuth2TokenAPITest(){
		
		RequestSpecification request = 
				RestAssured.given()
					.formParam("client_id", "NovAPIApp")
					.formParam("client_secret", "24cb2ed6a3ffceb4b10733e845e91963")
					.formParam("grant_type", "client_credentials");
		
		Response response = request.post("http://coop.apps.symfonycasts.com/token");
		
		System.out.println(response.getStatusCode());
		System.out.println(response.prettyPrint());
		
		String tokenID = response.jsonPath().getString("access_token");
		System.out.println("API token id is: "+ tokenID);
		
		//feed chicken api:
		RequestSpecification request1 =
				RestAssured.
				given()
					.auth()
						.oauth2(tokenID);
			
			Response response1 = request1.post("http://coop.apps.symfonycasts.com/api/396/chickens-feed");
			
			System.out.println(response1.getStatusCode());
			System.out.println(response1.prettyPrint());
		
	}
	
	
	

}
