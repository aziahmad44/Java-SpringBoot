package com.duaz.microservices.admin.services;

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

import com.duaz.microservices.admin.constant.RIConstants;
import com.duaz.microservices.admin.model.role.RoleFilter;
import com.duaz.microservices.admin.model.user.UserRole;
import com.duaz.microservices.admin.utility.DatabaseUtility;

@Transactional
@Repository
public class RoleService {

	@Autowired
	SessionFactory sessionFactory;
	
    @Autowired
    @Qualifier("dbNonHibernate.datasource")
    private DataSource ds;
    
    @Autowired
    private UserServices userService;

    /**
     * 
     * @param users
     * @param ljks
     * @param functions
     * @param profilers
     * @param roleNo
     * @param userNo
     * @throws Exception
     */
    public void updateRoleOld(final List<Integer> users, final List<Integer> ljks, final List<Integer> functions, final List<String> profilers, final Integer roleNo, final int userNo) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
        	conn = sessionFactory.
    				getSessionFactoryOptions().getServiceRegistry().
    				getService(ConnectionProvider.class).getConnection();
        	
            conn.setAutoCommit(false);

            if (users != null) {
                pstmt = conn.prepareStatement("update t_app_user set role_no = -1 where role_no = ?");
                pstmt.setInt(1, roleNo);
                pstmt.execute();
                
                // clear
                DatabaseUtility.clear(pstmt);
                
                pstmt = conn.prepareStatement("update t_app_user set role_no = ? where user_no = ?");
                for (final Integer u : users) {
                    pstmt.setInt(1, roleNo);
                    pstmt.setInt(2, u.intValue());
                    pstmt.addBatch();
                }
                pstmt.executeBatch();
            }
            else {
            	// user is reset
            	pstmt = conn.prepareStatement("update t_app_user set role_no = -1 where role_no = ?");
                pstmt.setInt(1, roleNo);
                pstmt.execute();
            }

            // clear
            DatabaseUtility.clear(pstmt);
            
            pstmt = conn.prepareStatement("delete from t_app_role_ljk where role_no = ?");
            pstmt.setInt(1, roleNo);
            pstmt.execute();

            if (ljks != null) {
            	// clear
                DatabaseUtility.clear(pstmt);
                
                pstmt = conn.prepareStatement("insert into t_app_role_ljk(role_no, ljk_no, status, cre_dt, cre_user_no) values(?, ?, ?, current_timestamp, ?)");
                
                for (final Integer ljk : ljks) {
                    pstmt.setInt(1, roleNo);
                    pstmt.setInt(2, ljk);
                    pstmt.setString(3, "A");
                    pstmt.setInt(4, userNo);
                    pstmt.addBatch();
                }
                
                pstmt.executeBatch();
            }

            // clear
            DatabaseUtility.clear(pstmt);
            
            pstmt = conn.prepareStatement("delete from t_app_role_function where role_no = ?");
            pstmt.setInt(1, roleNo);
            pstmt.execute();

            if (functions != null) {
            	// clear
                DatabaseUtility.clear(pstmt);
                
                pstmt = conn.prepareStatement("insert into t_app_role_function(role_no, function_no, status, cre_dt, cre_user_no) values(?, ?, ?, current_timestamp, ?)");
                
                for (final Integer function : functions) {
                    pstmt.setInt(1, roleNo);
                    pstmt.setInt(2, function);
                    pstmt.setString(3, "A");
                    pstmt.setInt(4, userNo);
                    pstmt.addBatch();
                }
                
                pstmt.executeBatch();
            }

            // clear
            DatabaseUtility.clear(pstmt);
            
            pstmt = conn.prepareStatement("delete from t_app_role_risk_profile where role_no = ?");
            pstmt.setInt(1, roleNo);
            pstmt.execute();

            if (profilers != null) {
            	// clear
                DatabaseUtility.clear(pstmt);
                
                pstmt = conn.prepareStatement("insert into t_app_role_risk_profile(role_no, risk_profile, status, cre_dt, cre_user_no) values(?, ?, ?, current_timestamp, ?)");
                
                for (final String profile : profilers) {
                    pstmt.setInt(1, roleNo);
                    pstmt.setString(2, profile);
                    pstmt.setString(3, "A");
                    pstmt.setInt(4, userNo);
                    pstmt.addBatch();
                }
                
                pstmt.executeBatch();
            }

