package com.duaz.microservices.admin.control;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.duaz.microservices.admin.model.Function;
import com.duaz.microservices.admin.services.FunctionService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/function/")
public class FunctionController {
	
	@Autowired
    private FunctionService functionService;
	
	@RequestMapping(
			value="listFunction",
			method=RequestMethod.GET)
	public List<Function> getListFunction(int isEngagement) {		
		List<Function> listFunction = null;
		
		try {			
			listFunction = null;
			listFunction = this.functionService.getListOfFunction(isEngagement);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return listFunction;
	}
	
	@RequestMapping(
			value="getAssignedFunction",
			method=RequestMethod.GET)
	public List<Function> getAssignedFunction(int roleNo) {		
		List<Function> listFunction = null;
		
		try {			
			listFunction = null;
			listFunction = this.functionService.getAssignedFunction(roleNo);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return listFunction;
	}
	

}
