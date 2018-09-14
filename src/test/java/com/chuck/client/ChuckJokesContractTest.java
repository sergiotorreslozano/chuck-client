package com.chuck.client;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureStubRunner(ids = {
		"com.springboot.docker:chuck-service:0.1.0:stubs:8080" }, stubsMode = StubRunnerProperties.StubsMode.LOCAL)
public class ChuckJokesContractTest {

	private RestTemplate restTemplate = new RestTemplate();

	@Test
	public void shouldReturnAJoke() {

		ResponseEntity<ChuckFact> response = restTemplate.getForEntity("http://localhost:8080/chuck", ChuckFact.class);
		assertTrue(response.getStatusCode().equals(HttpStatus.OK));
		assertNotNull(response.getBody().getId());
		assertNotNull(response.getBody().getFact());

	}

}
