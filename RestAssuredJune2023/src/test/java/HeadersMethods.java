
import io.restassured.http.Header;
import io.restassured.http.Headers;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;

import static io.restassured.RestAssured.*;


public class HeadersMethods {

    @Test
    public void mockHeaders(){
        baseURI = "https://eeebece1-574c-4429-a82b-89811a20da12.mock.pstmn.io/get";

        HashMap<String,String>  hashMap = new HashMap<>();
        hashMap.put("header","value1");
        hashMap.put("x-mock-match-request-headers","header");

        given()
                .headers(hashMap)
                //will send three headers with same header name header1 but with different values -"value1", "header2", "value2"
                .header("header1", "value1", "header2", "value2")
                .log().all().
                when().get()
                .then().log().all()
                .assertThat()
                .statusCode(200);
    }

    @Test
    public void mockHeaders_Test(){
        baseURI = "https://eeebece1-574c-4429-a82b-89811a20da12.mock.pstmn.io/get";

        Header header = new Header("header","value2");
        Header mockMatchHeader = new Header("x-mock-match-request-headers","header");

        Headers headers = new Headers(header,mockMatchHeader);

        given()
                .headers(headers)
                .log().all().
                when().get()
                .then().log().all()
                .assertThat()
                .statusCode(200);
    }

    @Test
    public void multiValue_response_Header_validation(){
        baseURI = "https://eeebece1-574c-4429-a82b-89811a20da12.mock.pstmn.io/get";

        Header header = new Header("header","value2");
        Header mockMatchHeader = new Header("x-mock-match-request-headers","header");

        Headers headers = new Headers(header,mockMatchHeader);

        given()
                .headers(headers)
                .header("multiValueHeader","value1","value2")
                .log().all().
                when().get()
                .then().log().all()
                .assertThat()
                .statusCode(200).and()
                .header("headerResponse" ,"valueResponse2");
    }

    @Test
    public void extract_Header_validation(){
        baseURI = "https://eeebece1-574c-4429-a82b-89811a20da12.mock.pstmn.io/get";

        Header header = new Header("header","value2");
        Header mockMatchHeader = new Header("x-mock-match-request-headers","header");

        Headers headers = new Headers(header,mockMatchHeader);

      Headers responseHeaders =  given()
                .headers(headers)
                .log().all().
                when().get()
                .then().log().all()
                .assertThat()
                .statusCode(200)
                .extract().headers();

      for(Header header1:responseHeaders){
          System.out.println("response header name = " + header1.getName() + ",");
          System.out.println("response header value = " + header1.getValue());
      }

       System.out.println("response header name = " + responseHeaders.getValue("headerResponse"));
        System.out.println("response header value = " + responseHeaders.hasHeaderWithName("x-mock-match-request-headers"));

        //getting multiple value headers
        List<String> multiValues = responseHeaders.getValues("multiValueHeader");
        System.out.println(multiValues);
    }


}
