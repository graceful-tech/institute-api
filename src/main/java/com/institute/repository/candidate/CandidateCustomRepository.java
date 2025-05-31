package com.institute.repository.candidate;

import org.springframework.data.domain.Pageable;

import com.institute.dto.WrapperDto;
import com.institute.dto.candidate.SearchCandidateDto;

public interface CandidateCustomRepository {

	WrapperDto<SearchCandidateDto> searchCandidate(SearchCandidateDto searchCandidateDto, Pageable pageable);

}
