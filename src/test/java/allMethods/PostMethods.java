package allMethods;

import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;

public class PostMethods {
	
	@Test
	public void PostMethodWithoutBody() {
		RestAssured.baseURI = "https://reqres.in";
		RequestSpecification request = RestAssured.given();
		JSONObject json = new JSONObject();
		request.header("Content-Type", "application/json");
		
		Response response = request.post("/api/users?page=2");
		int statusCode = response.getStatusCode();
		System.out.println("Post status code = "+ statusCode);
	}
	@Test
	public void PostMethodWithBody() {
		RestAssured.baseURI = "https://reqres.in";
		RequestSpecification request = RestAssured.given();
		JSONObject requestParam = new JSONObject();
		requestParam.put("email", "eve.holt@reqres.in");
		requestParam.put("password", "pistol");
		request.header("Content-Type", "application/json");
		request.body(requestParam.toJSONString()); 
		Response response = request.post("/api/register");
		int statusCode = response.getStatusCode();
		ResponseBody body = response.getBody(); 
		
		JsonPath jsonPathEvaluator = response.jsonPath();
		String token = jsonPathEvaluator.get("token");
		String CF_RAY = response.header("CF-RAY");
		
		System.out.println("Post status code = "+ statusCode);
		System.out.println("Post status received => " + response.getStatusLine()); 
		System.out.println("Post Response=>" + response.prettyPrint());
		System.out.println("Post Response Body is: " + body.asString());
		System.out.println("Post token is: " + token);
		System.out.println("Post CF-RAY is: " + CF_RAY);
	}

}
