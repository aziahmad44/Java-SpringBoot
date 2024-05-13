package com.duaz.microservices.admin.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.sql.DataSource;
import javax.transaction.Transactional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.duaz.microservices.admin.model.Client;
import com.duaz.microservices.admin.utility.DatabaseUtility;

@Transactional
@Repository
public class ClientServices {
  private static final Log LOG = LogFactory.getLog(ClientServices.class);
  
  @Autowired
  @Qualifier("dbNonHibernate.datasource")
  private DataSource ds;
    
  public Client getClientByClientIdApiKey(String clientId, String apiKey) throws Exception {
    Client c = null;
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    int i = 1;
    String SQL = "select client_no, client_id, client_name, api_key from   t_ri_client where  client_id = ? and    api_key = ? ";
    try {
      conn = this.ds.getConnection();
      pstmt = conn.prepareStatement(SQL);
      pstmt.setObject(i++, clientId);
      pstmt.setObject(i++, apiKey);
      rs = pstmt.executeQuery();
      if (rs.next()) {
        c = null;
        c = new Client();
        c.setClientNo(Integer.valueOf(rs.getInt("client_no")));
        c.setClientId((rs.getString("client_id") == null) ? "" : rs.getString("client_id"));
        c.setClientName((rs.getString("client_name") == null) ? "" : rs.getString("client_name"));
        c.setApiKey((rs.getString("api_key") == null) ? "" : rs.getString("api_key"));
      } 
    } catch (Exception e) {
      LOG.error("Exception at getClientByClientIdApiKey");
      LOG.error(ClientServices.class, e);
      throw e;
    } finally {
      try {
        DatabaseUtility.clear(conn, pstmt, rs);
      } catch (Exception exception) {}
    } 
    return c;
  }
  
}
