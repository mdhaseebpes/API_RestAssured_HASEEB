package params;

import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.awt.geom.RectangularShape;
import java.io.*;

import static io.restassured.RestAssured.given;

public class DownloadFile {

    @Test
    public void multi_formData(){

   InputStream is  =    given()
                .baseUri("https://github.com/appium-boneyard")
                .log().all()
                .when()
                .get("/sample-code/raw/master/sample-code/apps/ApiDemos/bin/ApiDemos-debug.apk")
                .then()
                .log().all().extract().response().asInputStream();

        try {
            OutputStream outputStream = new FileOutputStream(new File("ApiDemos-debug.apk"));
            byte[] bytes = new byte[is.available()];
            is.read(bytes);
            outputStream.write(bytes);
            outputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
