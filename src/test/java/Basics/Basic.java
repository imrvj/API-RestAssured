package Basics;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;

import Payloads.Payload;
import Payloads.Reusable;

public class Basic {

	public static void main(String[] args) {
		

		RestAssured.baseURI = "https://rahulshettyacademy.com";

		// Add Place
		String AddPlaceresponse = given().relaxedHTTPSValidation().queryParam("key", "qaclick123")
				.header("Content-Type", "text/plain").body(Payload.AddPlace()).when().post("maps/api/place/add/json")
				.then().assertThat().statusCode(200).body("scope", equalTo("APP"))
				.header("Server", "Apache/2.4.18 (Ubuntu)").extract().response().asString();

		JsonPath js=Reusable.rawToJSON(AddPlaceresponse);
		String PlaceID = js.getString("place_id");
		System.out.println("Place added and Place ID is :" + js.getString("place_id"));

		// Update Place
		String address="Dwarka,New Delhi";
		String UpdatePlace=given().relaxedHTTPSValidation().queryParam("key", "qaclick123").header("Content-Type", "text/plain")
				.body("{\r\n" + "\"place_id\":\"" + PlaceID + "\",\r\n" + "\"address\":\""+address+"\",\r\n"
						+ "\"key\":\"qaclick123\"\r\n" + "}\r\n" + "")
				.when().put("/maps/api/place/update/json")
				.then().assertThat().statusCode(200)
				.body("msg", equalTo("Address successfully updated")).extract().response().asString();
		
		JsonPath js3=new JsonPath(UpdatePlace);
		System.out.println("Status of Update: "+js3.getString("msg"));
		
		//Get Place
		String getplaceresponse=given().relaxedHTTPSValidation().queryParam("key", "qaclick123").queryParam("place_id", PlaceID)
		.when().get("/maps/api/place/get/json")
		.then().assertThat().statusCode(200).body("address", equalTo(address)).extract().response().asString();
		
		JsonPath js1=new JsonPath(getplaceresponse);
		String ActualAddress=js1.getString("address");
		System.out.println("New Place address is :"+js1.getString("address"));
		Assert.assertEquals(ActualAddress, "Dwarka,New Delhi");
		
		//Delete Place
		String DeleteResponse=given().relaxedHTTPSValidation().queryParam("key", "qaclick123").header("Content-Type", "text/plain")
		.body("{\r\n" + 
				"    \"place_id\":\""+PlaceID+"\"\r\n" + 
				"}\r\n" + 
				"")
		.when().delete("/maps/api/place/delete/json")
		.then().assertThat().statusCode(200).body("status", equalTo("OK")).extract().response().asString();
		
		JsonPath js2=new JsonPath(DeleteResponse);
		System.out.println("Address Deleted :"+js2.getString("status"));
		

	}

}
