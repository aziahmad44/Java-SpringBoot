package com.duaz.microservices.admin.response;

public class RequestTokenAPIResponse extends BaseResponse {
  private String securityToken;
  
  public String getSecurityToken() {
    return this.securityToken;
  }
  
  public void setSecurityToken(String securityToken) {
    this.securityToken = securityToken;
  }
}
