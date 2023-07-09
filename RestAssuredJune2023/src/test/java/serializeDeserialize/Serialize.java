package serializeDeserialize;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.restassured.config.EncoderConfig;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import java.awt.geom.RectangularShape;
import java.util.HashMap;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class Serialize {

    @Test
    public void createWorkSpace_HashMap_Serialize(){
        HashMap<String,Object> mainObject = new HashMap<String,Object>();

        HashMap<String,String> subJson =  new HashMap<String,String>();
        subJson.put("name","Serialize WorkSpace");
        subJson.put("type","team");
        subJson.put("description","Automate Test");

        mainObject.put("workspace",subJson);

        String mainObjStr;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
             mainObjStr=objectMapper.writeValueAsString(mainObject);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        baseURI = "https://api.postman.com";
        given().log().all()
                .header("x-api-key","PMAK-647365c9dfbb9d1cf0a80219-05f64be0ff54bbab62e6f5da8ffaf8bb53")
                .body(mainObjStr).
                when().
                post("/workspaces")
                .then()
                .body("workspace.name" , equalTo("Serialize WorkSpace")
                        ,"workspace.id", Matchers.matchesPattern("^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$"))
                .log().all();

    }

    @Test
    public void createWorkSpace_ObjectNode_Serialize() throws JsonProcessingException {
        //using ObjectNode
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("name","Serialize WorkSpace 1");
        objectNode.put("type","team");
        objectNode.put("description","Automate Test");

        ObjectNode mainObjectNode = objectMapper.createObjectNode();
        mainObjectNode.put("workspace", objectNode);

     //  String objStr =  objectMapper.writeValueAsString(mainObjectNode);

        baseURI = "https://api.postman.com";
        given().log().all()
                .header("x-api-key","PMAK-647365c9dfbb9d1cf0a80219-05f64be0ff54bbab62e6f5da8ffaf8bb53")
                .body(mainObjectNode).
                when().
                post("/workspaces")
                .then()
                .body("workspace.name" , equalTo("Serialize WorkSpace 1")
                        ,"workspace.id", Matchers.matchesPattern("^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$"))
                .log().all();


    }

    @Test
    public void createWorkSpace_Array_ObjectNode_Serialize() throws JsonProcessingException {
        //using ObjectNode for Arrays and Objects
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode mainObj = objectMapper.createObjectNode();

        ArrayNode employees = objectMapper.createArrayNode();
        ArrayNode products = objectMapper.createArrayNode();

        ObjectNode empObj =  objectMapper.createObjectNode();
        empObj.put("name","John Doe");
        empObj.put("age",30);
        empObj.put("department","HR");

        ObjectNode empObj01 =  objectMapper.createObjectNode();
        empObj01.put("name","Michael Johnson");
        empObj01.put("age",40);
        empObj01.put("department","Finance");

        ObjectNode prodObj =  objectMapper.createObjectNode();
        prodObj.put("name","Apples");
        prodObj.put("price",1.99);
        prodObj.put("quantity",10);

        ObjectNode prodObj01 =  objectMapper.createObjectNode();
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
        mainObj.set("employees",employees);
        mainObj.set("products",products);

        baseURI = "https://eeebece1-574c-4429-a82b-89811a20da12.mock.pstmn.io/post";
      Response response = given().log().all()
                .header("x-mock-match-request-body","true")
              .config(config.encoderConfig(EncoderConfig.encoderConfig()
                      .appendDefaultContentCharsetToContentTypeIfUndefined(false)))
              .contentType(ContentType.JSON)
                .body(mainObj).
                when().
                post()
                .then().log().all()
                .extract().response();

        assertThat(response.path("message"), equalTo("successfully Created"));

    }
}
