package com.institute.dto.user;

import com.institute.dto.BaseDto;

public class UserDto extends BaseDto {

	private Long id;

	private String name;

	private String email;

	private String mobileNumber;

	private String userName;

	private String password;

	private Long signedUserId;

	private String search;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public Long getSignedUserId() {
		return signedUserId;
	}

	public void setSignedUserId(Long signedUserId) {
		this.signedUserId = signedUserId;
	}
	
}
