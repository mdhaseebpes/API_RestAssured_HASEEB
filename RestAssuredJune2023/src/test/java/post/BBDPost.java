package post;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.with;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class BBDPost {

    @BeforeClass
    public void beforeClass(){
        RequestSpecBuilder requestSpecBuilder= new RequestSpecBuilder()
                .setBaseUri("https://api.postman.com/")
                .addHeader("x-api-key","PMAK-647365c9dfbb9d1cf0a80219-05f64be0ff54bbab62e6f5da8ffaf8bb53")
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL);

        RestAssured.requestSpecification = requestSpecBuilder.build();

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .log(LogDetail.ALL);

        RestAssured.responseSpecification = responseSpecBuilder.build();
    }

    @Test
    public void createWorkSpace(){
       File file  = new File("src/main/resources/workspace.json");
        given()
                .body(file)
                .post("/workspaces")
                .then()
                .body("workspace.name" , equalTo("Demo Workspace")
                ,"workspace.id", Matchers.matchesPattern("^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$"));

    }

    @Test
    public void createWorkSpace_NonBDD(){
        String payLoad = "{\n" +
                "    \"workspace\": {\n" +
                "        \"name\": \"Demo Workspace3\",\n" +
                "        \"type\": \"team\",\n" +
                "        \"description\": \"This workspace contains all your team's collections and environments that were previously in the Team Library, as well as any monitors, mock servers or integrations that were shared with the team.\"\n" +
                "     \n" +
                "    }\n" +
                "}";
     Response response =  with()
                .body(payLoad)
                .post("/workspaces");

     assertThat(response.path("workspace.name") , equalTo("Demo Workspace3"));
     assertThat(response.path("workspace.id"), Matchers.matchesPattern("^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$"));


    }

    @Test
    public void deleteWorkspace(){
        String workspaceID = "7b6acb1a-8328-4a6e-9e2f-3c9cca369670";
     Response response =   with()
                .pathParam("workspaceId",workspaceID)
               .delete("workspaces/{workspaceId}");
            // .delete("workspaces/"+workspaceID);

     assertThat(response.path("workspace.id"),Matchers.matchesPattern("^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$"));
     assertThat(response.path("workspace.id"),equalTo(workspaceID));
    }
}
