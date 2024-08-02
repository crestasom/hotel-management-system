package com.cretasom.hotel_security.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cretasom.hotel_security.dto.AuthRequest;
import com.cretasom.hotel_security.dto.AuthResp;
import com.cretasom.hotel_security.service.AuthService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/auth")
public class AuthController {
	@Autowired
	private AuthService service;
	@Value("${github.redirect.uri}")
	private String REDIRECT_URI;
	@Value("${github.client.id}")
	private String CLIENT_ID;

	@PostMapping("/token")
	public ResponseEntity<AuthResp> getToken(@RequestBody AuthRequest authRequest) {
		AuthResp resp = service.validateAuthRequestt(authRequest);
		return ResponseEntity.status(resp.getRespCode()).body(resp);
	}

	@GetMapping("/validate/{token}")
	public ResponseEntity<Integer> validateToken(@PathVariable String token) {
		int respCode = service.validateToken(token);
		return ResponseEntity.status(200).body(respCode);
	}

	@GetMapping("/oauth2/authorize/github")
	public void authorize(HttpServletResponse response) throws IOException {
		String authorizationUrl = "https://github.com/login/oauth/authorize" + "?client_id=" + CLIENT_ID
		// + "&redirect_uri=" + REDIRECT_URI
				+ "&scope=read:user";
		response.sendRedirect(authorizationUrl);
	}
}
