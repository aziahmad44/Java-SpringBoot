package com.duaz.microservices.admin.model.user;

import java.util.List;

public class UserRole {
	private Integer rNo;
    private String rName;
    private String status;
    private Integer counterUser;
    private List<User>listUser;
    private Integer isEngagement;
    private Integer roleLevel;
    
	public Integer getrNo() {
		return rNo;
	}
	public void setrNo(Integer rNo) {
		this.rNo = rNo;
	}
	public String getrName() {
		return rName;
	}
	public void setrName(String rName) {
		this.rName = rName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getCounterUser() {
		return counterUser;
	}
	public void setCounterUser(Integer counterUser) {
		this.counterUser = counterUser;
	}
	public List<User> getListUser() {
		return listUser;
	}
	public void setListUser(List<User> listUser) {
		this.listUser = listUser;
	}
	public Integer getIsEngagement() {
		return isEngagement;
	}
	public void setIsEngagement(Integer isEngagement) {
		this.isEngagement = isEngagement;
	}
	/**
	 * @return the roleLevel
	 */
	public Integer getRoleLevel() {
		return roleLevel;
	}
	/**
	 * @param roleLevel the roleLevel to set
	 */
	public void setRoleLevel(Integer roleLevel) {
		this.roleLevel = roleLevel;
	}
	
}
