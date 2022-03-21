package test1;

import static io.restassured.RestAssured.given;

import org.json.simple.JSONObject;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class assignment3 {

		@Test(enabled = true)
		public void createUser(ITestContext val)
		{
			try
			{
				RestAssured.baseURI="https://petstore.swagger.io/v2";
				JSONObject obj = new JSONObject();
				obj.put("username", "kakashi");
				obj.put("firstName", "kakashi");
				obj.put("lastName", "Kaka");
				obj.put("email", "kaka@shi.com");
				obj.put("password", "kaka1234");
				obj.put("phone", "9876543210");
				
				String u_name="kakashi";
				
	//			System.out.println(obj.toJSONString());
				
				
				given()
				.contentType(ContentType.JSON)
				.body(obj.toJSONString()).
			when()
				.post("/user").
			then()
				.statusCode(200)
				.log()
				.all();
				
				val.setAttribute("username", u_name);
			}
			catch(Exception e)
			{
				System.out.println("Error occured::"+e);
			}
		}
		
		@Test(enabled = true, dependsOnMethods="createUser")
		public void login()
		{
			try
			{
				RestAssured.baseURI="https://petstore.swagger.io/v2";
				given().
				when()
					.queryParam("username","kakashi")
					.queryParam("password","kaka1234")
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
		public void edit(ITestContext val)
		{
			try
			{
				RestAssured.baseURI="https://petstore.swagger.io/v2";
				JSONObject obj = new JSONObject();
				obj.put("username", "obito");
				obj.put("firstName", "obito");
				obj.put("lastName", "uchiha");
				obj.put("email", "obi@ichi.com");
				obj.put("password", "obi1234");
				obj.put("phone", "9876543210");
				
				String u_name="obito";
				
	//			System.out.println(obj.toJSONString());
				
				given()
				.contentType(ContentType.JSON)
				.body(obj.toJSONString()).
			when()
				.put("/user/"+val.getAttribute("username")).
			then()
				.statusCode(200)
				.log()
				.all();
	//			System.out.println(val.getAttribute("username"));
				val.setAttribute("username", u_name);
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
				
	//			System.out.println(obj.toJSONString());
	//			System.out.println(val.getAttribute("username"));
	
				
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
