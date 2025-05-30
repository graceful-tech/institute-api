package com.institute.service.candidate;

import com.institute.dto.candidate.CandidateDto;

public interface CandidateService {

	Long createCandidate(CandidateDto candiateDto, String username);

}
