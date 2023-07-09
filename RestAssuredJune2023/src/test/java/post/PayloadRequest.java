package post;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class PayloadRequest {

    RequestSpecification requestSpecification;

    @BeforeClass
    public void beforeClass(){
        RequestSpecBuilder requestSpecBuilder= new RequestSpecBuilder()
                .setBaseUri("https://api.postman.com/")
                .addHeader("x-api-key","PMAK-647365c9dfbb9d1cf0a80219-05f64be0ff54bbab62e6f5da8ffaf8bb53")
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL);

      requestSpecification = requestSpecBuilder.build();

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .log(LogDetail.ALL);

        RestAssured.responseSpecification = responseSpecBuilder.build();
    }

    @Test
    public void createWorkSpace_File(){
        File file  = new File("src/main/resources/workspace.json");
        given()
                .body(file)
                .post("/workspaces")
                .then()
                .body("workspace.name" , equalTo("Demo Workspace")
                        ,"workspace.id", Matchers.matchesPattern("^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$"));

    }

    @Test
    public void createWorkSpace_HashMap(){
        HashMap<String,Object> mainObject = new HashMap<String,Object>();

        HashMap<String,String> subJson =  new HashMap<String,String>();
        subJson.put("name","Map WorkSpace");
        subJson.put("type","team");
        subJson.put("description","Automate Test");

        mainObject.put("workspace",subJson);

        given()
                .body(mainObject).
                when().
                post("/workspaces")
                .then()
                .body("workspace.name" , equalTo("Map WorkSpace")
                        ,"workspace.id", Matchers.matchesPattern("^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$"));

    }


}
