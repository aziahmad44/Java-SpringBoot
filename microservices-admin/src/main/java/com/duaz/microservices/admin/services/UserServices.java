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
import com.duaz.microservices.admin.model.PaginationMetadata;
import com.duaz.microservices.admin.model.ReturnMessage;
import com.duaz.microservices.admin.model.department.Department;
import com.duaz.microservices.admin.model.user.ListUserFilter;
import com.duaz.microservices.admin.model.user.ListUserResponse;
import com.duaz.microservices.admin.model.user.User;
import com.duaz.microservices.admin.model.user.UserRole;
import com.duaz.microservices.admin.utility.DatabaseUtility;
import com.duaz.microservices.admin.utility.FormatUtility;
import com.duaz.microservices.admin.utility.PaginationUtility;

@Transactional
@Repository
public class UserServices {
	@Autowired
	SessionFactory sessionFactory;
	
    @Autowired
    @Qualifier("dbNonHibernate.datasource")
    private DataSource ds;
    
	@Autowired
	private DepartmentServices departmentServices;
	
	private Md5PasswordEncoder md5 = new Md5PasswordEncoder();
	
	public List<User> getListUser() throws Exception {
       List<User> list = null;
       User user = null;

       final String SQL = "select user_no as userNo, username, email, password, full_name as FullName, salt, client_no as clientNo, " +
    		   		   "role_no as roleNo, role_name as roleName, " +
    		   		   "FORMAT(created_dt,'yyyy-MM-dd hh:mm:ss') as createdDt, created_user_no as createdUserNo, " +
    		   		   "FORMAT(updated_dt,'yyyy-MM-dd hh:mm:ss') as updatedDt, updated_user_no as updatedUserNo, status " +
       				   "from   t_app_user    " + 
       				   "order by userNo asc ";

       try {
           Session session = sessionFactory.getCurrentSession();
			
		   list = session.createSQLQuery(SQL)
					.setResultTransformer(Transformers.aliasToBean(User.class))
					.list();

       } catch (final Exception e) {
           throw e;
       } finally {
           // clean up

       }

       return list;
   }
	
	/**
	 * getUserById
	 * 
	 * @return List<UserJson>
	 * @throws Exception
	 */
	public User getUserById(Integer id) throws Exception {
		User user = null;

       final String SQL = "select user_no as userNo, username, email, password, full_name as FullName, salt, client_no as clientNo, " +
    		   			  "role_no as roleNo, role_name as roleName, " +
			    		  "FORMAT(created_dt,'yyyy-MM-dd hh:mm:ss') as createdDt, created_user_no as createdUserNo, " +
				   		  "FORMAT(updated_dt,'yyyy-MM-dd hh:mm:ss') as updatedDt, updated_user_no as updatedUserNo, status " +
						  "from   t_app_user  " + 
				   		  "where  user_no = :userNo  " + 
				   		  "order by userNo asc ";

       try {
    	   user = new User();
    	   
    	   Session session = sessionFactory.getCurrentSession();
			
    	   user = (User) session.createSQLQuery(SQL)
					.setResultTransformer(Transformers.aliasToBean(User.class))
					.setInteger("userNo", id)
					.uniqueResult();

       } catch (final Exception e) {
           throw e;
       } finally {
           // clean up
//    	   if (user.getUsername() == null)
//    		   user = null;
       }

       return user;
   }
	
