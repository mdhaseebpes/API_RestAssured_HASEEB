import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.QueryableRequestSpecification;
import io.restassured.specification.SpecificationQuerier;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static  org.hamcrest.MatcherAssert.assertThat;

public class RequestResponseSpecificationDefault {

    @BeforeClass
    public void beforeClass(){

        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder()
                .setBaseUri("https://api.postman.com/")
                .addHeader("x-api-key","PMAK-647365c9dfbb9d1cf0a80219-05f64be0ff54bbab62e6f5da8ffaf8bb53")
                .log(LogDetail.ALL);

     RestAssured.requestSpecification = requestSpecBuilder.build();

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .log(LogDetail.ALL);

        RestAssured.responseSpecification = responseSpecBuilder.build();
    }


    @Test
    public void testDefaultRequestSpecification(){
                 Response response = get("workspaces");
                 assertThat(response.statusCode(),equalTo(200));
    }

    @Test
    public void fetchRequestParameter(){
        QueryableRequestSpecification queryableRequestSpecification =
                SpecificationQuerier.query(RestAssured.requestSpecification);

        System.out.println(queryableRequestSpecification.getHeaders());
        System.out.println(queryableRequestSpecification.getBaseUri());

    }
}
