package com.pcms.users.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

//@Entity
public class Users {
//	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
	private int userId;
	private String fullName;
	private String mailId;
	private String phoneNumber;
	private String ssn;
	private String addressLine1;
	private String addressLine2;
	private String password;
	private String confirmPassword;
	private String status;
	private String rejectionComment;
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getMailId() {
		return mailId;
	}
	public void setMailId(String mailId) {
		this.mailId = mailId;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getSsn() {
		return ssn;
	}
	public void setSsn(String ssn) {
		this.ssn = ssn;
	}
	public String getAddressLine1() {
		return addressLine1;
	}
	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}
	public String getAddressLine2() {
		return addressLine2;
	}
	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRejectionComment() {
		return rejectionComment;
	}
	public void setRejectionComment(String rejectionComment) {
		this.rejectionComment = rejectionComment;
	}
	public Users() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Users(int userId, String fullName, String mailId, String phoneNumber, String ssn, String addressLine1,
			String addressLine2, String password, String confirmPassword, String status, String rejectionComment) {
		super();
		this.userId = userId;
		this.fullName = fullName;
		this.mailId = mailId;
		this.phoneNumber = phoneNumber;
		this.ssn = ssn;
		this.addressLine1 = addressLine1;
		this.addressLine2 = addressLine2;
		this.password = password;
		this.confirmPassword = confirmPassword;
		this.status = status;
		this.rejectionComment = rejectionComment;
	}
	
	

}