	/**
	 * getUserById
	 * 
	 * @return List<UserJson>
	 * @throws Exception
	 */
	public User getUserByUsername(String username) throws Exception {
		User user = null;

       final String SQL = "select user_no as userNo, username, email, password, full_name as FullName, salt, client_no as clientNo, " +
    		              "role_no as roleNo, role_name as roleName, " +
			    		  "FORMAT(created_dt,'yyyy-MM-dd hh:mm:ss') as createdDt, created_user_no as createdUserNo, " +
				   		  "FORMAT(updated_dt,'yyyy-MM-dd hh:mm:ss') as updatedDt, updated_user_no as updatedUserNo, status " +
						  "from   t_app_user  " + 
				   		  "where username = :username  " + 
				   		  "order by userNo asc ";

       try {
    	   user = new User();
    	   
    	   Session session = sessionFactory.getCurrentSession();
			
    	   user = (User) session.createSQLQuery(SQL)
					.setResultTransformer(Transformers.aliasToBean(User.class))
					.setString("username", username)
					.uniqueResult();
    	   
       } catch (final Exception e) {
           throw e;
       } finally {
           // clean up
//    	   if (user.getUsername() == null)
//    		   user = null;
       }

       return user;
   }
	
	/**
	 * getUserById
	 * @param in 
	 * 
	 * @return List<UserJson>
	 * @throws Exception
	 */
	public User getUserByIdUsername(int id, String username, String validate) throws Exception {
		User user = null;

       String SQL = "select user_no as userNo, username, email, password, full_name as FullName, salt, client_no as clientNo, " +
    		              "role_no as roleNo, role_name as roleName, " +
			    		  "FORMAT(created_dt,'yyyy-MM-dd hh:mm:ss') as createdDt, created_user_no as createdUserNo, " +
				   		  "FORMAT(updated_dt,'yyyy-MM-dd hh:mm:ss') as updatedDt, updated_user_no as updatedUserNo, status " +
						  "from   t_app_user  " + 
				   		  "where  username = :username  ";
      
       if (validate == RIConstants.IN ) {
    	   SQL += " and user_no = :userNo  ";
       } else if (validate == RIConstants.NOT_IN ) {
    	   SQL += " and user_no != :userNo  ";
       }
      
       try {
    	   user = new User();
    	   
    	   Session session = sessionFactory.getCurrentSession();
			
    	   user = (User) session.createSQLQuery(SQL)
					.setResultTransformer(Transformers.aliasToBean(User.class))
					.setString("username", username)
					.setInteger("userNo", id)
					.uniqueResult();
    	   
       } catch (final Exception e) {
           throw e;
       } finally {
           // clean up
//    	   if (user.getUsername() == null)
//    		   user = null;
       }

       return user;
   }
	
	
	/**
     * 
     * @param user
     * @throws Exception
     */
    public ReturnMessage addUser(User user)
    throws Exception {		
	    Session session =  sessionFactory.getCurrentSession();
	    Transaction tx = null;
	    
	    ReturnMessage rm = new ReturnMessage();
	    Boolean isSuccess = true;
		String msg = "Successfuly added data";
		
		FormatUtility fu = null;
		String salt = null;
		String pass = "";
		Integer rolesNo = 0;
	    final String SQL = "insert into t_app_user " +
	    				   "(username, email, password, full_name, salt, client_no, role_no, role_name, " + 
	    				   " status, created_dt, created_user_no )" +
	    			       "values " +
	    				   "(:username, :email, :password, :fullname, :salt, :clientNo, :roleNo, :roleName, " + 
	    				   " :status, getdate(), :createdUserNo)";
	    			              
	    try {
	    	
	    	tx = session.beginTransaction();
			
	    	fu = new FormatUtility();
	    	// -- Generate Alphabet & Number Random
	        salt = fu.generateAlphanumericRandomString(10);
			// -- Hashing Endcode Password with MD5
	        pass = this.md5.encodePassword(user.getPassword(), salt);
	        
	        rolesNo = user.getRoleNo() == null ? 0 : user.getRoleNo();

			session.createSQLQuery(SQL)
			.setString("username", user.getUsername())
			.setString("email", user.getEmail())
			.setString("password", pass)
			.setString("salt", salt)
			.setString("fullname", user.getFullName())
			.setInteger("clientNo", user.getClientNo())
			.setInteger("roleNo", rolesNo)
			.setString("roleName", this.getRole(rolesNo).getrName())
			.setInteger("createdUserNo", user.getCreatedUserNo() == null ? 0 : user.getCreatedUserNo())
			.setString("status", user.getStatus())
			.executeUpdate();
			
			tx.commit();
			
	    }
    	catch (final Exception e) {
			e.printStackTrace();
			if (tx!=null) tx.rollback();
			
			System.out.println("e = " + e);
			isSuccess = false;
			msg = e.getMessage();
		}
	    
	    rm.setIsSuccess(isSuccess);
		rm.setMessage(msg);
		rm.setData(user);

		return rm;
    }
    
