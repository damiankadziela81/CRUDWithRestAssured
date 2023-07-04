package org.example;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

class CreateNewEmployeeTest {

    @Test
    void createNewEmployeeTest() {

        String body = """
                {
                    "firstName": "Bartek",
                    "lastName": "Czarny",
                    "username": "bczarny",
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
                .post("http://localhost:3000/employees")
                .then()
                .extract()
                .response();

        Assertions.assertEquals(201, response.statusCode());
        JsonPath json = response.jsonPath();
        Assertions.assertEquals("Bartek",json.getString("firstName"));
        Assertions.assertEquals("Czarny",json.getString("lastName"));
        Assertions.assertEquals("bczarny",json.getString("username"));
        Assertions.assertEquals("8",json.getString("address.suite"));
    }
}
