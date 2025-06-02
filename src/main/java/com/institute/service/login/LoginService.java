package com.institute.service.login;

import com.institute.dto.login.LoginDto;
import com.institute.dto.user.UserDto;

public interface LoginService {

	UserDto findByMobileNumber(LoginDto loginDto);

}
