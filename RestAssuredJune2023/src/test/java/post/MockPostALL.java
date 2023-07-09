package post;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.config.EncoderConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import serializeDeserialize.pojoAll.Address;
import serializeDeserialize.pojoAll.MainEmployee;
import serializeDeserialize.pojoAll.PhoneNumbers;

import java.io.File;
import java.util.*;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;



public class MockPostALL {

    RequestSpecification requestSpecification;

    @BeforeClass
    public void setUp(){

        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder()
                .setBaseUri("https://eeebece1-574c-4429-a82b-89811a20da12.mock.pstmn.io")
                .addHeader("x-mock-match-request-body", "true")
                .setConfig(config.encoderConfig(EncoderConfig.encoderConfig()
                        .appendDefaultContentCharsetToContentTypeIfUndefined(false)))
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL);

        RestAssured.requestSpecification = requestSpecBuilder.build();

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .log(LogDetail.ALL);

        RestAssured.responseSpecification = responseSpecBuilder.build();


    }

    @Test
    public void ObjectCreationTest(){

        //Main
        HashMap<String, Object> mainObj = new LinkedHashMap<>();


        HashMap<String,String> address1 = new HashMap<>();
        address1.put("street","123 Main St");
        address1.put("city", "New York");
        address1.put("state","NY");
        address1.put("zipCode", "10001");

      /*  HashMap<String,Object> addressMain = new HashMap<>();
        addressMain.put("address",address1);*/

        //phone number
        List<Map> phone = new ArrayList<>();

        HashMap<String,String> phone01 = new HashMap<>();
        phone01.put("type","home");
        phone01.put("number","555-1234");

        HashMap<String,String> phone02 = new HashMap<>();
        phone02.put("type","work");
        phone02.put("number","555-5678");


        //phone Numbers
        phone.add(phone01);
        phone.add(phone02);

        //main
        mainObj.put("name","John Doe");
        mainObj.put("age",30);
        mainObj.put("email","johndoe@example.com");
        mainObj.put("address", address1);
        mainObj.put("phoneNumbers", phone);
        mainObj.put("active", true);

     Response response =   given()
                .body(mainObj)
                .post("/postSimplePojo");

     assertThat(response.path("message"), equalTo("employee created successfully"));
    }

    @Test
    public void pojoMock(){
        Address address = new Address("123 Main St","New York", "NY","10001");

        PhoneNumbers phoneNumbers = new PhoneNumbers("home","555-1234");
        PhoneNumbers phoneNumbers1= new PhoneNumbers("work","555-5678");

        List<PhoneNumbers> phoneNumbersList = new ArrayList<>();
        phoneNumbersList.add(phoneNumbers);
        phoneNumbersList.add(phoneNumbers1);

        MainEmployee mainEmployee = new MainEmployee("John Doe"
        ,30,"johndoe@example.com",address,phoneNumbersList,true);

        Response response =   given()
                .body(mainEmployee)
                .post("/postSimplePojo");

        assertThat(response.path("message"), equalTo("employee created successfully"));
    }


    @Test
    public void filePathPost(){
        File file = new File("src/main/resources/employee.json");

        Response response =   given()
                .body(file)
                .post("/postSimplePojo");

        assertThat(response.path("message"), equalTo("employee created successfully"));
    }
}
