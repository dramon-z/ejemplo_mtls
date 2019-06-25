package com.cdc.pdmx.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/server")
public class TestController {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	ResponseEntity<?> getMessage() {
		return ResponseEntity.ok("Server successfully called!");
	}

}
