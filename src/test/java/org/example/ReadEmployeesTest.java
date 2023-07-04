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



}
