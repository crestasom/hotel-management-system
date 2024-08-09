package com.cretasom.hotel_security.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class AuthConfig {
//	@Value("${jwt.set.uri}")
//	private String JWK_SET_URI;
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
	@Autowired
	private CustomAuthenticationSuccessHandler successHandler;
	@Autowired
	private CustomAuthenticationFailureHandler failureHandler;

//	@Bean
//	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//		return http.csrf(csrf -> csrf.disable())
//				.authorizeHttpRequests(
//						requests -> requests.requestMatchers("/auth/token", "/auth/validate/**").permitAll())
//				.exceptionHandling(
//						exceptionHandling -> exceptionHandling.authenticationEntryPoint(customAuthenticationEntryPoint))
//				.build();
//	}

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(requests -> requests.requestMatchers("/auth/token", "/auth/validate/**",
						"/oauth2/authorize/github", "/oauth2/callback/github").permitAll())
				.oauth2ResourceServer(oauth2 -> oauth2.jwt()).build();
	}

//	@Bean
//	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//		return http.csrf(csrf -> csrf.disable())
//				.authorizeHttpRequests(requests -> requests.requestMatchers("/auth/token", "/auth/validate/**",
//						"/oauth2/authorize/github", "/oauth2/callback/github", "/actuator/**").permitAll())
//				.oauth2Login(oauth2Login -> oauth2Login.successHandler(successHandler).failureHandler(failureHandler))
//				.build();
//	}

//	@Bean
//	JwtAuthenticationConverter jwtAuthenticationConverter() {
//		JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
//		return converter;
//	}

//	@Bean
//	JwtDecoder jwtDecoder() {
//		return NimbusJwtDecoder.withJwkSetUri(JWK_SET_URI).build();
//
//	}

	@SuppressWarnings("deprecation")
	@Bean
	static NoOpPasswordEncoder passwordEncoder() {
		return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("user1").password("user1pass").roles("ADMIN");
	}

	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
}
