package com.duaz.microservices.admin.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.duaz.microservices.admin.model.Token;
import com.duaz.microservices.admin.utility.DatabaseUtility;
import com.duaz.microservices.admin.utility.FormatUtility;

@Service("tokenService")
@Scope("prototype")
public class TokenService {
  static Logger LOG = Logger.getLogger(TokenService.class);
  
  @Autowired
  @Qualifier("dbNonHibernate.datasource")
  private DataSource ds;
  
  @Autowired
  private FormatUtility formatUtility;
  
  public String generateVerificationCode(int length) throws Exception {
    String verificationCode = "";
    try {
      verificationCode = this.formatUtility.generateAlphanumericRandomString(length);
    } catch (Exception e) {
      LOG.error("Exception at generateVerificationCode");
      LOG.error(TokenService.class, e);
      throw e;
    } finally {}
    return verificationCode;
  }
  
  public boolean isTokenValid(Token token, int noValidHours) throws Exception {
    boolean tokenValid = false;
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String SQL = " SELECT COUNT(1) AS counter  FROM t_ri_token  WHERE upper(email) = ?  AND id = ?  AND token = ?  AND    DATE_PART('hour', expiry_dt - current_timestamp)  <= ?   AND used_dt IS NULL ";
    int counter = 0;
    try {
      conn = this.ds.getConnection();
      ps = conn.prepareStatement(" SELECT COUNT(1) AS counter  FROM t_ri_token  WHERE upper(email) = ?  AND id = ?  AND token = ?  AND    DATE_PART('hour', expiry_dt - current_timestamp)  <= ?   AND used_dt IS NULL ");
      ps.setString(1, token.getEmail().trim().toUpperCase());
      ps.setString(2, token.getId());
      ps.setString(3, token.getToken());
      ps.setInt(4, noValidHours);
      rs = ps.executeQuery();
      counter = 0;
      if (rs.next())
        counter = rs.getInt("counter"); 
      if (counter > 0) {
        tokenValid = true;
      } else {
        tokenValid = false;
      } 
    } catch (Exception e) {
      LOG.error("Exception at isTokenValid");
      LOG.error(TokenService.class, e);
      throw e;
    } finally {
      try {
        DatabaseUtility.clear(conn, ps, rs);
      } catch (Exception exception) {}
    } 
    return tokenValid;
  }
  
  public void invalidateForgetPasswordTokenByEmail(String email) throws Exception {
    Connection conn = null;
    PreparedStatement ps = null;
    String SQL_PREV = "update t_ri_token set    used_dt = current_timestamp where  email = ? and    id = ? and    used_dt is null";
    try {
      conn = this.ds.getConnection();
      ps = conn.prepareStatement("update t_ri_token set    used_dt = current_timestamp where  email = ? and    id = ? and    used_dt is null");
      ps.setString(1, email);
      ps.setString(2, "FORGOT_PASSWORD");
      ps.executeUpdate();
    } catch (Exception e) {
      LOG.error("Exception at invalidateForgetPasswordTokenByEmail");
      LOG.error(TokenService.class, e);
      throw e;
    } finally {
      try {
        DatabaseUtility.clear(ps);
        DatabaseUtility.clear(conn);
      } catch (Exception exception) {}
    } 
  }
  
  public void insertToken(Token token) throws Exception {
    Connection conn = null;
    PreparedStatement ps = null;
    String SQL = "insert into t_ri_token (email, id, token,  expiry_dt, used_dt) values (?, ?, ?,  (current_timestamp + interval '2 hour'), null) ";
    LOG.info("insert into t_ri_token (email, id, token,  expiry_dt, used_dt) values (?, ?, ?,  (current_timestamp + interval '2 hour'), null) ");
    try {
      conn = this.ds.getConnection();
      ps = conn.prepareStatement("insert into t_ri_token (email, id, token,  expiry_dt, used_dt) values (?, ?, ?,  (current_timestamp + interval '2 hour'), null) ");
      ps.setString(1, token.getEmail());
      ps.setString(2, token.getId());
      ps.setString(3, token.getToken());
      if (token.getId().equalsIgnoreCase("FORGOT_PASSWORD"));
      ps.executeUpdate();
    } catch (Exception e) {
      LOG.error("Exception at insertToken");
      LOG.error(TokenService.class, e);
      throw e;
    } finally {
      try {
        DatabaseUtility.clear(ps);
        DatabaseUtility.clear(conn);
      } catch (Exception exception) {}
    } 
  }
}
