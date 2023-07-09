package params;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.config.EncoderConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.util.HashMap;

import static io.restassured.RestAssured.*;

public class RequestParameters {

    RequestSpecification requestSpecification;
    ResponseSpecification responseSpecification;

    @BeforeClass
    public void beforeClass(){
        RequestSpecBuilder requestSpecBuilder = new
                RequestSpecBuilder().setBaseUri("https://postman-echo.com")
                .log(LogDetail.ALL);

      RestAssured.requestSpecification = requestSpecBuilder.build();


        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder()
                .expectStatusCode(200).log(LogDetail.ALL);

     RestAssured.responseSpecification = responseSpecBuilder.build();
    }

    @Test
    public void QueryParams(){
      Response response =  with().
              /*  queryParam("param1" ,"value1").
                queryParam("param2" ,"value2")*/
              param("param1" ,"value1").
              param("param2" ,"value2")

                .get("/get");

        System.out.println(response.asPrettyString());
    }

    @Test
    public void QueryParams_Map(){

        HashMap<String ,String> hashMap = new HashMap<>();
        hashMap.put("param1" ,"value1");
        hashMap.put("param2" ,"value2");

        Response response =  given().
               // queryParams(hashMap)
                params(hashMap)
                .get("/get");

        System.out.println(response.asPrettyString());
    }

    @Test
    public void QueryParams_multiValue(){
        Response response =  with().
                  queryParam("param2" ,"value2,value2;value3")
              //  param("param2" ,"value2,value3,value4")

                .get("/get");

        System.out.println(response.asPrettyString());
    }

    @Test
    public void path_parameter(){
        HashMap<String,String> path = new HashMap<>();
        path.put("userId", "10");

        given()
                .baseUri("https://reqres.in")
               .pathParam("userId", "10")
                //.pathParams(path)
                .log().all()
                .when()
                .get("/api/users/{userId}")
                .then()
                .log().all().assertThat().statusCode(200);
    }


    @Test
    public void multi_formData(){
        given()
                .multiPart("foo1" ,"value1")
                .multiPart("foo2" ,"value1")
                .log().all()
                .when()
                .post("/post")
                .then()
                .log().all().assertThat().statusCode(200);
    }


    @Test
    public void multi_FileUpload(){
String attributes = "  {\n" +
        "            \"id\": \"dd4d240d-3141-42a3-88f0-592547109007\",\n" +
        "            \"name\": \"Map WorkSpace\",\n" +
        "            \"type\": \"team\",\n" +
        "            \"visibility\": \"team\"\n" +
        "        }";
        given()
                .multiPart("file",new File("multiPartFile.txt"))
                .multiPart("attribute" ,attributes, "application/json")
                .log().all()
                .when()
                .post("/post")
                .then()
                .log().all().assertThat().statusCode(200);
    }

    @Test
    public void form_urlEncoded(){
        given()
                .config(config.encoderConfig(EncoderConfig.encoderConfig()
                        .appendDefaultContentCharsetToContentTypeIfUndefined(false)))
                .formParam("fo01","value1")
                .formParam("fo02","value2")
                .log().all()
                .when()
                .post("/post")
                .then()
                .log().all().assertThat().statusCode(200);
    }

}

