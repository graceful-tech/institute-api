package com.institute.service.impl.login;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.institute.dto.login.LoginDto;
import com.institute.dto.user.UserDto;
import com.institute.entity.user.UserEntity;
import com.institute.repository.user.UserRepository;
import com.institute.service.login.LoginService;
import com.institute.utility.CommonUtils;

@Service
public class LoginServiceImpl implements LoginService {
	private static final Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);

	@Autowired
	UserRepository userRepository;

	@Autowired
	CommonUtils passwordEncoder;

	@Autowired
	ModelMapper modelMapper;

	@Override
	public UserDto findByMobileNumber(LoginDto loginDto) {
		logger.debug("LoginServiceImpl :: findByMobileNumber :: Exited");
		UserDto userDto = null;
		UserEntity userEntity = null;
		try {
			userEntity = userRepository.findByMobileNumber(loginDto.getMobileNumber());
			if (loginDto.getMobileNumber().equals(userEntity.getMobileNumber())
					&& loginDto.getPassword().equals(passwordEncoder.decryptPassword(userEntity.getPassword()))) {
				userDto = modelMapper.map(userEntity, UserDto.class);
			}
		} catch (Exception e) {
			logger.debug("LoginServiceImpl :: userLogin :: findByMobileNumber" + e.getMessage());
		}
		logger.debug("LoginServiceImpl :: findByMobileNumber :: Exited");
		userEntity = null;
		return userDto;
	}

}
