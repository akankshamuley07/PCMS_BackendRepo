package com.pcms.users.Exception;

public class APIError {
	private Integer errorCode;
	private String errordesc;
	public Integer getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrordesc() {
		return errordesc;
	}
	public void setErrordesc(String errordesc) {
		this.errordesc = errordesc;
	}
	public APIError(Integer errorCode, String errordesc) {
		super();
		this.errorCode = errorCode;
		this.errordesc = errordesc;
	}
	
	
	
}
