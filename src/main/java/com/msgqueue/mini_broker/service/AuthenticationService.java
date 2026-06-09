package com.msgqueue.mini_broker.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.msgqueue.mini_broker.exception.UnauthorisedException;
import com.msgqueue.mini_broker.model.ApiKeyPrinciple;
import com.msgqueue.mini_broker.model.Role;

@Service
public class AuthenticationService {

	private final Map<String, ApiKeyPrinciple> apiKeys;

	public AuthenticationService(){
		apiKeys = new HashMap<>();

		apiKeys.put("admin-key",
				new ApiKeyPrinciple(
					"admin-key", 
					"admin-client", 
					Role.ADMIN)
				);
		
		apiKeys.put("producer-key",
				new ApiKeyPrinciple(
					"producer-key", 
					"producer-client", 
					Role.PRODUCER)
				);
		apiKeys.put("consumer-key",
				new ApiKeyPrinciple(
					"consumer-key", 
					"consumer-client", 
					Role.CONSUMER)
				);
	}

	public ApiKeyPrinciple authenticate(String apiKey){
		ApiKeyPrinciple principle = apiKeys.get(apiKey);

		if(principle == null){
			throw new UnauthorisedException("API key is not present in the DB");
		}

		return principle;
	}
}
