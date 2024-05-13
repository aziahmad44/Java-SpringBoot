package com.duaz.microservices.admin.model.user;

import java.util.List;

public class UpdateRoleRequest {

	private Integer roleNo;
	private Integer roleLevel;
    private List<Integer> users;
    private List<Integer> functions;
    
	public Integer getRoleNo() {
		return roleNo;
	}
	public void setRoleNo(Integer roleNo) {
		this.roleNo = roleNo;
	}
	public Integer getRoleLevel() {
		return roleLevel;
	}
	public void setRoleLevel(Integer roleLevel) {
		this.roleLevel = roleLevel;
	}
	public List<Integer> getUsers() {
		return users;
	}
	public void setUsers(List<Integer> users) {
		this.users = users;
	}
	public List<Integer> getFunctions() {
		return functions;
	}
	public void setFunctions(List<Integer> functions) {
		this.functions = functions;
	}


}
