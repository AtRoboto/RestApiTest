package ApiTesting.RestAssuredTest;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;

public class RestAssuredApiTest {

	@Test
	public void getBookDetails()
	{
		// Specify the base URL to the RESTful web service 
		//RestAssured.baseURI = "https://demoqa.com/BookStore/v1/Books"; 
        RestAssured.baseURI = "https://demoqa.com/Account/v1/User/"; // Error

		
		// Get the RequestSpecification of the request to be sent to the server. 
		RequestSpecification httpRequest = RestAssured.given();
		
		// specify the method type (GET) and the parameters if any. 
		//In this case the request does not take any parameters 
		//Response response = httpRequest.get(""); // hort-hand methods provided by Rest Assured
		Response response = httpRequest.request(Method.GET,"");
		//Response response = httpRequest.request(Method.GET,"test"); // Used for error in uri

		int statusCode = response.getStatusCode();
		String statusLine = response.getStatusLine();
		
		// Print the status and message body of the response received from the server 
		System.out.println("Status code received =  "+statusCode);
		System.out.println("Status line received =  "+response.getStatusLine());
		System.out.println("Response = "+response.prettyPrint());
		
		Assert.assertEquals(statusCode, 200,"Status code is verified = 200");
		Assert.assertEquals(statusLine, "HTTP/1.1 200 OK","Status line is verified");
	}
	
	@Test 
	public void IteratingHeaders() 
	{ RestAssured.baseURI = "https://demoqa.com/BookStore/v1/Books"; 
	 RequestSpecification httpRequest = RestAssured.given(); 
	 Response response = httpRequest.get(""); 
	 // Get all the headers and then iterate over allHeaders to print each header 
	 Headers allHeaders = response.headers(); 
	 // Iterate over all the Headers 
	 for(Header header : allHeaders) { 
	   System.out.println("Key: " + header.getName() + " Value: " + header.getValue()); 
	 } 
	}
	
	@Test
	public void GetBookHeaders() { 
	RestAssured.baseURI = "https://demoqa.com/BookStore/v1/Books";
	RequestSpecification httpRequest = RestAssured.given();
	Response response = httpRequest.get(""); 
	// Access header with a given name. 
	String contentType = response.header("Content-Type"); 
	System.out.println("Content-Type value: " + contentType); 
	// Access header with a given name. 
	String serverType = response.header("Server"); 
	System.out.println("Server value: " + serverType); 
	// Access header with a given name. Header = Content-Encoding 
	String acceptLanguage = response.header("Content-Encoding"); 
	System.out.println("Content-Encoding: " + acceptLanguage); 
	  } 
	
	@Test
	public void ValidateBookHeaders() 
	{ 
		RestAssured.baseURI = "https://demoqa.com/BookStore/v1/Books";
		RequestSpecification  httpRequest = RestAssured.given();
		Response response = httpRequest.get("");
		
		// Access header with a given name. Header = Content-Type 
		String contentType = response.header("Content-Type");
		Assert.assertEquals(contentType, "application/json; charset=utf-8","Content type validated");
		
		// Access header with a given name. Header = Server 
		String serverType = response.header("Server");
		//Assert.assertEquals(serverType, "nginx/1.17.10 (Ubuntu)", "Server type validated");
	}
	
	@Test
	public void WeatherMessageBody()
	{
		RestAssured.baseURI = "https://restapi.demoqa.com/utilities/weather/city";
		RequestSpecification httpRequest = RestAssured.given();
		Response response = httpRequest.get("/Hyderabad");

		// Retrieve the body of the Response
		ResponseBody body = response.getBody();

		// By using the ResponseBody.asString() method, we can convert the  body
		// into the string representation.
		System.out.println("Response Body is: " + body.asString());
		
		// By using the ResponseBody.asString() method, we can convert the  body
		// into the string representation.
		System.out.println("Response Body is: " + body.asString());
		Assert.assertEquals(body.asString().contains("Hyderabad"), true, "Response body contains Hyderabad");

	}
	@Test
	public void VerifyCityInJsonResponse()
	{
		RestAssured.baseURI = "https://restapi.demoqa.com/utilities/weather/city";
		RequestSpecification httpRequest = RestAssured.given();
		Response response = httpRequest.get("/Hyderabad");

		// First get the JsonPath object instance from the Response interface
		JsonPath jsonPathEvaluator = response.jsonPath();

		// Then simply query the JsonPath object to get a String value of the node
		// specified by JsonPath: City (Note: You should not put $. in the Java code)
		String city = jsonPathEvaluator.get("City");

		// Let us print the city variable to see what we got
		System.out.println("City received from Response " + city);

		// Validate the response
		Assert.assertEquals(city, "Hyderabad", "Correct city name received in the Response");
	}
	@Test
	public void DisplayAllNodesInWeatherAPI()
	{
		RestAssured.baseURI = "https://restapi.demoqa.com/utilities/weather/city";
		RequestSpecification httpRequest = RestAssured.given();
		Response response = httpRequest.get("/Hyderabad");

		// First get the JsonPath object instance from the Response interface
		JsonPath jsonPathEvaluator = response.jsonPath();

		// Let us print the city variable to see what we got
		System.out.println("City received from Response " + jsonPathEvaluator.get("City"));

		// Print the temperature node
		System.out.println("Temperature received from Response " + jsonPathEvaluator.get("Temperature"));

		// Print the humidity node
		System.out.println("Humidity received from Response " + jsonPathEvaluator.get("Humidity"));

		// Print weather description
		System.out.println("Weather description received from Response " + jsonPathEvaluator.get("Weather"));

		// Print Wind Speed
		System.out.println("City received from Response " + jsonPathEvaluator.get("WindSpeed"));

		// Print Wind Direction Degree
		System.out.println("City received from Response " + jsonPathEvaluator.get("WindDirectionDegree"));
	}

}
