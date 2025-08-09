package com.pcms.users.Model;

public class UserDTO {
	
	private int userId;
	private String fullName;
	private String mailId;
	private String phoneNumber;
	private String ssn;
	private String addressLine1;
	private String addressLine2;
	private String zipcode;
	private String status;
	private String rejectionComment;
	private String role;
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
	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
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
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public UserDTO(int userId, String fullName, String mailId, String phoneNumber, String ssn, String addressLine1,String addressLine2, String zipcode, String status, String rejectionComment, String role) {
        this.userId = userId;
        this.fullName = fullName;
        this.mailId = mailId;
        this.phoneNumber = phoneNumber;
        this.ssn = ssn;
        this.addressLine1 = addressLine1;
		this.addressLine2= addressLine2;
        this.zipcode = zipcode;
        this.status = status;
        this.rejectionComment = rejectionComment;
        this.role = role;
    }
	public UserDTO() {
	}
    
    
}
