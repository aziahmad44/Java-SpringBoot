package com.duaz.microservices.admin.model.department;

public class DepartmentLevelDescr {
  private Integer levelId;
  
  private String levelDescr;
  
  public Integer getLevelId() {
    return this.levelId;
  }
  
  public void setLevelId(Integer levelId) {
    this.levelId = levelId;
  }
  
  public String getLevelDescr() {
    return this.levelDescr;
  }
  
  public void setLevelDescr(String levelDescr) {
    this.levelDescr = levelDescr;
  }
}
