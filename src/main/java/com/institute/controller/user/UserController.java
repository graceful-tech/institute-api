package com.institute.controller.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.institute.controller.BaseController;
import com.institute.dto.user.UserDto;
import com.institute.service.user.UserService;
import com.institute.utility.CommonConstants;

@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	UserService userService;

	@PostMapping("/create")
	public ResponseEntity<?> createUser(@RequestBody UserDto userDto) {
		logger.debug("UserController :: createUser :: Entered");
		boolean user = false;
		user = userService.createUser(userDto);

		logger.debug("UserController :: createUser :: Exited");
		if (!user) {
			logger.debug("UserController :: createUser :: Error");
			 return new ResponseEntity<>(buildResponse(CommonConstants.I_0003), HttpStatus.BAD_REQUEST);
			
		}
		return new ResponseEntity<>(null, HttpStatus.OK);
	}

}
