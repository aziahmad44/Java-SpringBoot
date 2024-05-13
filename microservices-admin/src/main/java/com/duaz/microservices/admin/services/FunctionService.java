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

import com.duaz.microservices.admin.model.Function;
import com.duaz.microservices.admin.utility.DatabaseUtility;

@Transactional
@Repository
public class FunctionService {

	@Autowired
	SessionFactory sessionFactory;
	
    @Autowired
    @Qualifier("dbNonHibernate.datasource")
    private DataSource ds;
    
    /**
    *
    * @return
    * @throws Exception
    */
   public List<Function> getListOfFunction(int isEngagement) throws Exception {
       List<Function> list = null;
       Function function = null;

       Connection conn = null;
       PreparedStatement pstmt = null;
       ResultSet rs = null;

       final String SQL = "select function_no, function_name, is_engagement  " + 
       				   "from   t_app_function  " + 
       				   "where  status = 'A' AND is_engagement = ?" +
       				   "order by function_name asc ";

       try {
           list = new ArrayList<Function>();

           conn = sessionFactory.
   				getSessionFactoryOptions().getServiceRegistry().
   				getService(ConnectionProvider.class).getConnection();
           
           pstmt = conn.prepareStatement(SQL);
           pstmt.setInt(1, isEngagement);
           rs = pstmt.executeQuery();

           while (rs.next()) {
               function = null;
               function = new Function();

               function.setFunctionNo(Integer.valueOf(rs.getInt("function_no")));
               function.setFunctionName(rs.getString("function_name") == null ? "" : rs.getString("function_name"));
               function.setIsEngagement(Integer.valueOf(rs.getInt("is_engagement")));
               
               list.add(function);
           }

       } catch (final Exception e) {

           throw e;
       } finally {
           // clean up
           function = null;

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
   * @return
   * @throws Exception
   */
  public List<Function> getAssignedFunction(final int roleNo) throws Exception {
      List<Function> list = null;
      Function function = null;

      Connection conn = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;

      final String SQL = "select f.function_no, f.function_name  "+
      						"from  t_app_function f left join t_app_role_function rf on f.function_no=rf.function_no WHERE rf.role_no =? order by function_name asc ";

      try {
          list = new ArrayList<Function>();

          conn = sessionFactory.
  				getSessionFactoryOptions().getServiceRegistry().
  				getService(ConnectionProvider.class).getConnection();
          
          pstmt = conn.prepareStatement(SQL);
          pstmt.setInt(1, roleNo);

          rs = pstmt.executeQuery();

          while (rs.next()) {
              function = null;
              function = new Function();

              function.setFunctionNo(Integer.valueOf(rs.getInt("function_no")));
              function.setFunctionName(rs.getString("function_name") == null ? "" : rs.getString("function_name"));

              list.add(function);
          }

      } catch (final Exception e) {

          throw e;
      } finally {
          // clean up
          function = null;

          try {
              DatabaseUtility.clear(conn, pstmt, rs);
          } catch (final Exception ex) {
              // do nothing
          }
      }

      return list;
  }
  
}
