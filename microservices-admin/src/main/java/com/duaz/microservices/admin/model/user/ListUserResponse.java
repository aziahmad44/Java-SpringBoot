package com.duaz.microservices.admin.model.user;

import java.util.List;

import com.duaz.microservices.admin.model.PaginationMetadata;

public class ListUserResponse {
	private List<User> listUser;
	
	// metadata
	private PaginationMetadata metadata;

	/**
	 * @return the listUser
	 */
	public List<User> getListUser() {
		return listUser;
	}

	/**
	 * @param listUser the listUser to set
	 */
	public void setListUser(List<User> listUser) {
		this.listUser = listUser;
	}

	/**
	 * @return the metadata
	 */
	public PaginationMetadata getMetadata() {
		return metadata;
	}

	/**
	 * @param metadata the metadata to set
	 */
	public void setMetadata(PaginationMetadata metadata) {
		this.metadata = metadata;
	}
	
	
}