            // commit
            conn.commit();

        } 
        catch (final Exception e) {
            conn.rollback();

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
    }
    
    public boolean updateRole(final List<Integer> users,  final List<Integer> functions, final Integer roleNo, final int userNo, final Integer roleLevel) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean result = true;
        
        try {
        	conn = sessionFactory.
    				getSessionFactoryOptions().getServiceRegistry().
    				getService(ConnectionProvider.class).getConnection();
        	
            conn.setAutoCommit(false);

            if (users != null) {
                pstmt = conn.prepareStatement("delete from t_app_user_role where role_no = ?");
                pstmt.setInt(1, roleNo);
                pstmt.execute();
                
                // clear
                DatabaseUtility.clear(pstmt);
                
                pstmt = conn.prepareStatement("insert into t_app_user_role (user_no, role_no, status, cre_dt, cre_user_no) values (?, ?, 'A', getdate(), ?)");
                for (final Integer u : users) {
                    pstmt.setInt(1, u.intValue());
                    pstmt.setInt(2, roleNo);
                    pstmt.setInt(3, userNo);
                    pstmt.addBatch();
                }
                pstmt.executeBatch();
            }
            else {
            	// user is reset
            	pstmt = conn.prepareStatement("delete from t_app_user_role where role_no = ?");
                pstmt.setInt(1, roleNo);
                pstmt.execute();
            }


            // clear
            DatabaseUtility.clear(pstmt);
            
            pstmt = conn.prepareStatement("delete from t_app_role_function where role_no = ?");
            pstmt.setInt(1, roleNo);
            pstmt.execute();

            if (functions != null) {
            	// clear
                DatabaseUtility.clear(pstmt);
                
                pstmt = conn.prepareStatement("insert into t_app_role_function(role_no, function_no, status, cre_dt, cre_user_no) values(?, ?, ?, current_timestamp, ?)");
                
                for (final Integer function : functions) {
                    pstmt.setInt(1, roleNo);
                    pstmt.setInt(2, function);
                    pstmt.setString(3, "A");
                    pstmt.setInt(4, userNo);
                    pstmt.addBatch();
                }
                
                pstmt.executeBatch();
            }
            
            if(roleLevel != null && roleLevel > 0) {
            	// clear
                DatabaseUtility.clear(pstmt);
                String sql = "UPDATE t_app_role SET "
                		+ "role_level = ?, "
                		+ "upd_dt = CURRENT_TIMESTAMP, "
                		+ "upd_user_no = ? "
                		+ "WHERE role_no = ?";
                
                pstmt = conn.prepareStatement(sql);
                
                pstmt.setInt(1, roleLevel);
                pstmt.setInt(2, userNo);
                pstmt.setInt(3, roleNo);
                pstmt.execute();
            }

            // commit
            conn.commit();

        } 
        catch (final Exception e) {
            conn.rollback();
            result = false;
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
        
        return result;
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
        	
            pstmt = conn.prepareStatement("SELECT role_no, role_name, role_level, status, is_engagement, role_level FROM  t_app_role r where role_no = ?");
            pstmt.setInt(1, roleNo);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                userRole = new UserRole();
                userRole.setrNo(rs.getInt("role_no"));
                userRole.setrName(rs.getString("role_name"));
                userRole.setRoleLevel(rs.getInt("role_level"));
                userRole.setStatus(rs.getString("status"));
                userRole.setIsEngagement(rs.getInt("is_engagement"));
                userRole.setRoleLevel(rs.getInt("role_level"));
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

    /**
     *
     * @param roleFilter
     * @return
     * @throws Exception
     */
    public List<UserRole> list(final RoleFilter roleFilter) throws Exception {
        List<UserRole> list = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

       // String SQL = "SELECT * FROM (SELECT ROW_NUMBER() OVER(ORDER BY role_no) AS rowNum, role_no, role_name, status " +
        		//"FROM  t_app_role ";
        String SQL = "SELECT * FROM (SELECT ROW_NUMBER() OVER(ORDER BY r.role_no) AS rowNum, r.role_no, r.role_name, r.status, r.is_engagement, r.role_level " +
        		 //"(select COUNT (1) from t_app_user_role where role_no = r.role_no) as counter_user " +
        		 " FROM  t_app_role r where 1=1 ";
        
        if (roleFilter.getRoleName()!=null) {
			SQL+= " and (r.role_name LIKE ?) ";
		}
        
        
        
        SQL+=") AS result WHERE rowNum >= ?  ORDER BY rowNum";
        
        try {        	
            list = new ArrayList<UserRole>();
            
            conn = sessionFactory.
    				getSessionFactoryOptions().getServiceRegistry().
    				getService(ConnectionProvider.class).getConnection();
            
            pstmt = conn.prepareStatement(SQL);
            int i = 1;
            
            if (roleFilter.getRoleName() != null) {
				pstmt.setObject(i++, "%" + roleFilter.getRoleName().toUpperCase()+"%");
			}
            
            pstmt.setObject(i++, roleFilter.getStart());

            rs = pstmt.executeQuery();

            while (rs.next()) {
                final UserRole r = new UserRole();
                r.setrNo(rs.getInt("role_no"));
                r.setrName(rs.getString("role_name"));
                r.setStatus(rs.getString("status"));
                r.setIsEngagement(rs.getInt("is_engagement"));
                r.setRoleLevel(rs.getInt("role_level"));
                //r.setCounterUser(rs.getInt("counter_user"));
                r.setListUser(this.userService.getAssignedUsers(r.getrNo().intValue()));
                
                list.add(r);
            }

        } 
        catch (final Exception e) {

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
    
    public List<UserRole> listRoleEngagement() throws Exception {
        List<UserRole> list = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

       // String SQL = "SELECT * FROM (SELECT ROW_NUMBER() OVER(ORDER BY role_no) AS rowNum, role_no, role_name, status " +
        		//"FROM  t_app_role ";
        String SQL = "SELECT * FROM (SELECT ROW_NUMBER() OVER(ORDER BY r.role_no) AS rowNum, r.role_no, r.role_name, r.status, r.is_engagement " +
        		 //"(select COUNT (1) from t_app_user_role where role_no = r.role_no) as counter_user " +
        		 " FROM  t_app_role r where 1=1 and r.is_engagement = 1";
        
        
        SQL+=") AS result ORDER BY rowNum";
        
        try {        	
            list = new ArrayList<UserRole>();
            
            conn = sessionFactory.
    				getSessionFactoryOptions().getServiceRegistry().
    				getService(ConnectionProvider.class).getConnection();
            
            pstmt = conn.prepareStatement(SQL);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                final UserRole r = new UserRole();
                r.setrNo(rs.getInt("role_no"));
                r.setrName(rs.getString("role_name"));
                r.setStatus(rs.getString("status"));
                r.setIsEngagement(rs.getInt("is_engagement"));
                
                list.add(r);
            }

        } 
        catch (final Exception e) {

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
    
    public Integer getCount (RoleFilter f) throws Exception {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String SQL = "SELECT COUNT(*) FROM  t_app_role r where 1=1 "; 
		
		if (f.getRoleName()!=null){
			SQL+="and (role_name LIKE ?)";
		}
		
		if(f.getUsers()!=null && !f.getUsers().equals("")) {
			SQL+=" and role_no in (select u.role_no  FROM tugu_user.dbo.v_ri_user_master ppl,t_app_user_role u WHERE ppl.user_no = u.user_no and r.role_no = u.role_no and ppl.full_name LIKE ? )";
		}
		
		try {
			
			conn = sessionFactory.
    				getSessionFactoryOptions().getServiceRegistry().
    				getService(ConnectionProvider.class).getConnection();
			
			pstmt = conn.prepareStatement(SQL);
			int i = 1;
			
			if (f.getRoleName()!=null){
				pstmt.setObject(i++, "%" + f.getRoleName()+"%");
			}
			
			if(f.getUsers() !=null && !f.getUsers().equals("")) {
            	pstmt.setObject(i++, "%" + f.getUsers().toUpperCase()+"%");
            }
		
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
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

		return null;
	} 
    
    public List<UserRole> listNonRoleEngagement() throws Exception {
        List<UserRole> list = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

       // String SQL = "SELECT * FROM (SELECT ROW_NUMBER() OVER(ORDER BY role_no) AS rowNum, role_no, role_name, status " +
        		//"FROM  t_app_role ";
        String SQL = "SELECT role_no, role_name "
        		+ "FROM t_app_role "
        		+ "WHERE is_engagement = 0";
        
        try {        	
            list = new ArrayList<UserRole>();
            
            conn = sessionFactory.
    				getSessionFactoryOptions().getServiceRegistry().
    				getService(ConnectionProvider.class).getConnection();
            
            pstmt = conn.prepareStatement(SQL);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                final UserRole r = new UserRole();
                r.setrNo(rs.getInt("role_no"));
                r.setrName(rs.getString("role_name"));
                
                list.add(r);
            }

        } 
        catch (final Exception e) {

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
     * @param roleName
     * @param creUserNo
     * @throws Exception
     */
    public boolean insertRole(UserRole request)
    throws Exception {
    	boolean result = false;
    	Connection conn = null;
        PreparedStatement pstmt = null;

        final String SQL = "insert into t_app_role " +
        				   "(role_name, status, cre_dt, cre_user_no, is_engagement) " +
        				   "values " +
        				   "(?, 'A', getdate(), ?, ?) ";
        
       
        try {
        	conn = sessionFactory.
    				getSessionFactoryOptions().getServiceRegistry().
    				getService(ConnectionProvider.class).getConnection();
        	
            pstmt = conn.prepareStatement(SQL);

            pstmt.setString(1, request.getrName());
            pstmt.setInt(2, 0);
            pstmt.setInt(3, request.getIsEngagement());

            pstmt.executeUpdate();
            
            result = true;
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
        
        return result;
    }
    
    /**
   	 * 
   	 * @param rNo
   	 * @throws Exception
   	 */
   	public boolean deleteRole(int rNo) throws Exception {
   		Connection conn = null;
   		PreparedStatement pstmt = null;
   		ResultSet rs = null;
   		boolean result = false;

   		final String SQL = "DELETE FROM t_app_role " +
   						   "WHERE  role_no = ?";

   		try {

   			conn = sessionFactory.
    				getSessionFactoryOptions().getServiceRegistry().
    				getService(ConnectionProvider.class).getConnection();
   			
   			pstmt = conn.prepareStatement(SQL);
   			pstmt.setObject(1, rNo);

   			pstmt.executeUpdate();
   			
   			result = true;

   		} catch (final Exception e) {
   			throw e;
   		} finally {
   			try {
   				DatabaseUtility.clear(conn, pstmt, rs);
   			} catch (final Exception ex) {
   				// do nothing
   			}
   		}
   		
   		return result;
   	}
    
    public List<UserRole> getListRole(final RoleFilter roleFilter) throws Exception {
        List<UserRole> list = null;

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String SQL = "select * from t_app_role r where 1=1";
        try {        	
            list = new ArrayList<UserRole>();
            
            if (roleFilter.getRoleName()!=null && roleFilter.getRoleName().trim().length() > 0) {
    			SQL+= " and upper(r.role_name) LIKE ? ";
    		}
            
            if(roleFilter.getUsers() !=null && !roleFilter.getUsers().equals("")) {
            	SQL+=" and r.role_no in (select u.role_no  FROM tugu_user.dbo.v_ri_user_master ppl,t_app_user_role u WHERE ppl.user_no = u.user_no and r.role_no = u.role_no and upper(ppl.full_name) LIKE ? )";
            }
            
            conn = sessionFactory.
    				getSessionFactoryOptions().getServiceRegistry().
    				getService(ConnectionProvider.class).getConnection();
            
            pstmt = conn.prepareStatement(SQL);
            
            int i = 1;
            
            if (roleFilter.getRoleName()!=null && roleFilter.getRoleName().trim().length() > 0 ) {
				pstmt.setObject(i++, "%" + roleFilter.getRoleName().toUpperCase()+"%");
			}
            
            if(roleFilter.getUsers() !=null && !roleFilter.getUsers().equals("")) {
            	pstmt.setObject(i++, "%" + roleFilter.getUsers().toUpperCase()+"%");
            }

            rs = pstmt.executeQuery();

            while (rs.next()) {
                final UserRole r = new UserRole();
                r.setrNo(rs.getInt("role_no"));
                r.setrName(rs.getString("role_name"));
                r.setRoleLevel(rs.getInt("role_level"));
                
                if(rs.getString("status").equals(RIConstants.STATUS_ACTIVE)) {
                	r.setStatus("Active");
                	
                } else {
                	r.setStatus("Inactive");
                	
                }
                
                r.setListUser(this.userService.getAssignedUsers(r.getrNo().intValue()));
                
                list.add(r);
            }

        } 
        catch (final Exception e) {

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
     * @param roleLevel
     * @return
     * @throws Exception
     */
    public List<UserRole> getListHigherEngagementRole(int roleLevel) 
    throws Exception {
        List<UserRole> list = null;
        UserRole r = null;
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String SQL = "select * from t_app_role r " + 
        			 "where  is_engagement = 1 " + 
        		     "and    status = 'A' " + 
        			 "and    role_level is not null and role_level > ?";
        
        try {        	
            list = new ArrayList<UserRole>();
            
            conn = sessionFactory.
    				getSessionFactoryOptions().getServiceRegistry().
    				getService(ConnectionProvider.class).getConnection();
            
            pstmt = conn.prepareStatement(SQL);
            pstmt.setObject(1, roleLevel);
            
            rs = pstmt.executeQuery();

            while (rs.next()) {
            	r = null;
                r = new UserRole();
                
                r.setrNo(rs.getInt("role_no"));
                r.setrName(rs.getString("role_name"));
                r.setRoleLevel(rs.getInt("role_level"));
                
                if(rs.getString("status").equals(RIConstants.STATUS_ACTIVE)) {
                	r.setStatus("Active");
                	
                } else {
                	r.setStatus("Inactive");
                	
                }
                
                r.setListUser(this.userService.getListOfUserByRoleLight(r.getrNo().intValue()));
                
                list.add(r);
            }

        } 
        catch (final Exception e) {

            throw e;
        } 
        finally {
            // clean up
        	r = null;
        	
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
