package com.duaz.microservices.admin.services;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;
import javax.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.duaz.microservices.admin.model.PaginationMetadata;
import com.duaz.microservices.admin.model.ReturnMessage;
import com.duaz.microservices.admin.model.auditlog.AuditLog;
import com.duaz.microservices.admin.model.auditlog.ListAuditLogFilter;
import com.duaz.microservices.admin.model.auditlog.ListAuditLogResponse;
import com.duaz.microservices.admin.utility.DatabaseUtility;
import com.duaz.microservices.admin.utility.PaginationUtility;


@Transactional
@Repository
public class AuditLogServices {

	@Autowired
	SessionFactory sessionFactory;
	
	@Autowired()
    @Qualifier("dbNonHibernate.datasource")
    private DataSource ds;
	
	public ListAuditLogResponse getListAuditLog(ListAuditLogFilter filter){
		ListAuditLogResponse resp = null;
		PaginationMetadata metadata = null;
		
    	List<AuditLog> list = null;
    	AuditLog al = null;
    	
    	int i = 1;
    	int totalRow = 0;
    	
    	Connection conn = null;
	  	PreparedStatement pstmt = null;
	  	ResultSet rs = null;
	  	
	  	String SQL = "";
		
		try {
			resp = new ListAuditLogResponse();
    		metadata = new PaginationMetadata();
    		
    		list = new ArrayList<AuditLog>();
    		
    		if (filter.getNoRows() != null && filter.getNoRows().intValue() == -1) {
    			SQL += "select * ";
    			SQL += "from ( ";
    		}
    		else {
    			SQL += "select top " + filter.getNoRows().intValue() + " * ";
    			SQL += "from ( ";
    		}
    		
    		SQL += "    SELECT c.*, ROW_NUMBER() OVER (ORDER BY "+ filter.getOrderBy() +") AS num_ind ";
			SQL += "    from ( ";
			
			SQL += "select audit_no, message, username, rint_module, ip_address, convert(char, al.cre_dt, 105) as cre_dt  from t_ams_audit_log al " + 
					 "where al.status in ('A', 'S') ";
			
			if (filter.getMessage() != null && filter.getMessage().trim().length() > 0) {
    			SQL += "and al.message like (?) ";
    		}
			
			if (filter.getUsername() != null && filter.getUsername().trim().length() > 0) {
				SQL += "and al.username like (?) ";    	
    		}
			
			if (filter.getDateFrom() != null && filter.getDateFrom().trim().length() > 0) {
				SQL += "and convert(char, al.cre_dt, 105) >= ? ";    	
    		}
			
			if (filter.getDateTo() != null && filter.getDateTo().trim().length() > 0) {
				SQL += "and  convert(char, al.cre_dt, 105) <= ? ";    	
    		}
			
			SQL += "    ) c ";
    		SQL += ") result ";    		
    		
    		if (filter.getNoRows() != null && filter.getNoRows().intValue() > 0) {
    			SQL += "where num_ind >= ?  ";
    		}
    		else {
    			// nothing
    		}
    		
    		//conn = ds.getConnection();
    		conn = sessionFactory.
    				getSessionFactoryOptions().getServiceRegistry().
    				getService(ConnectionProvider.class).getConnection();
      		pstmt = conn.prepareStatement(SQL);
      		
      		if (filter.getMessage() != null && filter.getMessage().trim().length() > 0) {
    			pstmt.setObject(i++, "%"+filter.getMessage().toUpperCase()+"%");
    		}
      		if (filter.getUsername()!= null && filter.getUsername().trim().length() > 0) {
    			pstmt.setObject(i++, "%"+filter.getUsername().toUpperCase()+"%");
    		}
      		if (filter.getDateFrom()!= null && filter.getDateFrom().trim().length() > 0) {
    			pstmt.setObject(i++, "%");
    		}
      		if (filter.getDateTo()!= null && filter.getDateTo().trim().length() > 0) {
    			pstmt.setObject(i++, "%"+filter.getDateTo().toUpperCase()+"%");
    		}
    		System.out.println("date from : " +filter.getDateFrom());
    		if (filter.getNoRows() != null && filter.getNoRows().intValue() > 0) {
    			pstmt.setObject(i++, filter.getStart().intValue());
    		}
    		
    		rs = pstmt.executeQuery();
    		
    		while (rs.next()) {
            	al = null;
				al = new AuditLog();
				
				al.setAuditNo(new BigInteger(Integer.valueOf(rs.getInt("audit_no")).toString()));
				al.setMessage(rs.getString("message") == null ? "" : rs.getString("message"));
				al.setUsername(rs.getString("username") == null ? "" : rs.getString("username"));
				al.setCreatedDate(rs.getString("cre_dt") == null ? "" : rs.getString("cre_dt"));
				al.setRintModule(rs.getString("rint_module") == null ? "" : rs.getString("rint_module"));
				al.setIpAddress(rs.getString("ip_address") == null ? "" : rs.getString("ip_address"));
	   			
				if (al != null) {
					list.add(al);
				}
            }
    		
    		// set response
    		resp.setListAuditLog(list);
    		
    		// metadata
    		if (filter.getMetadata() != null && filter.getMetadata().intValue() == 1) {
    			totalRow = 0;
    			totalRow = this.countListOfAuditLog(filter);
    			
    			// get metadata
    			metadata = PaginationUtility.getPaginationMetadata(totalRow, filter.getNoRows(), filter.getStart().intValue());    			
    		}
    		
    		// set response
    		resp.setMetadata(metadata);
			
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		finally {
			// clean up
			metadata = null;
			list = null;
			
	  		try {
	             DatabaseUtility.clear(conn, pstmt, rs);
	  		}
	  		catch (final Exception ex) {
	             // do nothing
	  		}
		}
		
		return resp;
	}
	
	public Integer countListOfAuditLog(ListAuditLogFilter filter) throws Exception {
		int counter = 0;
		
    	int i = 1;
    	
    	Connection conn = null;
	  	PreparedStatement pstmt = null;
	  	ResultSet rs = null;
	  	
	  	String SQL_COUNT = "";
    	
    	try {
    	
    		SQL_COUNT += "SELECT count(1) as counter ";
    		SQL_COUNT += "from ( ";
    		
    		SQL_COUNT += "select audit_no, message, username, rint_module, ip_address, convert(char, al.cre_dt, 105) as cre_dt from t_ams_audit_log al " + 
					 "where al.status in ('A', 'S') ";
    		
    		if (filter.getMessage() != null && filter.getMessage().trim().length() > 0) {
    			SQL_COUNT += "and al.message like (?) ";
    		}
			
			if (filter.getUsername() != null && filter.getUsername().trim().length() > 0) {
				SQL_COUNT += "and al.username like (?) ";    	
    		}
			
			if (filter.getDateFrom() != null && filter.getDateFrom().trim().length() > 0) {
				SQL_COUNT += "and convert(char, al.cre_dt, 105) >= ? ";    	
    		}
			
			if (filter.getDateTo() != null && filter.getDateTo().trim().length() > 0) {
				SQL_COUNT += "and  convert(char, al.cre_dt, 105) <= ? ";    	
    		}
			    	
    		SQL_COUNT += "    ) c ";
    		
    		//conn = ds.getConnection();
    		conn = sessionFactory.
    				getSessionFactoryOptions().getServiceRegistry().
    				getService(ConnectionProvider.class).getConnection();
    		
      		pstmt = conn.prepareStatement(SQL_COUNT);
      		
      		if (filter.getMessage() != null && filter.getMessage().trim().length() > 0) {
    			pstmt.setObject(i++, "%"+filter.getMessage().toUpperCase()+"%");
    		}
      		if (filter.getUsername()!= null && filter.getUsername().trim().length() > 0) {
    			pstmt.setObject(i++, "%"+filter.getUsername().toUpperCase()+"%");
    		}
      		
      		if (filter.getDateFrom()!= null && filter.getDateFrom().trim().length() > 0) {
    			pstmt.setObject(i++, "%"+filter.getDateFrom().toUpperCase()+"%");
    		}
      		if (filter.getDateTo()!= null && filter.getDateTo().trim().length() > 0) {
    			pstmt.setObject(i++, "%"+filter.getDateTo().toUpperCase()+"%");
    		}
 
    		rs = pstmt.executeQuery();
    		
    		if (rs.next()) {
            	counter = rs.getInt("counter");
            }    		
    	}
    	catch (Exception e) {
			
			e.printStackTrace();
			System.out.println(e.getMessage());
			
			throw e;
		}
		finally {
			// clean up
	  		try {
	             DatabaseUtility.clear(conn, pstmt, rs);
	  		}
	  		catch (final Exception ex) {
	             // do nothing
	  		}
		}
		
        return counter;
    }
	
	/**
     * 
     * @param username
     * @param module
     * @param message
     * @throws Exception
     */
    public ReturnMessage addAuditLog(AuditLog auditLog)
    throws Exception {
    	ReturnMessage rMessage = null;
	    Boolean isSuccess = true;
		String msg = "Successfuly added data";
		
    	Connection conn = null;
        PreparedStatement pstmt = null;
        
        final String SQL = "insert into t_ri_audit_log " + 
        				   "(username, rint_module, message, ip_address, status, cre_dt, cre_user_no) " + 
        				   "values " + 
        				   "(?, ?, ?, ?, 'A', getdate(), ?) ";
        		
        try {
        	rMessage = new ReturnMessage();
        	
        	conn = this.ds.getConnection();
        	
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, auditLog.getUsername());
            pstmt.setString(2, auditLog.getRintModule());
            pstmt.setString(3, auditLog.getMessage());
            pstmt.setString(4, auditLog.getIpAddress());
            pstmt.setInt(5, auditLog.getCreatedNo());
            
            pstmt.executeUpdate();
            
        }
        catch (final Exception e) {
        	e.printStackTrace();
			isSuccess = false;
			msg = e.getMessage();
        }
         
        rMessage.setIsSuccess(isSuccess);
        rMessage.setMessage(msg);
		rMessage.setData(auditLog);
		
		return rMessage;

    }
}
