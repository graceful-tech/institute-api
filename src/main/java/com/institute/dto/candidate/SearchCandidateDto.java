package com.institute.dto.candidate;

import java.time.LocalDate;

import com.institute.dto.BaseDto;

public class SearchCandidateDto extends BaseDto {

	private Long id;

	private String name;

	private String mobileNumber;

	private String email;

	private String parentContact;

	private String location;

	private String qualification;

	private String courseName;

	private String mode;

	private String batchPreference;

	private String batchName;

	private LocalDate batchStartDate;

	private LocalDate batchEndDate;

	private String status;

	private Long candidateId;

	private String userName;

	private Long loggedUserId;

	private String search;

	private String modifiedUserName;

	private double courseFees;

	private double amountPaid;

	private double balanceAmount;

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

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getParentContact() {
		return parentContact;
	}

	public void setParentContact(String parentContact) {
		this.parentContact = parentContact;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getQualification() {
		return qualification;
	}

	public void setQualification(String qualification) {
		this.qualification = qualification;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getBatchPreference() {
		return batchPreference;
	}

	public void setBatchPreference(String batchPreference) {
		this.batchPreference = batchPreference;
	}

	public String getBatchName() {
		return batchName;
	}

	public void setBatchName(String batchName) {
		this.batchName = batchName;
	}

	public LocalDate getBatchStartDate() {
		return batchStartDate;
	}

	public void setBatchStartDate(LocalDate batchStartDate) {
		this.batchStartDate = batchStartDate;
	}

	public LocalDate getBatchEndDate() {
		return batchEndDate;
	}

	public void setBatchEndDate(LocalDate batchEndDate) {
		this.batchEndDate = batchEndDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(Long candidateId) {
		this.candidateId = candidateId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Long getLoggedUserId() {
		return loggedUserId;
	}

	public void setLoggedUserId(Long loggedUserId) {
		this.loggedUserId = loggedUserId;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public String getModifiedUserName() {
		return modifiedUserName;
	}

	public void setModifiedUserName(String modifiedUserName) {
		this.modifiedUserName = modifiedUserName;
	}

	public double getCourseFees() {
		return courseFees;
	}

	public void setCourseFees(double courseFees) {
		this.courseFees = courseFees;
	}

	public double getAmountPaid() {
		return amountPaid;
	}

	public void setAmountPaid(double amountPaid) {
		this.amountPaid = amountPaid;
	}

	public double getBalanceAmount() {
		return balanceAmount;
	}

	public void setBalanceAmount(double balanceAmount) {
		this.balanceAmount = balanceAmount;
	}

}
