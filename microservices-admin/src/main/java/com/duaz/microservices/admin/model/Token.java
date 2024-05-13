package com.duaz.microservices.admin.model;

public class Token extends RintBean {
  private int tokenNo;
  
  private String id;
  
  private String email;
  
  private String token;
  
  private String expiryDt;
  
  private String usedDt;
  
  public int getTokenNo() {
    return this.tokenNo;
  }
  
  public void setTokenNo(int tokenNo) {
    this.tokenNo = tokenNo;
  }
  
  public String getId() {
    return this.id;
  }
  
  public void setId(String id) {
    this.id = id;
  }
  
  public String getEmail() {
    return this.email;
  }
  
  public void setEmail(String email) {
    this.email = email;
  }
  
  public String getToken() {
    return this.token;
  }
  
  public void setToken(String token) {
    this.token = token;
  }
  
  public String getExpiryDt() {
    return this.expiryDt;
  }
  
  public void setExpiryDt(String expiryDt) {
    this.expiryDt = expiryDt;
  }
  
  public String getUsedDt() {
    return this.usedDt;
  }
  
  public void setUsedDt(String usedDt) {
    this.usedDt = usedDt;
  }
}
