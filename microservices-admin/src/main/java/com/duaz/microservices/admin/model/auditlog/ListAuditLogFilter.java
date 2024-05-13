package com.duaz.microservices.admin.model.auditlog;

public class ListAuditLogFilter {
	private String username;
	private String message;
	private String dateFrom;
	private String dateTo;
	private Integer metadata;
	
	// pagination	
	private Integer start;
	private Integer noRows; // if noRows = -1 means no pagination
	private String orderBy;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getDateFrom() {
		return dateFrom;
	}
	public void setDateFrom(String dateFrom) {
		this.dateFrom = dateFrom;
	}
	public String getDateTo() {
		return dateTo;
	}
	public void setDateTo(String dateTo) {
		this.dateTo = dateTo;
	}
	public Integer getStart() {
		return start;
	}
	public void setStart(Integer start) {
		this.start = start;
	}
	public Integer getNoRows() {
		return noRows;
	}
	public void setNoRows(Integer noRows) {
		this.noRows = noRows;
	}
	public String getOrderBy() {
		return orderBy;
	}
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	public Integer getMetadata() {
		return metadata;
	}
	public void setMetadata(Integer metadata) {
		this.metadata = metadata;
	}
	
	
}
