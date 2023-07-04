package org.example;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

class UpdateEmployeeTest {

    private static final String BASE_URL = "http://localhost:3000/employees";

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
        employee.put("firstName", "Czarek");
        employee.put("lastName", "Biały");
        employee.put("username", "cbiały");
        employee.put("email", "bczarny@testerprogramuje.pl");
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
        Assertions.assertEquals("Czarek",json.getString("firstName"));
        Assertions.assertEquals("Biały",json.getString("lastName"));
        Assertions.assertEquals("cbiały",json.getString("username"));
    }

    @Test
    void partialUpdateEmployeeTest() {

        JSONObject address = new JSONObject();
        address.put("street", "Ul. Sezamkowa");
        address.put("suite", "9");
        address.put("city", "Wrocław");
        address.put("zipcode", "12-123");

        JSONObject employee = new JSONObject();
        employee.put("address", address);

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
        Assertions.assertEquals("9",json.getString("address.suite"));
    }
}
