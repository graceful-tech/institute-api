package com.institute.service.impl.user;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.institute.dto.WrapperDto;
import com.institute.dto.user.UserDto;
import com.institute.entity.user.UserEntity;
import com.institute.repository.user.UserRepository;
import com.institute.repository.user.UserRepositoryCustom;
import com.institute.service.user.UserService;
import com.institute.utility.CommonUtils;

@Service
public class UserServiceImpl implements UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	UserRepository userRepository;

	@Autowired
	CommonUtils passwordEncoder;

	@Autowired
	UserRepositoryCustom userRepositoryCustom;

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

	@Override
	public UserDto getUserById(Long id) {
		logger.debug("UserServiceImpl :: getUserById :: Entered");
		UserDto userDto = new UserDto();
		try {
			userDto = convertUserEntityToUserDto(userRepository.getById(id));
		} catch (Exception e) {
			logger.debug("UserServiceImpl :: getUserById :: Error" + e.getMessage());
		}

		logger.debug("UserServiceImpl :: getUserById :: Exited");
		return userDto;
	}

	@Override
	public boolean updateUser(UserDto userDto, Long id) {
		logger.debug("UserServiceImpl :: updateUser :: Entered");
		boolean status = false;
		try {

//			if (isDuplicateUser(userDto)) {
//				throw new InstituteException(CommonConstants.PM_0003);
//			}
//
//			if (validateUserName(userDto)) {
//				throw new InstituteException(CommonConstants.PM_0003);
//			}
			userDto.setId(id);
			userDto.setPassword(passwordEncoder.encryptPassword(userDto.getPassword()));

			UserEntity userEntity = convertUserDtoToUserEntity(userDto);
			UserEntity savedEntity = userRepository.save(userEntity);

			if (Objects.nonNull(savedEntity)) {
				status = true;
			}
		} catch (Exception e) {
			logger.error("UserServiceImpl :: updateUser :: Exception :: " + e.getMessage());
		}
		logger.debug("UserServiceImpl :: updateUser :: Exited");
		return status;
	}

	private boolean isDuplicateUser(UserDto userDto) {
		logger.debug("UserServiceImpl :: isDuplicateUser :: Entered");
		boolean status = false;
		try {
			int count = 0;

			if (Objects.nonNull(userDto.getId())) {
				count = userRepository.getDuplicateUserCount(userDto.getMobileNumber(), userDto.getEmail(),
						userDto.getId());
			} else {
				count = userRepository.getDuplicateUserCount(userDto.getMobileNumber(), userDto.getEmail());
			}

			if (count > 0) {
				status = true;
			}
		} catch (Exception e) {
			logger.error("UserServiceImpl :: isDuplicateUser :: Exception :: " + e.getMessage());
		}
		logger.debug("UserServiceImpl :: isDuplicateUser :: Exited");
		return status;
	}

	public boolean validateUserName(UserDto userDto) {
		logger.debug("UserServiceImpl :: validateUserName :: Entered");
		boolean status = false;
		try {
			int count = 0;

			if (Objects.nonNull(userDto.getId())) {
				count = userRepository.getDuplicateUserNameCount(userDto.getUserName(), userDto.getId());
			} else {
				count = userRepository.getDuplicateUserNameCount(userDto.getUserName());
			}

			if (count > 0) {
				status = true;
			}
		} catch (Exception e) {
			logger.error("UserServiceImpl :: validateUserName :: Exception :: " + e.getMessage());
		}
		logger.debug("UserServiceImpl :: validateUserName :: Exited");
		return status;
	}

	@Override
	public WrapperDto<UserDto> searchUsers(UserDto searchUserDto, String userName) {
		logger.debug("Service :: searchUsers :: Entered");
		WrapperDto<UserDto> wrapperDto = null;
		try {
			UserEntity userEntity = userRepository.getUserByUserName(userName);

			Pageable pageable = PageRequest.of(searchUserDto.getPage(), searchUserDto.getLimit());
			wrapperDto = userRepositoryCustom.searchUsers(searchUserDto, userEntity, pageable);
		} catch (Exception e) {
			logger.error("Service :: searchUsers :: Exception :: " + e.getMessage());
		}
		logger.debug("Service :: searchUsers :: Exited");
		return wrapperDto;
	}

	public UserDto convertUserEntityToUserDto(UserEntity userEntity) {
		UserDto userDetails = new UserDto();
		userDetails.setEmail(userEntity.getEmail());
		userDetails.setName(userEntity.getName());
		userDetails.setMobileNumber(userEntity.getMobileNumber());
		userDetails.setId(userEntity.getId());
		userDetails.setUserName(userEntity.getUserName());
		userDetails.setPassword(passwordEncoder.decryptPassword(userEntity.getPassword()));

		return userDetails;
	}

	public UserEntity convertUserDtoToUserEntity(UserDto userDto) {
		UserEntity userDetails = new UserEntity();
		if (userDto.getId() != null) {
			userDetails.setId(userDto.getId());
		}
		userDetails.setEmail(userDto.getEmail());
		userDetails.setName(userDto.getName());
		userDetails.setMobileNumber(userDto.getMobileNumber());
		userDetails.setId(userDto.getId());
		userDetails.setUserName(userDto.getUserName());
		userDetails.setPassword(userDto.getPassword());

		return userDetails;
	}

}
