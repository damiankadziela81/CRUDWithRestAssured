package org.example.bugs;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

class CreateNewBugTest {

    private static final String BASE_URL = "http://localhost:3000/bugs";

    @Test
    void creteNewBugTest() {
        JSONObject bug = new JSONObject();
        bug.put("title", "test bug");
        bug.put("description", "test bug description");
        bug.put("employeeId", 1);
        bug.put("status", "in progress");

        Response response = given()
                .contentType(ContentType.JSON)
                .body(bug.toString())
                .when()
                .post(BASE_URL)
                .then()
                .extract()
                .response();

        Assertions.assertEquals(HttpStatus.SC_CREATED, response.statusCode());
        JsonPath json = response.jsonPath();
        Assertions.assertEquals("test bug", json.getString("title"));
        Assertions.assertEquals("test bug description", json.getString("description"));
        Assertions.assertEquals(1, json.getInt("employeeId"));
        Assertions.assertEquals("in progress", json.getString("status"));

        //delete resource after test
        int id = json.getInt("id");
        System.out.println("id of the created bug is: " + id);
        response = given()
                .pathParams("id", id)
                .when()
                .delete(BASE_URL + "/{id}")
                .then()
                .extract()
                .response();

        Assertions.assertEquals(HttpStatus.SC_OK, response.getStatusCode());

    }
}
