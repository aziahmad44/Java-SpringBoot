package com.duaz.microservices.admin.model.role;


public class RoleFilter {

    private String roleName;
    private String users;
    // pagination	
 	private Integer start;
 	private Integer noRows; // if noRows = -1 means no pagination
 	private String orderBy;
 	
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getUsers() {
		return users;
	}
	public void setUsers(String users) {
		this.users = users;
	}
	public Integer getStart() {
		return start;
	}
	public void setStart(Integer start) {
		this.start = start;
	}
	public Integer getNoRows() {
		return noRows;
	}
	public void setNoRows(Integer noRows) {
		this.noRows = noRows;
	}
	public String getOrderBy() {
		return orderBy;
	}
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
 	


}
