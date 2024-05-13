package com.duaz.microservices.admin.model.auditlog;

import java.math.BigInteger;

public class AuditLog {
	private BigInteger auditNo;
	private String username;
	private String rintModule;
	private String message;
	private String ipAddress;
	private String createdDate;
	private int createdNo;

	public BigInteger getAuditNo() {
		return auditNo;
	}
	public void setAuditNo(BigInteger auditNo) {
		this.auditNo = auditNo;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getRintModule() {
		return rintModule;
	}
	public void setRintModule(String rintModule) {
		this.rintModule = rintModule;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public int getCreatedNo() {
		return createdNo;
	}
	public void setCreatedNo(int createdNo) {
		this.createdNo = createdNo;
	}
}
