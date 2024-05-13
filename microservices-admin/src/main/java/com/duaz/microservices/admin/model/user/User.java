package com.duaz.microservices.admin.model.user;

import java.util.ArrayList;
import java.util.List;

import com.duaz.microservices.admin.model.department.Department;

public class User {
	private Integer userNo; 
    private String username;
    private String fullName;
    private String password;        
    private String salt;
    private String email;   
    
    private Integer clientNo;

    private String deptId;
    private String deptName;
    private String costCenter;
    
    private Integer roleNo;
    private String roleName;   
    private ArrayList<String> roles;

    private String userLevel;
    private Integer excludePrivilege;
    private ArrayList<Integer> functions;    
    private List<Department> additionalPrivileges;
    
    private Integer reportsTo;
    private String reportsToName;
    private Integer positionNo;
    private String positionName;
    
    private String status;
	private String createdDt;
	private Integer createdUserNo;
	private String updatedDt;
	private Integer updatedUserNo;
	
	/**
	 * @return the userNo
	 */
	public Integer getUserNo() {
		return userNo;
	}
	/**
	 * @param userNo the userNo to set
	 */
	public void setUserNo(Integer userNo) {
		this.userNo = userNo;
	}
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * @return the fullName
	 */
	public String getFullName() {
		return fullName;
	}
	/**
	 * @param fullName the fullName to set
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the salt
	 */
	public String getSalt() {
		return salt;
	}
	/**
	 * @param salt the salt to set
	 */
	public void setSalt(String salt) {
		this.salt = salt;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the deptId
	 */
	public String getDeptId() {
		return deptId;
	}
	/**
	 * @param deptId the deptId to set
	 */
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	/**
	 * @return the deptName
	 */
	public String getDeptName() {
		return deptName;
	}
	/**
	 * @param deptName the deptName to set
	 */
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	/**
	 * @return the costCenter
	 */
	public String getCostCenter() {
		return costCenter;
	}
	/**
	 * @param costCenter the costCenter to set
	 */
	public void setCostCenter(String costCenter) {
		this.costCenter = costCenter;
	}
	/**
	 * @return the roleNo
	 */
	public Integer getRoleNo() {
		return roleNo;
	}
	/**
	 * @param roleNo the roleNo to set
	 */
	public void setRoleNo(Integer roleNo) {
		this.roleNo = roleNo;
	}
	/**
	 * @return the roleName
	 */
	public String getRoleName() {
		return roleName;
	}
	/**
	 * @param roleName the roleName to set
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	/**
	 * @return the roles
	 */
	public ArrayList<String> getRoles() {
		return roles;
	}
	/**
	 * @param roles the roles to set
	 */
	public void setRoles(ArrayList<String> roles) {
		this.roles = roles;
	}
	/**
	 * @return the userLevel
	 */
	public String getUserLevel() {
		return userLevel;
	}
	/**
	 * @param userLevel the userLevel to set
	 */
	public void setUserLevel(String userLevel) {
		this.userLevel = userLevel;
	}
	/**
	 * @return the excludePrivilege
	 */
	public Integer getExcludePrivilege() {
		return excludePrivilege;
	}
	/**
	 * @param excludePrivilege the excludePrivilege to set
	 */
	public void setExcludePrivilege(Integer excludePrivilege) {
		this.excludePrivilege = excludePrivilege;
	}
	/**
	 * @return the functions
	 */
	public ArrayList<Integer> getFunctions() {
		return functions;
	}
	/**
	 * @param functions the functions to set
	 */
	public void setFunctions(ArrayList<Integer> functions) {
		this.functions = functions;
	}
	/**
	 * @return the additionalPrivileges
	 */
	public List<Department> getAdditionalPrivileges() {
		return additionalPrivileges;
	}
	/**
	 * @param additionalPrivileges the additionalPrivileges to set
	 */
	public void setAdditionalPrivileges(List<Department> additionalPrivileges) {
		this.additionalPrivileges = additionalPrivileges;
	}
	/**
	 * @return the reportsTo
	 */
	public Integer getReportsTo() {
		return reportsTo;
	}
	/**
	 * @param reportsTo the reportsTo to set
	 */
	public void setReportsTo(Integer reportsTo) {
		this.reportsTo = reportsTo;
	}
	/**
	 * @return the reportsToName
	 */
	public String getReportsToName() {
		return reportsToName;
	}
	/**
	 * @param reportsToName the reportsToName to set
	 */
	public void setReportsToName(String reportsToName) {
		this.reportsToName = reportsToName;
	}
	/**
	 * @return the positionNo
	 */
	public Integer getPositionNo() {
		return positionNo;
	}
	/**
	 * @param positionNo the positionNo to set
	 */
	public void setPositionNo(Integer positionNo) {
		this.positionNo = positionNo;
	}
	/**
	 * @return the positionName
	 */
	public String getPositionName() {
		return positionName;
	}
	/**
	 * @param positionName the positionName to set
	 */
	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the createdDt
	 */
	public String getCreatedDt() {
		return createdDt;
	}
	/**
	 * @param createdDt the createdDt to set
	 */
	public void setCreatedDt(String createdDt) {
		this.createdDt = createdDt;
	}
	/**
	 * @return the createdUserNo
	 */
	public Integer getCreatedUserNo() {
		return createdUserNo;
	}
	/**
	 * @param createdUserNo the createdUserNo to set
	 */
	public void setCreatedUserNo(Integer createdUserNo) {
		this.createdUserNo = createdUserNo;
	}
	/**
	 * @return the updatedDt
	 */
	public String getUpdatedDt() {
		return updatedDt;
	}
	/**
	 * @param updatedDt the updatedDt to set
	 */
	public void setUpdatedDt(String updatedDt) {
		this.updatedDt = updatedDt;
	}
	/**
	 * @return the updatedUserNo
	 */
	public Integer getUpdatedUserNo() {
		return updatedUserNo;
	}
	/**
	 * @param updatedUserNo the updatedUserNo to set
	 */
	public void setUpdatedUserNo(Integer updatedUserNo) {
		this.updatedUserNo = updatedUserNo;
	}
	/**
	 * @return the clientNo
	 */
	public Integer getClientNo() {
		return clientNo;
	}
	/**
	 * @param clientNo the clientNo to set
	 */
	public void setClientNo(Integer clientNo) {
		this.clientNo = clientNo;
	}
}
