package com.institute.service.impl.candidate;

import java.time.LocalDateTime;
import java.util.Objects;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.institute.dto.candidate.CandidateDto;
import com.institute.entity.candidate.CandidateEntity;
import com.institute.repository.candidate.CandidateRepository;
import com.institute.service.candidate.CandidateService;

@Service
public class CandidateServiceImpl implements CandidateService {

	private static final Logger logger = LoggerFactory.getLogger(CandidateServiceImpl.class);

	@Autowired
	CandidateRepository candidatesRepository;

	@Autowired
	ModelMapper modelMapper;

	@Override
	public Long createCandidate(CandidateDto candiateDto, String username) {
		logger.debug("Service :: createCandidate :: Entered");

		CandidateEntity candidateEntity = null;
		CandidateEntity saveCandiate = null;
		Long id = null;

		try {

			candidateEntity = candidatesRepository.getCandidateDetailsByMobileNumber(candiateDto.getMobileNumber());

			if (Objects.nonNull(candidateEntity)) {

				candidateEntity = modelMapper.map(candiateDto, CandidateEntity.class);
				candidateEntity.setId(candidateEntity.getId());
				candidateEntity.setModifiedDate(LocalDateTime.now());
				candidateEntity.setModifiedUser(candiateDto.getCreatedUser());
				candidateEntity.setCreatedUserName(username);

				saveCandiate = candidatesRepository.save(candidateEntity);

				id = saveCandiate.getId();
			} else {
				candidateEntity = modelMapper.map(candiateDto, CandidateEntity.class);
				candidateEntity.setCreatedDate(LocalDateTime.now());
				candidateEntity.setCreatedUser(candiateDto.getCreatedUser());
				candidateEntity.setCreatedUserName(username);

				saveCandiate = candidatesRepository.save(candidateEntity);

				id = saveCandiate.getId();
			}

		} catch (Exception e) {
			logger.error("Service :: createCandidate :: Exception :: " + e.getMessage());
		}
		logger.debug("Service :: createCandidate :: Exited");

		return id;
	}

}
