package Authentication.authentications;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

public class OAuth1APITest {
	
	@Test
	public void TwitterStatusAPI_OAuth1_Test(){
		
		RequestSpecification request = 
				RestAssured.given()
			.auth()
				.oauth(
						"HGgqWV2t6YnEhhBvuDnAcYlli", 
						"LTqWrkWEkcW7FsZO93gq1Z9IfnED8LSY4cpCBAiQGY8AXrLloQ",
						"220976784-2eQZmLlaEPxeNb3Wywy24dXldUlq1ge29afKA2AR",
						"SgXmzqweFoaSilrWLCwYXCYKme4Rk8oXlK4rtE1CUrJN7");
		
	Response response =	request.post("https://api.twitter.com/1.1/statuses/update.json?status=This is tweet from Rest Assured Code");
		
	System.out.println(response.getStatusCode());
	System.out.println(response.prettyPrint());
		
		
		
		
		
	}
	
	
	
	
	
	

}
