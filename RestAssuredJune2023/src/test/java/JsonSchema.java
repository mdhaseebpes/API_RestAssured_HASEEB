import org.testng.annotations.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static  io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class JsonSchema {

    @Test
    public void jsonSchemaValidation(){
        baseURI = "https://api.postman.com/";
        given()
                .header("x-api-key", "PMAK-647365c9dfbb9d1cf0a80219-05f64be0ff54bbab62e6f5da8ffaf8bb53")
                .log().all()
                .when()
                .get("/workspaces")
                .then()
                .body(matchesJsonSchemaInClasspath("schemaWorkSpace.json"));
    }

}
