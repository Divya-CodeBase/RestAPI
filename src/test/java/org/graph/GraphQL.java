package org.graph;

import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.given;

public class GraphQL {
    public static void main(String args[]){


        String res= given().log().all().baseUri("https://rahulshettyacademy.com")
                .header("Content-type","application/json")
                .body("{\"query\":\"mutation { createEpisode(episode:{name:\\\"The middle\\\", air_date:\\\"2030-09-08\\\", episode:\\\"the shodde\\\"}) { id } }\"}")
                .when().log().all()
                .post("/gq/graphql").then().log().all().extract().response().asString();
        JsonPath jp= new JsonPath(res);
        System.out.println(jp.getString("data.createEpisode.id"));
    }
}
