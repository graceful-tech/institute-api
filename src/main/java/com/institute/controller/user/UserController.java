package com.institute.controller.user;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.institute.controller.BaseController;
import com.institute.dto.WrapperDto;
import com.institute.dto.user.UserDto;
import com.institute.service.user.UserService;
import com.institute.utility.CommonConstants;

@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	UserService userService;

	@GetMapping
	public ResponseEntity<?> getUsers() {
		logger.debug("Controller :: getUsers :: Entered");

		List<UserDto> allUsers = userService.getAllUsers();

		logger.debug("Controller :: getUsers :: Exited");

		return new ResponseEntity<>(allUsers, HttpStatus.OK);
	}

	@PostMapping("/create")
	public ResponseEntity<?> createUser(@RequestBody UserDto userDto) {
		logger.debug("Controller :: createUser :: Entered");

		boolean user = false;
		user = userService.createUser(userDto);

		logger.debug("Controller :: createUser :: Exited");
		if (!user) {
			return new ResponseEntity<>(buildResponse(CommonConstants.I_0003), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(null, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getUser(@PathVariable Long id) {
		logger.debug("UserController :: getUser :: Entered");

		UserDto userDto = userService.getUserById(id);

		logger.debug("UserController :: getUser :: Exited");

		return new ResponseEntity<>(userDto, HttpStatus.OK);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateUser(@RequestBody UserDto userDto, @PathVariable Long id) {
		logger.debug("Controller :: updateUser :: Entered");

		boolean user = false;
		user = userService.updateUser(userDto, id);

		logger.debug("Controller :: updateUser :: Exited");

		if (!user) {
			return new ResponseEntity<>(buildResponse(CommonConstants.I_0003), HttpStatus.BAD_REQUEST);

		}
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@PostMapping("/search")
	public ResponseEntity<?> searchUsers(@RequestBody UserDto searchUserDto,
			@RequestHeader("username") String userName) {
		logger.debug("Controller :: searchUsers :: Entered");

		WrapperDto<UserDto> searchUsers = userService.searchUsers(searchUserDto, userName);

		logger.debug("Controller :: searchUsers :: Exited");

		return new ResponseEntity<>(searchUsers, HttpStatus.OK);
	}

}
