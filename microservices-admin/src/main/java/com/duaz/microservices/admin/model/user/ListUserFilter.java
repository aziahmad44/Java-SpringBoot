package com.duaz.microservices.admin.model.user;

public class ListUserFilter {
	private Integer rNo;
	private String fullName;
	private String deptId;
	private String deptIdPrivilege;
	private Integer metadata;
	
	// pagination	
	private Integer start;
	private Integer noRows; // if noRows = -1 means no pagination
	private String orderBy;
	
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
	 * @return the deptIdPrivilege
	 */
	public String getDeptIdPrivilege() {
		return deptIdPrivilege;
	}
	/**
	 * @param deptIdPrivilege the deptIdPrivilege to set
	 */
	public void setDeptIdPrivilege(String deptIdPrivilege) {
		this.deptIdPrivilege = deptIdPrivilege;
	}
	/**
	 * @return the rNo
	 */
	public Integer getrNo() {
		return rNo;
	}
	/**
	 * @param rNo the rNo to set
	 */
	public void setrNo(Integer rNo) {
		this.rNo = rNo;
	}
	/**
	 * @return the start
	 */
	public Integer getStart() {
		return start;
	}
	/**
	 * @param start the start to set
	 */
	public void setStart(Integer start) {
		this.start = start;
	}
	/**
	 * @return the noRows
	 */
	public Integer getNoRows() {
		return noRows;
	}
	/**
	 * @param noRows the noRows to set
	 */
	public void setNoRows(Integer noRows) {
		this.noRows = noRows;
	}
	/**
	 * @return the orderBy
	 */
	public String getOrderBy() {
		return orderBy;
	}
	/**
	 * @param orderBy the orderBy to set
	 */
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	/**
	 * @return the metadata
	 */
	public Integer getMetadata() {
		return metadata;
	}
	/**
	 * @param metadata the metadata to set
	 */
	public void setMetadata(Integer metadata) {
		this.metadata = metadata;
	}
	
	
}
