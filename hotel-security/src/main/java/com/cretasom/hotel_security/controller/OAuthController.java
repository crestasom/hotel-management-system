package com.cretasom.hotel_security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cretasom.hotel_security.dto.AuthResp;
import com.cretasom.hotel_security.service.AuthService;

@RestController
@RequestMapping("/oauth2")
public class OAuthController {
	@Value("${github.redirect.uri}")
	private String REDIRECT_URI;
	@Value("${github.client.id}")
	private String CLIENT_ID;
	@Value("${github.auth.uri}")
	private String githubAuthUri;
	@Autowired
	private AuthService authService;

	@GetMapping("/callback/github")
	public AuthResp callback(@RequestParam(name = "code", required = false) String code) {
		return authService.processCallbackGithub(code);
	}

//	@GetMapping("/authorize/github")
//	public void authorize(HttpServletResponse response) throws IOException {
//		String authorizationUrl = githubAuthUri.replace("##CLIENT_ID##", CLIENT_ID);
//		response.sendRedirect(authorizationUrl);
//	}

}
