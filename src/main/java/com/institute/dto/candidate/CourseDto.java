package com.institute.dto.candidate;

import java.time.LocalDate;

import com.institute.dto.BaseDto;

public class CourseDto extends BaseDto {

	private Long id;

	private String courseName;

	private String mode;

	private String batchPreference;

	private String batchName;

	private LocalDate batchStartDate;

	private LocalDate batchEndDate;

	private String counsellorName;

	private String leadSource;

	private String status;

	private LocalDate followUpDate;

	private Long candidateId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getCounsellorName() {
		return counsellorName;
	}

	public void setCounsellorName(String counsellorName) {
		this.counsellorName = counsellorName;
	}

	public String getLeadSource() {
		return leadSource;
	}

	public void setLeadSource(String leadSource) {
		this.leadSource = leadSource;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDate getFollowUpDate() {
		return followUpDate;
	}

	public void setFollowUpDate(LocalDate followUpDate) {
		this.followUpDate = followUpDate;
	}

	public Long getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(Long candidateId) {
		this.candidateId = candidateId;
	}

}
