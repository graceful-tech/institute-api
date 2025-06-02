package com.institute.repository.user;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.institute.dto.WrapperDto;
import com.institute.dto.user.UserDto;
import com.institute.entity.user.UserEntity;



public interface UserRepositoryCustom {
	
	WrapperDto<UserDto> searchUsers(UserDto searchUserDto, UserEntity userEntity, Pageable pageable);

//	List<SearchUserDto> getAvailableUsers(TeamDto teamDto);

}
