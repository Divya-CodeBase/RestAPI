package org.example;

import io.restassured.RestAssured;
import org.com.CommonMethods;
import org.com.PayLoad;
import org.testng.Assert;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

/**
 * Hello world!
 */
public class BasicAddTest {
    public static void main(String[] args) {

        RestAssured.baseURI = "https://rahulshettyacademy.com";
        String response = given()
                .log()
                .all()
                .body(PayLoad.AddPlace())
                .queryParam("key", "qaclick123")
                .header("Content-Type", "application/json")
                .when().post("maps/api/place/add/json")
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(200)
                .body("scope", equalTo("APP"))
                .header("Server", "Apache/2.4.52 (Ubuntu)")
                .extract().response().asString();
        System.out.println(response);
        String placeID = CommonMethods.parseJsonResponse(response,"place_id");
        System.out.println(placeID);

        String address = "90, main layout, cohen 09";
        given().log()
                .all()
                .body(PayLoad.UpdatePlace(placeID,address))
                .queryParam("key", "qaclick123")
                .header("Content-Type", "application/json")
                .when().put("maps/api/place/update/json")
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(200)
                .body("msg", equalTo("Address successfully updated"));

        String getResponse = given().log()
                .all()
                .queryParam("place_id", placeID)
                .queryParam("key", "qaclick123")
                .when().get("maps/api/place/get/json")
                .then().assertThat().log().all()
                .statusCode(200).extract().response().asString();

        String addressVal = CommonMethods.parseJsonResponse(getResponse,"address");
        System.out.println(addressVal);

        Assert.assertEquals(address,addressVal);


    }
}
