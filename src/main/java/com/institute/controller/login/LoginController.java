package com.institute.controller.login;

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
import com.institute.dto.login.LoginDto;
import com.institute.service.login.LoginService;
import com.institute.utility.CommonConstants;



@RestController
@RequestMapping("/auth")
public class LoginController extends BaseController {
	
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	LoginService loginService;
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
		logger.debug("LoginController :: login :: Entered");
		com.institute.dto.user.UserDto userDto = null;
		try {
			userDto = loginService.findByMobileNumber(loginDto);
			

			logger.debug("LoginController :: userLogin :: Exited");
			return ResponseEntity.ok(userDto);
		} catch (Exception e) {
			logger.debug("LoginController :: userLogin :: Error");
		}
		return new ResponseEntity<>(buildResponse(CommonConstants.I_0005), HttpStatus.BAD_REQUEST);
	}


}
