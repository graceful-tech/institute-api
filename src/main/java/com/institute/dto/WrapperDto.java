package com.institute.dto;

import java.util.List;

public class WrapperDto<T> {

	private List<T> results;

	private Long totalRecords;

	private Long id;

	private Long pendingAuditCount;

	public List<T> getResults() {
		return results;
	}

	public void setResults(List<T> results) {
		this.results = results;
	}

	public Long getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(Long totalRecords) {
		this.totalRecords = totalRecords;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPendingAuditCount() {
		return pendingAuditCount;
	}

	public void setPendingAuditCount(Long pendingAuditCount) {
		this.pendingAuditCount = pendingAuditCount;
	}

}
