package com.institute.dto;

public class ValueSetDto {

	private Long id;

	private String valueSetCode;

	private String dataValue;

	private String displayValue;

	private Long displayOrder;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getValueSetCode() {
		return valueSetCode;
	}

	public void setValueSetCode(String valueSetCode) {
		this.valueSetCode = valueSetCode;
	}

	public String getDataValue() {
		return dataValue;
	}

	public void setDataValue(String dataValue) {
		this.dataValue = dataValue;
	}

	public String getDisplayValue() {
		return displayValue;
	}

	public void setDisplayValue(String displayValue) {
		this.displayValue = displayValue;
	}

	public Long getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(Long displayOrder) {
		this.displayOrder = displayOrder;
	}

}
