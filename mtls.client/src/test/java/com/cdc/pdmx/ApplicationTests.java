package com.cdc.pdmx;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

	@Autowired
	private RestTemplate restTemplate;

	private String serverUrl = "https://localhost:8111/server/";

	@Test
	public void contextLoads() {
		System.out.println("Holis");
		ResponseEntity<String> getResponse = restTemplate.getForEntity(serverUrl, String.class);
		
		
		assertEquals(HttpStatus.OK, getResponse.getStatusCode());
		
		System.out.println(getResponse.getBody());
	}

}
