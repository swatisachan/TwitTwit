package sele1.TwitterRestApi;

import org.testng.annotations.Test;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class GetTwitterTweets {

	@Test
	public void getTwitts() {
		
		Response resp=given().auth().oauth("PKgt2ZCsWp5KoAD9NhzoqwVBM", "AlEd1cin1vc2hd4Qun5MY09ZeeAeFtXqtPzXHVUrRwkbiZAIB9", "1153961818777219073-AgWT0mUkBbwMMaSe9NqLMyNYsrHDc0", "xgADYYSRzkAilFyGTMACTfAbe1I7lUR5GesBw67Y5eG4E")
		.when().get("https://api.twitter.com/1.1/statuses/home_timeline.json").then().extract().response();
		System.out.println(resp.asString());
	}
}
