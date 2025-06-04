package com.institute.dto.candidate;

import java.time.LocalDateTime;

import com.institute.dto.PageDto;

public class CommentDto extends PageDto {

	private Long id;

	private String comment;

	private LocalDateTime createdDate;

	private String userName;

	private Long candidateId;

	private String candidateName;

	private String signedUserName;

	private Long signedUserId;

	private Long userId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Long getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(Long candidateId) {
		this.candidateId = candidateId;
	}

	public String getCandidateName() {
		return candidateName;
	}

	public void setCandidateName(String candidateName) {
		this.candidateName = candidateName;
	}

	public String getSignedUserName() {
		return signedUserName;
	}

	public void setSignedUserName(String signedUserName) {
		this.signedUserName = signedUserName;
	}

	public Long getSignedUserId() {
		return signedUserId;
	}

	public void setSignedUserId(Long signedUserId) {
		this.signedUserId = signedUserId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
