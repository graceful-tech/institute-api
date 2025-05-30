package com.institute.entity.common;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "environment_config")
public class EnvironmentEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 30)
	private String environmentKey;

	@Column(nullable = false, length = 200)
	private String environmentValue;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEnvironmentKey() {
		return environmentKey;
	}

	public void setEnvironmentKey(String environmentKey) {
		this.environmentKey = environmentKey;
	}

	public String getEnvironmentValue() {
		return environmentValue;
	}

	public void setEnvironmentValue(String environmentValue) {
		this.environmentValue = environmentValue;
	}

}
