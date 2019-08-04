package sele1.TwitterRestApi;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

public class PostTwitterTweets {
	public static FileInputStream fis;
	public static Properties prop;
	public static BufferedWriter fout;
	public static String tweetId;

	@BeforeClass
	public void init() throws FileNotFoundException {
		fis= new FileInputStream(new File("C:\\Users\\swati sachan\\git\\TwitTwit\\TwitterRestApi\\src\\test\\java\\sele1\\TwitterRestApi\\Credentials.properties"));
		prop= new Properties();
		try {
			prop.load(fis);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test(priority=0, description="sending the unique tweet into the twitter",enabled=false)
	public void postTweets() throws IOException {
		JsonPath respBody=given().auth().oauth(prop.getProperty("ConsumerKey"), prop.getProperty("ConsumerSecret"), prop.getProperty("Token"), prop.getProperty("TokenScret"))
		.when().post("https://api.twitter.com/1.1/statuses/update.json?status=hello134790new").then()
		.extract().response().jsonPath();
		 JSONObject object1= new JSONObject();
		 
		System.out.println("RESPONSE IS BELOW----");
		object1.put("id_str", respBody.get("id_str"));
		tweetId=respBody.get("id_str");
		object1.put("user.screen_name",respBody.get("user.screen_name"));
		object1.put("text", respBody.get("text"));
		object1.put("user.name", respBody.get("user.name"));
		object1.put("user.location", respBody.get("user.location"));
		object1.put("user.created_at", respBody.get("user.created_at"));
		object1.put("user.geo_enabled", respBody.get("user.geo_enabled"));
		System.out.println(object1.toString());
		//writing the json in file
		fout= new BufferedWriter(new FileWriter("C:\\Users\\swati sachan\\git\\TwitTwit\\TwitterRestApi\\src\\test\\java\\sele1\\TwitterRestApi\\PostTweetsJsonResponse",true));
		fout.append(object1.toString());
		
		fout.close();
	}
	
	@Test(description="posting the same twits twice",priority=1,enabled=false)
	public void postingSameTwits() {
		Response resp=given().auth().oauth(prop.getProperty("ConsumerKey"), prop.getProperty("ConsumerSecret"), prop.getProperty("Token"), prop.getProperty("TokenScret"))
		.when().post("https://api.twitter.com/1.1/statuses/update.json?status=hello1347new")
		.then().extract().response();
		System.out.println("Response is"+resp.body().asString());
		assertEquals(resp.jsonPath().get("errors.code").toString(),"[187]");
		assertEquals(resp.jsonPath().get("errors.message").toString(),"[Status is a duplicate.]");
	}
	
	@Test(description="desrtoy id, deleting the particual tweet based on the id",priority=2,enabled=false)
	public void destroyId() {
		Response resp=given().auth().oauth(prop.getProperty("ConsumerKey"), prop.getProperty("ConsumerSecret"), prop.getProperty("Token"), prop.getProperty("TokenScret"))
		.when().post("https://api.twitter.com/1.1/statuses/destroy/"+tweetId+".json")
		.then().extract().response();
		
		System.out.println(resp.body().asString());
	}
	
	@Test(description="deleting the particular tweet already being deleted",priority=3,enabled=false)
	public void deletingAgain() {
		Response resp=given().auth().oauth(prop.getProperty("ConsumerKey"), prop.getProperty("ConsumerSecret"), prop.getProperty("Token"), prop.getProperty("TokenScret"))
				.when().post("https://api.twitter.com/1.1/statuses/destroy/1157977641699487744.json")
				.then().extract().response();
				
				System.out.println(resp.body().asString());
				assertEquals(resp.jsonPath().get("errors[0].code").toString(),"144");
				assertEquals(resp.jsonPath().get("errors[0].message").toString(),"No status found with that ID.");
				
	}
	
	@Test(description="get-statuses-show-id, getting data from particular Id",priority=4,enabled=false)
	public void showId() {
		Response resp=given().auth().oauth(prop.getProperty("ConsumerKey"), prop.getProperty("ConsumerSecret"), prop.getProperty("Token"), prop.getProperty("TokenScret"))
		.when().get("https://api.twitter.com/1.1/statuses/show.json?id=1157980199889309697")
		.then().extract().response();
		System.out.println(resp.body().asString());
	}
	
	@Test(description="get status look up, getting all the ID's listed ",priority=5,enabled=false)
	public void getStatusLookUp() {
		Response resp=given().auth().oauth(prop.getProperty("ConsumerKey"), prop.getProperty("ConsumerSecret"), prop.getProperty("Token"), prop.getProperty("TokenScret"))
		.when().get("https://api.twitter.com/1.1/statuses/lookup.json?id=1158001383028731904,1157980199889309697")
		.then().extract().response();
		System.out.println(resp.body().asString());
	}
	
	@Test(description="re tweet on a particular tweet based on ID, same twit content can be published one more time",priority=6,enabled=false)
	public void reTweet() {
		Response resp=given().auth().oauth(prop.getProperty("ConsumerKey"), prop.getProperty("ConsumerSecret"), prop.getProperty("Token"), prop.getProperty("TokenScret"))
		.when().post("https://api.twitter.com/1.1/statuses/retweet/"+tweetId+".json")
		.then().extract().response();
		System.out.println(resp.body().asString());
	}
	
	@Test(description="publishing same twit mutiple times will throw the error",priority=7,enabled=true)
	public void reTweetmultipleTimes() {
		Response resp=given().auth().oauth(prop.getProperty("ConsumerKey"), prop.getProperty("ConsumerSecret"), prop.getProperty("Token"), prop.getProperty("TokenScret"))
		.when().post("https://api.twitter.com/1.1/statuses/retweet/1158001383028731904.json")
		.then().extract().response();
		System.out.println(resp.body().asString());
		//will throw as :327(You have already retweeted this Tweet.)
	}
	
	@Test(description="un retweet by reducing two times tweet to single",priority=8,enabled=false)
	public void untweet() {
		Response resp=given().auth().oauth(prop.getProperty("ConsumerKey"), prop.getProperty("ConsumerSecret"), prop.getProperty("Token"), prop.getProperty("TokenScret"))
		.when().post("https://api.twitter.com/1.1/statuses/unretweet/1158001383028731904.json")
		.then().extract().response();
		System.out.println(resp.body().asString());
	}
	
	@Test(description=" retweets ",priority=9)
	public void retweets() {
		Response resp=given().auth().oauth(prop.getProperty("ConsumerKey"), prop.getProperty("ConsumerSecret"), prop.getProperty("Token"), prop.getProperty("TokenScret"))
		.when().post("https://api.twitter.com/1.1/statuses/unretweet/1158001383028731904.json")
		.then().extract().response();
		System.out.println(resp.body().asString());
	}
}
