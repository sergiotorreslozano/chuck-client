package com.chuck.client;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureStubRunner(ids = {
		"com.springboot.docker:chuck-service:0.1.0:stubs:8080" },
		stubsMode = StubRunnerProperties.StubsMode.LOCAL)
public class ChuckJokesContractTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void shouldReturnAJoke() {

		ResponseEntity<ChuckFact> response = restTemplate.getForEntity
					("/chuck", ChuckFact.class);
		assertTrue(response.getStatusCode().equals(HttpStatus.OK));
		assertNotNull(response.getBody().getId());
		assertNotNull(response.getBody().getFact());

	}

}
