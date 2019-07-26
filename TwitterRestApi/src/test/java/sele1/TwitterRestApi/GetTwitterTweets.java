package sele1.TwitterRestApi;

import org.testng.annotations.Test;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class GetTwitterTweets {

	@Test(description="getting timeline tweets")
	public void getTwitts() throws IOException {
		FileInputStream fis= new FileInputStream(new File("C:\\Users\\swati sachan\\git\\TwitTwit\\TwitterRestApi\\src\\test\\java\\sele1\\TwitterRestApi\\Credentials.properties"));
		Properties prop= new Properties();
		prop.load(fis);
		Response resp=given().auth().oauth(prop.getProperty("ConsumerKey"), prop.getProperty("ConsumerSecret"), prop.getProperty("Token"), prop.getProperty("TokenScret"))
		.when().get("https://api.twitter.com/1.1/statuses/home_timeline.json").then().extract().response();
		System.out.println(resp.asString());
	}
}
