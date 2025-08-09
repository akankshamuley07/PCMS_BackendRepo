package com.pcms.users.Config.Model;

public class JwtResponse {
	private String userNameString;
	private String tokenString;
	private String responseMessage;
	private int userId;
	
	public String getUserNameString() {
		return userNameString;
	}
	public void setUserNameString(String userNameString) {
		this.userNameString = userNameString;
	}
	public String getTokenString() {
		return tokenString;
	}
	public void setTokenString(String tokenString) {
		this.tokenString = tokenString;
	}
	public String getResponseMessage() {
		return responseMessage;
	}
	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public JwtResponse(String userNameString, String tokenString, String responseMessage, int userId) {
		super();
		this.userNameString = userNameString;
		this.tokenString = tokenString;
		this.responseMessage = responseMessage;
		this.userId =userId;
	}
	public JwtResponse() {
		super();
	}
	
}

