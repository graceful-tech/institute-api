package com.institute.entity.candidate;

import java.time.LocalDate;

import com.institute.entity.common.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Id;

@Entity
@Table(name = "course")
public class CourseEntity extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = true, length = 30)
	private String courseName;

	@Column(nullable = true, length = 20)
	private String mode;

	@Column(nullable = true, length = 20)
	private String batchPreference;

	@Column(nullable = true, length = 20)
	private String batchName;

	@Column(nullable = true)
	private LocalDate batchStartDate;

	@Column(nullable = true)
	private LocalDate batchEndDate;

	@Column(nullable = true, length = 30)
	private String counsellorName;

	@Column(nullable = true, length = 30)
	private String leadSource;

	@Column(nullable = true)
	private String status;

	@Column(nullable = true)
	private LocalDate followUpDate;

	@Column(nullable = false)
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
