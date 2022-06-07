package com.MoreiraJunior.cloud.tema7.twitter.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class TwitterService {
	
	@Value("${app.token}")
	private String bearerToken;
	
	public JsonNode getResponseJson(String user) throws Throwable {
		URL url;
		try {
			url = new URL("https://api.twitter.com/2/users/by/username/"+user+"?user.fields=public_metrics");
			HttpURLConnection http = (HttpURLConnection)url.openConnection();
			http.setRequestProperty("Authorization", "Bearer "+ bearerToken);
			http.setRequestProperty("Content-Type", "application/json");
			BufferedReader reader = new BufferedReader(new 
					InputStreamReader(http.getInputStream()));
			ObjectMapper mapper = new ObjectMapper();
			JsonNode json = mapper.readTree(reader);
			return json;
		} catch (MalformedURLException e) {
			throw new MalformedURLException();
		} catch (IOException e) {
			throw new IOException();
		}
	}	
}
	
