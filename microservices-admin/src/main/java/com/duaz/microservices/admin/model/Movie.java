package com.duaz.microservices.admin.model;

public class Movie {
	
	private int id;
	private String title;
	private String description;
	private double rating;
	private String image;
	private String createdDt;
	private Integer createdUserNo;
	private String updatedDt;
	private Integer updatedUserNo;
	private String status;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public double getRating() {
		return rating;
	}
	public void setRating(double rating) {
		this.rating = rating;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getCreatedDt() {
		return createdDt;
	}
	public void setCreatedDt(String createdDt) {
		this.createdDt = createdDt;
	}
	public Integer getCreatedUserNo() {
		return createdUserNo;
	}
	public void setCreatedUserNo(Integer createdUserNo) {
		this.createdUserNo = createdUserNo;
	}
	public String getUpdatedDt() {
		return updatedDt;
	}
	public void setUpdatedDt(String updatedDt) {
		this.updatedDt = updatedDt;
	}
	public Integer getUpdatedUserNo() {
		return updatedUserNo;
	}
	public void setUpdatedUserNo(Integer updatedUserNo) {
		this.updatedUserNo = updatedUserNo;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
