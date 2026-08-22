package org.example;

import io.restassured.RestAssured;
import org.com.PayLoad;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;

public class DynamicJSON_Library_GoogleAPI {

    @Test
    public void addBook() {
        RestAssured.baseURI = "http://216.10.245.166";
        String response = given()
                .log()
                .all()
                .header("Content-Type", "application/json")
                .body(PayLoad.addBookPayload())
                .when().post("/Library/Addbook.php")
                .then().log().all()
                .statusCode(200)
                .extract().response().asString();

        System.out.println(response);
       // System.out.println(CommonMethods.parseJsonResponse(response, "ID"));


    }

    @Test
    public void addBook_StaticJsonFilePayload() throws IOException {
        RestAssured.baseURI = "http://216.10.245.166";
        String response = given()
                .log()
                .all()
                .header("Content-Type", "application/json")
                .body(new String(Files.readAllBytes(Paths.get(System.getProperty("user.dir") + "/src/test/resources/book.json"))))
                .when().post("/Library/Addbook.php")
                .then().log().all()
                .statusCode(200)
                .extract().response().asString();

        System.out.println(response);
        //System.out.println(CommonMethods.parseJsonResponse(response, "ID"));


    }
}