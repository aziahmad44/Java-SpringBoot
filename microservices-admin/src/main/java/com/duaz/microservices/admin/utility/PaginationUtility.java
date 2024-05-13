package com.duaz.microservices.admin.utility;

import com.duaz.microservices.admin.model.PaginationMetadata;

public class PaginationUtility {

	/**
	 * 
	 * @param totalRow
	 * @param noRowsPerPage
	 * @param start
	 * @return
	 * @throws Exception
	 */
	public static PaginationMetadata getPaginationMetadata(int totalRow, int noRowsPerPage, int start)
	throws Exception {
		PaginationMetadata metadata = null;
		
		try {
			metadata = new PaginationMetadata();
			
			if (totalRow <= 0) {
				metadata.setNoTotalRows(0);
				metadata.setNoPage(0);
				metadata.setCurrentPage(1);
				metadata.setNoRowsPerPage(noRowsPerPage);
				
				metadata.setFirstPage(1);
				metadata.setLastPage(1);
				metadata.setNextPage(1);
				metadata.setPrevPage(1);
			}
			else {
				metadata.setNoTotalRows(totalRow);
				metadata.setNoRowsPerPage(noRowsPerPage);
				
				if ((totalRow % noRowsPerPage) > 0) {
					metadata.setNoPage((totalRow / noRowsPerPage) + 1);
				}
				else {
					metadata.setNoPage((totalRow / noRowsPerPage));
				}
				
				if (start == 0) {
					metadata.setCurrentPage(1);
				}
				else if (start > 0) {
					metadata.setCurrentPage(start / noRowsPerPage);
				}
				else {
					metadata.setCurrentPage(1);
				}
				
				if (metadata.getCurrentPage() == 0) {
					// should not go here
					metadata.setFirstPage(1);
    				metadata.setLastPage(1);
    				metadata.setNextPage(1);
    				metadata.setPrevPage(1);
				}
				else {
					if (metadata.getCurrentPage() == metadata.getNoPage()) {
						metadata.setLastPage(metadata.getCurrentPage());
						metadata.setFirstPage(1);
						metadata.setNextPage(metadata.getCurrentPage());
						
						if (metadata.getCurrentPage() == 1) {
							metadata.setPrevPage(1);
						}
						else {
							metadata.setPrevPage(metadata.getCurrentPage() - 1);
						}
					}
					else if (metadata.getCurrentPage() == 1 && (metadata.getCurrentPage() != metadata.getNoPage())) {
						metadata.setLastPage(metadata.getNoPage());
						metadata.setFirstPage(1);
						metadata.setNextPage(metadata.getCurrentPage() + 1);
						metadata.setPrevPage(metadata.getCurrentPage());
					}
				}
			}
		}
		catch (Exception e) {
			System.out.println("Exception at getPaginationMetadata");
			e.printStackTrace();
			metadata = null;
		}
		finally {
			// clean up
		}
		
		return metadata;
	}
}
