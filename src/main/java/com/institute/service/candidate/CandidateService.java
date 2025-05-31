package com.institute.service.candidate;

import com.institute.dto.WrapperDto;
import com.institute.dto.candidate.CandidateDto;
import com.institute.dto.candidate.SearchCandidateDto;

public interface CandidateService {

	Long createCandidate(CandidateDto candiateDto, String username);

	CandidateDto getCandidateById(Long id);

	WrapperDto<SearchCandidateDto> searchCandidate(SearchCandidateDto SearchCandidateDto, String userName);

	Long UpdateCandidate(CandidateDto candiateDto, String username);

	boolean getCandidateDetailsByMobileNumber(CandidateDto candiateDto);

}
