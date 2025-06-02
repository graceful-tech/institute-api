package com.institute.service.user;

import java.util.List;

import com.institute.dto.WrapperDto;
import com.institute.dto.user.UserDto;

public interface UserService {

	boolean createUser(UserDto userDto);
	
	UserDto getUserById(Long id);
	
	List<UserDto> getAllUsers();

	boolean updateUser(UserDto userDto,Long id);

	WrapperDto<UserDto> searchUsers(UserDto searchUserDto, String userName);

}
