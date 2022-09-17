package allMethods;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;

public class GetMethods {

	@Test
	public void get1()
	{
		RestAssured.baseURI = "https://demoqa.com/BookStore/v1/Books";
		RequestSpecification request = RestAssured.given();
		Response response = request.get();
		
		ResponseBody body = response.getBody();
		int statusCode = response.getStatusCode();
		
		System.out.println("Get status code = "+statusCode);
		System.out.println("Get body  = "+body.asString());
		System.out.println("Response=>" + response.prettyPrint());
	}
}
