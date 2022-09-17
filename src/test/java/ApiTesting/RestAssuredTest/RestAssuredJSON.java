package ApiTesting.RestAssuredTest;

import java.awt.print.Book;
import java.net.MalformedURLException;
import java.util.List;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RestAssuredJSON {
	
	@Test
	public void JsonPathUsage() throws MalformedURLException
	{
		RestAssured.baseURI = "https://restapi.demoqa.com/utilities/books/getallbooks";
		RequestSpecification httpRequest = RestAssured.given();
		Response response = httpRequest.get("");

		// First get the JsonPath object instance from the Response interface
		JsonPath jsonPathEvaluator = response.jsonPath();

		// Read all the books as a List of String. Each item in the list
		// represent a book node in the REST service Response
		List<String> allBooks = jsonPathEvaluator.getList("books.title");

		// Iterate over the list and print individual book item
		for(String book : allBooks)
		{
			System.out.println("Book: " + book);
		}
	}
	
	@Test
	public void JsonArrayToArray()
	{

		RestAssured.baseURI = "https://restapi.demoqa.com/utilities/books/getallbooks";
		RequestSpecification request = RestAssured.given();

		Response response = request.get();
		System.out.println("Response Body -> " + response.body().asString());

		// We can convert the Json Response directly into a Java Array by using
		// JsonPath.getObject method. Here we have to specify that we want to
		// deserialize the Json into an Array of Book. This can be done by specifying
		// Book[].class as the second argument to the getObject method.
		Book[] books = response.jsonPath().getObject("books",Book[].class );

		for(Book book : books)
		{
			System.out.println("Book title " + book.title());
		}
	}

}
