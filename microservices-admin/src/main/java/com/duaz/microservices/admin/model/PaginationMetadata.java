package com.duaz.microservices.admin.model;

public class PaginationMetadata {
	private Integer noTotalRows;
	private Integer noPage;
	private Integer noRowsPerPage;
	private Integer currentPage;
	
	private Integer firstPage;
	private Integer prevPage;
	private Integer nextPage;
	private Integer lastPage;
	
	/**
	 * @return the noTotalRows
	 */
	public Integer getNoTotalRows() {
		return noTotalRows;
	}
	/**
	 * @param noTotalRows the noTotalRows to set
	 */
	public void setNoTotalRows(Integer noTotalRows) {
		this.noTotalRows = noTotalRows;
	}
	/**
	 * @return the noPage
	 */
	public Integer getNoPage() {
		return noPage;
	}
	/**
	 * @param noPage the noPage to set
	 */
	public void setNoPage(Integer noPage) {
		this.noPage = noPage;
	}
	/**
	 * @return the noRowsPerPage
	 */
	public Integer getNoRowsPerPage() {
		return noRowsPerPage;
	}
	/**
	 * @param noRowsPerPage the noRowsPerPage to set
	 */
	public void setNoRowsPerPage(Integer noRowsPerPage) {
		this.noRowsPerPage = noRowsPerPage;
	}
	/**
	 * @return the currentPage
	 */
	public Integer getCurrentPage() {
		return currentPage;
	}
	/**
	 * @param currentPage the currentPage to set
	 */
	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}
	/**
	 * @return the firstPage
	 */
	public Integer getFirstPage() {
		return firstPage;
	}
	/**
	 * @param firstPage the firstPage to set
	 */
	public void setFirstPage(Integer firstPage) {
		this.firstPage = firstPage;
	}
	/**
	 * @return the prevPage
	 */
	public Integer getPrevPage() {
		return prevPage;
	}
	/**
	 * @param prevPage the prevPage to set
	 */
	public void setPrevPage(Integer prevPage) {
		this.prevPage = prevPage;
	}
	/**
	 * @return the nextPage
	 */
	public Integer getNextPage() {
		return nextPage;
	}
	/**
	 * @param nextPage the nextPage to set
	 */
	public void setNextPage(Integer nextPage) {
		this.nextPage = nextPage;
	}
	/**
	 * @return the lastPage
	 */
	public Integer getLastPage() {
		return lastPage;
	}
	/**
	 * @param lastPage the lastPage to set
	 */
	public void setLastPage(Integer lastPage) {
		this.lastPage = lastPage;
	}
	
	
}
