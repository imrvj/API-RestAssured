package Basics;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;

import Payloads.Payload;
import Payloads.Reusable;

import io.restassured.RestAssured;

public class WeatherCheck {

	public static void main(String[] args) {
		
	RestAssured.baseURI="https://covid-19-data.p.rapidapi.com";
	
	String res=given().queryParam("name", "Italy").queryParam("date", "2020-04-01").header("Content-Type","application/json")
	.header("x-rapidapi-host","covid-19-data.p.rapidapi.com").header("x-rapidapi-key", "e38214b049msha08dff806626c5cp1ec253jsnb684af858955")
	.when().get("/report/country/name")	
	.then().assertThat().statusCode(200).extract().response().asString();
	
	JsonPath js= new JsonPath(res);
	System.out.println("Country name is "+ js.getString("provinces.confirmed"));
	
			
		
		
	}
}
