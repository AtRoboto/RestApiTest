package allMethods;

import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;

public class PatchMethods {

	@Test
	public void put1()
	{
		RestAssured.baseURI = "https://reqres.in";
		RequestSpecification request = RestAssured.given();
		JSONObject requestParam = new JSONObject();
		//requestParam.put("name", "morpheus");
		requestParam.put("job", "zion resident");
		request.header("Content-Type","application/json");
		request.body(requestParam.toJSONString());
		Response response = request.patch("/api/users/2");
		
		int statusCode = response.getStatusCode();
		ResponseBody body = response.getBody();
		System.out.println("Patch status code = "+statusCode);
		System.out.println("Patch body = "+body.asString());
		
	}
}
