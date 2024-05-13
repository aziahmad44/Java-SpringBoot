package com.duaz.microservices.admin.response;

public class BaseResponse {
	  private Integer status;
	  
	  private String message;
	  
	  public Integer getStatus() {
	    return this.status;
	  }
	  
	  public void setStatus(Integer status) {
	    this.status = status;
	  }
	  
	  public String getMessage() {
	    return this.message;
	  }
	  
	  public void setMessage(String message) {
	    this.message = message;
	  }
	}