    public ReturnMessage updateUser(Integer id, User user)
    		throws Exception {
		Session session =  sessionFactory.getCurrentSession();
		Transaction tx = null;		
		
		ReturnMessage rm = new ReturnMessage();
	    Boolean isSuccess = true;
		String msg = "Successfuly udate data";
		
		FormatUtility fu = null;
		String salt = null;
		Integer rolesNo = 0;
		
		String SQL = "UPDATE t_app_user " + 
				   " SET   username = :username, email = :email, " +
				   " password = :password, salt = :salt, full_name = :fullName, client_no = :clientNo, role_no = :roleNo, role_name = :roleName, " +
				   " updated_dt = getdate(),  updated_user_no = :updatedUserNo, status = :status " +
				   " where user_no = :userNo " ;
		try {
			tx = session.beginTransaction();
			
			fu = new FormatUtility();
			// -- Generate Alphabet & Number Random
	        salt = fu.generateAlphanumericRandomString(10);
	        
	        rolesNo = user.getRoleNo() == null ? 0 : user.getRoleNo();
	        
			session.createSQLQuery(SQL)
			.setInteger("userNo", id)
			.setString("username", user.getUsername())
			.setString("email", user.getEmail())
			// -- Hashing Endcode Password with MD5
			.setString("password", this.md5.encodePassword(user.getPassword(), salt))
			.setString("salt", salt)
			.setString("fullName", user.getFullName())
			.setInteger("clientNo", user.getClientNo())
			.setInteger("roleNo", rolesNo)
			.setString("roleName", this.getRole(rolesNo).getrName())
			.setInteger("updatedUserNo", user.getUpdatedUserNo() == null ? 0 : user.getUpdatedUserNo())
			.setString("status", user.getStatus())
			.executeUpdate();
			
			tx.commit();
		}
		catch (Exception e) {
			e.printStackTrace();
			if (tx!=null) tx.rollback();
			isSuccess = false;
			msg = e.getMessage();
		}
		
		 rm.setIsSuccess(isSuccess);
		 rm.setMessage(msg);
		 rm.setData(user);
			
		return rm;
	}
    
    public ReturnMessage removeUser(Integer id, User user)
    		throws Exception {
		Session session =  sessionFactory.getCurrentSession();
		Transaction tx = null;		
		
		ReturnMessage rm = new ReturnMessage();
	    Boolean isSuccess = true;
		String msg = "Successfuly Remove data";
		
		String SQL = "UPDATE t_app_user " + 
				   "SET    status = 'D', updated_dt = getdate(),  updated_user_no = :updatedUserNo  "
				   + "where user_no = :userNo " ;
		try {
			tx = session.beginTransaction();
			
			session.createSQLQuery(SQL)
			.setInteger("userNo", id)
			.setInteger("updatedUserNo", user.getUpdatedUserNo() == null ? 0 : user.getUpdatedUserNo())
			.executeUpdate();
			
			tx.commit();
		}
		catch (Exception ex) {
			ex.printStackTrace();
			if (tx!=null) tx.rollback();
			isSuccess = false;
			msg = ex.getMessage();
		}
		
		 rm.setIsSuccess(isSuccess);
		 rm.setMessage(msg);
		 rm.setData(user);
			
		return rm;
	}
	
