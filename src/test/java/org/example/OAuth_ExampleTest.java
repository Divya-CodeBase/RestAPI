package org.example;

import io.restassured.RestAssured;
import org.com.CommonMethods;
import org.com.POJO.CourseDetails;
import org.com.POJO.GetCourses;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;

/**
 * Hello world!
 */
public class OAuth_ExampleTest {
    public static void main(String[] args) {

         RestAssured.baseURI = "https://rahulshettyacademy.com";
        String response = given()
                .log()
                .all()
                .formParams("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
                .formParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
                .formParams("grant_type", "client_credentials")
                .formParams("scope", "trust")
                .when()
                .log().all()
                .post("/oauthapi/oauth2/resourceOwner/token").asString();

        System.out.println(response);
        String token = CommonMethods.parseJsonResponse(response, "access_token");
        System.out.println(token);

        GetCourses gc =
                given().log()
                        .all()
                        .queryParam("access_token", token)
                        .header("Content-Type", "application/json")
                        .when().get("/oauthapi/getCourseDetails").as(GetCourses.class);

        System.out.println(gc.getExpertise());
        System.out.println(gc.getCourses().getMobile().get(0).getCourseTitle());
        List<CourseDetails> WebcourseTitle= gc.getCourses().getWebAutomation();
        List<String> actual= new ArrayList<>();
        for(CourseDetails title: WebcourseTitle){
            System.out.println(title.getCourseTitle());
            actual.add(title.getCourseTitle());
        }
        List<String> actuals=WebcourseTitle.stream().map(CourseDetails::getCourseTitle).toList();

                System.out.println(gc.getCourses().getMobile().get(0).getCourseTitle());

        List<String> Expected= Arrays.asList("Selenium Webdriver Java", "Cypress", "Protractor") ;
        Assert.assertEquals(actuals,Expected);





    }
}
