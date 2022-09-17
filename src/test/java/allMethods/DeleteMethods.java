package allMethods;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;

public class DeleteMethods {
	
	@Test
	public void delete1()
	{
		RestAssured.baseURI = "https://reqres.in/";
		RequestSpecification request = RestAssured.given();
		Response response = request.delete("/api/users/2");
		
		int statusCode = response.getStatusCode();
		ResponseBody body = response.getBody();
		System.out.println("Delete status code = "+statusCode);
		System.out.println("Delete body = "+body.asString());
		
	}

}
