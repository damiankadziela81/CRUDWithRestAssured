package org.example;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;

class ReadEmployeesTest {

    @Test
    void readAllEmployeesTest() {

        Response response = given()
                .when()
                .get("http://localhost:3000/employees");

//        This 2 will display response body on the console
//        System.out.println(response.asString());
//        response.prettyPrint();
//        This will display response status-code, headers and body
//        response.prettyPeek();

        Assertions.assertEquals(200,response.getStatusCode());

        JsonPath json = response.jsonPath();
        List<String> firstNames = json.getList("firstName");
        Assertions.assertTrue(firstNames.size() > 0);
    }

    @Test
    void readOneEmployeeTest() {

        Response response = given()
                .when()
                .get("http://localhost:3000/employees/1");

        Assertions.assertEquals(200,response.getStatusCode());

        JsonPath json = response.jsonPath();
        Assertions.assertEquals("Bartek",json.getString("firstName"));
        Assertions.assertEquals("Czarny",json.getString("lastName"));
        Assertions.assertEquals("bczarny",json.getString("username"));
        Assertions.assertEquals("8",json.getString("address.suite"));
    }

    @Test
    void readOneUserWithPathVariableTest() {

        Response response = given()
                .pathParams("id", 2)
                .when()
                .get("http://localhost:3000/employees/{id}");

        Assertions.assertEquals(200,response.getStatusCode());

        JsonPath json = response.jsonPath();
        Assertions.assertEquals("Kasia",json.getString("firstName"));
        Assertions.assertEquals("Niebieska",json.getString("lastName"));
        Assertions.assertEquals("kniebieska",json.getString("username"));
        Assertions.assertEquals("12/6",json.getString("address.suite"));
    }

    @Test
    void readEmployeeWithQueryParamsTest() {

        Response response = given()
                .queryParam("firstName", "Bartek")
                .when()
                .get("http://localhost:3000/employees");

        JsonPath json = response.jsonPath();
        //Note that the response will be a list, not a single object!
        Assertions.assertEquals("Bartek",json.getList("firstName").get(0));
        Assertions.assertEquals("Czarny",json.getList("lastName").get(0));
        Assertions.assertEquals("bczarny",json.getList("username").get(0));
        Assertions.assertEquals("8",json.getList("address.suite").get(0));
    }



}
