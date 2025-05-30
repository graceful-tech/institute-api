package com.institute.controller.candidate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.institute.dto.candidate.CourseDto;
import com.institute.service.candidate.CourseService;

@RestController
@RequestMapping("/course")
public class CourseController {

	private static final Logger logger = LoggerFactory.getLogger(CourseController.class);

	@Autowired
	CourseService courseService;

	@PostMapping("/create")
	public ResponseEntity<?> createCource(@RequestBody CourseDto courseDto, @RequestHeader("username") String username,
			@RequestHeader("userid") Long userid) {
		logger.debug("Controller :: createCandidate :: Entered");

		if (userid != null) {
			courseDto.setCreatedUser(Long.valueOf(userid));
		}

		Long id = courseService.createCourse(courseDto, username);

		logger.debug("Controller :: createCandidate :: Exited");

		return new ResponseEntity<>(id, HttpStatus.OK);
	}
}
