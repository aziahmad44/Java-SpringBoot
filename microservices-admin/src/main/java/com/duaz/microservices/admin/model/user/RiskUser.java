package com.duaz.microservices.admin.model.user;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.duaz.microservices.admin.model.department.Department;

public class RiskUser implements UserDetails {
  
  private static final long serialVersionUID = 1L;
  
  private Integer userNo;
  
  private Integer roleNo;
  
  private String username;
  
  private String roleName;
  
  private String fullName;
  
  private String reportsToName;
  
  private String email;
  
  private String status;
  
  private String deptId;
  
  private String deptName;
  
  private String costCenter;
  
  private String password;
  
  private String userLevel;
  
  private Integer excludePrivilege;
  
  private ArrayList<String> roles;
  
  private ArrayList<Integer> functions;
  
  private List<Department> additionalPrivileges;
  
  private Integer reportTo;
  
  private Integer positionNo;
  
  private String suspendFlag;
  
  private String changePassword;
  
  private String salt;
  
  private String workflowName;
  
  private Integer wtNo;
  
  private String delegateFlag;
  
  private String positionName;
  
  public Collection<SimpleGrantedAuthority> getAuthorities() {
    List<SimpleGrantedAuthority> grantedAuthorities = new ArrayList<SimpleGrantedAuthority>();
    if (this.roles != null)
      for (String role : this.roles)
        grantedAuthorities.add(new SimpleGrantedAuthority(role));  
    return grantedAuthorities;
  }
  
  public String getPassword() {
    return this.password;
  }
  
  public String getUsername() {
    return this.username;
  }
  
  public boolean isAccountNonExpired() {
    return true;
  }
  
  public boolean isAccountNonLocked() {
    return true;
  }
  
  public boolean isCredentialsNonExpired() {
    return true;
  }
  
  public boolean isEnabled() {
    return true;
  }
  
  public boolean isUserAdmin() throws Exception {
    boolean is = false;
    try {
      for (String role : this.roles) {
        if (role != null && role.equalsIgnoreCase("USER_ADMIN")) {
          is = true;
          break;
        } 
      } 
    } catch (Exception e) {
      throw e;
    } finally {}
    return is;
  }
  
  public boolean hasAccessToFunction(int functionNo) throws Exception {
    boolean is = false;
    try {
      for (Integer i : this.functions) {
        if (i != null && i.intValue() == functionNo) {
          is = true;
          break;
        } 
      } 
    } catch (Exception e) {
      throw e;
    } finally {}
    return is;
  }
  
  public Integer getUserNo() {
    return this.userNo;
  }
  
  public void setUserNo(Integer userNo) {
    this.userNo = userNo;
  }
  
  public Integer getRoleNo() {
    return this.roleNo;
  }
  
  public void setRoleNo(Integer roleNo) {
    this.roleNo = roleNo;
  }
  
  public String getDeptId() {
    return this.deptId;
  }
  
  public void setDeptId(String deptId) {
    this.deptId = deptId;
  }
  
  public String getDeptName() {
    return this.deptName;
  }
  
  public void setDeptName(String deptName) {
    this.deptName = deptName;
  }
  
  public String getCostCenter() {
    return this.costCenter;
  }
  
  public void setCostCenter(String costCenter) {
    this.costCenter = costCenter;
  }
  
  public String getEmail() {
    return this.email;
  }
  
  public void setEmail(String email) {
    this.email = email;
  }
  
  public String getStatus() {
    return this.status;
  }
  
  public void setStatus(String status) {
    this.status = status;
  }
  
  public String getRoleName() {
    return this.roleName;
  }
  
  public void setRoleName(String roleName) {
    this.roleName = roleName;
  }
  
  public String getUserLevel() {
    return this.userLevel;
  }
  
  public void setUserLevel(String userLevel) {
    this.userLevel = userLevel;
  }
  
  public Integer getExcludePrivilege() {
    return this.excludePrivilege;
  }
  
  public void setExcludePrivilege(Integer excludePrivilege) {
    this.excludePrivilege = excludePrivilege;
  }
  
  public ArrayList<String> getRoles() {
    return this.roles;
  }
  
  public void setRoles(ArrayList<String> roles) {
    this.roles = roles;
  }
  
  public ArrayList<Integer> getFunctions() {
    return this.functions;
  }
  
  public void setFunctions(ArrayList<Integer> functions) {
    this.functions = functions;
  }
  
  public List<Department> getAdditionalPrivileges() {
    return this.additionalPrivileges;
  }
  
  public void setAdditionalPrivileges(List<Department> additionalPrivileges) {
    this.additionalPrivileges = additionalPrivileges;
  }
  
  public String getReportsToName() {
    return this.reportsToName;
  }
  
  public void setReportsToName(String reportsToName) {
    this.reportsToName = reportsToName;
  }
  
  public Integer getPositionNo() {
    return this.positionNo;
  }
  
  public void setPositionNo(Integer positionNo) {
    this.positionNo = positionNo;
  }
  
  public String getWorkflowName() {
    return this.workflowName;
  }
  
  public void setWorkflowName(String workflowName) {
    this.workflowName = workflowName;
  }
  
  public Integer getWtNo() {
    return this.wtNo;
  }
  
  public void setWtNo(Integer wtNo) {
    this.wtNo = wtNo;
  }
  
  public String getDelegateFlag() {
    return this.delegateFlag;
  }
  
  public void setDelegateFlag(String delegateFlag) {
    this.delegateFlag = delegateFlag;
  }
  
  public String getPositionName() {
    return this.positionName;
  }
  
  public void setPositionName(String positionName) {
    this.positionName = positionName;
  }
  
  public void setUsername(String username) {
    this.username = username;
  }
  
  public void setPassword(String password) {
    this.password = password;
  }
  
	public String getFullName() {
		return fullName;
	}
	
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	public Integer getReportTo() {
    return this.reportTo;
  }
  
  public void setReportTo(Integer reportTo) {
    this.reportTo = reportTo;
  }
  
  public String getSuspendFlag() {
    return this.suspendFlag;
  }
  
  public void setSuspendFlag(String suspendFlag) {
    this.suspendFlag = suspendFlag;
  }
  
  public String getChangePassword() {
    return this.changePassword;
  }
  
  public void setChangePassword(String changePassword) {
    this.changePassword = changePassword;
  }
  
  public String getSalt() {
    return this.salt;
  }
  
  public void setSalt(String salt) {
    this.salt = salt;
  }
}
