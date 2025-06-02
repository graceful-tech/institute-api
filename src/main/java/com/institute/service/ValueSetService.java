package com.institute.service;

import java.util.List;

import com.institute.dto.ValueSetDto;
import com.institute.dto.user.UserDto;

 
public interface ValueSetService {

	List<ValueSetDto> getValueSetsByCode(ValueSetDto valueSetDto);
	
	List<UserDto> getAllUsers();
}
