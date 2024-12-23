package com.api.tests;

import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.HttpHeader;

public class APIHeaderTest {
	
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
	public void APIHeadersTest() {
		response = requestContext.get("https://gorest.co.in/public/v2/users");
		Map<String, String> headerMap = response.headers();
		headerMap.forEach((k,v)-> System.out.println(k +" : " +v));
		Assert.assertEquals(headerMap.get("content-type"), "application/json; charset=utf-8");
		System.out.println("******************Test case executed**********************");
	}
	
	@Test
	public void HeaderwithArrayTest() {
		
		List<HttpHeader> headerList = response.headersArray();
		for(HttpHeader e:headerList) {
			System.out.println(e.name +" : " +e.value);
		}
	}
	
	
	@AfterTest
	public void tearDown() {
		response.dispose();
		playwright.close();
	}

}
