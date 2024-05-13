package com.duaz.microservices.admin.control;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.duaz.microservices.admin.constant.RIConstants;
import com.duaz.microservices.admin.model.ReturnMessage;
import com.duaz.microservices.admin.model.user.ListUserFilter;
import com.duaz.microservices.admin.model.user.ListUserResponse;
import com.duaz.microservices.admin.model.user.User;
import com.duaz.microservices.admin.model.user.UserRest;
import com.duaz.microservices.admin.model.user.UserRole;
import com.duaz.microservices.admin.response.CommonAPIResponse;
import com.duaz.microservices.admin.response.RequestTokenAPIResponse;
import com.duaz.microservices.admin.services.UserServices;
import com.duaz.microservices.admin.utility.FormatUtility;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/user")
public class UserController {
	
	static Logger LOG = Logger.getLogger(UserController.class); 

	@Autowired
	private UserServices userServices;
	
	@RequestMapping(
			value="/getListUser",
			method=RequestMethod.GET)
	public @ResponseBody List<User> getListUser() {
		List<User> list = null;
		
		try {			
			list = this.userServices.getListUser();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	@RequestMapping(
			value="/getUserById",
			method=RequestMethod.GET)
	public @ResponseBody User getUserById(@RequestParam Integer userNo) {
		User user = null;
		
		try {			
			user = this.userServices.getUserById(userNo);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return user;
	}
	
	@RequestMapping(
			value="/getUserByUsername",
			method=RequestMethod.GET)
	public @ResponseBody User getUserByUsername(@RequestParam String username) {
		User user = null;
		try {			
			user = this.userServices.getUserByUsername(username);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return user;
	}
	
	@RequestMapping(
			value="/addUser",
			method=RequestMethod.POST)
	public ReturnMessage addUser(@RequestBody User user) {
		ReturnMessage rMessage = new ReturnMessage();
		int usernameLength = user.getUsername().length();
		
		try {
			LOG.info("Start Process Rest API Insert Data User");
			if (usernameLength <= 4 && usernameLength <= 25) {
				rMessage.setIsSuccess(false);
				rMessage.setMessage("Username must be between 4 to 25 characters");
				rMessage.setData(user);
			} else if (this.userServices.getUserByUsername(user.getUsername()) != null) {
				rMessage.setIsSuccess(false);
				rMessage.setMessage("Username Already Existing on Data User");
				rMessage.setData(user);
			} else if (!isEmailValid(user.getEmail())) {
				rMessage.setIsSuccess(false);
				rMessage.setMessage("Not a valid Email");
				rMessage.setData(user);
			} else {
				rMessage = this.userServices.addUser(user);
			}
			
		} catch (Exception e) {
			rMessage.setMessage(e.getMessage());
			e.printStackTrace();
			LOG.error("Error Insert Data User : " + e.getMessage());
		}
		
		LOG.info("End Process Rest API Insert Data User");
		
		return rMessage;
	}
	
	@RequestMapping(value="/updateUser",method=RequestMethod.POST)
	private ReturnMessage updateUser(@RequestParam int id, @RequestBody User user) {
		ReturnMessage rMessage = new ReturnMessage();
		int usernameLength = user.getUsername().length();

		try {
			LOG.info("Start Process Rest API Update Data User");
			
			if (usernameLength <= 4 && usernameLength <= 25) {
				rMessage.setIsSuccess(false);
				rMessage.setMessage("Username must be between 4 to 25 characters");
				rMessage.setData(user);
			} else if (this.userServices.getUserByIdUsername(id, user.getUsername(), RIConstants.NOT_IN) != null) {
				rMessage.setIsSuccess(false);
				rMessage.setMessage("Username Already Existing on Data User");
				rMessage.setData(user);
			} else if (!isEmailValid(user.getEmail())) {
				rMessage.setIsSuccess(false);
				rMessage.setMessage("Not a valid Email");
				rMessage.setData(user);
			} else {
				rMessage = this.userServices.updateUser(id, user);
			}
						
		} catch (Exception e) {
			rMessage.setIsSuccess(false);
			rMessage.setMessage(e.getMessage());
			e.printStackTrace();
			LOG.error("Error Update Data User : " + e.getMessage());
		}
		LOG.info("End Process Rest API Update Data User");

		
		return rMessage;
	}
	

	@RequestMapping(value="/removeUser",method=RequestMethod.POST)
	private ReturnMessage removeUser(@RequestParam int id, @RequestBody User user) {
		ReturnMessage rMessege = new ReturnMessage();
		
		try {
			LOG.info("Start Process Rest API Remove Data User");

			rMessege = this.userServices.removeUser(id, user);
			
		} catch (Exception e) {
			rMessege.setIsSuccess(false);
			rMessege.setMessage(e.getMessage());
			e.printStackTrace();
			LOG.error("Error Remove Data User : " + e.getMessage());
		}
		LOG.info("End Process Rest API Remove Data User");

		
		return rMessege;
	}
	
	@RequestMapping(value="/updateLastLogin",method=RequestMethod.POST)
	private ReturnMessage updateLastLogin(@RequestBody Integer userNo) {
		ReturnMessage rMessege = new ReturnMessage();
		
		try {
			LOG.info("Start Process Rest API Update Data Last Login");

			rMessege = this.userServices.updateLastLogin(userNo);
			
		} catch (Exception e) {
			rMessege.setIsSuccess(false);
			rMessege.setMessage(e.getMessage());
			e.printStackTrace();
			LOG.error("Error Update Data Last Login : " + e.getMessage());
		}
		LOG.info("End Process Rest API Update Data Last Login");

		
		return rMessege;
	}
	
	@RequestMapping(
			value="/listOfUser",
			method=RequestMethod.GET)
	public @ResponseBody ListUserResponse listOfUser(@ModelAttribute ListUserFilter filter) {		
		ListUserResponse lur = null;
		
		try {			
			lur = this.userServices.listOfUser(filter);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return lur;
	}
	
	@RequestMapping(value="/getListOfRole",method=RequestMethod.GET)
	private List<UserRole> getListOfRole(String param) {
		
		List<UserRole> result = new ArrayList<UserRole>();
		
		try {
			result = this.userServices.getListOfRole(param);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static boolean isEmailValid(String email) {
	    final Pattern EMAIL_REGEX = Pattern.compile("[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", Pattern.CASE_INSENSITIVE);
	    return EMAIL_REGEX.matcher(email).matches();
	}

}
