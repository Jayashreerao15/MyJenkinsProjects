package com.itc.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class MyController {

	@GetMapping("/hi")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public String sayHi()
	{
		return "hiiiiiiiii";
	}
	
	@GetMapping("/admin")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public String hiAdmin()
	{
		return "this is from admin";
	}
	
	@GetMapping("/user")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public String hiUser()
	{
		return "this is from user";
	}
	
	
}
