package com.example.demo;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
@RequestMapping("/test")
public class TestController {

	@RequestMapping("/publicApi")
	public String publicApi() {
		return "무인증API";
	}
	
	@RequestMapping("/securedApi")
	@PreAuthorize("isAuthenticated()")
	public String securedApi() {
		return "인증API";
	}
	
}
