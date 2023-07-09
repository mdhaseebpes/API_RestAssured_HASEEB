package Authentication;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Base64;
import java.util.HashMap;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;


public class Oauth2GmailAPI {

   final String  access_token = "ya29.a0AWY7CklB4qE1MACsCuF716SITL8F49dzgkul6J1rNqjQs5Tyg1pRbTdOo2EStmvkBSX_Aue4vpGHM4NE07D1Y3rvGKYPzMGuV3qYIB2OZ8dSAN4to5VEA8s2fVC22UScdyDGXObhHu27aHHzLhcYxdWdxqg1aCgYKAVwSARESFQG1tDrpxo4yunTe8_cMtGybN9fuqw0163";

    @BeforeClass
    public void beforeClass(){

        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder()
                .setBaseUri("https://gmail.googleapis.com")
                .addHeader("Authorization","Bearer " +access_token)
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL);


        requestSpecification = requestSpecBuilder.build();

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .log(LogDetail.ALL);

      responseSpecification =  responseSpecBuilder.build();

    }

    @Test
    public void getEmailDetails(){
      Response response =  given()
                .get("/gmail/v1/users/waiz1211khan@gmail.com/profile")
                .then().extract().response();

        System.out.println(response.asPrettyString());

    }


    @Test
    public void sendEmail(){
        String emailBody = "From:  waiz1211Khan@gmail.com\n" +
                "To:  waiz1211Khan@gmail.com\n" +
                "Subject: Test -Journey In progress";

        String base64UrlEncoded = Base64.getUrlEncoder().encodeToString(emailBody.getBytes());

        HashMap<String,String> map = new HashMap<>();
        map.put("raw",base64UrlEncoded);

        Response response =  given()
                .pathParams("email", "waiz1211khan@gmail.com")
                .body(map)
                .post("/gmail/v1/users/{email}/messages/send")
                .then().extract().response();

        System.out.println(response.asPrettyString());

    }


}
