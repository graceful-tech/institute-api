package com.institute.service.impl.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.institute.dto.user.UserDto;
import com.institute.entity.user.UserEntity;
import com.institute.repository.user.UserRepository;
import com.institute.service.user.UserService;
import com.institute.utility.CommonUtils;

@Service
public class UserServiceImpl implements UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	UserRepository userRepository;

	@Autowired
	CommonUtils passwordEncoder;

	@Override
	public boolean createUser(UserDto userDto) {
		logger.debug("UserServiceImpl :: createUser :: Entered");
		boolean status = false;
		UserEntity makeProfileUserEntity = null;
		try {
			if (userRepository.findByMobileNumberAndEmail(userDto.getMobileNumber(), userDto.getEmail()) == 0) {
				status = true;
			}
			userDto.setPassword(passwordEncoder.encryptPassword(userDto.getPassword()));
			makeProfileUserEntity = convertUserDtoToUserEntity(userDto);
			userRepository.save(makeProfileUserEntity);

			makeProfileUserEntity = null;
		} catch (Exception e) {
			status = false;
			logger.debug("UserServiceImpl :: createUser :: Error" + e.getMessage());

		}
		logger.debug("UserServiceImpl :: createUser :: Exited");

		return status;
	}

	public UserDto convertUserEntityToUserDto(UserEntity userEntity) {
		UserDto userDetails = new UserDto();
		userDetails.setEmail(userEntity.getEmail());
		userDetails.setName(userEntity.getName());
		userDetails.setMobileNumber(userEntity.getMobileNumber());
		userDetails.setId(userEntity.getId());
		userDetails.setUserName(userEntity.getUserName());
		userDetails.setPassword(userEntity.getPassword());

		return userDetails;
	}

	public UserEntity convertUserDtoToUserEntity(UserDto userDto) {
		UserEntity userDetails = new UserEntity();
		userDetails.setEmail(userDto.getEmail());
		userDetails.setName(userDto.getName());
		userDetails.setMobileNumber(userDto.getMobileNumber());
		userDetails.setId(userDto.getId());
		userDetails.setUserName(userDto.getUserName());
		userDetails.setPassword(userDto.getPassword());

		return userDetails;
	}

}
