package testng.Scenario;

import model.LoginRequest;
import model.ObjectUpdateRequest;
import model.RegisterRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class RestAssuredE2EPOJO {

    String token;
    int createdObjectId;

    @Test(priority = 0)
    public void registerAndLogin() throws Exception {
        RestAssured.baseURI = "https://whitesmokehouse.com";

        // Unique email to allow re-registration on each run
        String email = "bryantaxell" + System.currentTimeMillis() + "@gmail.com";
        RegisterRequest registerRequest = new RegisterRequest(
        email,
        "Bryant Axell",
        "@dmin123",
        "Technology",
        "088271263649"
    );

    ObjectMapper objectMapper = new ObjectMapper();
    String registerBody = objectMapper.writeValueAsString(registerRequest);

        Response registerResponse = RestAssured.given()
        .header("Content-Type", "application/json")
        .body(registerBody)
        .log().all()
        .post("/webhook/api/register");

        System.out.println("Response: " + registerResponse.asPrettyString());
        Assert.assertEquals(registerResponse.getStatusCode(), 200, "Expected status code 200");
        Assert.assertEquals(registerResponse.jsonPath().getString("email"), email);
        Assert.assertNotNull(registerResponse.jsonPath().getString("id"), "ID should not be null");

        // === STEP 2: LOGIN ===
        LoginRequest loginRequest = new LoginRequest(email, "@dmin123");
        String loginBody = objectMapper.writeValueAsString(loginRequest);

        Response loginResponse = RestAssured.given()
                .header("Content-Type", "application/json")
                .body(loginBody)
                .log().all()
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
                .get("/webhook/api/objects");

        System.out.println("Get All Objects: " + response.asPrettyString());
        Assert.assertEquals(response.statusCode(), 200, "Failed to get all objects");
    }

    @Test(priority = 2, dependsOnMethods = "registerAndLogin")
public void addObject() throws Exception {
    ObjectMapper mapper = new ObjectMapper();

    // ✅ Step 1: Buat inner object "data"
    ObjectUpdateRequest.Data data = new ObjectUpdateRequest.Data(
        2020,
        1500.00,
        "i9",
        "2TB",
        "4 cpu",
        "16 Inch",
        "Black"
    );

    // ✅ Step 2: Buat request utama yang menyertakan data
    ObjectUpdateRequest objectData = new ObjectUpdateRequest("Asus Tuf Gaming", data);

    // ✅ Step 3: Convert to JSON
    String jsonBody = mapper.writeValueAsString(objectData);

    // ✅ Step 4: Kirim request POST
    Response response = RestAssured.given()
            .header("Authorization", "Bearer " + token)
            .header("Content-Type", "application/json")
            .body(jsonBody)
            .log().all()
            .post("/webhook/api/objects");

    // ✅ Step 5: Validasi respons
    Assert.assertEquals(response.getStatusCode(), 200, "Failed to add object");

    createdObjectId = Integer.parseInt(
        response.jsonPath().getString("id").replace("[", "").replace("]", "")
    );

    System.out.println("Created Object ID: " + createdObjectId);
    }

    @Test(priority = 3, dependsOnMethods = "addObject")
        public void updateObject() throws Exception {
    ObjectMapper mapper = new ObjectMapper();
    ObjectUpdateRequest.Data data = new ObjectUpdateRequest.Data(
        2022, 1800.00, "i7", "1TB", "6 cpu", "17 Inch", "White"
    );

    ObjectUpdateRequest updateRequest = new ObjectUpdateRequest("Asus TUF Updated", data);
        // ✅ Convert to JSON
    String jsonBody = mapper.writeValueAsString(updateRequest);

    Response response = RestAssured.given()
            .header("Authorization", "Bearer " + token)
            .header("Content-Type", "application/json")
            .body(jsonBody)
            .log().all()
            .put("/webhook/37777abe-a5ef-4570-a383-c99b5f5f7906/api/objects/" + createdObjectId);

    System.out.println("Update Object: " + response.asPrettyString());
    Assert.assertEquals(response.statusCode(), 200, "Failed to update object");
    }

    @Test(priority = 4, dependsOnMethods = "addObject")
    public void deleteObject() {
        Response response = RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .log().all()
                .delete("/webhook/d79a30ed-1066-48b6-83f5-556120afc46f/api/objects/" + createdObjectId);

        System.out.println("Delete Object: " + response.asPrettyString());
        Assert.assertEquals(response.getStatusCode(), 200, "Failed to delete object");
    }
}
