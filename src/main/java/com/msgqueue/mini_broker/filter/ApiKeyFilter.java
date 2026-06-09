package com.msgqueue.mini_broker.filter;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.msgqueue.mini_broker.model.ApiKeyPrinciple;
import com.msgqueue.mini_broker.service.AuthenticationService;
import com.msgqueue.mini_broker.Constants;
import com.msgqueue.mini_broker.exception.UnauthorisedException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class ApiKeyFilter extends OncePerRequestFilter {
	private final AuthenticationService authenticationService;

	public ApiKeyFilter(AuthenticationService authenticationService){
		this.authenticationService = authenticationService;
	}

	@Override
	protected void doFilterInternal(
			HttpServletRequest request,
			HttpServletResponse response,
			FilterChain filterChain
			) throws ServletException, IOException {

		try {
			String apiKey = request.getHeader(Constants.API_KEY_HEADER);

		if(apiKey == null || apiKey.isBlank()){
			response.sendError(
					HttpServletResponse.SC_UNAUTHORIZED,
					"Missing API Key header in X-API-KEY"
					);
			return;
		}

		ApiKeyPrinciple principle = authenticationService.authenticate(apiKey);
		
		request.setAttribute("principle", principle);

		filterChain.doFilter(request, response);

	
		} catch (UnauthorisedException e) {
			// TODO: handle exception
			response.sendError(
				HttpServletResponse.SC_UNAUTHORIZED,
				e.getMessage());
		}
		}
}
