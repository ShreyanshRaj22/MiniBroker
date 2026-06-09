package com.msgqueue.mini_broker.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.msgqueue.mini_broker.model.ApiKeyPrinciple;
import com.msgqueue.mini_broker.model.Role;
import com.msgqueue.mini_broker.exception.UnauthorisedException;

public class AuthenticationServiceTest {
	private AuthenticationService authenticationService;

	@BeforeEach
	void setup(){
		authenticationService = new AuthenticationService();
	}

	@Test
	void shouldAuthenticateValidKeys(){
		ApiKeyPrinciple adminPrinciple = new ApiKeyPrinciple("admin-key", "admin-client", Role.ADMIN);
		ApiKeyPrinciple producerPrinciple = new ApiKeyPrinciple("producer-key", "producer-client", Role.PRODUCER);
		ApiKeyPrinciple consumerPrinciple = new ApiKeyPrinciple("consumer-key", "consumer-client", Role.CONSUMER);

		assertTrue(adminPrinciple.equals(authenticationService.authenticate("admin-key")));
		assertTrue(producerPrinciple.equals(authenticationService.authenticate("producer-key")));
		assertTrue(consumerPrinciple.equals(authenticationService.authenticate("consumer-key")));
	}

	@Test
	void shouldThrowErrorForInvalidKey(){
		assertThrows(
				UnauthorisedException.class,
				() -> authenticationService.authenticate("random-key")
			    );
	}
}
