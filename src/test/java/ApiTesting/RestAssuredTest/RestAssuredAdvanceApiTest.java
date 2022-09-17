package ApiTesting.RestAssuredTest;

import java.awt.Rectangle;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;

public class RestAssuredAdvanceApiTest {
	
	@Test
	public void UserRegistrationSuccessful() 
	{ 
	    RestAssured.baseURI ="https://demoqa.com/Account/v1"; 
	    RequestSpecification request = RestAssured.given(); 
	    JSONObject requestParams = new JSONObject();
	    requestParams.put("userName", "test_rest");
	    requestParams.put("password", "Testrest@123"); 
	    request.body(requestParams.toJSONString());
	    Response response = request.put("/User"); 
	    ResponseBody body = response.getBody();
	    System.out.println(response.getStatusLine());
	    System.out.println(body.asString());
	}
	@Test
	public static void SerializeToFile(Object classObject, String fileName)
	{
		try {

			// Step 1: Open a file output stream to create a file object on disk.
			// This file object will be used to write the serialized bytes of an object
			FileOutputStream fileStream = new FileOutputStream(fileName);

			// Step 2: Create a ObjectOutputStream, this class takes a files stream.
			// This class is responsible for converting the Object of any type into
			// a byte stream
			ObjectOutputStream objectStream = new ObjectOutputStream(fileStream);

			// Step 3: ObjectOutputStream.writeObject method takes an Object and 
			// converts it into a ByteStream. Then it writes the Byte stream into
			// the file using the File stream that we created in step 1.
			objectStream.writeObject(classObject);

			// Step 4: Gracefully close the streams
			objectStream.close();
			fileStream.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public static void Serialize()
	{
		Rectangle rect = new Rectangle(18, 78);
		SerializeToFile(rect, "rectSerialized");
	}
	@Test
	public static Object DeSerializeFromFileToObject(String fileName)
	{
		try {

			// Step 1: Create a file input stream to read the serialized content
			// of rectangle class from the file
			FileInputStream fileStream = new FileInputStream(new File(fileName));

			// Step 2: Create an object stream from the file stream. So that the content
			// of the file is converted to the Rectangle Object instance
			ObjectInputStream objectStream = new ObjectInputStream(fileStream);

			// Step 3: Read the content of the stream and convert it into object
			Object deserializeObject = objectStream.readObject();

			// Step 4: Close all the resources
			objectStream.close();
			fileStream.close();

			// return the deserialized object
			return deserializeObject;

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	@Test
	public static void DeSerialized()
	{
		Rectangle rect = new Rectangle(18, 78);
		SerializeToFile(rect, "rectSerialized");

		Rectangle deSerializedRect = (Rectangle) DeSerializeFromFileToObject("rectSerialized");
		System.out.println("Rect area is " + deSerializedRect.Area());
	}
	
	@Test
	public void UserRegistrationSuccessfulJSON() { 
	RestAssured.baseURI ="https://demoqa.com"; 
	RequestSpecification request = RestAssured.given(); 
	JSONObject requestParams = new JSONObject(); 
	requestParams.put("UserName", "test_rest"); 
	requestParams.put("Password", "rest@123"); 
	request.body(requestParams.toJSONString()); 
	Response response = request.post("/Account/v1/User"); 
	ResponseBody body = response.getBody(); 
	// Deserialize the Response body into JSONSuccessResponse 
	JSONSuccessResponse responseBody = body.as(JSONSuccessResponse.class); 
	// Use the JSONSuccessResponseclass instance to Assert the values of Response. 
	Assert.assertEquals("OPERATION_SUCCESS", responseBody.SuccessCode); 
	Assert.assertEquals("Operation completed successfully", responseBody.Message);
	}
	
	@Test
	public void updateBook() {
		 String userId= "toolsqa_test"; 
		 String baseUrl="https://demoqa.com"; 
		 String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyTmFtZSI6InRlc3RpbmcxMjMiLCJwYXNzd29yZCI6IlBhc3N3b3JkQDEiLCJpYXQiOjE2Mjg1NjQyMjF9.lW8JJvJF7jKebbqPiHOBGtCAus8D9Nv1BK6IoIIMJQ4"; 
		 String isbn ="9781449325865";
	    RestAssured.baseURI = baseUrl;
	    RequestSpecification httpRequest = RestAssured.given().header("Authorization", "Bearer " + token)
	            .header("Content-Type", "application/json");

	    //Calling the Delete API with request body
	    Response res = httpRequest.body("{ \"isbn\": \"" + isbn + "\", \"userId\": \"" + userId + "\"}").put("/BookStore/v1/Book/9781449325862");

	    //Fetching the response code from the request and validating the same
	    System.out.println("The response code - " +res.getStatusCode());
	    Assert.assertEquals(res.getStatusCode(),200);
	}
	
	@Test
	  public void deleteBook() {
		String userId= "de5d75d1-59b4-487e-b632-f18bc0665c0d";
		String baseUrl="https://demoqa.com/swagger/";
		String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyTmFtZSI6InRlc3RpbmcxMjMiLCJwYXNzd29yZCI6IlBhc3N3b3JkQDEiLCJpYXQiOjE2Mjg1NjQyMjF9.lW8JJvJF7jKebbqPiHOBGtCAus8D9Nv1BK6IoIIMJQ4";
		String isbn ="9781449337711";
		  RestAssured.baseURI = baseUrl;
		  RequestSpecification httpRequest = RestAssured.given().header("Authorization", "Bearer " + token)
			         .header("Content-Type", "application/json");
		 
		  //Calling the Delete API with request body
		  Response res = httpRequest.body("{ \"isbn\": \"" + isbn + "\", \"userId\": \"" + userId + "\"}").delete("/BookStore/v1/Book");
	 
		  //Fetching the response code from the request and validating the same
		  System.out.println("The response code is - " +res.getStatusCode());
	      Assert.assertEquals(res.getStatusCode(),204);
   
	  }
	
}
