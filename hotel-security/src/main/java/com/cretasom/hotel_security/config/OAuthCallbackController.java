package com.cretasom.hotel_security.config;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class OAuthCallbackController {

	private static final Logger logger = LoggerFactory.getLogger(OAuthCallbackController.class);
	@Value("${github.client.id}")
	private String CLIENT_ID = "your-github-client-id";
	@Value("${github.client.secret}")
	private String CLIENT_SECRET = "your-github-client-secret";
	@Value("${github.redirect.uri}")
	private String REDIRECT_URI = "your-redirect-uri";

	@GetMapping("/oauth2/callback/github")
	public String callback(@RequestParam("code") String code) {
		logger.info("Received OAuth callback with code: {}", code);

		RestTemplate restTemplate = new RestTemplate();

		// Set the headers
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", "application/json");

		// Create the request body
		Map<String, String> requestBody = new HashMap<>();
		requestBody.put("client_id", CLIENT_ID);
		requestBody.put("client_secret", CLIENT_SECRET);
		requestBody.put("code", code);
		requestBody.put("redirect_uri", REDIRECT_URI);

		HttpEntity<Map<String, String>> entity = new HttpEntity<>(requestBody, headers);

		try {
			// Exchange the authorization code for an access token
			ResponseEntity<Map> response = restTemplate.exchange("https://github.com/login/oauth/access_token",
					HttpMethod.POST, entity, Map.class);

			logger.info("Access token response: {}", response.getBody());

			if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null
					&& response.getBody().get("access_token") != null) {
				String accessToken = (String) response.getBody().get("access_token");
				logger.info("Obtained access token: {}", accessToken);

				// Fetch user information
				String userInfoUrl = "https://api.github.com/user";
				HttpHeaders userHeaders = new HttpHeaders();
				userHeaders.set("Authorization", "Bearer " + accessToken);
				userHeaders.set("Accept", "application/vnd.github.v3+json"); // Optional: Set Accept header for GitHub
																				// API v3

				HttpEntity<String> userEntity = new HttpEntity<>(userHeaders);
				ResponseEntity<String> userInfoResponse = restTemplate.exchange(userInfoUrl, HttpMethod.GET, userEntity,
						String.class);

				logger.info("User info response: {}", userInfoResponse.getBody());

				if (userInfoResponse.getStatusCode().is2xxSuccessful()) {
					return userInfoResponse.getBody().toString();
//					return new ResponseEntity<>(
//							new OAuthResponse(true, "Authentication successful", userInfoResponse.getBody()),
//							HttpStatus.OK);
				} else {
					return "bad request";
//					return new ResponseEntity<>(new OAuthResponse(false, "Failed to fetch user info"),
//							HttpStatus.BAD_REQUEST);
				}
			} else {

				logger.error("Failed to obtain access token: {}", response.getBody());
				return "bad request";
//				return new ResponseEntity<>(new OAuthResponse(false, "Failed to obtain access token"),
//						HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {

			logger.error("An error occurred during the OAuth callback", e);
			return "An error occurred: " + e.getMessage();
//			return new ResponseEntity<>(new OAuthResponse(false, "An error occurred: " + e.getMessage()),
//					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public static class OAuthResponse {
		private boolean success;
		private String message;
		private Object data;

		public OAuthResponse(boolean success, String message) {
			this.success = success;
			this.message = message;
		}

		public OAuthResponse(boolean success, String message, Object data) {
			this.success = success;
			this.message = message;
			this.data = data;
		}

		// Getters and setters...
	}
}
