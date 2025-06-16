package com.institute.service.impl.candidate;

import java.time.LocalDateTime;
import java.util.Objects;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.institute.dto.WrapperDto;
import com.institute.dto.candidate.CandidateDto;
import com.institute.dto.candidate.SearchCandidateDto;
import com.institute.entity.candidate.CandidateEntity;
import com.institute.exception.InstituteException;
import com.institute.repository.candidate.CandidateCustomRepository;
import com.institute.repository.candidate.CandidateRepository;
import com.institute.repository.user.UserRepository;
import com.institute.service.candidate.CandidateService;

@Service
public class CandidateServiceImpl implements CandidateService {

	private static final Logger logger = LoggerFactory.getLogger(CandidateServiceImpl.class);

	@Autowired
	CandidateRepository candidatesRepository;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	CandidateCustomRepository candidateCustomRepository;

	@Autowired
	UserRepository userRepository;

	@Override
	public Long createCandidate(CandidateDto candiateDto, String username) {
		logger.debug("Service :: createCandidate :: Entered");

		CandidateEntity candidateEntity = null;
		CandidateEntity saveCandidate = null;
		CandidateEntity Candidate = null;
		Long id = null;

		try {

			Long userId = userRepository.getUserId(username);

			Candidate = candidatesRepository.getCandidateDetailsByMobileNumber(candiateDto.getMobileNumber());

			if (Objects.isNull(Candidate)) {
				if (Objects.nonNull(candiateDto.getName())) {
					candiateDto.setName(candiateDto.getName().replaceAll("\\s{2,}", " "));
				}
				candidateEntity = modelMapper.map(candiateDto, CandidateEntity.class);

				candidateEntity.setId(candidateEntity.getId());
				candidateEntity.setModifiedDate(LocalDateTime.now());
				candidateEntity.setModifiedUser(userId);
				candidateEntity.setCreatedUserName(username);
				candidateEntity.setCreatedDate(LocalDateTime.now());
				candidateEntity.setCreatedUser(userId);

				saveCandidate = candidatesRepository.save(candidateEntity);

				id = saveCandidate.getId();
			}
			candidateEntity = null;
			saveCandidate = null;
			Candidate = null;
			userId = null;
		} catch (Exception e) {
			logger.error("Service :: createCandidate :: Exception :: " + e.getMessage());
		}
		logger.debug("Service :: createCandidate :: Exited");

		return id;
	}

	@Override
	public CandidateDto getCandidateById(Long id) {
		logger.debug("Service :: getCandidateById :: Entered");

		CandidateEntity candidateById = null;
		CandidateDto candidate = null;

		try {
			candidateById = candidatesRepository.getCandidateById(id);
			candidate = modelMapper.map(candidateById, CandidateDto.class);

			candidateById = null;

		} catch (Exception e) {
			logger.error("Service :: getCandidateById :: Exception :: " + e.getMessage());
		}
		logger.debug("Service :: getCandidateById :: Exited");
		return candidate;
	}

	@Override
	public WrapperDto<SearchCandidateDto> searchCandidate(SearchCandidateDto SearchCandidateDto, String userName) {

		logger.debug("Service :: searchCandidate :: Entered");

		WrapperDto<SearchCandidateDto> candidateWrapperDto = null;
		try {
			SearchCandidateDto.setLoggedUserName(userName);
			Pageable pageable = PageRequest.of(SearchCandidateDto.getPage(), SearchCandidateDto.getLimit());
			candidateWrapperDto = candidateCustomRepository.searchCandidate(SearchCandidateDto, pageable);
		} catch (Exception e) {
			logger.error("Service :: shareSearchCandidates :: Exception :: " + e.getMessage());
		}
		logger.debug("Service :: shareSearchCandidates :: Exited");
		return candidateWrapperDto;
	}

	@Override
	public Long UpdateCandidate(CandidateDto candiateDto, String username) {
		logger.debug("Service :: createCandidate :: Entered");

		CandidateEntity candidateEntity = null;
		CandidateEntity saveCandidate = null;
		Long id = null;

		try {
			Long userId = userRepository.getUserId(username);

			candidateEntity = candidatesRepository.getCandidateDetailsByMobileNumber(candiateDto.getMobileNumber());

			if (Objects.nonNull(candidateEntity)) {

				candidateEntity = modelMapper.map(candiateDto, CandidateEntity.class);
				candidateEntity.setId(candidateEntity.getId());
				candidateEntity.setModifiedDate(LocalDateTime.now());
				candidateEntity.setModifiedUser(userId);
				candidateEntity.setCreatedUserName(candidateEntity.getCreatedUserName());
				candidateEntity.setCreatedUser(candidateEntity.getCreatedUser());

				saveCandidate = candidatesRepository.save(candidateEntity);

				id = saveCandidate.getId();
			} else {
				CandidateEntity candidate = new CandidateEntity();
				candidate = modelMapper.map(candiateDto, CandidateEntity.class);
				candidate.setCreatedDate(LocalDateTime.now());
				candidate.setCreatedUser(userId);
				candidate.setModifiedUser(userId);
				candidate.setModifiedDate(LocalDateTime.now());
				candidate.setCreatedUserName(username);

				saveCandidate = candidatesRepository.save(candidate);

				id = saveCandidate.getId();

				candidate = null;
			}
			candidateEntity = null;
			saveCandidate = null;
			userId = null;
		} catch (Exception e) {
			logger.error("Service :: createCandidate :: Exception :: " + e.getMessage());
		}
		logger.debug("Service :: createCandidate :: Exited");
		return id;

	}

	@Override
	public boolean getCandidateDetailsByMobileNumber(CandidateDto candiateDto) {
		logger.debug("Service :: getCandidateById :: Entered");

		boolean status = false;
		CandidateEntity candidateDetailsByMobileNumber = null;
		try {
			candidateDetailsByMobileNumber = candidatesRepository
					.getCandidateDetailsByMobileNumber(candiateDto.getMobileNumber());
			if (Objects.nonNull(candidateDetailsByMobileNumber)) {
				status = true;
			}
			candidateDetailsByMobileNumber = null;

		} catch (Exception e) {
			logger.error("Service :: getCandidateById :: Exception :: " + e.getMessage());
		}
		logger.debug("Service :: getCandidateById :: Exited");
		return status;
	}

}
