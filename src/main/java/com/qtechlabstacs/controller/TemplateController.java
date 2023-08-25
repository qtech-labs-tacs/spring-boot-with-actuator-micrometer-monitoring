package com.qtechlabstacs.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/template/v1.0")
public class TemplateController {

	
	@GetMapping("/dummy")
	public ResponseEntity<String> getDummyResponse(){
		return new ResponseEntity<String>("Template Microservice", HttpStatus.OK);
	}
	
}

