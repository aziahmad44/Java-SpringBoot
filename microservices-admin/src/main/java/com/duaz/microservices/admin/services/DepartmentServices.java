package com.duaz.microservices.admin.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.hibernate.internal.SessionImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.duaz.microservices.admin.model.department.Department;
import com.duaz.microservices.admin.utility.DatabaseUtility;

@Transactional
@Repository
public class DepartmentServices {

	@Autowired
	SessionFactory sessionFactory;
	
	public Department getDepartmentByDeptId(String deptId) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		Department d = null;
		
		final String SQL = " select -1 as dept_no, DEPTID as dept_id, DEPT_DESCR as dept_name, LEVEL_ID as level, LEVEL_DESCR, LOCATION, LOCATION_DESCR, " + 
				   "       MANAGER_ID, MANAGER_NAME, Level03_ID, Level03_NAME " + 
				   " from   AMS_PPL.dbo.PPLMAPMasterDepartment_TM " + 
				   " where DEPTID = ? ";
		
		try {
			conn = ((SessionImpl) this.sessionFactory.getCurrentSession()).connection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, deptId);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				d = null;
				d = new Department();
				
				d.setDeptNo(rs.getInt("dept_no"));
				d.setDeptId(rs.getString("dept_id") == null ? "" : rs.getString("dept_id"));
				d.setDeptName(rs.getString("dept_name") == null ? "" : rs.getString("dept_name"));
				d.setLevel(rs.getInt("level"));
				//d.setParentDeptNo(rs.getInt("parent_dept_no"));
				//d.setParentDeptId(rs.getString("parent_dept_id") == null ? "" : rs.getString("parent_dept_id"));
				
				if (d.getLevel().intValue() == 0) {
					d.setParentDeptId("");
				}
				else {
					d.setParentDeptId(this.getParentDeptIdOfDept(d.getDeptId().trim(), d.getLevel().intValue()));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		} finally {
			// clean up
			try {
				DatabaseUtility.clear(conn, pstmt, rs);
			} catch (final Exception ex) {
				// do nothing
			}
		}

		return d;
	}
	
	public String getParentDeptIdOfDept(String deptId, int levelId)
	throws Exception {
		String parentDeptId = "";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		int levelIdTmp = 0;
		int counter = 0;
		
		String SQL = "select * " + 
					 "from   AMS_PPL.dbo.PPLMAPMasterDepartment_TM " + 
					 "where  DEPTID = ? ";
		
		try {
			levelIdTmp = levelId;
			
			conn = ((SessionImpl) this.sessionFactory.getCurrentSession()).connection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, deptId);

			rs = pstmt.executeQuery();

			if (rs.next()) {				
				parentDeptId = rs.getObject("Level0" + (levelId - 1) + "_ID") == null ? null : rs.getString("level0" + (levelId - 1) + "_id");	
				
				// loop 
				while ((parentDeptId == null || parentDeptId.trim().length() == 0) && levelIdTmp >= 0) {
					// keep reducing
					levelIdTmp--;
					
					parentDeptId = rs.getObject("level0" + (levelIdTmp) + "_id") == null ? null : rs.getString("level0" + (levelIdTmp) + "_id");
					
					counter++;
					
					if (counter == 100) {
						// shortcut exist
						break;
					}
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
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
		
		return parentDeptId;
	}
}
