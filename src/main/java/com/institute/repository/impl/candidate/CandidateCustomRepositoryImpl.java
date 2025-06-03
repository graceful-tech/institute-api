package com.institute.repository.impl.candidate;

import java.lang.ref.WeakReference;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
import com.institute.utility.CommonUtils;

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

	private static final BiFunction<SearchCandidateDto, List<Object>, String> search_candidate_parameters = (
			searchCandidateDto, params) -> {

		StringBuilder sb = new StringBuilder();

		if (Objects.nonNull(searchCandidateDto.getSearch()) && !searchCandidateDto.getSearch().isEmpty()) {
			sb.append(" and (upper(candidate.name) like upper(?) or upper(candidate.mobile_number) like upper(?) "
					+ "or upper(candidate.email) like upper(?)) ");
			params.add(CommonUtils.appendLikeOperator(searchCandidateDto.getSearch()));
			params.add(CommonUtils.appendLikeOperator(searchCandidateDto.getSearch()));
			params.add(CommonUtils.appendLikeOperator(searchCandidateDto.getSearch()));
		}

		if (Objects.nonNull(searchCandidateDto.getBatchName()) && !searchCandidateDto.getBatchName().isEmpty()) {
			sb.append(" and course_candidate.batch_name = ? ");
			params.add(searchCandidateDto.getBatchName());
		}

		if (Objects.nonNull(searchCandidateDto.getUserName()) && !searchCandidateDto.getUserName().isEmpty()) {
			sb.append(" and candidate.created_username = ? ");
			params.add(searchCandidateDto.getUserName());
		}

		if (Objects.nonNull(searchCandidateDto.getBatchPreference())
				&& !searchCandidateDto.getBatchPreference().isEmpty()) {
			sb.append(" and course_candidate.batch_preference = ? ");
			params.add(searchCandidateDto.getBatchPreference());
		}

		if (Objects.nonNull(searchCandidateDto.getMode()) && !searchCandidateDto.getMode().isEmpty()) {
			sb.append(" and course_candidate.mode = ? ");
			params.add(searchCandidateDto.getMode());
		}
		
		if (Objects.nonNull(searchCandidateDto.getStatus())
				&& !searchCandidateDto.getStatus().isEmpty()) {
			sb.append(" and  course_candidate.status  = ? ");
			params.add(searchCandidateDto.getStatus());
		}

		return sb.toString();
	};

	private static final BiFunction<SearchCandidateDto, List<Object>, String> search_candidates = (searchCandidateDto,
			params) -> {

		StringBuilder sb = new StringBuilder();

		sb.append(
				" select candidate.id, candidate.name, candidate.mobile_number, candidate.email, candidate.qualification ,candidate.location ,candidate.created_username, "
						+ "candidate.created_date, candidate.modified_date, course_candidate.mode, "
						+ "course_candidate.batch_preference , course_candidate.batch_name, course_candidate.status,modified_user.user_name as modified_user ,course_candidate.course_name, "
						+ "payments.course_fees ,payments.amount_paid ,payments.balance_amount "
						+ "from candidates candidate "
						+ "left join user created_user on created_user.id = candidate.created_user  "
						+ "left join course course_candidate on course_candidate.candidate_id = candidate.id "
						+ "left join payment payments on payments.candidate_id = candidate.id "
						+ "left join user modified_user on modified_user.id=candidate.modified_user where 1 = 1 ");

		if (searchCandidateDto.getLoggedUserId() != 1) {
			sb.append("and candidate.created_username = ? ");
			params.add(searchCandidateDto.getLoggedUserName());
		}

		String parameters = null;
		parameters = search_candidate_parameters.apply(searchCandidateDto, params);

		if (Objects.nonNull(parameters) && !parameters.isEmpty()) {
			sb.append(parameters);
		}

		sb.append(" order by candidate.modified_date desc ");
		
		return sb.toString();
	};

	@Override
	public WrapperDto<SearchCandidateDto> searchCandidate(SearchCandidateDto searchCandidateDto, Pageable pageable) {
		logger.debug("Repository :: searchCandidate :: Entered");

		List<Object> params = new ArrayList<>();
		WrapperDto<SearchCandidateDto> wrapperDto = new WrapperDto<>();
		List<SearchCandidateDto> candidateList = new ArrayList<>();
		WeakReference<List<SearchCandidateDto>> weakListRef = new WeakReference<>(candidateList);

		try {

			Query query = entityManager.createNativeQuery(search_candidates.apply(searchCandidateDto, params));

			int count = 1;
			for (Object param : params) {
				query.setParameter(count++, param);
			}

			query.setFirstResult((pageable.getPageNumber() - 1) * pageable.getPageSize());
			query.setMaxResults(pageable.getPageSize());

			List<Object[]> results = query.getResultList();

			results.forEach(result -> {
				SearchCandidateDto searchCandidate = new SearchCandidateDto();

				searchCandidate.setId((Long) result[0]);
				searchCandidate.setName((String) result[1]);
				searchCandidate.setMobileNumber((String) result[2]);
				searchCandidate.setEmail((String) result[3]);
				searchCandidate.setQualification((String) result[4]);
				searchCandidate.setLocation((String) result[5]);
				searchCandidate.setCreatedUserName((String) result[6]);

				Timestamp createdDate = (Timestamp) result[7];

				if (Objects.nonNull(createdDate)) {
					searchCandidate.setCreatedDate(createdDate.toLocalDateTime());
				}

				Timestamp modifiedDate = (Timestamp) result[8];

				if (Objects.nonNull(modifiedDate)) {
					searchCandidate.setModifiedDate(modifiedDate.toLocalDateTime());
				}

				searchCandidate.setMode((String) result[9]);
				searchCandidate.setBatchPreference((String) result[10]);
				searchCandidate.setBatchName((String) result[11]);
				searchCandidate.setStatus((String) result[12]);
				searchCandidate.setModifiedUserName((String) result[13]);
				searchCandidate.setCourseName((String) result[14]);

				if (Objects.nonNull(result[15])) {
					searchCandidate.setCourseFees(((Number) result[15]).doubleValue());
				}

				if (Objects.nonNull(result[16])) {
					searchCandidate.setAmountPaid(((Number) result[16]).doubleValue());
				}

				if (Objects.nonNull(result[17])) {
					searchCandidate.setBalanceAmount(((Number) result[17]).doubleValue());
				}

				weakListRef.get().add(searchCandidate);

				searchCandidate = null;

			});

			wrapperDto.setResults(weakListRef.get());
			wrapperDto.setTotalRecords(getSearchCandidateCount(searchCandidateDto));

			candidateList = null;

		} catch (Exception e) {
			logger.error("Repository :: searchCandidate :: Exception :: " + e.getMessage());
		}
		logger.debug("Repository :: searchCandidate :: Exited");
		return wrapperDto;
	}

	private static final BiFunction<SearchCandidateDto, List<Object>, String> get_candidates_count = (
			searchCandidateDto, params) -> {

		StringBuilder sb = new StringBuilder();

		sb.append(
				" select count(*) from candidates candidate left join user created_user on created_user.id = candidate.created_user "
						+ "left join course course_candidate on course_candidate.candidate_id = candidate.id where 1 = 1 ");

		if (searchCandidateDto.getLoggedUserId() != 1) {
			sb.append("and candidate.created_username = ? ");
			params.add(searchCandidateDto.getLoggedUserName());
		}

		String parameters = null;
		parameters = search_candidate_parameters.apply(searchCandidateDto, params);

		if (Objects.nonNull(parameters) && !parameters.isEmpty()) {
			sb.append(parameters);
		}

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
