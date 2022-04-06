package api;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;

public class ReqresTest {
    private final static String URL = "https://reqres.in/";

    @Test
    public void checkGetRequestStatusCode200() {
        Response response = given()
                .when()
                .contentType(ContentType.JSON)
                .get(URL+"api/users?page=2");
        List<UserData> users = response
                .then().log().all()
                .extract().body().jsonPath().getList("data", UserData.class);
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test
    public void checkPost() {
        Response response = given()
                .body("{\n" +
                        "    \"name\": \"jenya222\",\n" +
                        "    \"job\": \"qa\"\n" +
                        "}")
                .post(URL+"api/users");
                        response.then().log().all();
        String id = response.body().jsonPath().getString("id");
       // Response getResponse = given()
         //       .get(URL+"api/users/?page=2");
        //getResponse.then().log().all();
        //UserData userData = getResponse.body().jsonPath().getObject("data", UserData.class);
        Assert.assertEquals(response.getStatusCode(), 201);
        //Assert.assertEquals(userData.getFirst_name(), "jenya222");
        Assert.assertFalse(response.body().jsonPath().getString("id").isEmpty());
    }
}
