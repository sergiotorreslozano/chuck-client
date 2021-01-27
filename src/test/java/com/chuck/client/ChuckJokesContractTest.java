package com.chuck.client;


import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties.StubsMode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureStubRunner(ids = {
		"com.springboot.docker:chuck-service:0.1.0:stubs" },
		stubsMode = StubsMode.CLASSPATH
   )
public class ChuckJokesContractTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void shouldReturnAJoke() {

		ResponseEntity<ChuckFact> response = restTemplate.getForEntity
					("http://localhost:" +port +"/chuck", ChuckFact.class);
		assertTrue(response.getStatusCode().equals(HttpStatus.OK));
		assertNotNull(response.getBody().getId());
		assertNotNull(response.getBody().getFact());

	}

}
