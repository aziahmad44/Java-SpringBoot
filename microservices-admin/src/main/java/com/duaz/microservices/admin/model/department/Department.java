package com.duaz.microservices.admin.model.department;


import java.util.List;

import com.duaz.microservices.admin.model.RintBean;

public class Department extends RintBean{
	private String deptId;
	private String deptDescr;
	private String levelId;
	private String levelDescr;
	private String location;
	private String locationDescr;
	private String managerId;
	private String managerName;
	private String divisionId;
	private String divisionName;
	
	private Integer deptNo;
	private String deptName;
	private Integer level;	

	private String parentDeptId;
	
	private List<Department> listDept;

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getDeptDescr() {
		return deptDescr;
	}

	public void setDeptDescr(String deptDescr) {
		this.deptDescr = deptDescr;
	}

	public String getLevelId() {
		return levelId;
	}

	public void setLevelId(String levelId) {
		this.levelId = levelId;
	}

	public String getLevelDescr() {
		return levelDescr;
	}

	public void setLevelDescr(String levelDescr) {
		this.levelDescr = levelDescr;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getLocationDescr() {
		return locationDescr;
	}

	public void setLocationDescr(String locationDescr) {
		this.locationDescr = locationDescr;
	}

	public String getManagerId() {
		return managerId;
	}

	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public String getDivisionId() {
		return divisionId;
	}

	public void setDivisionId(String divisionId) {
		this.divisionId = divisionId;
	}

	public String getDivisionName() {
		return divisionName;
	}

	public void setDivisionName(String divisionName) {
		this.divisionName = divisionName;
	}

	public String getParentDeptId() {
		return parentDeptId;
	}

	public void setParentDeptId(String parentDeptId) {
		this.parentDeptId = parentDeptId;
	}

	public List<Department> getListDept() {
		return listDept;
	}

	public void setListDept(List<Department> listDept) {
		this.listDept = listDept;
	}

	public Integer getDeptNo() {
		return deptNo;
	}

	public void setDeptNo(Integer deptNo) {
		this.deptNo = deptNo;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}


	
}
