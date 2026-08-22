package org.example;

import io.restassured.path.json.JsonPath;
import org.com.PayLoad;
import org.testng.Assert;

import java.util.List;
import java.util.Map;

public class ComplexJsonParse {


    public static void main(String[] args) {
        JsonPath response = new JsonPath(PayLoad.ComplexJsonResponseDummy());
//1. Print No of courses returned by API
        int courseCount = response.getInt("courses.size()");
        System.out.println(courseCount);
        //2. Print Purchase Amount
        int purcahaseAmt = response.getInt("dashboard.purchaseAmount");
        System.out.println(purcahaseAmt);
        //3. Title of first course
        String Title = response.getString("courses[0].title");
        System.out.println(Title);
        //4. Print all courses and prices

        for (int i = 0; i < courseCount; i++) {
            System.out.println(response.getString("courses[" + i + "].title"));
            System.out.println(response.getString("courses[" + i + "].price"));
        }
        List<Map<String, Object>> courses = response.getList("courses");


        courses.stream().forEach(course -> {
                    System.out.println(course.get("title"));
                    System.out.println(course.get("price"));
                }
        );
        //5. Print sum of all prices
        int sum = courses.stream().mapToInt(course -> (Integer) course.get("price")).sum();
        System.out.println(sum);

        //6. Print copies sold by RPA course
        System.out.println(courses.stream().filter(course -> course.get("title").equals("RPA"))
                .map(course -> (Integer) course.get("copies")).findFirst().orElse(0));

        //7. Sum of all course price == Purchase Amount
        int total = courses.stream().mapToInt(course -> (Integer) course.get("price") * (Integer) course.get("copies"))
                .sum();

        Assert.assertEquals(total, purcahaseAmt);
    }

}
