package com.duaz.microservices.admin.control;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.duaz.microservices.admin.model.ReturnMessage;
import com.duaz.microservices.admin.model.role.RoleFilter;
import com.duaz.microservices.admin.model.user.UpdateRoleRequest;
import com.duaz.microservices.admin.model.user.UserRole;
import com.duaz.microservices.admin.services.RoleService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/role/")
public class RoleController {
	@Autowired
	private RoleService roleService;
	
	@RequestMapping(
			value="listRole",
			method=RequestMethod.GET)
	public List<UserRole> getUserByLoginId(@ModelAttribute RoleFilter f) {		
		List<UserRole> listRoles = null;
		
		try {			
			listRoles = null;
            listRoles = this.roleService.list(f);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return listRoles;
	}
	
	@RequestMapping(
			value="listRoleEngagement",
			method=RequestMethod.GET)
	public List<UserRole> listRoleEngagement() {		
		List<UserRole> listRoles = null;
		
		try {			
			listRoles = null;
            listRoles = this.roleService.listRoleEngagement();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return listRoles;
	}
	
	@RequestMapping(
			value="listNonRoleEngagement",
			method=RequestMethod.GET)
	public List<UserRole> listNonRoleEngagement() {		
		List<UserRole> listRoles = null;
		
		try {			
			listRoles = null;
            listRoles = this.roleService.listNonRoleEngagement();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return listRoles;
	}
	
	@RequestMapping(
			value="getRole",
			method=RequestMethod.GET)
	public UserRole getRole(int rNo) {		
		UserRole role = null;
		
		try {			
			role = null;
			role = this.roleService.getRole(rNo);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return role;
	}
	
	@RequestMapping(value="insertRole",method=RequestMethod.POST)
	private ReturnMessage insertAuditableUnit(@RequestBody UserRole request) {
		ReturnMessage rMessege = new ReturnMessage();
		boolean result = false;
		
		try {
			result = roleService.insertRole(request);
			
			if(result) {
				rMessege.setIsSuccess(true);
			}else {
				rMessege.setIsSuccess(false);
			}
			
		} catch (Exception e) {
			rMessege.setIsSuccess(false);
			rMessege.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return rMessege;
	}
	
	@RequestMapping(value="editRole",method=RequestMethod.POST)
	private ReturnMessage editRole(@RequestBody UpdateRoleRequest updateRoleRequest) {
		ReturnMessage rMessege = new ReturnMessage();
		boolean result = false;
		
		try {
			result = roleService.updateRole(updateRoleRequest.getUsers(), updateRoleRequest.getFunctions(), updateRoleRequest.getRoleNo(), 0, updateRoleRequest.getRoleLevel());
			
			if(result) {
				rMessege.setIsSuccess(true);
			}else {
				rMessege.setIsSuccess(false);
			}
			
		} catch (Exception e) {
			rMessege.setIsSuccess(false);
			rMessege.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return rMessege;
	}
	
	@RequestMapping(value="deleteRole",method=RequestMethod.POST)
	private ReturnMessage deleteRole(@RequestBody UserRole request) {
		ReturnMessage rMessege = new ReturnMessage();
		boolean result = false;
		
		try {
			result = roleService.deleteRole(request.getrNo());
			
			if(result) {
				rMessege.setIsSuccess(true);
			}else {
				rMessege.setIsSuccess(false);
			}
			
		} catch (Exception e) {
			rMessege.setIsSuccess(false);
			rMessege.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return rMessege;
	}

	@RequestMapping(
			value="getListHigherEngagementRole",
			method=RequestMethod.GET)
	public List<UserRole> getListHigherEngagementRole(Integer roleLevel) {		
		List<UserRole> listRoles = new ArrayList<UserRole>();
		
		try {			
			listRoles = null;
            listRoles = this.roleService.getListHigherEngagementRole(roleLevel);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return listRoles;
	}	
	
	/*@RequestMapping(
			value="getUserEngagementRole",
			method=RequestMethod.GET)
	public UserRole getUserEngagementRole(Integer userNo, Integer aeNo) {		
		UserRole role = null;
		
		try {			
			role = this.roleService.getUserEngagementRole(userNo, aeNo);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return role;
	}*/	
}