    /**
     * 
     * @param user
     * @throws Exception
     */
    public ReturnMessage updateLastLogin(Integer userNo) throws Exception {
		ReturnMessage rm = new ReturnMessage();
	    Boolean isSuccess = true;
		String msg = "Successfuly update-LastLogin data";
		
        int i = 1;

        Connection conn = null;
        PreparedStatement pstmt = null;

        final String SQL = "update t_app_user " +
                           "set    last_login = CURRENT_TIMESTAMP, updated_dt = CURRENT_TIMESTAMP, updated_user_no = ? " +
                           "where  user_no = ? ";
        
        try {
            conn = this.ds.getConnection();
            pstmt = conn.prepareStatement(SQL);
            
            pstmt.setInt(i++, userNo);
            pstmt.setInt(i++, userNo);
            
            pstmt.executeUpdate();
        }
        catch (final Exception e) {            
            throw e;
        }
        finally {
            // clean up
            try {
                DatabaseUtility.clear(conn, pstmt);
            } 
            catch (final Exception ex) {
                // do nothing
            }
        }
        
        rm.setIsSuccess(isSuccess);
		rm.setMessage(msg);
		rm.setData(userNo);
			
		return rm;
    }
	
    /**
     * 
     * @param roleNo
     * @return
     * @throws Exception
     */
    public UserRole getRole(final int roleNo) throws Exception {
        UserRole userRole = null;

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
        	conn = sessionFactory.
    				getSessionFactoryOptions().getServiceRegistry().
    				getService(ConnectionProvider.class).getConnection();
        	
            pstmt = conn.prepareStatement("SELECT role_no, role_name, role_level, status FROM  t_app_role r where role_no = ?");
            pstmt.setInt(1, roleNo);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                userRole = new UserRole();
                userRole.setrNo(rs.getInt("role_no"));
                userRole.setrName(rs.getString("role_name"));
                userRole.setRoleLevel(rs.getInt("role_level"));
                userRole.setStatus(rs.getString("status"));
            }

        } catch (final Exception e) {
            throw e;
        } finally {
            // clean up
            try {
                DatabaseUtility.clear(conn, pstmt, rs);
            } catch (final Exception ex) {
                // do nothing
            }
        }

        return userRole;
    }

	public ArrayList<String> getRolesByUser(int userNo)
    throws Exception {
    	ArrayList<String> sList = null;

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String roles = "";

        final String SQLRoles = "select distinct r.role_name " +
        					    "from   t_app_user u, t_app_role r " +
        					    "where  u.role_no = r.role_no " +
        					    "and    u.status = 'A' " +
        					    "and    r.status = 'A' " +
        					    "and    u.user_no = ? ";

        try {
        	sList = new ArrayList<String>();
        	conn = sessionFactory.
    				getSessionFactoryOptions().getServiceRegistry().
    				getService(ConnectionProvider.class).getConnection();

            pstmt = conn.prepareStatement(SQLRoles);
            pstmt.setInt(1, userNo);

            rs = pstmt.executeQuery();

            while (rs.next()) {
            	roles = rs.getString("role_name") == null ? "" : rs.getString("role_name");

            	if (roles != null && roles.trim().length() > 0) {
            		sList.add(roles);
            	}
            }

            // always add ROLE_USER
            sList.add("ROLE_USER");

        } 
        catch (final Exception e) {
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

        return sList;
    }
	
	public ArrayList<Integer> getListOfFunctionByUser(int userNo)
    throws Exception {
   		ArrayList<Integer> list = null;

   		Connection conn = null;
   		PreparedStatement pstmt = null;
   		ResultSet rs = null;

   		final String SQL = "select distinct rf.function_no " +
       					   "from   t_app_user u, t_app_role r, t_app_role_function rf " +
       					   "where  u.role_no = r.role_no " +
       					   "and    r.role_no = rf.role_no " +
       					   "and    u.status = 'A' " +
       					   "and    r.status = 'A' " +
       					   "and    rf.status = 'A' " +
       					   "and    u.user_no = ? ";

       try {
       		list = new ArrayList<Integer>();
  
       		conn = sessionFactory.
    				getSessionFactoryOptions().getServiceRegistry().
    				getService(ConnectionProvider.class).getConnection();
       		
       		pstmt = conn.prepareStatement(SQL);
       		pstmt.setInt(1, userNo);

       		rs = pstmt.executeQuery();

       		while (rs.next()) {
       			list.add(new Integer(rs.getInt("function_no")));
           	}

       	} 
       	catch (final Exception e) {
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

       	return list;
    }
	
	public List<Department> getPrivileges(int userNo) throws Exception{
		List<Department> list = null;

	  	Connection conn = null;
	  	PreparedStatement pstmt = null;
	  	ResultSet rs = null;

	  	String SQL =  "select dept_id from t_app_user_add_privileges where user_no = ? ";

	  	try {
	  		list = new ArrayList<Department>();
	  		
	  		conn = sessionFactory.
    				getSessionFactoryOptions().getServiceRegistry().
    				getService(ConnectionProvider.class).getConnection();

	  		pstmt = conn.prepareStatement(SQL);
	  		pstmt.setInt(1, userNo);

	  		rs = pstmt.executeQuery();

	  		while (rs.next()) {
	  			String deptId = rs.getString("dept_id");
	  			list.add(this.departmentServices.getDepartmentByDeptId(deptId));
	         }

	  	}
	  	catch (final Exception e) {
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
	  	
	  	return list;
	}
	
	/**
	 * 
	 * @param filter
	 * @return
	 * @throws Exception
	 */
	public ListUserResponse listOfUser(ListUserFilter filter) 
    throws Exception {
		ListUserResponse resp = null;
		PaginationMetadata metadata = null;
		
    	List<User> list = null;
    	User u = null;
    	
    	int i = 1;
    	int totalRow = 0;
    	
    	Connection conn = null;
	  	PreparedStatement pstmt = null;
	  	ResultSet rs = null;
	  	
	  	String SQL = "";

    	try {
    		resp = new ListUserResponse();
    		metadata = new PaginationMetadata();
    		
    		list = new ArrayList<User>();
    		
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
			
			/*"select user_no as userNo, username, email, password, full_name as FullName, salt, " +
	   		   
			   "from   t_app_user  " + 
			   "order by userNo asc "*/
			
    		SQL += "select u.user_no, u.username, u.full_name, u.password, u.email, " +
 			       "       '' as dept_id, '' as dept_name,  u.role_no, u.role_name, u.salt, u.client_no as clientNo, " +
 			       "       FORMAT(created_dt,'yyyy-MM-dd hh:mm:ss') as createdDt, created_user_no as createdUserNo, " +
 		   		   "       FORMAT(updated_dt,'yyyy-MM-dd hh:mm:ss') as updatedDt, updated_user_no as updatedUserNo, status " +
 				   "	   from   t_app_user u " +   
  				   "	   where  u.status in ('A', 'I') ";
    		
    		if (filter.getFullName() != null && filter.getFullName().trim().length() > 0) {
    			SQL += "and u.full_name like (?) ";
    		}
    		
    		SQL += "    ) c ";
    		SQL += ") result ";    		
    		
    		if (filter.getNoRows() != null && filter.getNoRows().intValue() > 0) {
    			SQL += "where num_ind >= ? ";
    		}
    		else {
    			// nothing
    		}
    		
   
    		conn = sessionFactory.
    				getSessionFactoryOptions().getServiceRegistry().
    				getService(ConnectionProvider.class).getConnection();
    		
      		pstmt = conn.prepareStatement(SQL);
    		
    		if (filter.getFullName() != null && filter.getFullName().trim().length() > 0) {
    			pstmt.setObject(i++, "%"+filter.getFullName().toUpperCase()+"%");
    		}
    		
    		if (filter.getNoRows() != null && filter.getNoRows().intValue() > 0) {
    			pstmt.setObject(i++, filter.getStart().intValue());
    		}
    		
    		rs = pstmt.executeQuery();
    		
    		while (rs.next()) {
            	u = null;
				u = new User();
				
				u.setUserNo(rs.getInt("user_no"));
				u.setUsername(rs.getString("username") == null ? "" : rs.getString("username"));
				u.setFullName(rs.getString("full_name") == null ? "" : rs.getString("full_name"));
				u.setEmail(rs.getString("email") == null ? "" : rs.getString("email"));
				u.setStatus(rs.getString("status") == null ? "" : rs.getString("status"));
				u.setRoleNo(rs.getInt("role_no"));
				u.setRoleName(rs.getString("role_name") == null ? "" : rs.getString("role_name"));
				u.setCreatedDt(rs.getString("createdDt") == null ? "" : rs.getString("createdDt"));
				u.setCreatedUserNo(rs.getInt("createdUserNo"));
				u.setUpdatedDt(rs.getString("updatedDt") == null ? "" : rs.getString("updatedDt"));
				u.setUpdatedUserNo(rs.getInt("updatedUserNo"));
				u.setClientNo(Integer.valueOf(rs.getInt("clientNo")));
				
				if (u != null) {
					list.add(u);
				}
            }
    		
    		// set response
    		resp.setListUser(list);
    		
    		// metadata
    		if (filter.getMetadata() != null && filter.getMetadata().intValue() == 1) {
    			totalRow = 0;
    			totalRow = this.countListOfUser(filter);
    			
    			// get metadata
    			metadata = PaginationUtility.getPaginationMetadata(totalRow, filter.getNoRows(), filter.getStart().intValue());    			
    		}
    		
    		// set response
    		resp.setMetadata(metadata);
    	}
    	catch (Exception e) {
			//LOG.error("Exception at listOfUser");
			//LOG.error(UserService.class, e);
			
			e.printStackTrace();
			System.out.println(e.getMessage());
			
			throw e;
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
	
	/**
	 * 
	 * @param filter
	 * @return
	 * @throws Exception
	 */
	public Integer countListOfUser(ListUserFilter filter) 
    throws Exception {
		int counter = 0;
		
    	int i = 1;
    	
    	Connection conn = null;
	  	PreparedStatement pstmt = null;
	  	ResultSet rs = null;
	  	
	  	String SQL_COUNT = "";
    	
    	try {
    	
    		SQL_COUNT += "SELECT count(1) as counter ";
    		SQL_COUNT += "from ( ";
    		
    		SQL_COUNT += "select u.user_no, u.username, u.full_name, u.password, u.email, " +
  			       "       '' as dept_id, '' as dept_name,  u.role_no, u.role_name, u.salt, u.status " +
  				   "	   from   t_app_user u " +   
   				   "	   where  u.status in ('A', 'I') ";
    		
    		if (filter.getFullName() != null && filter.getFullName().trim().length() > 0) {
    			SQL_COUNT += "and u.full_name like (?) ";
    		}
    		
    		SQL_COUNT += "    ) c ";
    		
    		conn = sessionFactory.
    				getSessionFactoryOptions().getServiceRegistry().
    				getService(ConnectionProvider.class).getConnection();
    		
      		pstmt = conn.prepareStatement(SQL_COUNT);
    		
    		if (filter.getFullName() != null && filter.getFullName().trim().length() > 0) {
    			pstmt.setObject(i++, "%"+filter.getFullName().toUpperCase()+"%");
    		}
    		
    		rs = pstmt.executeQuery();
    		
    		if (rs.next()) {
            	counter = rs.getInt("counter");
            }    		
    	}
    	catch (Exception e) {
			//LOG.error("Exception at listOfUser");
			//LOG.error(UserService.class, e);
			
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
	 * getAppUserById
	 * 
	 * @param userNo
	 * @return
	 * @throws Exception
	 */
	public User getAppUserById(final Integer userNo) 
    throws Exception {
		User user = null;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

       String SQL =  "select u.user_no, u.username, u.full_name, u.password, u.email, " +
			         "       '' as dept_id, '' as dept_name,  u.role_no, u.role_name, u.salt, u.status " +
				     "from   t_app_user u where 1=1 " + 
					 "and    u.user_no = ? ";
			       
       try {
    	   conn = sessionFactory.
   				getSessionFactoryOptions().getServiceRegistry().
   				getService(ConnectionProvider.class).getConnection();

           pstmt = conn.prepareStatement(SQL);
           pstmt.setInt(1, userNo);

           rs = pstmt.executeQuery();

           if (rs.next()) {        	   
        	    user = new User();
                user.setUserNo(Integer.valueOf(rs.getInt("user_no")));
	      		user.setUsername(rs.getString("username") == null ? "" : rs.getString("username"));
	      		user.setFullName(rs.getString("full_name") == null ? "" : rs.getString("full_name"));
	   			user.setPassword((rs.getString("password") == null) ? "" : rs.getString("password"));
	   			user.setEmail(rs.getString("email") == null ? "" : rs.getString("email"));
	   			user.setDeptId(rs.getString("dept_id") == null ? "" : rs.getString("dept_id"));
	   			user.setDeptName(rs.getString("dept_name") == null ? "" : rs.getString("dept_name"));
	   			user.setRoleNo(Integer.valueOf(rs.getInt("role_no")));
	   			user.setRoleName(rs.getString("role_name") == null ? "" : rs.getString("role_name"));
		        user.setSalt((rs.getString("salt") == null) ? "" : rs.getString("salt"));
		        user.setFunctions(getListOfFunctionByUser(user.getUserNo().intValue()));
		        user.setClientNo(Integer.valueOf(rs.getInt("clientNo")));
		        
               return user;
           }
           
       } 
       catch (final Exception e) {
           //LOG.error("Exception at getUserByIdSimple");
           //LOG.error(UserService.class, e);
    	   e.printStackTrace();			
			
           throw e;
       } 
       finally {
           // clean up
           try {
               DatabaseUtility.clear(conn, pstmt, rs);
           } catch (final Exception ex) {
               // do nothing
           }
       }
       
       return null;
	}
    
    public List<UserRole> getListOfRole(String param){
		List<UserRole> list = null;
		
		String SQL = "select CAST(role_no AS int) as rNo, role_name as rName, status as status "
				+ "from   t_app_role  ";
		
		try {
			Session session = sessionFactory.getCurrentSession();
			
			
			list = session.createSQLQuery(SQL)
					.setResultTransformer(Transformers.aliasToBean(UserRole.class))
					.list();
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		finally {
			// clean up
		}
		
		return list;
	}
    
    public List<User> getAssignedUsers(final int roleNo) throws Exception {
        List<User> list = null;
        User user = null;

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

           
        
        String SQL ="select u.user_no, u.username, u.full_name, u.password, u.email, " +
	     	       "       '' as dept_id, '' as dept_name,  u.role_no, u.role_name, u.salt, u.client_no as clientNo," +
	    	       "       FORMAT(u.created_dt,'yyyy-MM-dd hh:mm:ss') as createdDt, u.created_user_no as createdUserNo, " +
	     		   "       FORMAT(u.updated_dt,'yyyy-MM-dd hh:mm:ss') as updatedDt, u.updated_user_no as updatedUserNo, u.status " +
	    		   "	   from   t_app_user u " +
			       " INNER JOIN t_app_user_role ur on u.user_no = ur.user_no " +
				   " WHERE    ur.role_no = ? " + 
				   " and    ur.status = 'A'";

        try {
            list = new ArrayList<User>();
            conn = sessionFactory.
    				getSessionFactoryOptions().getServiceRegistry().
    				getService(ConnectionProvider.class).getConnection();

            pstmt = conn.prepareStatement(SQL);
            pstmt.setInt(1, roleNo);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                user = null;
                user = new User();
                
                user.setUserNo(Integer.valueOf(rs.getInt("user_no")));
           		user.setUsername(rs.getString("login_id") == null ? "" : rs.getString("login_id"));
           		user.setFullName(rs.getString("full_name") == null ? "" : rs.getString("full_name"));
	   			user.setEmail(rs.getString("email") == null ? "" : rs.getString("email"));
	   			user.setDeptId(rs.getString("dept_id") == null ? "" : rs.getString("dept_id"));
	   			user.setDeptName(rs.getString("dept_name") == null ? "" : rs.getString("dept_name"));
	   			user.setRoleNo(Integer.valueOf(rs.getInt("role_no")));	
	   			user.setRoleName(rs.getString("role_name") == null ? "" : rs.getString("role_name"));	
	   			user.setClientNo(Integer.valueOf(rs.getInt("clientNo")));
                // set roles
    			user.setRoles(getRolesByUser(user.getUserNo().intValue()));

    			// set functions
    			user.setFunctions(getListOfFunctionByUser(user.getUserNo().intValue()));
    			    		
    			
                if (user != null) {
                    list.add(user);
                }
            }

        } 
        catch (final Exception e) {

            throw e;
        } 
        finally {
            // clean up
            user = null;

            try {
                DatabaseUtility.clear(conn, pstmt, rs);
            } catch (final Exception ex) {
                // do nothing
            }
        }

        return list;
    }

    /**
    *
    * @param roleNo
    * @return
    * @throws Exception
    */
    public List<User> getListOfUserByRoleLight(int roleNo) 
    throws Exception {
   	 List<User> list = null;
   	 User user = null;

   	 Connection conn = null;
   	 PreparedStatement pstmt = null;
   	 ResultSet rs = null;

   	String SQL = "select u.user_no, u.username, u.full_name, u.password, u.email, " +
  	       "       '' as dept_id, '' as dept_name,  u.role_no, u.role_name, u.salt, u.client_no as clientNo, " +
 	       "       FORMAT(u.created_dt,'yyyy-MM-dd hh:mm:ss') as createdDt, u.created_user_no as createdUserNo, " +
  		   "       FORMAT(u.updated_dt,'yyyy-MM-dd hh:mm:ss') as updatedDt, u.updated_user_no as updatedUserNo, u.status " +
 		   "	   from   t_app_user u " +
		   "	   where  ppl.EMPLID = u.user_no " + 
		   "	   and    u.role_no = ? " + 
		   "	   and    u.status = 'A'";
       
   	 try {
   		 list = new ArrayList<User>();
   		conn = sessionFactory.
				getSessionFactoryOptions().getServiceRegistry().
				getService(ConnectionProvider.class).getConnection();

   		 pstmt = conn.prepareStatement(SQL);
   		 pstmt.setInt(1, roleNo);

   		 rs = pstmt.executeQuery();

   		 while (rs.next()) {
   			 user = null;
   			 user = new User();
   			 
        		user.setUsername(rs.getString("login_id") == null ? "" : rs.getString("login_id"));
        		user.setFullName(rs.getString("full_name") == null ? "" : rs.getString("full_name"));
	   			user.setEmail(rs.getString("email") == null ? "" : rs.getString("email"));
	   			user.setDeptId(rs.getString("dept_id") == null ? "" : rs.getString("dept_id"));
	   			user.setDeptName(rs.getString("dept_name") == null ? "" : rs.getString("dept_name"));
	   			user.setRoleNo(Integer.valueOf(rs.getInt("role_no")));	
	   			user.setRoleName(rs.getString("role_name") == null ? "" : rs.getString("role_name"));	
	   			user.setClientNo(Integer.valueOf(rs.getInt("clientNo")));	
	   			user.setStatus(rs.getString("status") == null ? "" : rs.getString("status"));	

	   			 if (user != null) {
	   				 list.add(user);
	   			 }
   		 }

   	 } 
   	 catch (final Exception e) {

   		 throw e;
   	 } 
   	 finally {
   		 // clean up
   		 user = null;
   		 
   		 try {
   			 DatabaseUtility.clear(conn, pstmt, rs);
   		 } 
   		 catch (final Exception ex) {
              // do nothing
   		 }
   	 }
   	 
   	 return list;
    }
	
}
