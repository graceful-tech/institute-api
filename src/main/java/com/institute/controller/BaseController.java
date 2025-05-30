package com.institute.controller;

import java.util.Objects;

import com.institute.dto.MessageDto;
import com.institute.dto.ResponseDto;



public class BaseController {
	
	
	
	
	
	public ResponseDto buildResponse(String code) {
		ResponseDto responseDto = new ResponseDto();
//		MessageDto messageDto = messageService.getMessageByCode(code);
//
//		if (Objects.nonNull(messageDto)) {
//			responseDto.setCode(code);
//			responseDto.setStatus(messageDto.getType());
//			responseDto.setMessage(messageDto.getMessage());
//		}
		return responseDto;
	}
}
