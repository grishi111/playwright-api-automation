package com.api.tests;

import java.io.IOException;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;

public class GetAPITest {
	
	Playwright playwright;
	APIRequest request;
	APIRequestContext requestContext;
	APIResponse response;
	
	
	@BeforeTest
	public void setUp() {
	playwright = Playwright.create();
	request = playwright.request();
	requestContext = request.newContext();
	}
	
	@Test
	public void verifyGetCall() throws IOException {
	
	response = requestContext.get("https://gorest.co.in/public/v2/users",RequestOptions.create().setQueryParam("id", 5850643).setQueryParam("gender", "male"));
	int resCode = response.status();
	System.out.println(resCode);
	System.out.println(response.text());
	
	ObjectMapper om = new ObjectMapper();
	JsonNode jnode = om.readTree(response.body());
	response.dispose(); //dispose only response body only not other things in response
	String jsonPrettyResponse = jnode.toPrettyString();
	System.out.println(jsonPrettyResponse);

	}
	@Test
	public void verifyGetCallWithQueryParams() throws IOException {
	
	//APIResponse response = requestContext.get("https://gorest.co.in/public/v2/users",);
	response = requestContext.get("https://gorest.co.in/public/v2/users");
	int resCode = response.status();
	System.out.println(resCode);
	System.out.println(response.text());
	
	ObjectMapper om = new ObjectMapper();
	JsonNode jnode = om.readTree(response.body());
	
	String jsonPrettyResponse = jnode.toPrettyString();
	System.out.println(jsonPrettyResponse);

	}
	
	@AfterTest
	public void tearDown() {
		response.dispose();
		playwright.close();
	}
	
}
