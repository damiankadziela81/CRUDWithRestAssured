package org.example.bugs;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;

class ReadBugTest {

    private static final String BASE_URL = "http://localhost:3000/bugs";

    @Test
    void readAllBugsTest() {
        Response response = given()
                .when()
                .get(BASE_URL);

        Assertions.assertEquals(HttpStatus.SC_OK, response.statusCode());
        JsonPath json = response.jsonPath();
        List<Integer> employeeIds = json.getList("employeeId");
        Assertions.assertTrue(employeeIds.size() > 0);

    }

    @Test
    void readBugsWithPathVariablesTest() {
        Response response = given()
                .pathParams("id", 1)
                .when()
                .get(BASE_URL + "/{id}");
        Assertions.assertEquals(HttpStatus.SC_OK, response.getStatusCode());
        JsonPath json = response.jsonPath();
        Assertions.assertEquals("Incorrect response code after PATH to /bugs", json.getString("title"));
    }

    @Test
    void readBugsWithQueryParamsTest() {
        Response response = given()
                .queryParams("status", "open")
                .when()
                .get(BASE_URL);
        Assertions.assertEquals(HttpStatus.SC_OK, response.getStatusCode());
        JsonPath json = response.jsonPath();
        List<String> statuses = json.getList("status");
        for (String status : statuses) {
            Assertions.assertEquals("open", status);
            System.out.println("Current bug status is: " + status);
        }
    }
}
