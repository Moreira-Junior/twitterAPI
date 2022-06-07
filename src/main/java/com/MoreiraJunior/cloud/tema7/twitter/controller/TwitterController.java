package com.MoreiraJunior.cloud.tema7.twitter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.MoreiraJunior.cloud.tema7.twitter.service.TwitterService;
import com.fasterxml.jackson.databind.JsonNode;

@RestController
@RequestMapping("/twitter")
public class TwitterController {
	
	@Autowired
	private TwitterService twitterService;
	
	@GetMapping("/{user}")
	public ResponseEntity<Object> getTwitter(@PathVariable(value = "user")String user) {
		if(user == null || user.isBlank()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User cannot be null");
		}
		try {
			JsonNode json = twitterService.getResponseJson(user);
			if(json.has("errors")) {
				return ResponseEntity.status(HttpStatus.OK).body(json.get("errors").get(0).get("detail"));
			}
			JsonNode userName = json.get("data").get("name");
			JsonNode numberOfTweets = json.get("data").get("public_metrics").get("tweet_count");
			return ResponseEntity.status(HttpStatus.OK).body(userName + " has " + numberOfTweets + " tweets!");
		}
		 catch (Throwable e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong with your request!");
		}
	}
}
