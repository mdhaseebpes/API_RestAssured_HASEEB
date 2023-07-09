import io.restassured.config.LogConfig;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import static io.restassured.RestAssured.*;

public class loggingMethods {

    @Test
    public void logIfValidationFails(){
        baseURI = "https://api.postman.com/";
        //logging only if there is a failure using log().ifValidationFails()
        given().
                header("x-api-key", "PMAK-647365c9dfbb9d1cf0a80219-05f64be0ff54bbab62e6f5da8ffaf8bb53")
                .log().ifValidationFails()
                .when().get("workspaces").
                then().
                log().ifValidationFails()
                .assertThat()
                .statusCode(201);
    }

    @Test
    public void logIfError(){
        baseURI = "https://api.postman.com/";
        //logging only if there is a error applicable only for response then() using log().ifError()
        given().
                header("x-api-key", "PMAK-647365c9dfbb9d1cf0a80219-05f64be0ff54bbab62e6f5da8ffaf8bb53")
                .when().get("workspaces").
                then()
               .log().ifError()
                .assertThat()
                .statusCode(201);
    }

    @Test
    public void log_only_if_validation_fails(){

        baseURI = "https://api.postman.com/";
        //Enable logging of both the request and the response if REST Assureds test validation fails.
        given().
                header("x-api-key", "PMAK-647365c9dfbb9d1cf0a80219-05f64be0ff54bbab62e6f5da8ffaf8bb53")
                .config(config.logConfig(LogConfig.logConfig().enableLoggingOfRequestAndResponseIfValidationFails()))
                .when().get("workspaces").
                then()
                .assertThat()
                .statusCode(201);
    }


    @Test
    public void blackList_Headers(){

        baseURI = "https://api.postman.com/";

        given()
                .header("x-api-key", "PMAK-647365c9dfbb9d1cf0a80219-05f64be0ff54bbab62e6f5da8ffaf8bb53")
                .config(config.logConfig(LogConfig.logConfig().blacklistHeader("x-api-key")))
                .log().all()
                .when().get("/workspaces")
                .then().
                assertThat().statusCode(201);
    }


    @Test
    public void blackList_Headers_02(){

        Set<String> headers = new HashSet<>();
        headers.add("x-api-key");
        headers.add("Accept");

        baseURI = "https://api.postman.com/";

        given()
                .header("x-api-key", "PMAK-647365c9dfbb9d1cf0a80219-05f64be0ff54bbab62e6f5da8ffaf8bb53")
                .config(config.logConfig(LogConfig.logConfig().blacklistHeaders(headers)))
                .log().all()
                .when().get("/workspaces")
                .then().
                assertThat().statusCode(200);
    }

    @Test
    public void headers_parameters(){
        HashMap<String,String> headersMap = new HashMap<>();
        headersMap.put("x-api-key", "PMAK-647365c9dfbb9d1cf0a80219-05f64be0ff54bbab62e6f5da8ffaf8bb53");
        headersMap.put("Accept","*/*");
        headersMap.put("Content-Type","application/json");

        baseURI = "https://api.postman.com/";

        given()
                .headers(headersMap)
                .log().all()
                .when().get("/workspaces")
                .then().log().all().
                assertThat().statusCode(200);
    }

}
