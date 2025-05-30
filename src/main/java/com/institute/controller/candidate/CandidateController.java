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

import com.institute.dto.candidate.CandidateDto;
import com.institute.service.candidate.CandidateService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/candidate")
public class CandidateController {

	private static final Logger logger = LoggerFactory.getLogger(CandidateController.class);

	@Autowired
	CandidateService candidateService;

	@Autowired
	HttpServletRequest httpRequest;

	@PostMapping("/create")
	public ResponseEntity<?> createCandidate(@RequestBody CandidateDto candidateDto,
			@RequestHeader("username") String username, @RequestHeader("userid") Long userid) {
		logger.debug("Controller :: createCandidate :: Entered");

		if (userid != null) {
			candidateDto.setCreatedUser(Long.valueOf(userid));
		}

		Long id = candidateService.createCandidate(candidateDto, username);

		logger.debug("Controller :: createCandidate :: Exited");

		return new ResponseEntity<>(id, HttpStatus.OK);
	}

}
