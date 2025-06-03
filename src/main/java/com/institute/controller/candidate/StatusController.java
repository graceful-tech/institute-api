package com.institute.controller.candidate;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.institute.dto.StatusDto;
import com.institute.service.candidate.StatusService;
 
@RestController
@RequestMapping("/status")
public class StatusController {

	private static final Logger logger = LoggerFactory.getLogger(StatusController.class);

	@Autowired
	StatusService statusService;

	@GetMapping
	public ResponseEntity<?> getStatus() {
		logger.debug("Controller :: getStatus :: Entered");

		List<StatusDto> valueSets = statusService.getAllStatus();

		logger.debug("Controller :: getStatus :: Exited");
		return new ResponseEntity<>(valueSets, HttpStatus.OK);
	}
}
