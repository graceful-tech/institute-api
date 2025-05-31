package com.institute.repository.impl.candidate;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.institute.dto.WrapperDto;
import com.institute.dto.candidate.SearchCandidateDto;
import com.institute.repository.candidate.CandidateCustomRepository;
import com.institute.repository.candidate.CandidateRepository;
import com.institute.repository.candidate.CourseRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

@Repository
public class CandidateCustomRepositoryImpl implements CandidateCustomRepository {

	private static final Logger logger = LoggerFactory.getLogger(CandidateCustomRepositoryImpl.class);

	@Autowired
	EntityManager entityManager;

	@Autowired
	CandidateRepository candidateRepository;

	@Autowired
	CourseRepository courseRepository;

	private static final BiFunction<SearchCandidateDto, List<Object>, String> search_candidates_filter = (
			searchCandidateDto, params) -> {

		StringBuilder sb = new StringBuilder();

		return sb.toString();
	};

	private static final BiFunction<SearchCandidateDto, List<Object>, String> search_candidates = (searchCandidateDto,
			params) -> {

		StringBuilder sb = new StringBuilder();

		return sb.toString();
	};

	@Override
	public WrapperDto<SearchCandidateDto> searchCandidate(SearchCandidateDto searchCandidateDto, Pageable pageable) {
		logger.error("Repository :: searchCandidate :: Entered");

		List<Object> params = new ArrayList<>();
		WrapperDto<SearchCandidateDto> wrapper = new WrapperDto<>();
		List<SearchCandidateDto> candidateList = new ArrayList<>();

		try {

			Query query = entityManager.createNativeQuery(search_candidates.apply(searchCandidateDto, params));

			int count = 1;
			for (Object param : params) {
				query.setParameter(count++, param);
			}

			query.setFirstResult((pageable.getPageNumber() - 1) * pageable.getPageSize());
			query.setMaxResults(pageable.getPageSize());

			List<Object[]> results = query.getResultList();

		} catch (Exception e) {
			logger.error("Repository :: searchCandidate :: Exception :: " + e.getMessage());
		}
		logger.debug("Repository :: searchCandidate :: Exited");
		return null;
	}

	private static final BiFunction<SearchCandidateDto, List<Object>, String> get_candidates_count = (
			searchCandidateDto, params) -> {

		StringBuilder sb = new StringBuilder();

		return sb.toString();
	};

	private Long getSearchCandidateCount(SearchCandidateDto searchCandidateDto) {
		logger.error("Repository :: getSearchCandidateCount :: Entered");

		Long totalRecords = null;
		List<Object> params = new ArrayList<>();
		try {

			Query query = entityManager.createNativeQuery(get_candidates_count.apply(searchCandidateDto, params));

			int count = 1;
			for (Object param : params) {
				query.setParameter(count++, param);
			}

			totalRecords = (Long) query.getSingleResult();
		} catch (Exception e) {
			logger.error("Repository :: getSearchCandidateCount :: Exception :: " + e.getMessage());
		}
		logger.debug("Repository :: getSearchCandidateCount :: Exited");
		return totalRecords;

	}

}
