package Payloads;

import io.restassured.path.json.JsonPath;

public class Reusable {

	public static JsonPath rawToJSON(String response)
	{
		JsonPath js=new JsonPath(response);
		return js;
	}
}
