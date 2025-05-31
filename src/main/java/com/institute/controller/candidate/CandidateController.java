package com.institute.controller.candidate;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.institute.controller.BaseController;
import com.institute.dto.WrapperDto;
import com.institute.dto.candidate.CandidateDto;
import com.institute.dto.candidate.SearchCandidateDto;
import com.institute.service.candidate.CandidateService;
import com.institute.utility.CommonConstants;

import jakarta.websocket.server.PathParam;

@RestController
@RequestMapping("/candidate")
public class CandidateController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(CandidateController.class);

	@Autowired
	CandidateService candidateService;

	@PostMapping("/create")
	public ResponseEntity<?> createCandidate(@RequestBody CandidateDto candidateDto,
			@RequestHeader("username") String username, @RequestHeader("userid") Long userid) {
		logger.debug("Controller :: createCandidate :: Entered");

		if (userid != null) {
			candidateDto.setCreatedUser(Long.valueOf(userid));
		}

		Long id = candidateService.createCandidate(candidateDto, username);

		logger.debug("Controller :: createCandidate :: Exited");
		if (Objects.isNull(id)) {
			return new ResponseEntity<>(buildResponse(CommonConstants.I_0005), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(id, HttpStatus.OK);
	}

	@PostMapping("/update")
	public ResponseEntity<?> updateCandidate(@RequestBody CandidateDto candidateDto,
			@RequestHeader("username") String username, @RequestHeader("userid") Long userid) {
		logger.debug("Controller :: updateCandidate :: Entered");

		if (userid != null) {
			candidateDto.setCreatedUser(Long.valueOf(userid));
		}

		Long id = candidateService.UpdateCandidate(candidateDto, username);

		logger.debug("Controller :: updateCandidate :: Exited");

		return new ResponseEntity<>(id, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getCandidate(@PathVariable Long id, @RequestHeader("username") String username) {
		logger.debug("Controller :: createCandidate :: Entered");

		CandidateDto candidateById = candidateService.getCandidateById(id);

		logger.debug("Controller :: createCandidate :: Exited");

		return new ResponseEntity<>(candidateById, HttpStatus.OK);
	}

	@PostMapping("/search")
	public ResponseEntity<?> searchCandidate(@RequestBody SearchCandidateDto searchCandidateDto,
			@RequestHeader("username") String username, @RequestHeader("userid") Long userid) {
		logger.debug("Controller :: createCandidate :: Entered");

		if (userid != null) {
			searchCandidateDto.setLoggedUserId(userid);
		}

		WrapperDto<SearchCandidateDto> searchCandidate = candidateService.searchCandidate(searchCandidateDto, username);

		logger.debug("Controller :: createCandidate :: Exited");

		return new ResponseEntity<>(searchCandidate, HttpStatus.OK);
	}

	@PostMapping("/search-by-mobile-number")
	public ResponseEntity<?> getCandidateDetailsByMobileNumber(@RequestBody CandidateDto candidateDto)
			throws Exception {
		logger.debug("Controller :: getCandidateDetailsByMobileNumber :: Entered");

		boolean candidate = candidateService.getCandidateDetailsByMobileNumber(candidateDto);

		logger.debug("Controller :: getCandidateDetailsByMobileNumber :: Exited");
		if (candidate) {
			return new ResponseEntity<>(buildResponse(CommonConstants.I_0004), HttpStatus.BAD_REQUEST);

		} else {
			return new ResponseEntity<>(candidate, HttpStatus.OK);

		}

	}

}
