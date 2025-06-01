package testng.Scenario;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class RestAssuredE2E {

    String token;
    int createdObjectId;

    @Test(priority = 0)
    public void registerAndLogin() {
        RestAssured.baseURI = "https://whitesmokehouse.com";

        // Unique email supaya bisa register ulang setiap kali run
        String email = "bryantaxell31@gmail.com";

        // === STEP 1: REGISTER ===
        String registerBody = "{\n" +
                "  \"email\": \"" + email + "\",\n" +
                "  \"full_name\": \"Bryant Axell\",\n" +
                "  \"password\": \"@dmin123\",\n" +
                "  \"department\": \"Technology\",\n" +
                "  \"phone_number\": \"088271263649\"\n" +
                "}";

        Response registerResponse = RestAssured.given()
                .header("Content-Type", "application/json")
                .body(registerBody)
                .log().all()
                .when()
                .post("/webhook/api/register");
                
    System.out.println("Response: " + registerResponse.asPrettyString());

    // Validate status code
    Assert.assertEquals(registerResponse.getStatusCode(), 200, "Expected status code 200");

    // Validate response body fields
    Assert.assertEquals(registerResponse.jsonPath().getString("email"), ""+ email +"");
    Assert.assertEquals(registerResponse.jsonPath().getString("full_name"), "Bryant Axell");
    Assert.assertEquals(registerResponse.jsonPath().getString("department"), "Technology");
    Assert.assertEquals(registerResponse.jsonPath().getString("phone_number"), "088271263649");

    // Optional: Check ID is not null
    Assert.assertNotNull(registerResponse.jsonPath().getString("id"), "ID should not be null");

        // === STEP 2: LOGIN ===
        String loginBody = "{\n" +
                "  \"email\": \"" + email + "\",\n" +
                "  \"password\": \"@dmin123\"\n" +
                "}";

        Response loginResponse = RestAssured.given()
                .header("Content-Type", "application/json")
                .body(loginBody)
                .log().all()
                .when()
                .post("/webhook/api/login");

        Assert.assertEquals(loginResponse.getStatusCode(), 200, "Login failed");

        token = loginResponse.jsonPath().getString("token");
        Assert.assertNotNull(token, "Token should not be null");

        System.out.println("Token berhasil didapat: " + token);
    }

    @Test(priority = 1, dependsOnMethods = "registerAndLogin")
    public void getAllObject() {
        Response response = RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .log().all()
                .when()
                .get("/webhook/api/objects");

        System.out.println("Get All Objects: " + response.asPrettyString());
        Assert.assertEquals(response.statusCode(), 200, "Failed to get all objects");
    }

    @Test(priority = 2, dependsOnMethods = "registerAndLogin")
    public void addObject() {
        String body = "{\n" +
                "  \"name\": \"Asus Tuf Gaming\",\n" +
                "  \"data\": {\n" +
                "    \"year\": 2020,\n" +
                "    \"price\": 1500.00,\n" +
                "    \"cpu_model\": \"i9\",\n" +
                "    \"hard_disk_size\": \"2TB\",\n" +
                "    \"capacity\": \"4 cpu\",\n" +
                "    \"screen_size\": \"16 Inch\",\n" +
                "    \"color\": \"Black\"\n" +
                "  }\n" +
                "}";

        Response response = RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json")
                .body(body)
                .log().all()
                .when()
                .post("/webhook/api/objects");

        Assert.assertEquals(response.getStatusCode(), 200, "Failed to add object");

        createdObjectId = Integer.parseInt(response.jsonPath().getString("id").replace("[", "").replace("]", ""));
        System.out.println("Created Object ID: " + createdObjectId);
    }

    @Test(priority = 3, dependsOnMethods = "addObject")
    public void updateObject() {
        String body = "{\n" +
                "  \"name\": \"Asus TUF Updated\",\n" +
                "  \"data\": {\n" +
                "    \"year\": 2022,\n" +
                "    \"price\": 1800.00,\n" +
                "    \"cpu_model\": \"i7\",\n" +
                "    \"hard_disk_size\": \"1TB\",\n" +
                "    \"capacity\": \"6 cpu\",\n" +
                "    \"screen_size\": \"17 Inch\",\n" +
                "    \"color\": \"White\"\n" +
                "  }\n" +
                "}";

        Response response = RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json")
                .body(body)
                .log().all()
                .when()
                .put("/webhook/37777abe-a5ef-4570-a383-c99b5f5f7906/api/objects/" + createdObjectId);

        System.out.println("Update Object: " + response.asPrettyString());
        Assert.assertEquals(response.statusCode(), 200, "Failed to update object");
    }

    @Test(priority = 4  , dependsOnMethods = "addObject")
    public void deleteObject() {
        Response response = RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .log().all()
                .when()
                .delete("/webhook/d79a30ed-1066-48b6-83f5-556120afc46f/api/objects/" + createdObjectId);

        System.out.println("Delete Object: " + response.asPrettyString());
        Assert.assertEquals(response.getStatusCode(), 200, "Failed to delete object");
    }
}
