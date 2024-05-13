package com.duaz.microservices.admin.model;

public class BaseAPI {
  private String clientId;
  
  private String apiKey;
  
  private String securityToken;
  
  private Integer source;
  
  private Integer userNoMobile;
  
  public String getClientId() {
    return this.clientId;
  }
  
  public void setClientId(String clientId) {
    this.clientId = clientId;
  }
  
  public String getApiKey() {
    return this.apiKey;
  }
  
  public void setApiKey(String apiKey) {
    this.apiKey = apiKey;
  }
  
  public Integer getSource() {
    return this.source;
  }
  
  public void setSource(Integer source) {
    this.source = source;
  }
  
  public Integer getUserNoMobile() {
    return this.userNoMobile;
  }
  
  public void setUserNoMobile(Integer userNoMobile) {
    this.userNoMobile = userNoMobile;
  }
  
  public String getSecurityToken() {
    return this.securityToken;
  }
  
  public void setSecurityToken(String securityToken) {
    this.securityToken = securityToken;
  }
}
