package test1;

import static io.restassured.RestAssured.given;

import org.json.simple.JSONObject;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Pojo.pojoclass;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class assignment3_with_pojo {
	
	@Test(enabled=true)
	public void createUser(ITestContext val) throws JsonProcessingException
	{
		try
		{
			
			RestAssured.baseURI="https://petstore.swagger.io/v2";
			pojoclass obj = new pojoclass();
			obj.setUsername("john");
			obj.setFirstname("John");
			obj.setLastname("BanegaDon");
			obj.setEmail("john@a.com");
			obj.setPassword("123456");
			obj.setPhone("9876543210");
			
			ObjectMapper objmapper = new ObjectMapper();
			
			System.out.println(objmapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj));
			
			
			
			Response resp=given()
			.contentType(ContentType.JSON)
			.body(objmapper.writeValueAsString(obj)).
		when()
			.post("/user").
		then()
			.statusCode(200)
			.log()
			.all()
			.extract()
			.response();
			
			String code= resp.jsonPath().getString("code");
			System.out.println(code);
			
			val.setAttribute("username", obj.getUsername());
			System.out.println(val.getAttribute("username"));
			val.setAttribute("password", obj.getPassword());
			System.out.println(val.getAttribute("password"));
		}
		catch(Exception e)
		{
			System.out.println("Error occured::"+e);
		}
		
		
	}
	
	@Test(enabled = true, dependsOnMethods="createUser")
	public void login(ITestContext val)
	{
		try
		{
			RestAssured.baseURI="https://petstore.swagger.io/v2";
			given().
			when()
				.queryParam("username",val.getAttribute("username"))
				.queryParam("password",val.getAttribute("password"))
				.get("/user/login").
			then()
				.statusCode(200)
				.log()
				.all();
		}
		catch(Exception e)
		{
			System.out.println("Error occured::"+e);
		}
	} 
	
	@Test(enabled = true, dependsOnMethods= "login")
	public void edit(ITestContext val) throws JsonProcessingException
	{
		try
		{
			RestAssured.baseURI="https://petstore.swagger.io/v2";
			pojoclass obj = new pojoclass();
			obj.setUsername("gara");
			obj.setFirstname("gara");
			obj.setLastname("sand");
			obj.setEmail("gara@sand.com");
			obj.setPassword("12345678");
			obj.setPhone("9876543210");
			
			ObjectMapper objmapper = new ObjectMapper();
			
	//		System.out.println(objmapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj));
			
			
			
			given()
			.contentType(ContentType.JSON)
			.body(objmapper.writeValueAsString(obj)).
		when()
			.put("/user/"+val.getAttribute("username")).
		then()
			.statusCode(200)
			.log()
			.all();
	
			val.setAttribute("username", obj.getUsername());
		}
		catch(Exception e)
		{
			System.out.println("Error occured::"+e);
		}
		
	}
	
	@Test(enabled = true, dependsOnMethods= "edit")
	public void logout()
	{
		try
		{
			RestAssured.baseURI="https://petstore.swagger.io/v2";
			given().
			when()
				.get("/user/logout").
			then()
				.statusCode(200)
				.log()
				.all();
		}
		catch(Exception e)
		{
			System.out.println("Error occured::"+e);
		}
	}
	
	@Test(enabled = true, dependsOnMethods="logout")
	public void delete(ITestContext val)
	{
		try
		{
			RestAssured.baseURI="https://petstore.swagger.io/v2";
			
	//		System.out.println(obj.toJSONString());
	//		System.out.println(val.getAttribute("username"));
	
			
			given().
			when()
			.delete("/user/"+val.getAttribute("username").toString()).
		then()
			.statusCode(200)
			.log()
			.all();
		}
		catch(Exception e)
		{
			System.out.println("Error occured::"+e);
		}
		

	}
	

}
