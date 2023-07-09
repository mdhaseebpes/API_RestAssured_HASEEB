package Authentication;

import io.restassured.RestAssured;
import io.restassured.authentication.FormAuthConfig;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.session.SessionFilter;
import io.restassured.http.Cookie;
import io.restassured.http.Cookies;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;

public class FormAuthentication {

    @BeforeClass
    public void beforeClass(){
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder()
                .setBaseUri("https://localhost:8443")
                .setRelaxedHTTPSValidation()
                .log(LogDetail.ALL);

        RestAssured.requestSpecification = requestSpecBuilder.build();
    }

    @Test
    public void form_authentication_csrf_token(){

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

        given()
                .sessionId(filter.getSessionId())
                .log().all()
        .when()
                .get("/profile/users")
        .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .body("html.body.div.p",contains("This is user Profile"));
    }



}
