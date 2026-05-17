package com.frameowrkdesign.tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class ApiTests {

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://automationexercise.com";
}

    @Test
    public void getUserDetails() {
        Response response = given()
                .when()
                .get("/api/productsList")
                .then()
                .extract().response();

        System.out.println(response.asPrettyString());

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("products[1].category.category"), "Tshirts");
    }

    @Test
    public void verifyUser() {
        Response response = given()
                .param("email", "jsparrowbp007@gmail.com")
                .param("password", "Test@123")
                .when()
                .post("/api/verifyLogin")
                .then()
                .extract().response();

        System.out.println(response.asPrettyString());

        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test
    public void updateUser() {

//        String requestBody = """
//        {
//            "name": "Ashish",
//            "job": "Senior QA"
//        }
//        """;

        var response =
                given()
                        .param("email", "jsparrowbp007@gmail.com")
                        .param("mobile_number", "999999999")
                        .when()
                        .put("/api/updateAccount")
                        .then()
                        .extract().response();

        System.out.println(response.asPrettyString());

        Assert.assertEquals(response.getStatusCode(), 200);
//        Assert.assertEquals(response.jsonPath().getString("job"), "Senior QA");
    }

    @Test
    public void deleteUser() {

        var response =
                given()
                        .when()
                        .delete("/api/users/2")
                        .then()
                        .extract().response();

        Assert.assertEquals(response.getStatusCode(), 204);
    }
}
