package com.duaz.microservices.admin.control;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.duaz.microservices.admin.constant.RIConstants;
import com.duaz.microservices.admin.model.ReturnMessage;
import com.duaz.microservices.admin.model.user.User;
import com.duaz.microservices.admin.model.user.UserRest;
import com.duaz.microservices.admin.response.CommonAPIResponse;
import com.duaz.microservices.admin.response.RequestTokenAPIResponse;
import com.duaz.microservices.admin.services.TokenSecurityServices;
import com.duaz.microservices.admin.utility.FormatUtility;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/token")
public class TokenController {
	
	static Logger LOG = Logger.getLogger(TokenController.class); 

	@Autowired
	private TokenSecurityServices tokenServices;
	
	@RequestMapping(
			value="/addTokenSecurity",
			method=RequestMethod.POST)
	public ReturnMessage addTokenSecurity(@RequestBody UserRest userRest) {
		ReturnMessage rMessage = new ReturnMessage();
		
		try {
			LOG.info("Start Process Rest API Insert Data User");
			
			rMessage = this.tokenServices.addSecurityToken(userRest);
			
		} catch (Exception e) {
			rMessage.setMessage(e.getMessage());
			e.printStackTrace();
			LOG.error("Error Insert Data User : " + e.getMessage());
		}
		
		LOG.info("End Process Rest API Insert Data User");
		
		return rMessage;
	}
	
	@RequestMapping(value="/updateUser",method=RequestMethod.POST)
	private ReturnMessage updateUser(@RequestBody UserRest userRest) {
		ReturnMessage rMessage = new ReturnMessage();

		try {
			LOG.info("Start Process Rest API Update Data User");
			
			rMessage = this.tokenServices.invalidateSecurityToken(userRest);
						
		} catch (Exception e) {
			rMessage.setIsSuccess(false);
			rMessage.setMessage(e.getMessage());
			e.printStackTrace();
			LOG.error("Error Update Data User : " + e.getMessage());
		}
		LOG.info("End Process Rest API Updated Data User");

		
		return rMessage;
	}
	
}
