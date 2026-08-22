package org.example;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import org.com.POJO.*;
import org.testng.annotations.Test;

import java.io.File;
import java.util.Arrays;

import static io.restassured.RestAssured.given;

public class EcommWebAppTest {
    String Token;
    String userID;
    String productID;
    String orderID;

    LoginDetails ld = new LoginDetails();


    @Test(priority = 1)
    public void loginApplication() {
        ld.setUserEmail("testuser001@mail.com");
        ld.setUserPassword("Learn@123");

        RequestSpecification req = new RequestSpecBuilder()
                .setBaseUri("https://rahulshettyacademy.com")
                .setContentType(ContentType.JSON)
                .build();

        LoginResponse res = given().spec(req).body(ld).when().post("/api/ecom/auth/login").then().extract().response().as(LoginResponse.class);
        Token = res.getToken();
        userID = res.getUserId();
    }

    @Test(priority = 2, enabled = false)
    public void CreateProduct() {
        RequestSpecification req = new RequestSpecBuilder()
                .setBaseUri("https://rahulshettyacademy.com")
                .addHeader("authorization", Token)
                .build();

        ProductCreationData pData = given().spec(req).param("productName", "Laptop")
                .param("productAddedBy", userID)
                .param("productCategory", "electronic")
                .param("productSubCategory", "small")
                .param("productPrice", "11223")
                .param("productDescription", "office")
                .param("productFor", "all")
                .multiPart("productImage", new File("/Users/vivek_ravi/Desktop/Testing.png"))
                .when().post("/api/ecom/product/add-product").then().extract().response().as(ProductCreationData.class);

        productID = pData.getProductId();
    }

    @Test(priority = 3, enabled = false)
    public void CreateOrder() {
        OrderRequest or = new OrderRequest();
        OrderInputDetails oid = new OrderInputDetails();
        oid.setProductOrderedId(productID);
        oid.setCountry("Canada");
        or.setOrders(Arrays.asList(oid));

        RequestSpecification req = new RequestSpecBuilder()
                .setBaseUri("https://rahulshettyacademy.com")
                .addHeader("authorization", Token)
                .build();

        String res1 = given().log().all().spec(req).body(or).when().log().all().post("/api/ecom/order/create-order").then().extract().response().asString();
        System.out.println(res1);
        JsonPath jp = new JsonPath(res1);
        orderID = jp.getString("orders");
        System.out.println(orderID);
    }

    @Test(priority = 2)
    public void DeleteOrder() {

        RequestSpecification req = new RequestSpecBuilder()
                .setBaseUri("https://rahulshettyacademy.com")
                .addHeader("authorization", Token)
                .build();

        String res1 = given().log().all().spec(req).pathParam("productId","69bf405af86ba51a651b2326")
                .when().delete("/api/ecom/product/delete-product/{productId}")
                .then().extract().response().asString();

        System.out.println(res1);


    }

}
