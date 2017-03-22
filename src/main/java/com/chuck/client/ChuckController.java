package com.chuck.client;

import java.io.Serializable;
import java.lang.invoke.MethodHandles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
public class ChuckController {

	private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	private ChuckFactClient chuckFactClient;

	ChuckController(ChuckFactClient chuckFactClient) {
		this.chuckFactClient = chuckFactClient;
	}

	@RequestMapping("/")
	public @ResponseBody String home() {
		log.debug("Got a request!");
		return "Hello World";
	}

	@HystrixCommand(fallbackMethod = "localFact")
	@RequestMapping("/chuck")
	public @ResponseBody ChuckFact greeting() {
		log.debug("Got a request!");
		return chuckFactClient.randomFact();
	}

	ChuckFact localFact(){
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

@FeignClient("chuck-service")
interface ChuckFactClient {

	@RequestMapping(method = RequestMethod.GET, value = "/chuck")
	ChuckFact randomFact();
}