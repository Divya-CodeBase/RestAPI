package org.com;

import io.restassured.path.json.JsonPath;

public class CommonMethods {

    public static String parseJsonResponse(String response, String FieldName){
        JsonPath json1 = new JsonPath(response);
        return json1.getString(FieldName);
    }
}
