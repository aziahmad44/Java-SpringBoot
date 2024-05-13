package com.duaz.microservices.admin.model.auditlog;

import java.util.List;

import com.duaz.microservices.admin.model.PaginationMetadata;

public class ListAuditLogResponse {
private List<AuditLog> listAuditLog;
	
	// metadata
	private PaginationMetadata metadata;

	public List<AuditLog> getListAuditLog() {
		return listAuditLog;
	}

	public void setListAuditLog(List<AuditLog> listAuditLog) {
		this.listAuditLog = listAuditLog;
	}

	public PaginationMetadata getMetadata() {
		return metadata;
	}

	public void setMetadata(PaginationMetadata metadata) {
		this.metadata = metadata;
	}
	
	
}
