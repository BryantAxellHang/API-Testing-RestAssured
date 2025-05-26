package testng;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;


public class RestAssuredImplementation{
    String token;
    int createObjectId;
   
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

    @Test(priority = 4, dependsOnMethods = "testLoginSuccess")
    public void addObject(){
        String body = "{ \"name\": \"Asus Tuf Gaming\", \"data\": { \"year\": 2020, \"price\": 1500.00, \"cpu_model\": \"i9\", \"hard_disk_size\": \"2TB\", \"capacity\": \"4 cpu\", \"screen_size\": \"16 Inch\", \"color\": \"Black\" } }";
        
        Response response = RestAssured.given()
            .header("Authorization", "Bearer " + token)
            .header("Content-Type", "application/json")
            .body(body)
            .when()
            .post("/webhook/api/objects");

        createObjectId(response.jsonPath().getInt("id"));
        System.out.println("Add Object: " + response.asPrettyString());
        assert response.getStatusCode() == 200 : "Failed to add object";

    }

    @Test(priority = 5, dependsOnMethods = "testLoginSuccess")
    public void getSingleObjectbyPath(){
        Response response = RestAssured.given()
            .header("Authorization", "bearer " + token)
            .when()
            .get("/webhook/api/objects?id=3");

        System.out.println("Get Single Object by Path: " + response.asPrettyString());
        assert response.getStatusCode() == 200 : "Failed to get single object";

    }

    @Test(priority = 6, dependsOnMethods = "testLoginSuccess")
    public void testUpdateObject(){
        String body = "{ \"name\": \"Asus tuf gaming bry\", \"data\": { \"year\": 2021, \"price\": 3000.00, \"cpu_model\": \"i9\", \"hard_disk_size\": \"3TB\", \"capacity\": \"6 cpu\", \"screen_size\": \"15.6 Inch\", \"color\": \"White\" } }";

        Response response = RestAssured.given()
            .header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json")
                .body(body)
                .when()
                .put("/webhook/37777abe-a5ef-4570-a383-c99b5f5f7906/api/objects/489");

        System.out.println("Update Object: " + response.asPrettyString());
        assert response.getStatusCode() == 200 : "Failed to update object";
    }

    @Test(priority = 7,dependsOnMethods = "testLoginSuccess")
    public void testPartialUpdateObject(){        

    String patchBody = "{ \"name\": \"Asus tuf gaming 123\", \"year\": \"2030\" }";

    Response patchResponse = RestAssured.given()
            .header("Authorization", "Bearer " + token)
            .header("Content-Type", "application/json")
            .body(patchBody)
            .when()
            .patch("/webhook/39a0f904-b0f2-4428-80a3-391cea5d7d04/api/object/489");

    System.out.println("PATCH Response: " + patchResponse.asPrettyString());
    assert patchResponse.getStatusCode() == 200 : "Failed to patch object with ID ";
}

    @Test(priority = 8, dependsOnMethods = "testLoginSuccess")
    public void testDeleteObject() {
        Response response = RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .when()
                .delete("/webhook/d79a30ed-1066-48b6-83f5-556120afc46f/api/objects/489");

        System.out.println("Delete Object: " + response.asPrettyString());
        assert response.getStatusCode() == 200 : "Failed to delete object";
    }

    @Test(priority = 9, dependsOnMethods = "testLoginSuccess")
    public void testGetDepartments() {
        Response response = RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/webhook/api/department");

        System.out.println("Get Departments: " + response.asPrettyString());
        assert response.getStatusCode() == 200 : "Failed to get departments";
    }

}

       
