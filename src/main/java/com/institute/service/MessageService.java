package com.institute.service;

import com.institute.dto.MessageDto;

public interface MessageService {

	MessageDto getMessageByCode(String code);
}
