package org.example;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

class UpdateEmployeeTest {

    private static final String BASE_URL = "http://localhost:3000/employees";

    @Test
    void updateEmployeeTest() {

        String body = """
                {
                    "firstName": "Czarek",
                    "lastName": "Biały",
                    "username": "cbiały",
                    "email": "bczarny@testerprogramuje.pl",
                    "phone": "731-111-111",
                    "website": "testerprogramuje.pl",
                    "role": "qa",
                    "type": "b2b",
                    "address": {
                      "street": "Ul. Sezamkowa",
                      "suite": "8",
                      "city": "Wrocław",
                      "zipcode": "12-123"
                    },
                    "company": {
                      "companyName": "Akademia QA",
                      "taxNumber": "531-1593-430",
                      "companyPhone": "731-111-111"
                    }
                  }""";

        Response response = given()
                .contentType(ContentType.JSON)
                .body(body)
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

        String body = """
                {
                    "address": {
                      "street": "Ul. Sezamkowa",
                      "suite": "9",
                      "city": "Wrocław",
                      "zipcode": "12-123"
                    }
                  }""";

        Response response = given()
                .contentType(ContentType.JSON)
                .body(body)
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
