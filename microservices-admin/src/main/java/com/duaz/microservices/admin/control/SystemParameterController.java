package com.duaz.microservices.admin.control;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.duaz.microservices.admin.model.ReturnMessage;
import com.duaz.microservices.admin.services.SystemParameterServices;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/systemParam/")
public class SystemParameterController {
	
	@Autowired
	private SystemParameterServices systemParameterServices;
	
	@RequestMapping(
			value="getValueOfSystemParameterByCode",
			method=RequestMethod.GET)
	public @ResponseBody ReturnMessage getValueOfSystemParameterByCode(@RequestParam String code) {		
		ReturnMessage rm = null;
		String value = "";
		
		try {			
			rm = new ReturnMessage();
			
			value = this.systemParameterServices.getValueOfSystemParameterByCode(code);			

			rm.setIsSuccess(true);
			rm.setData(value);
		} catch (Exception e) {
			e.printStackTrace();
			
			rm.setIsSuccess(false);
		}
		
		return rm;
	}
	
	@RequestMapping(
			value="getListSystemParameterInMap",
			method=RequestMethod.GET)
	public @ResponseBody HashMap<String, String> getListSystemParameterInMap() {		
		HashMap<String, String> map = null;
		
		try {			
			map = this.systemParameterServices.getListSystemParameterInMap();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return map;
	}
	
	@RequestMapping(
			value="getCodeOfSystemParameterByValueAndPrefixCode",
			method=RequestMethod.GET)
	public @ResponseBody ReturnMessage getCodeOfSystemParameterByValueAndPrefixCode(@RequestParam String value, @RequestParam String prefixCode) {		
		ReturnMessage rm = null;
		String code = "";
		
		try {			
			rm = new ReturnMessage();
			
			code = this.systemParameterServices.getCodeOfSystemParameterByValueAndPrefixCode(value, prefixCode);			

			rm.setIsSuccess(true);
			rm.setData(code);
		} catch (Exception e) {
			e.printStackTrace();
			
			rm.setIsSuccess(false);
		}
		
		return rm;
	}
}
