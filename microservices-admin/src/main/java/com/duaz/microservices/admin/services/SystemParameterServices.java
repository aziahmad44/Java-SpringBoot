package com.duaz.microservices.admin.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;

import javax.sql.DataSource;
import javax.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.duaz.microservices.admin.model.SystemParameter;
import com.duaz.microservices.admin.utility.DatabaseUtility;

@Transactional
@Repository
public class SystemParameterServices {

	@Autowired
	SessionFactory sessionFactory;
	
	@Autowired
    @Qualifier("dbNonHibernate.datasource")
    private DataSource ds;
	
	/**
	 * 
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public String getValueOfSystemParameterByCode(String code)
	throws Exception {
		String value = "";
		
		final String SQL_GET_SYSTEM_PARAMETER = "select value " +												
												"from   t_app_system_parameter " +
												"where  code = ? " +
												"and    status = 'A'";

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null; 

		try {			
			//conn = ds.getConnection();
			conn = sessionFactory.
					getSessionFactoryOptions().getServiceRegistry().
					getService(ConnectionProvider.class).getConnection();
			
			ps = conn.prepareStatement(SQL_GET_SYSTEM_PARAMETER);
			ps.setString(1, code);
					
			rs = ps.executeQuery();
					
			if (rs.next()) {
				value = rs.getString("value") == null ? "" : rs.getString("value");			
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
				DatabaseUtility.clear(conn, ps, rs);
			}
			catch (Exception ex) {
				// ignore
				
			}
		}
				
		return value;
	}

	/**
	 * 
	 * @param spNo
	 * @return
	 * @throws Exception
	 */
	public SystemParameter getSystemParameterBySpNo(int spNo)
	throws Exception {
		SystemParameter sp = null;
				
		final String SQL_GET_SYSTEM_PARAMETER = "select sp_no, table_id, code, " +
												"       value, description, status, " +
												"       cre_dt, " +
												"       cre_user_no, " +
												"       upd_dt, " +
												"       upd_user_no " +												
												"from   t_app_system_parameter " +
												"where  sp_no = ? ";

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null; 

		try {			
			conn = sessionFactory.
    				getSessionFactoryOptions().getServiceRegistry().
    				getService(ConnectionProvider.class).getConnection();
					
			ps = conn.prepareStatement(SQL_GET_SYSTEM_PARAMETER);
			ps.setInt(1, spNo);
					
			rs = ps.executeQuery();
					
			if (rs.next()) {
				sp = null;
				sp = new SystemParameter();
						
				sp.setSpNo(rs.getInt("sp_no"));
				sp.setTableId(rs.getString("table_id") == null ? "" : rs.getString("table_id"));
				sp.setCode(rs.getString("code") == null ? "" : rs.getString("code"));
				sp.setValue(rs.getString("value") == null ? "" : rs.getString("value"));			
				sp.setDescription(rs.getString("description") == null ? "" : rs.getString("description"));			
				sp.setStatus(rs.getString("status") == null ? "" : rs.getString("status"));				
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
					DatabaseUtility.clear(conn, ps, rs);
				}
				catch (Exception ex) {
					// ignore
				}
		}
				
		return sp;
	}
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public HashMap<String, String> getListSystemParameterInMap() 
    throws Exception {
		HashMap<String, String> map = null;

    	final String SQL_GET_SYSTEM_PARAMETER = "select c.sp_no, c.table_id, c.code, c.value, c.description, c.status " +												
				"from   t_app_system_parameter c " +
		  	    "order by c.code asc ";

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null; 

		String code = "";
		String value = "";
		
    	try {    		    		
    		map = new HashMap<String, String>();

    		conn = sessionFactory.
    				getSessionFactoryOptions().getServiceRegistry().
    				getService(ConnectionProvider.class).getConnection();
    		
			ps = conn.prepareStatement(SQL_GET_SYSTEM_PARAMETER);			
			rs = ps.executeQuery();
			
			while (rs.next()) {								
				code = "";
				code = rs.getString("code") == null ? "" : rs.getString("code");
				
				value = "";
				value = rs.getString("value") == null ? "" : rs.getString("value");			
				
				map.put(code, value);
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
				DatabaseUtility.clear(conn, ps, rs);
			}
			catch (Exception ex) {
				// ignore
			}
		}
		
        return map;
    }
	
	public String getCodeOfSystemParameterByValueAndPrefixCode(String value, String prefixCode)
	throws Exception {
		String code = "";
		
		final String SQL_GET_SYSTEM_PARAMETER = "select code " +												
												"from   t_app_system_parameter " +
												"where  code like ? " +
												"and    value = ? " +
												"and    status = 'A'";

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null; 

		try {			
			//conn = ds.getConnection();
			conn = sessionFactory.
					getSessionFactoryOptions().getServiceRegistry().
					getService(ConnectionProvider.class).getConnection();
			
			ps = conn.prepareStatement(SQL_GET_SYSTEM_PARAMETER);
			ps.setObject(1, prefixCode + "%");
			ps.setObject(2, value);
					
			rs = ps.executeQuery();
					
			if (rs.next()) {
				code = rs.getString("code") == null ? "" : rs.getString("code");			
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
				DatabaseUtility.clear(conn, ps, rs);
			}
			catch (Exception ex) {
				// ignore
			}
		}
				
		return code;
	}
	
}
