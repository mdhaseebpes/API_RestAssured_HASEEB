package post;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.config.EncoderConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.config;
import static io.restassured.RestAssured.with;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class PayLoadMock {

    @BeforeClass
    public void beforeClass(){
        RequestSpecBuilder requestSpecBuilder= new RequestSpecBuilder()
                .setBaseUri("https://eeebece1-574c-4429-a82b-89811a20da12.mock.pstmn.io/post")
                .addHeader("x-mock-match-request-body","true")
                .setConfig(config.encoderConfig(EncoderConfig.encoderConfig()
                        .appendDefaultContentCharsetToContentTypeIfUndefined(false)))
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL);

        RestAssured.requestSpecification = requestSpecBuilder.build();

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder()
                .expectStatusCode(201)
                .expectContentType(ContentType.JSON)
                .log(LogDetail.ALL);

        RestAssured.responseSpecification = responseSpecBuilder.build();
    }

    @Test
    public void mockPost_ArrayList_Payload(){

        HashMap<String ,Object> mainObj = new HashMap<>();

        List<Map> employees = new ArrayList<>();
        List<Map> products = new ArrayList<>();

        HashMap<String,Object> empObj = new HashMap<>();
        empObj.put("name","John Doe");
        empObj.put("age",30);
        empObj.put("department","HR");

        HashMap<String,Object> empObj01 = new HashMap<>();
        empObj01.put("name","Michael Johnson");
        empObj01.put("age",40);
        empObj01.put("department","Finance");


        HashMap<String,Object> prodObj = new HashMap<>();
        prodObj.put("name","Apples");
        prodObj.put("price",1.99);
        prodObj.put("quantity",10);

        HashMap<String,Object> prodObj01 = new HashMap<>();
        prodObj01.put("name","Bananas");
        prodObj01.put("price",0.99);
        prodObj01.put("quantity",15);

        //adding employees arrays into list
        employees.add(empObj);
        employees.add(empObj01);

        //adding products arrays into list
        products.add(prodObj);
        products.add(prodObj01);

        //main json containing all
        mainObj.put("employees",employees);
        mainObj.put("products",products);

      Response response = with()
                .body(mainObj)
                .post();

      assertThat(response.path("message"), equalTo("successfully Created"));
    }


    @Test
    public void complexJsonMock(){

        ArrayList<Integer> alternateList = new ArrayList<>();
        alternateList.add(10);
        alternateList.add(20);
        alternateList.add(20);

        HashMap<String,Object> addressMap = new HashMap<>();
        addressMap.put("street" ,"456 Elm Street");
        addressMap.put("city" ,"San Francisco");
        addressMap.put("state" ,"CA");
        addressMap.put("zipcode" ,"94107");
        addressMap.put("alternate", alternateList);

        HashMap<String, String> phoneMap = new HashMap<>();
        phoneMap.put("type","home");
        phoneMap.put("number","555-1234");

        HashMap<String, String> phoneMap01 = new HashMap<>();
        phoneMap01.put("type","work");
        phoneMap01.put("number","555-5678");

        ArrayList<HashMap<String, String>> phoneNumbersList = new ArrayList<>();
        phoneNumbersList.add(phoneMap);
        phoneNumbersList.add(phoneMap01);

        HashMap<String, Object> employeeMap = new HashMap<>();
        employeeMap.put("firstName","John");
        employeeMap.put("lastName","Doe");
        employeeMap.put("email","johndoe@example.com");
        employeeMap.put("phoneNumbers",phoneNumbersList);

        ArrayList<Object> employeeList = new ArrayList<>();
        employeeList.add(employeeMap);

        HashMap<String,Object> companyMap = new HashMap<>();
        companyMap.put("name","Acme Corporation");
        companyMap.put("address",addressMap);
        companyMap.put("employees",employeeList);

        HashMap<String,Object> mainObject = new HashMap<>();
        mainObject.put("company",companyMap);

        Response response = with()
                .body(mainObject)
                .post();

        assertThat(response.path("message"), equalTo("employee record is created"));
        assertThat(response.path("id"), equalTo("4567890"));

    }



}
