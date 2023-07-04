package org.example;

import com.github.javafaker.Faker;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

class UpdateEmployeeTest {

    private static final String BASE_URL = "http://localhost:3000/employees";
    private static Faker faker;
    private String randomEmail;
    private String randomFirstName;
    private String randomLastName;
    private String randomUserName;
    private String randomStreet;

    @BeforeAll
    static void beforeAll() {
        faker = new Faker();
    }

    @BeforeEach
    void beforeEach() {
        randomEmail = faker.internet().emailAddress();
        randomFirstName = faker.name().firstName();
        randomLastName = faker.name().lastName();
        randomUserName = faker.name().username();
        randomStreet = faker.address().streetAddress();
    }

    @Test
    void updateEmployeeTest() {
        JSONObject company = new JSONObject();
        company.put("companyName", "Akademia QA");
        company.put("taxNumber", "531-1593-430");
        company.put("companyPhone", "731-111-111");

        JSONObject address = new JSONObject();
        address.put("street", "Ul. Sezamkowa");
        address.put("suite", "8");
        address.put("city", "Wrocław");
        address.put("zipcode", "12-123");

        JSONObject employee = new JSONObject();
        employee.put("firstName", randomFirstName);
        employee.put("lastName", randomLastName);
        employee.put("username", randomUserName);
        employee.put("email", randomEmail);
        employee.put("phone", "731-111-111");
        employee.put("website", "testerprogramuje.pl");
        employee.put("role", "qa");
        employee.put("type", "b2b");
        employee.put("address", address);
        employee.put("company", company);

        Response response = given()
                .contentType(ContentType.JSON)
                .body(employee.toString())
                .when()
                .put(BASE_URL + "/1")
                .then()
                .extract()
                .response();

        Assertions.assertEquals(200, response.statusCode());

        JsonPath json = response.jsonPath();
        Assertions.assertEquals(randomFirstName,json.getString("firstName"));
        Assertions.assertEquals(randomLastName,json.getString("lastName"));
        Assertions.assertEquals(randomUserName,json.getString("username"));
        Assertions.assertEquals(randomEmail,json.getString("email"));
    }

    @Test
    void partialUpdateEmployeeTest() {
        JSONObject address = new JSONObject();
        address.put("street", randomStreet);
        address.put("suite", "8");
        address.put("city", "Wrocław");
        address.put("zipcode", "12-123");

        JSONObject employee = new JSONObject();
        employee.put("address", address);
        employee.put("email",randomEmail);

        Response response = given()
                .contentType(ContentType.JSON)
                .body(employee.toString())
                .when()
                .patch(BASE_URL + "/1")
                .then()
                .extract()
                .response();

        Assertions.assertEquals(200, response.statusCode());

        JsonPath json = response.jsonPath();
        Assertions.assertEquals(randomStreet,json.getString("address.street"));
        Assertions.assertEquals(randomEmail,json.getString("email"));
    }
}
