package com.cretasom.hotel_security.config;

import java.io.IOException;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException {
		OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
		Map<String, Object> userAttributes = oauth2User.getAttributes();

		response.setContentType("application/json");
		response.getWriter().write("{\"message\": \"Login successful!\", \"user\": "
				+ new ObjectMapper().writeValueAsString(userAttributes) + "}");
	}
}
