package com.institute.service.impl.candidate;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.institute.dto.StatusDto;
import com.institute.entity.common.StatusEntity;
import com.institute.repository.candidate.StatusRepository;
import com.institute.service.candidate.StatusService;
 
@Service
public class StatusServiceImpl implements StatusService {

	private static final Logger logger = LoggerFactory.getLogger(StatusServiceImpl.class);

	@Autowired
	StatusRepository statusRepository;

	@Autowired
	ModelMapper modelMapper;

	@Override
	public List<StatusDto> getAllStatus() {
		logger.debug("Service :: getAllStatus :: Entered");

		List<StatusDto> status = new ArrayList<>();

		List<StatusEntity> allStatus = null;

		try {
			allStatus = statusRepository.getAllStatus();

			allStatus.forEach(sta -> {
				StatusDto statusDto = new StatusDto();

				statusDto.setId(sta.getId());
				statusDto.setStatusName(sta.getStatusName());
				status.add(statusDto);
				statusDto = null;
			});

			allStatus = null;

		} catch (Exception e) {
			logger.error("Service :: getAllStatus :: Exception :: " + e.getMessage());
		}
		logger.debug("Service :: getAllStatus :: Exited");
		return status;
	}

}
