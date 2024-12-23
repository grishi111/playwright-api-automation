package com.api.tests;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.api.data.Users;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;

public class ApiTestWithlombokPojoTest {
	Playwright playwright;
	APIRequest request;
	APIRequestContext requestContext;
	APIResponse response;
	static String email;
	//generate different/random email id everytime
	public String getRandomEmailId() {
		email = "testemail"+System.currentTimeMillis()+"@test.com";
		return email;
	}
	
	@BeforeTest
	public void setUp() {
	playwright = Playwright.create();
	request = playwright.request();
	requestContext = request.newContext();
	}
	
	@Test
	public void APIPostCallTest() throws IOException {
		
		Users user = Users.builder().build();
		response = requestContext.post("https://gorest.co.in/public/v2/users", 
				RequestOptions.create()
				.setHeader("Content-Type", "application/json")
				.setHeader("Authorization", "Bearer 7b292d6c43b73a154cb73b957e6a0b5d5934e4e1f39483d30f46658d56237801")
				.setData(userData));
		
		Assert.assertEquals(response.status(), 201);
		ObjectMapper om= new ObjectMapper();
		JsonNode js = om.readTree(response.body());
		System.out.println(js.toPrettyString());
		String userid = js.get("id").asText();
		System.out.println("user id created: " +userid);
		
		APIResponse getCallResponse = requestContext.get("https://gorest.co.in/public/v2/users/"+userid, RequestOptions.create()
				.setHeader("Content-Type", "application/json")
				.setHeader("Authorization", "Bearer 7b292d6c43b73a154cb73b957e6a0b5d5934e4e1f39483d30f46658d56237801"));
		Assert.assertEquals(getCallResponse.status(), 200);
		Assert.assertEquals(getCallResponse.statusText(), "OK");
		Assert.assertTrue(getCallResponse.text().contains(userid));
		Assert.assertTrue(getCallResponse.text().contains(email));
	}
	
	@AfterTest
	public void tearDown() {
		response.dispose();
		playwright.close();
	}
	

}
