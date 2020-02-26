package com.skynet.example.config;

import java.io.IOException;
import java.util.Arrays;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skynet.example.exception.ApiExceptionHandler;

public class JWTAuthenticationFilter extends OncePerRequestFilter {

	public static final String LOGIN_REQUEST_URI = "login";

	private ApiExceptionHandler apiExceptionHandler;

	public JWTAuthenticationFilter(ApplicationContext context) {
		this.apiExceptionHandler = context.getBean(ApiExceptionHandler.class);
	}

	@Override
	public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			if (!Arrays.asList(SecurityConfig.antMatcherPermitAll).contains(request.getRequestURI())) {
				Authentication authentication = TokenAuthenticationHandler.getAuthentication(request);
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
			filterChain.doFilter(request, response);
		} catch (Exception ex) {
			ResponseEntity<Object> responseEntity = apiExceptionHandler.handleFilterExceptions(ex, request);
			response.setStatus(responseEntity.getStatusCodeValue());
			String content = convertObjectToJson(responseEntity);
			response.getWriter().write(content);
			response.flushBuffer();
		}
	}

	private String convertObjectToJson(Object object) throws JsonProcessingException {
		if (object == null) {
			return null;
		}
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(object);
	}

}