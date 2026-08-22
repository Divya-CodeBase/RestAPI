package org.example;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.com.POJO.InputLocationData;
import org.com.POJO.Location;

import java.util.Arrays;

import static io.restassured.RestAssured.given;

public class GoogleMaps {

    public static void main(String args[]) {
        InputLocationData ip= new InputLocationData();
        Location lc= new Location();
        lc.setLat(-38.383494);
        lc.setLat(33.427362);
        ip.setLocation(lc);
        ip.setAccuracy(50);
        ip.setName("Frontline house");
        ip.setPhone_number("(+91) 983 893 3937");
        ip.setAddress("29, side layout, cohen 09");
        ip.setTypes(Arrays.asList("shoe park","shop"));
        ip.setWebsite("http://google.com");
        ip.setLanguage("French-IN");



        RestAssured.baseURI = "https://rahulshettyacademy.com";
        String output= given()
                .log()
                .all()
                .queryParam("key", "qaclick123")
                .body(ip)
                .when()
                .log()
                .all()
                .post("/maps/api/place/add/json").asString();
        JsonPath jp= new JsonPath(output);

        System.out.println(jp.getString("status"));





    }
}
