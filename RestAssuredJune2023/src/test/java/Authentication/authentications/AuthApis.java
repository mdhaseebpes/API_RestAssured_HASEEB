package Authentication.authentications;

import io.restassured.RestAssured;
import io.restassured.authentication.FormAuthConfig;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;


public class AuthApis {
	
		//basic
		//preemptive
		//digest
		//form
		//Oauth1
		//Oauth2
	
	@Test
	public void basic_auth_Preemptive_API_Test(){
		
		given().log().all()
		.auth()
		.preemptive()
			.basic("admin", "admin")
		.when().log().all()
			.get("http://the-internet.herokuapp.com/basic_auth")
		.then().log().all()
			.assertThat()
				.statusCode(200);
		
	}
	
	@Test
	public void basic_auth_API_Test(){
		
		given().log().all()
		.auth()
			.basic("admin", "admin")
		.when().log().all()
			.get("http://the-internet.herokuapp.com/basic_auth")
		.then().log().all()
			.assertThat()
				.statusCode(200);
	}
	
	
	@Test
	public void basic_auth_digest_API_Test(){
		
		given().log().all()
		.auth()
			.digest("admin", "admin")
		.when().log().all()
			.get("http://the-internet.herokuapp.com/basic_auth")
		.then().log().all()
			.assertThat()
				.statusCode(200);
	}
	
	
	@Test
	public void basic_auth_form_API_Test(){
		
		given().log().all()
		.auth()
			.form("admin", "admin", new FormAuthConfig("https://classic.freecrm.com/system/authenticate.cfm", "username", "password"))
		.when().log().all()
			.get("https://classic.freecrm.com/system/authenticate.cfm")
		.then().log().all()
			.assertThat()
				.statusCode(200);
	}
	
	
	
	

	//Oauth2.0:
	//if you are using:
	//1. with header: append your token with Bearer keyword
	//2. with oauth2() method: No need to add Bearer, just pass the token value
	@Test
	public void OAuth2_API_Test(){
		
		given()
			.auth()
				.oauth2("_FWTKt73f0EeVrfWj7d3sKoLMnw_9dqVcs0k")
		.when().log().all()
			.get("https://gorest.co.in/public-api/users?first_name=Elva").
		then().log().all()
			.assertThat()
				.statusCode(200);
	}
	
	
	@Test
	public void OAuth_API_Test_With_AuthHeader(){
		
		RestAssured.baseURI = "https://gorest.co.in";
		
		given().log().all()
			.contentType("application/json")
			.header("Authorization","Bearer _FWTKt73f0EeVrfWj7d3sKoLMnw_9dqVcs0k")
		.when().log().all()
			.get("/public-api/users?first_name=Elva")
		.then().log().all()
			.statusCode(200)
			.and()
			.header("Server", "nginx");
		
	}
	
	@Test
	public void OAuth_API_WithTwoQueryParams_API_Test(){
		
		RestAssured.baseURI = "https://gorest.co.in";

		given().log().all()
			.contentType("application/json")
			.header("Authorization","Bearer _FWTKt73f0EeVrfWj7d3sKoLMnw_9dqVcs0k")
			.queryParam("first_name", "John")
			.queryParam("gender", "male")
		.when().log().all()
			.get("public-api/users")
		.then().log().all()
			.statusCode(200)
			.and()
			.header("Server", "nginx");
	}
	
	

	
	
	

}
