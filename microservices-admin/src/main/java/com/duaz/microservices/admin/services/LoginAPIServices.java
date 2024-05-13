package com.duaz.microservices.admin.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.sql.DataSource;
import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.duaz.microservices.admin.model.Client;
import com.duaz.microservices.admin.model.user.User;
import com.duaz.microservices.admin.utility.DatabaseUtility;

@Transactional
@Repository
public class LoginAPIServices {
  static Logger LOG = Logger.getLogger(LoginAPIServices.class);
  
  @Autowired
  SessionFactory sessionFactory;
	
  @Autowired
  @Qualifier("dbNonHibernate.datasource")
  private DataSource ds;
  
  
  @Autowired
  private UserServices userServices;
  
  public Integer doLogin(String username, String password) throws Exception {
    int respond = 1;
    Md5PasswordEncoder passwordEncoder = null;
    User u = null;
    Object salt = null;
    try {
      passwordEncoder = new Md5PasswordEncoder();
      u = null;
      u = this.userServices.getUserByUsername(username);
      if (u != null) {
        if (u.getSalt() != null && u.getSalt().trim().length() > 0)
          salt = u.getSalt(); 
        if (!passwordEncoder.isPasswordValid(u.getPassword(), password, salt)) {
          respond = 2;
        } else {
          respond = 1;
        } 
      } else {
        respond = 3;
      } 
    } catch (Exception e) {
      LOG.error("Exception at doLogin");
      LOG.error(LoginAPIServices.class, e);
      throw e;
    } finally {}
    return Integer.valueOf(respond);
  }
  
  @Autowired
  private ClientServices clientServices;
  
  
  public int insertSecurityToken(String securityToken, int source, int userNo, int noDays, int creUserNo) throws Exception {
    int securityNo = -1;
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    int i = 1;
    String SQL = "insert into t_ri_token_security (user_no, token_no, source, expiry_dt, invalidate,  status, cre_dt, cre_user_no) values (?, ?, ?, DATEADD(day, " + noDays + ",  getdate()), 0,  ?, current_timestamp, ?) ";
    try {
      conn = this.ds.getConnection();
      pstmt = conn.prepareStatement(SQL, 1);
      pstmt.setObject(i++, Integer.valueOf(userNo));
      pstmt.setObject(i++, securityToken);
      pstmt.setObject(i++, Integer.valueOf(source));
      pstmt.setObject(i++, "A");
      pstmt.setObject(i++, Integer.valueOf(creUserNo));
      pstmt.executeUpdate();
      rs = pstmt.getGeneratedKeys();
      if (rs.next())
        securityNo = rs.getInt(1); 
    } catch (Exception e) {
      LOG.error("Exception at insertSecurityToken");
      LOG.error(LoginAPIServices.class, e);
      throw e;
    } finally {
      try {
        DatabaseUtility.clear(conn, pstmt, rs);
      } catch (Exception exception) {}
    } 
    return securityNo;
  }
  
  public boolean isSecurityTokenValid(String securityToken, int source, int userNo) throws Exception {
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
      pstmt.setObject(i++, Integer.valueOf(userNo));
      pstmt.setObject(i++, securityToken);
      pstmt.setObject(i++, Integer.valueOf(source));
      rs = pstmt.executeQuery();
      if (rs.next()) {
        counter = 0;
        counter = rs.getInt("counter");
      } 
      if (counter > 0)
        valid = true; 
    } catch (Exception e) {
      LOG.error("Exception at isSecurityTokenValid");
      LOG.error(LoginAPIServices.class, e);
      throw e;
    } finally {
      try {
        DatabaseUtility.clear(conn, pstmt, rs);
      } catch (Exception exception) {}
    } 
    return valid;
  }
  
  public String validateClientIdApiKey(String clientId, String apiKey) throws Exception {
    String error = "";
    Client c = null;
    try {
      if (clientId == null || clientId.trim().length() == 0) {
        error = "Client ID cannot be null";
        return error;
      } 
      if (apiKey == null || apiKey.trim().length() == 0) {
        error = "API Key cannot be null";
        return error;
      } 
      c = null;
      c = this.clientServices.getClientByClientIdApiKey(clientId, apiKey);
      if (c == null) {
        error = "Either Client ID or API Key is wrong / does not match";
        return error;
      } 
    } catch (Exception e) {
      LOG.error("Exception at validateClientIdApiKey");
      LOG.error(LoginAPIServices.class, e);
//      throw e;
    } finally {
      c = null;
    } 
    return "";
  }
  
  public String validateSecurityToken(String securityToken, int source, int userNo) throws Exception {
    String error = "";
    try {
      if (!isSecurityTokenValid(securityToken, source, userNo)) {
        error = "Security Token is invalid";
        return error;
      } 
    } catch (Exception e) {
      LOG.error("Exception at validateSecurityToken");
      LOG.error(LoginAPIServices.class, e);
      throw e;
    } finally {}
    return "";
  }
  
  public String restApiSecurity(String clientId, String apiKey, String securityToken, int source, int userNo) throws Exception {
    String error = "";
    Client c = null;
    try {
      if (clientId == null || clientId.trim().length() == 0) {
        error = "Client ID cannot be null";
        return error;
      } 
      if (apiKey == null || apiKey.trim().length() == 0) {
        error = "API Key cannot be null";
        return error;
      } 
      if (securityToken == null || securityToken.trim().length() == 0) {
        error = "Security Token cannot be null";
        return error;
      } 
      c = null;
      c = this.clientServices.getClientByClientIdApiKey(clientId, apiKey);
      if (c == null) {
        error = "Either Client ID or API Key is wrong / does not match";
        return error;
      } 
      if (source == 1) {
        if (userNo < 0) {
          error = "User No cannot be null";
          return error;
        } 
        error = validateSecurityToken(securityToken, source, userNo);
        return error;
      } 
      if (source == 2) {
        error = validateSecurityToken(securityToken, source, c.getClientNo().intValue());
        return error;
      } 
    } catch (Exception e) {
      LOG.error("Exception at restApiSecurity");
      LOG.error(LoginAPIServices.class, e);
      throw e;
    } finally {
      c = null;
    } 
    return "";
  }
  
  public void invalidateSecurityToken(int source, int userNo) throws Exception {
    Connection conn = null;
    PreparedStatement pstmt = null;
    int i = 1;
    String SQL = "update t_ri_token_security set    invalidate = 1 where  user_no = ? and    source = ? ";
    try {
      conn = this.ds.getConnection();
      pstmt = conn.prepareStatement(SQL);
      pstmt.setObject(i++, Integer.valueOf(userNo));
      pstmt.setObject(i++, Integer.valueOf(source));
      pstmt.executeUpdate();
    } catch (Exception e) {
      LOG.error("Exception at invalidateSecurityToken");
      LOG.error(LoginAPIServices.class, e);
      throw e;
    } finally {
      try {
        DatabaseUtility.clear(conn, pstmt);
      } catch (Exception exception) {}
    } 
  }

}
