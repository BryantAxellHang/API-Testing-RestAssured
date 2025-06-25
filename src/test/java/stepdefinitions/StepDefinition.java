package stepdefinitions;

import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import model.LoginRequest;
import model.ObjectUpdateRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.datatable.DataTable;
import org.testng.Assert;

import java.util.List;
import java.util.Map;

public class StepDefinition {
    private String token;
    private String email;
    private String password;
    private String objectId;
    private Response response;

    @Given("User has email {string} and password {string}")
    public void user_has_email_and_password(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @When("User sends POST request to {string}")
    public void user_sends_post_request(String endpoint) throws Exception {
        RestAssured.baseURI = "https://whitesmokehouse.com";
        LoginRequest login = new LoginRequest(email, password);
        String body = new ObjectMapper().writeValueAsString(login);

        response = RestAssured.given()
                .header("Content-Type", "application/json")
                .body(body)
                .log().all()
                .post(endpoint);
    }

    @Then("The response status code should be {int}")
    public void the_response_status_code_should_be(int statusCode) {
        Assert.assertEquals(response.getStatusCode(), statusCode, "Unexpected status code");
    }

    @Then("The response should contain a valid token")
    public void the_response_should_contain_a_valid_token() {
        token = response.jsonPath().getString("token");
        Assert.assertNotNull(token, "Token should not be null");
    }

    @When("User sends GET request to {string}")
    public void user_sends_get_request(String endpoint) {
        response = RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .log().all()
                .get(endpoint);
    }

    @Then("The object list should not be empty")
    public void the_object_list_should_not_be_empty() {
        List<Object> objects = response.jsonPath().getList("$");
        Assert.assertTrue(objects.size() > 0, "Object list should not be empty");
    }

    @When("User adds a new object with the following details:")
    public void user_adds_object_with_details(DataTable dataTable) throws Exception {
        Map<String, String> row = dataTable.asMaps().get(0);

        ObjectUpdateRequest.Data nested = new ObjectUpdateRequest.Data(
                Integer.parseInt(row.get("year")),
                Double.parseDouble(row.get("price")),
                row.get("cpu_model"),
                row.get("hard_disk_size"),
                row.get("capacity"),
                row.get("screen_size"),
                row.get("color")
        );

        ObjectUpdateRequest objectData = new ObjectUpdateRequest(row.get("name"), nested);
        String jsonBody = new ObjectMapper().writeValueAsString(objectData);

        response = RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json")
                .body(jsonBody)
                .log().all()
                .post("/webhook/api/objects");

        objectId = response.jsonPath().getString("id").replace("[", "").replace("]", "");
    }

    @Then("The response should contain object id")
    public void the_response_should_contain_object_id() {
        String id = response.jsonPath().getString("id");
        Assert.assertNotNull(id, "Object ID should not be null");
        System.out.println("Created Object ID: " + id);
    }

    @When("User updates the object with the following details:")
    public void user_updates_object_with_details(DataTable dataTable) throws Exception {
        Map<String, String> row = dataTable.asMaps().get(0);

        ObjectUpdateRequest.Data nested = new ObjectUpdateRequest.Data(
                Integer.parseInt(row.get("year")),
                Double.parseDouble(row.get("price")),
                row.get("cpu_model"),
                row.get("hard_disk_size"),
                row.get("capacity"),
                row.get("screen_size"),
                row.get("color")
        );

        ObjectUpdateRequest updatedData = new ObjectUpdateRequest(row.get("name"), nested);
        String jsonBody = new ObjectMapper().writeValueAsString(updatedData);

        response = RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json")
                .body(jsonBody)
                .log().all()
                .put("/webhook/37777abe-a5ef-4570-a383-c99b5f5f7906/api/objects/" + objectId);
    }

    @When("User deletes the object")
    public void user_deletes_the_object() {
        response = RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .log().all()
                .delete("/webhook/d79a30ed-1066-48b6-83f5-556120afc46f/api/objects/" + objectId);
    }
}
