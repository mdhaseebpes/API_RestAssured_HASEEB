package serializeDeserialize;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.config.EncoderConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import serializeDeserialize.complexpojo.*;
import serializeDeserialize.pojo.Address;
import serializeDeserialize.pojo.Employee;
import serializeDeserialize.pojo.MainWorkspace;
import serializeDeserialize.pojo.Workspace;

import java.util.ArrayList;
import java.util.HashMap;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class PojoCreation {

    public void beforeClass() {

        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder()
                .setBaseUri("https://eeebece1-574c-4429-a82b-89811a20da12.mock.pstmn.io/postSimplePojo")
                .addHeader("x-mock-match-request-body", "true")
                .setContentType(ContentType.JSON)
                .setConfig(config.encoderConfig(EncoderConfig.encoderConfig()
                        .appendDefaultContentCharsetToContentTypeIfUndefined(false)))
                .log(LogDetail.ALL);

        RestAssured.requestSpecification = requestSpecBuilder.build();

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .log(LogDetail.ALL);

        RestAssured.responseSpecification = responseSpecBuilder.build();
    }

    @Test
    public void pojoTest() throws JsonProcessingException {

        Address address = new Address("123 Main Street", "New York", "NY", "10001");

        ArrayList<String> skills = new ArrayList<>();
        skills.add("JavaScript");
        skills.add("Java");

        Employee employee = new Employee("John Doe", 30, "johndoe@example.com", address, skills);

        Response response = given()
                .body(employee)
                .post().then().extract().response();

        Employee actualResponse = response.as(Employee.class);

        assertThat(actualResponse.getName(), equalTo("John Doe"));
        assertThat(actualResponse.getAge(), equalTo(30));
        assertThat(actualResponse.getAddress().getZip(), equalTo("10001"));
        assertThat(actualResponse.getSkills().get(1), equalTo("Java"));

        //Comparing enter json response with actual request(payload)
        ObjectMapper objectMapper = new ObjectMapper();
        String actualStrRes = objectMapper.writeValueAsString(actualResponse);
        String expectStr = objectMapper.writeValueAsString(employee);

        assertThat(objectMapper.readTree(actualStrRes), equalTo(objectMapper.readTree(expectStr)));


    }

    @Test(dataProvider = "createworkspace")
    public void pojo_create_Test(String name , String type , String description) throws JsonProcessingException {
        Workspace workspace = new Workspace(name, type,description);

        HashMap<String,String> hashMap  = new HashMap<>();
        workspace.setMyHashMap(hashMap);

        MainWorkspace mainWorkspace = new MainWorkspace(workspace);

        baseURI = "https://api.postman.com";
        Response response = given().log().all()
                .header("x-api-key", "PMAK-647365c9dfbb9d1cf0a80219-05f64be0ff54bbab62e6f5da8ffaf8bb53")
                .body(mainWorkspace)
                .post("/workspaces")
                .then().log().all().extract().response();

        MainWorkspace actualResponse = response.as(MainWorkspace.class);

        assertThat(actualResponse.getWorkspace().getName(), equalTo(mainWorkspace.getWorkspace().getName()));
        // assertThat(response.path("workspace.name"),equalTo(mainWorkspace.getWorkspace().getName()));
        assertThat(actualResponse.getWorkspace().getId(), Matchers.matchesPattern("^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$"));
    }

    @DataProvider(name = "createworkspace")
    public Object[][] workspaceDetails() {
        return new Object[][]{
               // {"pojo", "personal", "automation"},
                {"rest collection","team", "rest assured test"}
        };
    }

    @Test
    public void mock_Complex_Pojo() throws JsonProcessingException {

        Projects projects01 = new Projects("P001","Project A","In Progress");
        Projects projects02 = new Projects("P002","Project B","Completed");

        ArrayList<Projects> projects = new ArrayList<>();
        projects.add(projects01);
        projects.add(projects02);

        Projects projects03 = new Projects("P003","Project C","In Progress");
        Projects projects04 = new Projects("P004","Project D","Pending");

        ArrayList<Projects> projects1 = new ArrayList<>();
        projects1.add(projects03);
        projects1.add(projects04);


        Employees employees = new Employees("001","John Doe","IT",5000,projects);
        Employees employees01 = new Employees("002","Jane Smith","HR",4000,projects1);

        ArrayList<Employees> mainEmployees = new ArrayList<>();
        mainEmployees.add(employees);
        mainEmployees.add(employees01);


        Manager manager = new Manager("001","John Doe");
        Manager manager1 = new Manager("002","Jane Smith");


        Department department = new Department("IT","Building A",manager);
        Department department1 = new Department("HR","Building B",manager1);

        ArrayList<Department> mainDepartment = new ArrayList<>();
        mainDepartment.add(department);
        mainDepartment.add(department1);

        MainObject mainObject = new MainObject(mainEmployees,mainDepartment);


        baseURI = "https://4f78426e-fd53-47ac-a346-f40983662489.mock.pstmn.io";
     Response response =    given().log().all()
                .header("x-mock-match-request-body", "true")
                .config(config.encoderConfig(EncoderConfig.encoderConfig()
                        .appendDefaultContentCharsetToContentTypeIfUndefined(false)))
                 .contentType(ContentType.JSON)
                .body(mainObject)
                .post()
                .then().log().all()
                .assertThat().statusCode(200)
                .extract().response();


      MainObject actualMainObject = response.as(MainObject.class);

      ObjectMapper objectMapper = new ObjectMapper();
     String requestMainObject = objectMapper.writeValueAsString(mainObject);
     String responseObject = objectMapper.writeValueAsString(actualMainObject);

      /*    JSONAssert.assertEquals(exceptCollectionStr,actualDeserializeCollectionStr,
                new CustomComparator(JSONCompareMode.LENIENT,
                        new Customization("collection.item[*].item[*].request.url", new ValueMatcher<Object>() {
                            public boolean equal(Object o1, Object o2){
                                return true;
                            }
                        })));*/

        assertThat(objectMapper.readTree(responseObject),equalTo(objectMapper.readTree(requestMainObject)));

    }
}
