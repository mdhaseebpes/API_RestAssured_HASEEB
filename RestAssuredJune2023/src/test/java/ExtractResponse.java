
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class ExtractResponse {

    @org.testng.annotations.Test
    public void test(){
        baseURI = "https://api.postman.com/";
        given().log().all().
                header("x-api-key","PMAK-647365c9dfbb9d1cf0a80219-05f64be0ff54bbab62e6f5da8ffaf8bb53")
                .when().get("workspaces").
                then()
                .log().all()
                .assertThat().statusCode(200)
                .and().assertThat().body("workspaces.name", hasItems("Team Workspace", "DecTeamAPI",
                        "APIRestAssured2021", "Gravity","DecAPIGoRest", "My Workspace",
                        "JAN2021", "PracticeWorkSpaceAPI", "testSpace", "testJsonFile", "MapWorkSpace",
                        "RestAssuredObjectMapper", "PojoSpaceV1", "PojoSpaceV2", "PojoSpaceV2", "PojoSpaceV2")
                ,"workspaces[1].name",equalTo("DecTeamAPI")
                ,"workspaces.size()",is(equalTo(16)),
                       "workspaces.name",hasItem("Gravity"));

    }


    @org.testng.annotations.Test
    public void extract_response() {
        baseURI = "https://api.postman.com/";
        Response response = given().log().all().
                header("x-api-key", "PMAK-647365c9dfbb9d1cf0a80219-05f64be0ff54bbab62e6f5da8ffaf8bb53")
                .when().get("workspaces").
                then().log().all()
                .extract().response();

        System.out.println("Response is " + response.asString());

        System.out.println("workspace name is " +response.path("workspaces[0].name"));

        JsonPath jsonPath = new JsonPath(response.asString());
        System.out.println(jsonPath.getString("workspaces.id"));

    }

    @org.testng.annotations.Test
    public void extract_response_asString() {
        baseURI = "https://api.postman.com/";

      String str =  given().
                header("x-api-key", "PMAK-647365c9dfbb9d1cf0a80219-05f64be0ff54bbab62e6f5da8ffaf8bb53")
                .when().get("workspaces").
                then().
              assertThat().extract().response().asString();


        System.out.println("workspaces = " + JsonPath.from(str).getString("workspaces.name"));

    }
}
