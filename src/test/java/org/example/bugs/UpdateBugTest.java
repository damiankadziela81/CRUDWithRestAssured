package org.example.bugs;

import com.github.javafaker.Faker;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static io.restassured.RestAssured.given;

class UpdateBugTest {

    private static final String BASE_URL = "http://localhost:3000/bugs";
    private static Faker faker;
    private String randomTitle;
    private String randomDescription;
    private Integer randomEmployeeId;
    private String randomStatus;

    @BeforeAll
    static void beforeAll() {
        faker = new Faker();
    }

    @BeforeEach
    void beforeEach() {
        randomTitle = faker.dragonBall().character();
        randomDescription = faker.funnyName().name();
        randomEmployeeId = faker.number().randomDigit();
        randomStatus = randomEmployeeId%2 == 0 ? "open" : "in progress";
    }

    @Test
    void updateBugTest() {
        JSONObject bug = new JSONObject();
        bug.put("title", randomTitle);
        bug.put("description", randomDescription);
        bug.put("employeeId", randomEmployeeId);
        bug.put("status", randomStatus);

        Response response = given()
                .contentType(ContentType.JSON)
                .body(bug.toString())
                .when()
                .put(BASE_URL + "/1")
                .then()
                .extract()
                .response();

        Assertions.assertEquals(HttpStatus.SC_OK, response.statusCode());
        JsonPath json = response.jsonPath();
        Assertions.assertEquals(randomTitle, json.getString("title"));
        Assertions.assertEquals(randomDescription, json.getString("description"));
        Assertions.assertEquals(randomEmployeeId, json.getInt("employeeId"));
        Assertions.assertEquals(randomStatus, json.getString("status"));
    }

    @Test
    void partialUpdateBugTest() {
        JSONObject bug = new JSONObject();
        bug.put("title", randomTitle);

        Response response = given()
                .contentType(ContentType.JSON)
                .body(bug.toString())
                .when()
                .patch(BASE_URL + "/1")
                .then()
                .extract()
                .response();

        Assertions.assertEquals(HttpStatus.SC_OK, response.statusCode());
        JsonPath json = response.jsonPath();
        Assertions.assertEquals(randomTitle, json.getString("title"));
    }



}
