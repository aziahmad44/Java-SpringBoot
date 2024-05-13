package com.duaz.microservices.admin.control;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin(origins = "*")
public class CheckHealthController {
	
	@RequestMapping(
			value="/health",
			method=RequestMethod.GET)
	public String checkHealth() {	
		return "OK";
	}
}
