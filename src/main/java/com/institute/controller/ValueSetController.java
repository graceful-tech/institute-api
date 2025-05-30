package com.institute.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.institute.dto.ValueSetDto;
import com.institute.repository.user.ValueSetService;

@RestController
@RequestMapping("/value-sets")
public class ValueSetController {

	private static final Logger logger = LoggerFactory.getLogger(ValueSetController.class);

	@Autowired
	ValueSetService valueSetService;

	@PostMapping("/search-by-code")
	public ResponseEntity<?> getValueSetsByCode(@RequestBody ValueSetDto valueSetDto) {
		logger.debug("Controller :: getValueSetsByCode :: Entered");

		List<ValueSetDto> valueSets = valueSetService.getValueSetsByCode(valueSetDto);

		logger.debug("Controller :: getValueSetsByCode :: Exited");
		return new ResponseEntity<>(valueSets, HttpStatus.OK);
	}
}
