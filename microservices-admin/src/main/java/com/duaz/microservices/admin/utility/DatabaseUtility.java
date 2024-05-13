package com.duaz.microservices.admin.utility;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

public class DatabaseUtility {
	static Logger LOG = Logger.getLogger(DatabaseUtility.class);
	
    /** ERR_NO_CONTEXT_FOUND */
    public static final String ERR_NO_CONTEXT_FOUND = "No Context Found";
    
    /** Hash Map to reuse the context */
	private static HashMap<String, Object> context_map = new HashMap<String, Object>();
    
    /**
     * 
     */
    public DatabaseUtility() {
        // do nothing   
    }
    
    /**
     * Method to call the context
     * 
     * getConnectionObject
     *
     * @param jndiName
     * @return
     * @throws Exception
     */
	private static Object getConnectionObject(String jndiName) 
    throws Exception {
        Object obj = null;
        Context ctx = null;        
                
        try {            
            if (context_map.get(jndiName) == null) {
                // prepare the Context
                ctx = new InitialContext();
                
                if (ctx == null) {
                    throw new Exception(ERR_NO_CONTEXT_FOUND);
                }
                 
				obj = ctx.lookup(jndiName);
                
                if (obj != null) {
                    context_map.put(jndiName, obj);
                }
            }
            else {
                obj = context_map.get(jndiName);
            }           
        }
        catch (Exception e) {
        	LOG.error("Exception at getConnectionObject");
        	LOG.error(DatabaseUtility.class, e);
        	
            throw e;
        }
        finally {
            // clean up  
            ctx = null;              
        }
        
        return obj;
    }
    

    /**
     * Method to get connection
     * 
     * getConnection
     *
     * @param JNDI_NAME
     * @return
     * @throws Exception
     */
    public static Connection getConnection(String JNDI_NAME) throws Exception {
        Connection conn = null;
        DataSource ds = null;
        Object obj = null;
        
        try {			
            obj = getConnectionObject(JNDI_NAME);
            
            // prepare the DataSource
			ds = (DataSource) obj;
			
			if (ds != null) {
				conn = ds.getConnection();
			}
        }
        catch (Exception e) {
        	LOG.error("Exception at getConnection");
        	LOG.error(DatabaseUtility.class, e);
        	        	
            throw e;    
        }
        finally {
            // clean up            
            ds = null; 
            obj = null;   
        }
        
        return conn;
    }
    
    /**
     * Method to clean up the connection, resultset and statement
     * 
     * clear
     *
     * @param conn
     * @param ps
     * @param rs
     * @throws Exception
     */
    public static void clear(Connection conn, PreparedStatement ps, ResultSet rs) throws Exception {
		try {
            // if statement is closed, the resultset is explicitly closed
			if (ps != null) {
				if (rs != null) {
					rs.close();
					rs = null;
				}
				ps.close();
				ps = null;
			}
			
			if (conn != null) {
				conn.close();
				conn = null;
			}
		}
		catch (Exception e) {
			LOG.error("Exception at clear(conn, ps, rs)");
        	LOG.error(DatabaseUtility.class, e);
        	
			throw e;			
		}        
    }
    
    /**
     * Method to clean up the connection, resultset and statement
     * 
     * clear
     *
     * @param conn
     * @param ps
     * @throws Exception
     */
    public static void clear(Connection conn, PreparedStatement ps) throws Exception {
		try {
            // if statement is closed, the resultset is explicitly closed
			if (ps != null) {				
				ps.close();
				ps = null;
			}
			
			if (conn != null) {
				conn.close();
				conn = null;
			}
		}
		catch (Exception e) {
			LOG.error("Exception at clear(conn, ps)");
        	LOG.error(DatabaseUtility.class, e);
        	
			throw e;			
		}        
    }
    
    /**
     * Method to clean up the statement and resultset 
     * 
     * clear
     *
     * @param ps
     * @param rs
     * @throws Exception
     */
    public static void clear(PreparedStatement ps, ResultSet rs) throws Exception {
		try {
            // if statement is closed, the resultset is explicitly closed
			if (ps != null) {
				if (rs != null) {
					rs.close();
					rs = null;
				}                				
				ps.close();
				ps = null;
			}			
		}
		catch (Exception e) {
			LOG.error("Exception at clear(ps, rs)");
        	LOG.error(DatabaseUtility.class, e);
        	        	
			throw e;			
		}        
    }  
        
    /**
     * Method to clean up the statement
     * 
     * clear
     *
     * @param ps
     * @throws Exception
     */
    public static void clear(PreparedStatement ps) throws Exception {
		try {
            // if statement is closed, the resultset is explicitly closed
			if (ps != null) {				
				ps.close();
				ps = null;
			}			
		}
		catch (Exception e) {
			LOG.error("Exception at clear(ps)");
        	LOG.error(DatabaseUtility.class, e);
        	
			throw e;			
		}        
    }  
    
    /**
     * Method to clean up the callable statement
     * 
     * clear
     *
     * @param cs
     * @throws Exception
     */
    public static void clear(CallableStatement cs) throws Exception {
		try {
            // if statement is closed, the resultset is explicitly closed
			if (cs != null) {				
				cs.close();
				cs = null;
			}			
		}
		catch (Exception e) {
			LOG.error("Exception at clear(cs)");
        	LOG.error(DatabaseUtility.class, e);
        	
			throw e;			
		}        
    }     
    
    /**
     * Method to clean up the callable statement
     * 
     * clear
     *
     * @param cs
     * @throws Exception
     */
    public static void clear(Statement s) throws Exception {
		try {
            // if statement is closed, the resultset is explicitly closed
			if (s != null) {				
				s.close();
				s = null;
			}			
		}
		catch (Exception e) {
			LOG.error("Exception at clear(s)");
        	LOG.error(DatabaseUtility.class, e);
        	
			throw e;			
		}        
    }      
    
    /**
     * Method to clean up the resultset
     * 
     * clear
     *
     * @param conn
     * @throws Exception
     */
    public static void clear(Connection conn) throws Exception {
		try {
			if (conn != null) {
				conn.close();
				conn = null;
			}		
		}
		catch (Exception e) {
			LOG.error("Exception at clear(conn)");
        	LOG.error(DatabaseUtility.class, e);
        	        	
			throw e;			
		}        
    }
}

