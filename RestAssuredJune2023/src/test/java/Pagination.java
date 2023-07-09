import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class Pagination {

    @Test
            public  void pages() {
        int page = 1;
        int limit = 10;
        Response response;
        do {
            response = given()
                    .param("page", page)
                    .param("limit", limit)
                    .when()
                    .get("https://api.example.com/users");

            // Assert or process the current page of results

            page++;
        } while (response.getStatusCode() == 200);

    }
}
