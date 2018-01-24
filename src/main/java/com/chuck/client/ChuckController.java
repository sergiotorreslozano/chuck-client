package com.chuck.client;

import java.io.Serializable;
import java.lang.invoke.MethodHandles;
import java.net.URI;

import com.sun.javafx.fxml.builder.URLBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.web.client.RestTemplate;

@RestController
public class ChuckController {

	private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	private ChuckFactClient chuckFactClient;

	private RestTemplate restTemplate;

	ChuckController(ChuckFactClient chuckFactClient, RestTemplate restTemplate) {
		this.chuckFactClient = chuckFactClient;
	}

	@RequestMapping("/")
	public @ResponseBody String home() {
		log.debug("Got a request!");
		return "Hello World";
	}

	@HystrixCommand(fallbackMethod = "localFact")
	@RequestMapping("/chuckJoke")
	public @ResponseBody ChuckFact findRandomJoke() {
		log.debug("Got a request!");
		return restTemplate.getForEntity("getstartedlab_chuck-service", ChuckFact.class).getBody();
	}

	@HystrixCommand(fallbackMethod = "localFact")
	@RequestMapping("/chuck")
	public @ResponseBody ChuckFact greeting() {
		log.debug("Got a request!");
		return chuckFactClient.randomFact();
	}

	ChuckFact localFact(){
		log.warn("Returning local fact...");
		return new ChuckFact(-1, "Chuck is taking a rest...");
	}

}

class ChuckFact implements Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private String fact;

	public ChuckFact() {
		super();
	}

	public ChuckFact(int id, String string) {
		this.id = id;
		this.fact = string;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the fact
	 */
	public String getFact() {
		return fact;
	}

}

@FeignClient("getstartedlab_chuck-service")
interface ChuckFactClient {

	@RequestMapping(method = RequestMethod.GET, value = "/chuck")
	ChuckFact randomFact();
}
