import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.equalTo;

public class ResponseSpecificationsMethods {

    RequestSpecification requestSpecification;
    ResponseSpecification responseSpecification;

    @BeforeClass
    public void beforeClass(){
        //with() and given() same methods
/*        requestSpecification = with().log().all().
                baseUri("https://api.postman.com/")
                .header("x-api-key","PMAK-647365c9dfbb9d1cf0a80219-05f64be0ff54bbab62e6f5da8ffaf8bb53");*/
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder()
                .setBaseUri("https://api.postman.com/")
                .addHeader("x-api-key","PMAK-647365c9dfbb9d1cf0a80219-05f64be0ff54bbab62e6f5da8ffaf8bb53")
                .log(LogDetail.ALL);

        requestSpecification =  requestSpecBuilder.build();

        responseSpecification = RestAssured.expect().statusCode(200)
                .contentType(ContentType.JSON)
                .log().all();
    }

    @Test
    public void requestSpecification(){
        given(requestSpecification)
                .when().get("workspaces")
                .then().log().all().spec(responseSpecification)
                .and().assertThat().body("workspaces.name", hasItems("Team Workspace", "DecTeamAPI",
                                "APIRestAssured2021", "Gravity","DecAPIGoRest", "My Workspace",
                                "JAN2021", "PracticeWorkSpaceAPI", "testSpace", "testJsonFile", "MapWorkSpace",
                                "RestAssuredObjectMapper", "PojoSpaceV1", "PojoSpaceV2", "PojoSpaceV2", "PojoSpaceV2")
                        ,"workspaces[1].name",equalTo("DecTeamAPI")
                        ,"workspaces.size()",is(equalTo(16)),
                        "workspaces.name",hasItem("Gravity"));
    }

    @Test
    public void requestSpecification_Test01(){
        Response response = given(requestSpecification)
                .header("test" ,"extraHeader")
                .get("workspaces")
                .then().log().all().spec(responseSpecification)
                .extract().response();

        assertThat(response.statusCode(),equalTo(200));
        assertThat(response.path("workspaces[1].name"), equalTo("DecTeamAPI"));

    }
}
