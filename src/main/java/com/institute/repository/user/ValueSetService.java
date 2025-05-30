package com.institute.repository.user;

import java.util.List;

import com.institute.dto.ValueSetDto;

 
public interface ValueSetService {

	List<ValueSetDto> getValueSetsByCode(ValueSetDto valueSetDto);
}
