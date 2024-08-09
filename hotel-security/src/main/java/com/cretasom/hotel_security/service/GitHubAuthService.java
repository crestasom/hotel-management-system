package com.cretasom.hotel_security.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GitHubAuthService {

	@Value("${github.client.id}")
	private String CLIENT_ID = "your-github-client-id";
	@Value("${github.client.secret}")
	private String CLIENT_SECRET = "your-github-client-secret";
	@Value("${github.redirect.uri}")
	private String REDIRECT_URI = "your-redirect-uri";
	@Value("${github.access.token.uri}")
	private String gitHubAccessTokenUri;
	@Value("${github.user.info.uri}")
	private String userInfoUrl;

	@Autowired
	private RestTemplate restTemplate;

	private Logger logger = LoggerFactory.getLogger(getClass());

	public String getAccessTokenGitHub(String code) {
		String accessToken = null;
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", "application/json");

		// Create the request body
		Map<String, String> requestBody = new HashMap<>();
		requestBody.put("client_id", CLIENT_ID);
		requestBody.put("client_secret", CLIENT_SECRET);
		requestBody.put("code", code);
		requestBody.put("redirect_uri", REDIRECT_URI);
		logger.info("request for getting github access token [{}]", requestBody);
		HttpEntity<Map<String, String>> entity = new HttpEntity<>(requestBody, headers);
		ResponseEntity<Map> response = restTemplate.exchange(gitHubAccessTokenUri, HttpMethod.POST, entity, Map.class);
		logger.info("Access token response: [{}]", response);
		if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null
				&& response.getBody().get("access_token") != null) {
			accessToken = (String) response.getBody().get("access_token");
			logger.info("Obtained access token: {}", accessToken);
		}
		return accessToken;
	}

	public Map<String, Object> getGitHubUserInfo(String accessToken) {

		HttpHeaders userHeaders = new HttpHeaders();
		userHeaders.set("Authorization", "Bearer " + accessToken);
		userHeaders.set("Accept", "application/vnd.github.v3+json");
		HttpEntity<String> userEntity = new HttpEntity<>(userHeaders);
		ResponseEntity<Map> userInfoResponse = restTemplate.exchange(userInfoUrl, HttpMethod.GET, userEntity,
				Map.class);
		if (userInfoResponse.getStatusCode().is2xxSuccessful()) {
			return userInfoResponse.getBody();
		}
		return null;

	}

}
