package com.example.demo;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class TestControllerTest {
	private static final Logger logger = LoggerFactory.getLogger(TestControllerTest.class);
	
	@Test
	public void publicApi() {
		logger.debug("-----------------------------");
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> entity = restTemplate.getForEntity("http://localhost:8080/test/publicApi", String.class);
		logger.debug("publicApi: {}", entity);
	}
	
	@Test
	public void securedApiWithoutToken() {
		logger.debug("-----------------------------");
		try {
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<String> entity = restTemplate.getForEntity("http://localhost:8080/test/securedApi", String.class);
			logger.debug("securedApi: {}", entity);
		} catch (Exception e) {
			logger.debug("securedApiWithoutToken: {} - {}", e.getClass().getName(), e.getMessage());
		}
	}

	@Test
	public void securedApiWithInvalidToken() {
		logger.debug("-----------------------------");
		try {
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.add("X-AUTH-ID", "deepfree");
			headers.add("X-AUTH-PASS", "1111");
			HttpEntity<String> requestEntity = new HttpEntity<>(headers);
			ResponseEntity<String> entity = restTemplate.exchange("http://localhost:8080/test/securedApi", HttpMethod.GET, requestEntity, String.class);
			logger.debug("securedApiWithInvalidToken: {}", entity);
		} catch (Exception e) {
			logger.debug("securedApiWithInvalidToken: {} - {}", e.getClass().getName(), e.getMessage());
		}
	}
	
	@Test
	public void securedApiWithValidToken() {
		logger.debug("-----------------------------");
		try {
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.add("X-AUTH-ID", "deepfree");
			headers.add("X-AUTH-PASS", "1234");
			HttpEntity<String> requestEntity = new HttpEntity<>(headers);
			ResponseEntity<String> entity = restTemplate.exchange("http://localhost:8080/test/securedApi", HttpMethod.GET, requestEntity, String.class);
			logger.debug("securedApiWithValidToken: {}", entity);
		} catch (Exception e) {
			logger.debug("securedApiWithValidToken: {} - {}", e.getClass().getName(), e.getMessage());
		}
	}
	
}


