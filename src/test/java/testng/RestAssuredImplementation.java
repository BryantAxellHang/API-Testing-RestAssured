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

    @Test(priority = 3, dependsOnMethods = "testLoginSuccess")
    public void  getAllobject(){
        Response response = RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/webhook/api/objects");
        
        System.out.println("Get All Object: " + response.asPrettyString());
        assert response.getStatusCode() == 200 : "Failed to get object by query ID";
    }

    @Test(priority = 4, dependsOnMethods = "testLoginSuccess")
    public void addObject(){
        String body = "{ \"name\": \"Asus Tuf Gaming\", \"data\": { \"year\": 2020, \"price\": 1500.00, \"cpu_model\": \"i9\", \"hard_disk_size\": \"2TB\", \"capacity\": \"4 cpu\", \"screen_size\": \"16 Inch\", \"color\": \"Black\" } }";
        
        Response response = RestAssured.given()
            .header("Authorization", "Bearer " + token)
            .header("Content-Type", "application/json")
            .body(body)
            .when()
            .post("/webhook/api/objects");

        System.out.println("Add Object: " + response.asPrettyString());
        assert response.getStatusCode() == 200 : "Failed to add object";

    }

    @Test(priority = 5, dependsOnMethods = "testLoginSuccess")
    public void getObjectByQuery(){
        Response response = RestAssured.given()
            .header("Authorization", "bearer" + token)
            .when()
            .get("/webhook-test/api/objects?id=3");

        System.out.println("Get Object by Query "+ response.asPrettyString());
        assert response.getStatusCode() == 200 :"Failed to get object";
    }

    @Test(priority = 6, dependsOnMethods = "testLoginSuccess")
    public void getSingleObjectbyPath(){
        Response response = RestAssured.given()
            .header("Authorization", "bearer" + token)
            .when()
            .get("https://whitesmokehouse.com/webhook/7f40e27b-d3bf-4e21-bca1-fd9514a3e6ae/api/objects/:id");

        System.out.println("Get Single Object by Path: " + response.asPrettyString());
        assert response.getStatusCode() == 200 : "Failed to get single object";

    }

    @Test(priority = 7, dependsOnMethods = "testLoginSuccess")
    public void testUpdateObject(){
        String body = "{ \"name\": \"Asus tuf gaming bry\", \"data\": { \"year\": 2021, \"price\": 3000.00, \"cpu_model\": \"i9\", \"hard_disk_size\": \"3TB\", \"capacity\": \"6 cpu\", \"screen_size\": \"15.6 Inch\", \"color\": \"White\" } }";

        Response response = RestAssured.given()
            .header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json")
                .body(body)
                .when()
                .put("/webhook/37777abe-a5ef-4570-a383-c99b5f5f7906/api/objects/:id");

        System.out.println("Update Object: " + response.asPrettyString());
        assert response.getStatusCode() == 200 : "Failed to update object";
    }

    @Test(priority = 8,dependsOnMethods = "testLoginSuccess")
    public void testPartialUpdateObject(){
        String body = "{ \"name\": \"Asus tuf gaming bry 1\", \year\": 2025\"}";
        
        Response response = RestAssured.given()
            .header()


    }
    }
}

       
