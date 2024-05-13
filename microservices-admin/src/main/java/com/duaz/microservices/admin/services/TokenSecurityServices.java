package com.duaz.microservices.admin.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.duaz.microservices.admin.constant.RIConstants;
import com.duaz.microservices.admin.exception.NoSuchUserException;
import com.duaz.microservices.admin.model.PaginationMetadata;
import com.duaz.microservices.admin.model.ReturnMessage;
import com.duaz.microservices.admin.model.department.Department;
import com.duaz.microservices.admin.model.user.ListUserFilter;
import com.duaz.microservices.admin.model.user.ListUserResponse;
import com.duaz.microservices.admin.model.user.User;
import com.duaz.microservices.admin.model.user.UserLabel;
import com.duaz.microservices.admin.model.user.UserRest;
import com.duaz.microservices.admin.model.user.UserRole;
import com.duaz.microservices.admin.utility.DatabaseUtility;
import com.duaz.microservices.admin.utility.FormatUtility;
import com.duaz.microservices.admin.utility.PaginationUtility;

@Transactional
@Repository
public class TokenSecurityServices {
	@Autowired
	SessionFactory sessionFactory;
	
    @Autowired
    @Qualifier("dbNonHibernate.datasource")
    private DataSource ds;
    	
	public ReturnMessage addSecurityToken(UserRest userRest) throws Exception {
	    int securityNo = -1;
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    int i = 1;
	    
	    ReturnMessage rm = new ReturnMessage();
	    Boolean isSuccess = true;
		String msg = "Successfuly added data";
		
	    String SQL = "insert into t_ri_token_security (user_no, token_no, source, expiry_dt, invalidate,  status, cre_dt, cre_user_no) values (?, ?, ?, DATEADD(day, " + userRest.getNoDays() + ",  getdate()), 0,  ?, current_timestamp, ?) ";
	    try {
	      conn = this.ds.getConnection();
	      pstmt = conn.prepareStatement(SQL, 1);
	      pstmt.setObject(i++, Integer.valueOf(userRest.getUserId()));
	      pstmt.setObject(i++, userRest.getSecurityToken());
	      pstmt.setObject(i++, Integer.valueOf(userRest.getSource()));
	      pstmt.setObject(i++, "A");
	      pstmt.setObject(i++, Integer.valueOf(userRest.getExecuteUserNo()));
	      pstmt.executeUpdate();
	      rs = pstmt.getGeneratedKeys();
	      if (rs.next())
	        securityNo = rs.getInt(1); 
	    } catch (Exception e) {
			isSuccess = false;
			msg = e.getMessage();
	      throw e;
	    } finally {
	      try {
	        DatabaseUtility.clear(conn, pstmt, rs);
	      } catch (Exception exception) {}
	    }
	    
	    rm.setIsSuccess(isSuccess);
		rm.setMessage(msg);
		rm.setData(userRest);

		return rm;
	  }
	
	public ReturnMessage invalidateSecurityToken(UserRest userRest) throws Exception {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    int i = 1;
	    
	    ReturnMessage rm = new ReturnMessage();
	    Boolean isSuccess = true;
		String msg = "Successfuly updated data";
		
	    String SQL = "update t_ri_token_security set    invalidate = 1 where  user_no = ? and    source = ? ";
	    try {
	      conn = this.ds.getConnection();
	      pstmt = conn.prepareStatement(SQL);
	      pstmt.setObject(i++, Integer.valueOf(userRest.getUserId()));
	      pstmt.setObject(i++, Integer.valueOf(userRest.getSource()));
	      pstmt.executeUpdate();
	    } catch (Exception e) {
			isSuccess = false;
			msg = e.getMessage();
			throw e;
	    } finally {
	      try {
	        DatabaseUtility.clear(conn, pstmt);
	      } catch (Exception exception) {}
	    }
	    
	    rm.setIsSuccess(isSuccess);
		rm.setMessage(msg);
		rm.setData(userRest);

		return rm;
	  }
	  
	public boolean isSecurityToken(UserRest userRest) throws Exception {
	    boolean valid = false;
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    int i = 1;
	    int counter = 0;
	    String SQL = "select security_no, user_no, token_no, source, expiry_dt, invalidate, status from   t_ri_token_security where  user_no = ? and    invalidate = 0 and    token_no = ? and    source = ? and    expiry_dt > current_timestamp ";
	    try {
	      conn = this.ds.getConnection();
	      pstmt = conn.prepareStatement(SQL);
	      pstmt.setObject(i++, Integer.valueOf(userRest.getUserId()));
	      pstmt.setObject(i++, userRest.getSecurityToken());
	      pstmt.setObject(i++, Integer.valueOf(userRest.getSource()));
	      rs = pstmt.executeQuery();
	      if (rs.next()) {
	        counter = 0;
	        counter = rs.getInt("counter");
	      } 
	      if (counter > 0)
	        valid = true; 
	    } catch (Exception e) {
	      throw e;
	    } finally {
	      try {
	        DatabaseUtility.clear(conn, pstmt, rs);
	      } catch (Exception exception) {}
	    } 
	    return valid;
	  }
	
	  public boolean isSecurityTokenValid(UserRest userRest) throws Exception {
	    boolean valid = false;
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    int i = 1;
	    int counter = 0;
	    String SQL = "select count(1) as counter from   t_ri_token_security where  user_no = ? and    invalidate = 0 and    token_no = ? and    source = ? and    expiry_dt > current_timestamp ";
	    try {
	      conn = this.ds.getConnection();
	      pstmt = conn.prepareStatement(SQL);
	      pstmt.setObject(i++, Integer.valueOf(userRest.getUserId()));
	      pstmt.setObject(i++, userRest.getSecurityToken());
	      pstmt.setObject(i++, Integer.valueOf(userRest.getSource()));
	      rs = pstmt.executeQuery();
	      if (rs.next()) {
	        counter = 0;
	        counter = rs.getInt("counter");
	      } 
	      if (counter > 0)
	        valid = true; 
	    } catch (Exception e) {
	      throw e;
	    } finally {
	      try {
	        DatabaseUtility.clear(conn, pstmt, rs);
	      } catch (Exception exception) {}
	    } 
	    return valid;
	  }
	
}
