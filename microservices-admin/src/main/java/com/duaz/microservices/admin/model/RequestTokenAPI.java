package com.duaz.microservices.admin.model;

public class RequestTokenAPI extends BaseAPI {
	  private Integer loggedUserNo;
	  
	  public Integer getLoggedUserNo() {
	    return this.loggedUserNo;
	  }
	  
	  public void setLoggedUserNo(Integer loggedUserNo) {
	    this.loggedUserNo = loggedUserNo;
	  }
	}

