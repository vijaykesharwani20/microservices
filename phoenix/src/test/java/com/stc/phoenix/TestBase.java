package com.stc.phoenix;


import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.io.IOException;
import java.util.Properties;

public class TestBase {
    public RequestSpecification REQUEST;

    public TestBase() {
        try {
            Properties props = new Properties();
            props.load(getClass().getClassLoader().getResourceAsStream("config.properties"));

            //Rest Assured config
            RestAssured.baseURI = props.getProperty("api.uri");
            //RestAssured.port = Integer.valueOf(props.getProperty("api.port"));
            RestAssured.basePath = props.getProperty("api.basePath");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        //basic request setting
        REQUEST = RestAssured.given().contentType(ContentType.JSON);
    }
}