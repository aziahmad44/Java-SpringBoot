package com.duaz.microservices.admin.model.department;

public class DepartmentWeightage {
  private Department department;
  
  private Department parentDepartment;
  
  private String parentDepartmentId;
  
  private Integer weightage;
  
  public DepartmentWeightage(Department department, String parentDepartmentId, Integer weightage) {
    this.department = department;
    this.parentDepartmentId = parentDepartmentId;
    this.weightage = weightage;
  }
  
  public Department getDepartment() {
    return this.department;
  }
  
  public void setDepartment(Department department) {
    this.department = department;
  }
  
  public Integer getWeightage() {
    return this.weightage;
  }
  
  public void setWeightage(Integer weightage) {
    this.weightage = weightage;
  }
  
  public Department getParentDepartment() {
    return this.parentDepartment;
  }
  
  public void setParentDepartment(Department parentDepartment) {
    this.parentDepartment = parentDepartment;
  }
  
  public String getParentDepartmentId() {
    return this.parentDepartmentId;
  }
  
  public void setParentDepartmentId(String parentDepartmentId) {
    this.parentDepartmentId = parentDepartmentId;
  }
}
