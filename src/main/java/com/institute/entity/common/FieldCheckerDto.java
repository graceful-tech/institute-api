package com.institute.entity.common;

import java.util.List;

public class FieldCheckerDto {

	Long count;

	List<String> fieldName;

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public List<String> getFieldName() {
		return fieldName;
	}

	public void setFieldName(List<String> fieldName) {
		this.fieldName = fieldName;
	}

}
