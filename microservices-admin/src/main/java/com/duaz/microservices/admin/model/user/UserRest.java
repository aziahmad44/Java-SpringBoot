package com.duaz.microservices.admin.model.user;

import java.util.HashMap;

public class UserRest {
	  private Integer userId;
	  
	  private String login;
	  
	  private String name;
	  
	  private String nick;
	  
	  private String color;
	  
	  private Integer uplineNo;
	  
	  private String email;
	  
	  private Integer clientNo;
	  
	  private String clientId;
	  
	  private Integer deptNo;
	  
	  private String password;
	  
	  private Integer roleNo;
	  
	  private Integer positionNo;
	  
	  private String changePassword;
	  
	  private String suspended;
	  
	  private String costCenter;
	  
	  private String phoneNumber;
	  
	  private String salt;
	  
	  private String usernameNotEmail;
	  
	  private String userPositionExternal;
	  
	  private String plainPassword;
	  
	  private Integer initUserNo;
	  
	  private String profileUrl;
	  
	  private String avatarProfileUrl;
	  
	  private String status;
	  
	  private HashMap<String, String> extraInfo;
	  
	  private String securityToken;
	  
	  private String apiKey;

	  private String source;
	  
	  private String noDays;

	  private int executeUserNo;
	  
	  public Integer getUserId() {
	    return this.userId;
	  }
	  
	  public void setUserId(Integer userId) {
	    this.userId = userId;
	  }
	  
	  public String getLogin() {
	    return this.login;
	  }
	  
	  public void setLogin(String login) {
	    this.login = login;
	  }
	  
	  public String getName() {
	    return this.name;
	  }
	  
	  public void setName(String name) {
	    this.name = name;
	  }
	  
	  public String getNick() {
	    return this.nick;
	  }
	  
	  public void setNick(String nick) {
	    this.nick = nick;
	  }
	  
	  public String getColor() {
	    return this.color;
	  }
	  
	  public void setColor(String color) {
	    this.color = color;
	  }
	  
	  public Integer getUplineNo() {
	    return this.uplineNo;
	  }
	  
	  public void setUplineNo(Integer uplineNo) {
	    this.uplineNo = uplineNo;
	  }
	  
	  public String getEmail() {
	    return this.email;
	  }
	  
	  public void setEmail(String email) {
	    this.email = email;
	  }
	  
	  public Integer getClientNo() {
	    return this.clientNo;
	  }
	  
	  public void setClientNo(Integer clientNo) {
	    this.clientNo = clientNo;
	  }
	  
	  public String getClientId() {
		return clientId;
	  }

	  public void setClientId(String clientId) {
		this.clientId = clientId;
	  }

	public Integer getDeptNo() {
	    return this.deptNo;
	  }
	  
	  public void setDeptNo(Integer deptNo) {
	    this.deptNo = deptNo;
	  }
	  
	  public Integer getInitUserNo() {
	    return this.initUserNo;
	  }
	  
	  public void setInitUserNo(Integer initUserNo) {
	    this.initUserNo = initUserNo;
	  }
	  
	  public String getPassword() {
	    return this.password;
	  }
	  
	  public void setPassword(String password) {
	    this.password = password;
	  }
	  
	  public Integer getRoleNo() {
	    return this.roleNo;
	  }
	  
	  public void setRoleNo(Integer roleNo) {
	    this.roleNo = roleNo;
	  }
	  
	  public Integer getPositionNo() {
	    return this.positionNo;
	  }
	  
	  public void setPositionNo(Integer positionNo) {
	    this.positionNo = positionNo;
	  }
	  
	  public String getChangePassword() {
	    return this.changePassword;
	  }
	  
	  public void setChangePassword(String changePassword) {
	    this.changePassword = changePassword;
	  }
	  
	  public String getSuspended() {
	    return this.suspended;
	  }
	  
	  public void setSuspended(String suspended) {
	    this.suspended = suspended;
	  }
	  
	  public String getCostCenter() {
	    return this.costCenter;
	  }
	  
	  public void setCostCenter(String costCenter) {
	    this.costCenter = costCenter;
	  }
	  
	  public String getPhoneNumber() {
	    return this.phoneNumber;
	  }
	  
	  public void setPhoneNumber(String phoneNumber) {
	    this.phoneNumber = phoneNumber;
	  }
	  
	  public String getPlainPassword() {
	    return this.plainPassword;
	  }
	  
	  public void setPlainPassword(String plainPassword) {
	    this.plainPassword = plainPassword;
	  }
	  
	  public String getProfileUrl() {
	    return this.profileUrl;
	  }
	  
	  public void setProfileUrl(String profileUrl) {
	    this.profileUrl = profileUrl;
	  }
	  
	  public String getAvatarProfileUrl() {
	    return this.avatarProfileUrl;
	  }
	  
	  public void setAvatarProfileUrl(String avatarProfileUrl) {
	    this.avatarProfileUrl = avatarProfileUrl;
	  }
	  
	  public String getSalt() {
	    return this.salt;
	  }
	  
	  public void setSalt(String salt) {
	    this.salt = salt;
	  }
	  
	  public String getUsernameNotEmail() {
	    return this.usernameNotEmail;
	  }
	  
	  public void setUsernameNotEmail(String usernameNotEmail) {
	    this.usernameNotEmail = usernameNotEmail;
	  }
	  
	  public String getUserPositionExternal() {
	    return this.userPositionExternal;
	  }
	  
	  public void setUserPositionExternal(String userPositionExternal) {
	    this.userPositionExternal = userPositionExternal;
	  }
	  
	  public String getStatus() {
	    return this.status;
	  }
	  
	  public void setStatus(String status) {
	    this.status = status;
	  }
	  
	  public HashMap<String, String> getExtraInfo() {
	    return this.extraInfo;
	  }
	  
	  public void setExtraInfo(HashMap<String, String> extraInfo) {
	    this.extraInfo = extraInfo;
	  }

		public String getSecurityToken() {
			return securityToken;
		}
		
		public void setSecurityToken(String securityToken) {
			this.securityToken = securityToken;
		}

		public String getSource() {
			return source;
		}

		public void setSource(String source) {
			this.source = source;
		}

		public String getNoDays() {
			return noDays;
		}

		public void setNoDays(String noDays) {
			this.noDays = noDays;
		}

		public int getExecuteUserNo() {
			return executeUserNo;
		}

		public void setExecuteUserNo(int executeUserNo) {
			this.executeUserNo = executeUserNo;
		}

		public String getApiKey() {
			return apiKey;
		}

		public void setApiKey(String apiKey) {
			this.apiKey = apiKey;
		}
	  
}
