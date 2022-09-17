package ApiTesting.RestAssuredTest;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;

public class RestAssuredAll {
	String userId= "de5d75d1-59b4-487e-b632-f18bc0665c0d";
	String baseUrl="https://demoqa.com";
	String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyTmFtZSI6InRlc3RpbmcxMjMiLCJwYXNzd29yZCI6IlBhc3N3b3JkQDEiLCJpYXQiOjE2Mjg1NjQyMjF9.lW8JJvJF7jKebbqPiHOBGtCAus8D9Nv1BK6IoIIMJQ4";
	String isbn ="9781449337711";
		

	@Test
	public void GetBooksDetails() { 
		// Specify the base URL to the RESTful web service 
		RestAssured.baseURI = "https://demoqa.com/BookStore/v1/Books"; 
		// Get the RequestSpecification of the request to be sent to the server. 
		RequestSpecification httpRequest = RestAssured.given(); 
		// specify the method type (GET) and the parameters if any. 
		//In this case the request does not take any parameters 
		Response response = httpRequest.request(Method.GET, "");
		// Print the status and message body of the response received from the server 
		System.out.println("Status received => " + response.getStatusLine()); 
		System.out.println("Response=>" + response.prettyPrint());
	   }
	@Test
	public void postRequestBooksAPI() 
	{ 
	   RestAssured.baseURI = "https://demoqa.com"; 
	   RequestSpecification request = RestAssured.given();
	   // JSONObject is a class that represents a Simple JSON. 
	   // We can add Key - Value pairs using the put method 
	   JSONObject requestParams = new JSONObject(); 
	   requestParams.put("userId", "TQ123"); 
	   requestParams.put("isbn", "9781449325862"); 
	   // Add a header stating the Request body is a JSON 
	   request.header("Content-Type", "application/json"); 
	   // Add the Json to the body of the request 
	   request.body(requestParams.toJSONString()); 
	   // Post the request and check the response
	   Response response = request.post("/BookStore/V1/Books"); 
	   System.out.println("The status received: " + response.statusLine());
	   System.out.println("The status received: " + response.statusCode());
	   JsonPath jsonPath = response.jsonPath();
	   System.out.println(jsonPath.getString("userId"));
	   
	}
	 @Test
     public void updateBook() {
	  RestAssured.baseURI = baseUrl;
	  RequestSpecification httpRequest = RestAssured.given()
			  .header("Authorization", "Bearer " + token)
			  .header("Content-Type", "application/json");
 
	  //Calling the Delete API with request body
	  Response res = httpRequest.body("{ \"isbn\": \"" + isbn + "\", \"userId\": \"" 
	  + userId + "\"}").put("/BookStore/v1/Book/9781449325862");
	 
	  //Fetching the response code from the request and validating the same
	  System.out.println("The response code - " +res.getStatusCode());
          Assert.assertEquals(res.getStatusCode(),200);
	     
	  }
	  @Test
	  public void deleteBook() {
		  RestAssured.baseURI = baseUrl;
		  RequestSpecification httpRequest = RestAssured.given().header("Authorization", "Bearer " + token)
			         .header("Content-Type", "application/json");
		 
		  //Calling the Delete API with request body
		  Response res = httpRequest.body("{ \"isbn\": \"" + isbn + "\", \"userId\": \"" + userId + "\"}").delete("/BookStore/v1/Book");
	 
		  //Fetching the response code from the request and validating the same
		  System.out.println("The response code is - " +res.getStatusCode());
	      Assert.assertEquals(res.getStatusCode(),204);
     
	  }
	  
	  @Test
	  public void e2eExample() {
	  String userID = "9b5f49ab-eea9-45f4-9d66-bcf56a531b85";
      String userName = "TOOLSQA-Test";
      String password = "Test@@123";
      String baseUrl = "https://bookstore.toolsqa.com";

      RestAssured.baseURI = baseUrl;
      RequestSpecification request = RestAssured.given();


      //Step - 1
      //Test will start from generating Token for Authorization
      request.header("Content-Type", "application/json");

      Response response = request.body("{ \"userName\":\"" + userName + "\", \"password\":\"" + password + "\"}")
              .post("/Account/v1/GenerateToken");

      Assert.assertEquals(response.getStatusCode(), 200);

      String jsonString = response.asString();
      Assert.assertTrue(jsonString.contains("token"));

      //This token will be used in later requests
      String token = JsonPath.from(jsonString).get("token");


      //Step - 2
      // Get Books - No Auth is required for this.
      response = request.get("/BookStore/v1/Books");

      Assert.assertEquals(response.getStatusCode(), 200);

      jsonString = response.asString();
      List<Map<String, String>> books = JsonPath.from(jsonString).get("books");
      Assert.assertTrue(books.size() > 0);

       //This bookId will be used in later requests, to add the book with respective isbn
      String bookId = books.get(0).get("isbn");


      //Step - 3
      // Add a book - with Auth
      //The token we had saved in the variable before from response in Step 1, 
      //we will be passing in the headers for each of the succeeding request
      request.header("Authorization", "Bearer " + token)
              .header("Content-Type", "application/json");

      response = request.body("{ \"userId\": \"" + userID + "\", " +
              "\"collectionOfIsbns\": [ { \"isbn\": \"" + bookId + "\" } ]}")
              .post("/BookStore/v1/Books");

      Assert.assertEquals( 201, response.getStatusCode());


      //Step - 4
      // Delete a book - with Auth
      request.header("Authorization", "Bearer " + token)
              .header("Content-Type", "application/json");

      response = request.body("{ \"isbn\": \"" + bookId + "\", \"userId\": \"" + userID + "\"}")
              .delete("/BookStore/v1/Book");

      Assert.assertEquals(204, response.getStatusCode());

      //Step - 5
      // Get User
      request.header("Authorization", "Bearer " + token)
              .header("Content-Type", "application/json");

      response = request.get("/Account/v1/User/" + userID);
      Assert.assertEquals(200, response.getStatusCode());

      jsonString = response.asString();
      List<Map<String, String>> booksOfUser = JsonPath.from(jsonString).get("books");
      Assert.assertEquals(0, booksOfUser.size());
  }


}
