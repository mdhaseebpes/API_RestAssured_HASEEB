package cookie;

import io.restassured.RestAssured;
import io.restassured.authentication.FormAuthConfig;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.session.SessionFilter;
import io.restassured.http.Cookie;
import io.restassured.http.Cookies;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.contains;

public class CookiesExample {

    @BeforeClass
    public void beforeClass(){
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder()
                .setBaseUri("https://localhost:8443")
                .setRelaxedHTTPSValidation()
                .log(LogDetail.ALL);

        RestAssured.requestSpecification = requestSpecBuilder.build();
    }



    @Test
    public void form_authentication_csrf_token_cookie_example(){

        SessionFilter filter = new SessionFilter();
        given().
                auth().form("dan","dan123", new FormAuthConfig("/signin",
                        "txtUsername","txtPassword")
                )
                .filter(filter)
                .log().all().
                when().get("/login")
                .then().log().all()
                .and().assertThat().statusCode(200);

        System.out.println("Session ID = " + filter.getSessionId());

        Cookie cookie = new Cookie.Builder("JSESSIONID", filter.getSessionId())
                .setSecured(true).setHttpOnly(true).setComment("my cookie").build();

        Cookie cookie1 = new Cookie.Builder("demo", "demo1")
                .setSecured(true).setHttpOnly(true).setComment("my cookie").build();

        Cookies cookies = new Cookies(cookie,cookie1);

        //cookie example
        given()
                //.cookie("JSESSIONID", filter.getSessionId())
                // .cookie(cookie)
                .cookies(cookies)
               //.cookies("JSESSIONID",filter.getSessionId(),"demo","demo1")
                .log().all()
                .when()
                .get("/profile/users")
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .body("html.body.div.p",contains("This is user Profile"));
    }


    @Test
    public void fetch_single_cookie(){
      Response response = given().
                log().all().
        when().get("/profile/index")
                .then()
                .log().all()
                .assertThat().extract().response();

        System.out.println(response.getCookie("JSESSIONID"));
        System.out.println(response.getDetailedCookie("JSESSIONID"));

    }

    @Test
    public void fetch_multiple_cookie(){
        Response response = given().
                log().all().
                when().get("/profile/index")
                .then()
                .log().all()
                .assertThat().extract().response();


        //name and values
      Map<String,String> cookies =  response.getCookies();

      for(Map.Entry<String,String> entry : cookies.entrySet())
      {
          System.out.print(entry.getKey() + ":" + entry.getValue());
          System.out.println();
      }

        //name , values with attributes
        Cookies cookies1 =  response.getDetailedCookies();
       List<Cookie> cookieList =cookies1.asList();

        for(Cookie cookie : cookies1)
        {
            System.out.println("cookie = " + cookie.toString());
        }


    }
}
