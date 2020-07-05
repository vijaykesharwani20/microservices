package com.stc.phoenix;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Date;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.stc.phoenix.model.User;

import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = RestWebServicesApplication.class)
@TestInstance(Lifecycle.PER_CLASS)
class RestWebServicesApplicationTests extends TestBase{
	
	@Test
	public void getAllUsers_testAllUsers_200() {

		/**
		 * Here we test that we can fetch a all user
		 * Accept the response in JSON format only
		 * and checks status code must be 200
		 * When perform get request for all Users
		 */
		Response response = REQUEST
				.accept(ContentType.JSON)
				.request(Method.GET, "/users");
		response.then().assertThat().statusCode(HttpStatus.SC_OK);
		assertNotNull(response.getBody());

		System.out.println("'getAllUsers_testAllUsers_200()' response:\n" + response.asString());
		
	}
	
	@Test
	public void getUser_testUserById_200() {

		/**
		 * Here we test that we can fetch a single user using its id
		 * Accept the response in JSON format only
		 * and checks status code must be 200
		 * When perform get request for specific userId 10000
		 */
		Response response = REQUEST
				.accept(ContentType.JSON)
				.request(Method.GET, "/users/10000");
		response.then().assertThat().statusCode(HttpStatus.SC_OK);

		JsonPath json = new  JsonPath(response.asString());
		assertEquals(10000, json.getInt("id"));
		System.out.println("'getUser_testUserById_200()' response:\n" + response.asString());
		
	}
	
	@Test
	public void postUser_testUserCreated_201() {

		/**
		 * Here we test that we can create a single user
		 * Send the request in JSON format only
		 * Accept the response in JSON format only
		 * and checks status code must be 201
		 * When perform post request for new user
		 */
		User user = new User();  
		user.setName("Admin"); user.setDob(new Date());
		Response response = REQUEST
				.accept(ContentType.JSON)
				.body(user)
				.request(Method.POST, "/users");
		response.then().assertThat().statusCode(HttpStatus.SC_CREATED);
		
	}
	
	@Test
	public void putUser_testUpdateUser_201() {

		/**
		 * Here we test that we can update a single user
		 * Send the request in JSON format only
		 * Accept the response in JSON format only
		 * and checks status code must be 201
		 * When perform put request for existing user
		 */
		User user = new User();  
		user.setName("Brian Lee"); user.setDob(new Date());
		Response response = REQUEST
				.accept(ContentType.JSON)
				.body(user)
				.request(Method.PUT, "/users/10001");
		response.then().assertThat().statusCode(HttpStatus.SC_CREATED);
		
	}
	
	@Test
	public void deleteUser_testDeleteUser_200() {
		
		/**
		 * Here we test that we can delete a single user
		 * Send the request in JSON format only
		 * Accept the response in JSON format only
		 * and checks status code must be 200
		 * When perform delete request for existing user
		 */
		Response response = REQUEST
				.accept(ContentType.JSON)
				.request(Method.DELETE, "/users/10001");
		response.then().assertThat().statusCode(HttpStatus.SC_OK);
	}

}
