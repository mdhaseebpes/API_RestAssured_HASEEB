import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Collection;
import java.util.Collections;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class HamcrestAssertion {

    @Test
    public void hamcrest_assert(){

        baseURI = "https://api.postman.com/";
        Response response = given().log().all().
                header("x-api-key", "PMAK-647365c9dfbb9d1cf0a80219-05f64be0ff54bbab62e6f5da8ffaf8bb53")
                .when().get("workspaces").
                then().log().all()
                .extract().response();

       String name = response.path("workspaces[1].name");

        assertThat(name,equalTo("DecTeamAPI"));

        Assert.assertEquals(response.path("workspaces[2].name"),equalTo("APIRestAssured2021"));
    }

    @Test
    public void hamcrest_assertion_Methods() {

        baseURI = "https://api.postman.com/";
        Response response = given().log().all().
                header("x-api-key", "PMAK-647365c9dfbb9d1cf0a80219-05f64be0ff54bbab62e6f5da8ffaf8bb53")
                .when().get("workspaces").
                then().log().all()
                .extract().response();

       // JsonPath jsonPath = new JsonPath(response.asString());

        //contains() checks all elements are in collections and in a strict order
        assertThat(response.path("workspaces.name"),
                contains("Team Workspace", "DecTeamAPI",
                        "APIRestAssured2021", "Gravity","DecAPIGoRest", "My Workspace",
                        "JAN2021", "PracticeWorkSpaceAPI", "testSpace", "testJsonFile", "MapWorkSpace",
                        "RestAssuredObjectMapper", "PojoSpaceV1", "PojoSpaceV2", "PojoSpaceV2", "PojoSpaceV2"));

        //containsInAnyOrder() checks all elements are in collection and in any order
        assertThat(response.path("workspaces.name"),
                containsInAnyOrder("DecTeamAPI","Team Workspace",
                        "APIRestAssured2021", "Gravity","DecAPIGoRest", "My Workspace",
                        "JAN2021", "PracticeWorkSpaceAPI", "testSpace", "testJsonFile", "MapWorkSpace",
                        "RestAssuredObjectMapper", "PojoSpaceV1",  "PojoSpaceV2", "PojoSpaceV2","PojoSpaceV2"));

    }

    @Test
    public void hamcrest_assertion_Methods_02() {

        baseURI = "https://api.postman.com/";
        Response response = given()
                .header("x-api-key", "PMAK-647365c9dfbb9d1cf0a80219-05f64be0ff54bbab62e6f5da8ffaf8bb53")
                .log().all()
                .when().get("workspaces").
                then().log().ifError()
                .extract().response();

        //check if collection is empty
      /*  assertThat(response.path("workspaces.name"),
                empty());*/

        //not(empty()) -checks if collection is not empty
        assertThat(response.path("workspaces.name"),
                not(empty()));

        // not(emptyArray()) -checks if Array is not empty
        assertThat(response.path("workspaces.name"),
                not(emptyArray()));

        //hasSize() -checks the size of collection
        assertThat(response.path("workspaces.name"),
                hasSize(16));


        //everyItem(startsWith()) -check if every item in collection starts with specified string
       /* assertThat(response.path("workspaces.visibility"),
                everyItem(startsWith("personal")));*/
    }

    @Test
    public void hamcrest_assertion_Methods_Map() {

        baseURI = "https://api.postman.com/";
        Response response = given().log().all().
                header("x-api-key", "PMAK-647365c9dfbb9d1cf0a80219-05f64be0ff54bbab62e6f5da8ffaf8bb53")
                .when().get("workspaces").
                then().log().everything()
                .extract().response();

        //hasKey() - check if map has the specified key [value is not checked]
        assertThat(response.path("workspaces[0]"),hasKey("id"));

        //hasValue() - check if map has the specified value [key is not checked]
        assertThat(response.path("workspaces[0]"),hasValue("3867ba57-a22f-461f-8832-eaf34cf777d9"));


        //hasEntry() - check if map has the specified key value pair
        assertThat(response.path("workspaces[2]"),hasEntry("name","APIRestAssured2021"));

        //is(Collections.EMPTY_MAP) - Maps check is empty
        //not(is(Collections.EMPTY_MAP)) - Maps check is not empty
        assertThat(response.path("workspaces[0]"),not(is(Collections.EMPTY_MAP)));

        //allOf()- Matches if all matchers
        assertThat(response.path("workspaces[0].name"),allOf(startsWith("Team"), containsString("Workspace")));

        //anyOf()- Matches if any of the matchers matches
        assertThat(response.path("workspaces[1].name"),anyOf(containsString("DecTeam")));

    }
}
