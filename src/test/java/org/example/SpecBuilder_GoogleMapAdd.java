package org.example;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.com.POJO.InputLocationData;
import org.com.POJO.Location;

import java.util.Arrays;

import static io.restassured.RestAssured.given;

public class SpecBuilder_GoogleMapAdd {
    public static void main(String args[]) {
        InputLocationData ip = new InputLocationData();
        Location lc = new Location();
        lc.setLat(-38.383494);
        lc.setLat(33.427362);
        ip.setLocation(lc);
        ip.setAccuracy(50);
        ip.setName("Frontline house");
        ip.setPhone_number("(+91) 983 893 3937");
        ip.setAddress("29, side layout, cohen 09");
        ip.setTypes(Arrays.asList("shoe park", "shop"));
        ip.setWebsite("http://google.com");
        ip.setLanguage("French-IN");

        RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
                .setContentType("application/json")
                .addQueryParam("key", "qaclick123").build();

        ResponseSpecification resSpec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
        RequestSpecification reqGiven = given()
                .spec(req)
                .body(ip);
        Response res = reqGiven.when()
                .log()
                .all()
                .post("/maps/api/place/add/json")
                .then().spec(resSpec).extract().response();
        JsonPath jp = new JsonPath(res.asString());

        System.out.println(jp.getString("status"));
    }
}
