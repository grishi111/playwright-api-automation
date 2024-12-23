package com.api.tests;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
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

public class GetDataFromFileTest {
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
	public void PostCallFileTest() throws IOException {
		
		File file=new File("./src/test/data/user.json");
		byte[] fileBytes = Files.readAllBytes(file.toPath());	
	
		response = requestContext.post("https://gorest.co.in/public/v2/users", 
				RequestOptions.create()
				.setHeader("Content-Type", "application/json")
				.setHeader("Authorization", "Bearer 7b292d6c43b73a154cb73b957e6a0b5d5934e4e1f39483d30f46658d56237801")
				.setData(fileBytes));
		
		Assert.assertEquals(response.status(), 201);
		ObjectMapper om= new ObjectMapper();
		JsonNode js = om.readTree(response.body());
		System.out.println(js.toPrettyString());
		String userid = js.get("id").asText();
		System.out.println("user id created: " +userid);
	}
	
	@AfterTest
	public void tearDown() {
		response.dispose();
		playwright.close();
	}
}
