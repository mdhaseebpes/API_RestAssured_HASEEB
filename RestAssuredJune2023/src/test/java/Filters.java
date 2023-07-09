import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import static io.restassured.RestAssured.given;

public class Filters {

    @Test
    public void loggingFilters(){
        given()
                .baseUri("https://postman-echo.com")
                //logging mechanism using filters
                .filter(new RequestLoggingFilter(LogDetail.ALL))
                .filter(new ResponseLoggingFilter(LogDetail.ALL))
                .get("/get")
                .then().assertThat().statusCode(200);

    }

    @Test
    public void loggingFilters_InFile() throws FileNotFoundException {

        PrintStream fileOutputStream = new PrintStream("restAssured.log");
        given()
                .baseUri("https://postman-echo.com")
                //logging mechanism using filters
                .filter(new RequestLoggingFilter(fileOutputStream))
                .filter(new ResponseLoggingFilter(fileOutputStream))
                .get("/get")
                .then().assertThat().statusCode(200);

    }

    @Test
    public void loggingFilters_SpecificInFile() throws FileNotFoundException {

        PrintStream fileOutputStream = new PrintStream("restAssured.log");
        given()
                .baseUri("https://postman-echo.com")
                //logging mechanism using filters
                .filter(new RequestLoggingFilter(LogDetail.BODY,fileOutputStream))
                .filter(new ResponseLoggingFilter(LogDetail.STATUS,fileOutputStream))
                .get("/get")
                .then().assertThat().statusCode(200);

    }
}
