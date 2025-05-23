package testng;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;


public class RestAssuredImplementation{
    String token;

     @Test()
    public void testLoginSuccess() {
        RestAssured.baseURI = "https://whitesmokehouse.com";

        String requestBody = "{\n " +
            "\"email\": \"bryantaxell50@gmail.com\", \n" +
            " \"password\":\"@dmin123\" \n" +
        "}";

        //Send POST
        Response response = RestAssured.given()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .log().all()
                .when()
                .post("/webhook/api/login");

        // Print the response
        System.out.println("Response: " + response.asPrettyString());

        // Extract token from response
        token = response.jsonPath().getString("token");

        // Validate token is not null
        assert response.getStatusCode() == 200 : "Expected status code 200 but got " + response.getStatusCode();
        assert token != null : "Token should not be null";
        System.out.println("Token: " + token);
        }
    
    
    
    @Test(priority = 2, dependsOnMethods = "testLoginSuccess")
    public void GetAllObject(){
    Response response = RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .log().all()
                .when()
                .get("/webhook/api/objects");

        // Print the response
        System.out.println("Response: " + response.asPrettyString());

        // Validate the response
        assert response.getStatusCode() == 200 : "Expected status code 200 but got " + response.getStatusCode();
        assert response.jsonPath().getList("").size() > 0 : "Expected non-empty object list";
    }

}
       
