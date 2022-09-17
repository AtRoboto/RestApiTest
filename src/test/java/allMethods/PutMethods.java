package allMethods;

import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;

public class PutMethods {
	
	@Test
	public void put1()
	{
		RestAssured.baseURI = "https://reqres.in";
		RequestSpecification request = RestAssured.given();
		JSONObject requestParam = new JSONObject();
		requestParam.put("name", "morpheus");
		requestParam.put("job", "zion resident");
		request.header("Content-Type", "application/json");
		request.body(requestParam.toJSONString());
		Response response = request.put("/api/users/2");
		ResponseBody body = response.getBody();
		
		int statusCode = response.getStatusCode();
		System.out.println("Put status code = "+statusCode);
		System.out.println("Put body = "+body.asString());
	}

}
