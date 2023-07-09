package post;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.config.EncoderConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


public class TestPost {

    RequestSpecification requestSpecification;

    ResponseSpecification responseSpecification;

    @BeforeClass
    public void setUp(){
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder()
                              .setBaseUri("https://eeebece1-574c-4429-a82b-89811a20da12.mock.pstmn.io/post")
                .addHeader("x-mock-match-request-body","true")
                .setConfig(config.encoderConfig(EncoderConfig.encoderConfig()
                        .appendDefaultContentCharsetToContentTypeIfUndefined(false)))
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL);
        RestAssured.requestSpecification = requestSpecBuilder.build();

        ResponseSpecBuilder responseSpecBuilder =  new ResponseSpecBuilder()
                .expectStatusCode(201)
                .expectContentType(ContentType.JSON)
                .log(LogDetail.ALL);

        RestAssured.responseSpecification = responseSpecBuilder.build();

    }

    @Test
    public void createWorkSpace(){

        HashMap<String, Object> main = new HashMap<>();

        ArrayList<Map> employees = new ArrayList<>();
        ArrayList<Map> products = new ArrayList<>();

        HashMap<String, Object> subEmployee = new HashMap<>();
        subEmployee.put("name", "John Doe");
        subEmployee.put("age", 30);
        subEmployee.put("department", "HR");

        HashMap<String, Object> subEmployee1 = new HashMap<>();
        subEmployee1.put("name", "Michael Johnson");
        subEmployee1.put("age", 40);
        subEmployee1.put("department", "Finance");


        HashMap<String, Object> subProducts = new HashMap<>();
        subProducts.put("name", "Apples");
        subProducts.put("price", 1.99);
        subProducts.put("quantity", 10);

        HashMap<String, Object> subProducts1 = new HashMap<>();
        subProducts1.put("name", "Bananas");
        subProducts1.put("price", 0.99);
        subProducts1.put("quantity", 15);

        employees.add(subEmployee);
        employees.add(subEmployee1);

        products.add(subProducts);
        products.add(subProducts1);

        main.put("employees", employees);
        main.put("products" , products);

     Response response = given().
               body(main).
                post();

     assertThat(response.path("message"),equalTo("successfully Created"));


    }
}
