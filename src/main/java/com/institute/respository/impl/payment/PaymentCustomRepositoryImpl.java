package com.institute.respository.impl.payment;

import java.lang.ref.WeakReference;
import java.sql.Timestamp;
import java.time.LocalDate;
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
import com.institute.dto.payment.SearchPaymentDto;
import com.institute.repository.payment.PaymentCustomRepository;
import com.institute.utility.CommonUtils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

@Repository
public class PaymentCustomRepositoryImpl implements PaymentCustomRepository {

	private static final Logger logger = LoggerFactory.getLogger(PaymentCustomRepositoryImpl.class);

	@Autowired
	EntityManager entityManager;

	private static final BiFunction<SearchPaymentDto, List<Object>, String> search_payment_parameters = (
			searchPaymentDto, params) -> {

		StringBuilder sb = new StringBuilder();

		if (Objects.nonNull(searchPaymentDto.getSearch()) && !searchPaymentDto.getSearch().isEmpty()) {
			sb.append(" and (upper(candidate.name) like upper(?) or upper(candidate.mobile_number) like upper(?) "
					+ "or upper(candidate.email) like upper(?)) ");
			params.add(CommonUtils.appendLikeOperator(searchPaymentDto.getSearch()));
			params.add(CommonUtils.appendLikeOperator(searchPaymentDto.getSearch()));
			params.add(CommonUtils.appendLikeOperator(searchPaymentDto.getSearch()));
		}

		if (Objects.nonNull(searchPaymentDto.getBatchPreference())
				&& !searchPaymentDto.getBatchPreference().isEmpty()) {
			sb.append(" and course.batch_preference = ? ");
			params.add(searchPaymentDto.getBatchPreference());
		}

		if (Objects.nonNull(searchPaymentDto.getMode()) && !searchPaymentDto.getMode().isEmpty()) {
			sb.append(" and course.mode = ? ");
			params.add(searchPaymentDto.getMode());
		}

		if (Objects.nonNull(searchPaymentDto.getFilterType())) {

			if (searchPaymentDto.getFilterType().equalsIgnoreCase("TODAY")) {
				sb.append(" and date(payment.modified_date) = curdate()");
			} else if (searchPaymentDto.getFilterType().equalsIgnoreCase("THIS WEEK")) {
				sb.append(" and week(payment.modified_date) = week(now())");
			} else if (searchPaymentDto.getFilterType().equalsIgnoreCase("CUSTOM")
					&& Objects.nonNull(searchPaymentDto.getFromDate())
					&& Objects.nonNull(searchPaymentDto.getToDate())) {
				sb.append(
						" and (date(payment.modified_date) between str_to_date(?, '%Y-%m-%d') and str_to_date(?, '%Y-%m-%d'))");
				params.add(searchPaymentDto.getFromDate());
				params.add(searchPaymentDto.getToDate());
			}

		}

		return sb.toString();

	};

	private static final BiFunction<SearchPaymentDto, List<Object>, String> search_payment = (searchPaymentDto,
			params) -> {

		StringBuilder sb = new StringBuilder();

		sb.append(
				"select payment.id,payment.course_fees,payment.amount_paid,payment.balance_amount,payment.discount,payment.payment_mode, "
						+ "candidate.name,candidate.mobile_number,course.course_name,course.status,course.batch_name,payment.candidate_id,payment.paid_date from payment payment "
						+ "left join candidates candidate on candidate.id = payment.candidate_id "
						+ "left join course course on course.candidate_id = payment.candidate_id  where 1 = 1 ");

		if (searchPaymentDto.getLoggedUserId() != 1) {
			sb.append("and payment.created_username = ? ");
			params.add(searchPaymentDto.getLoggedUserName());
		}

		String parameters = null;
		parameters = search_payment_parameters.apply(searchPaymentDto, params);

		if (Objects.nonNull(parameters) && !parameters.isEmpty()) {
			sb.append(parameters);
		}

		sb.append(" order by payment.modified_date desc ");

		return sb.toString();

	};

	@Override
	public WrapperDto<SearchPaymentDto> searchPayments(SearchPaymentDto searchPaymentDto, Pageable pageable) {
		logger.debug("Repository :: searchPayments :: Entered");

		List<Object> params = new ArrayList<>();
		WrapperDto<SearchPaymentDto> wrapperDto = new WrapperDto<>();
		List<SearchPaymentDto> paymentList = new ArrayList<>();
		WeakReference<List<SearchPaymentDto>> weakListRef = new WeakReference<>(paymentList);

		try {

			Query query = entityManager.createNativeQuery(search_payment.apply(searchPaymentDto, params));

			int count = 1;
			for (Object param : params) {
				query.setParameter(count++, param);
			}

			query.setFirstResult((pageable.getPageNumber() - 1) * pageable.getPageSize());
			query.setMaxResults(pageable.getPageSize());

			List<Object[]> results = query.getResultList();

			results.forEach(result -> {
				SearchPaymentDto searchPayment = new SearchPaymentDto();

				searchPayment.setId((Long) result[0]);
				searchPayment.setCourseFees(result[1] != null ? ((Number) result[1]).doubleValue() : 0.0);
				searchPayment.setAmountPaid(result[2] != null ? ((Number) result[2]).doubleValue() : 0.0);
				searchPayment.setBalanceAmount(result[3] != null ? ((Number) result[3]).doubleValue() : 0.0);

				searchPayment.setDiscount(result[4] != null ? ((Number) result[4]).doubleValue() : 0.0);
				searchPayment.setPaymentMode((String) result[5]);
				searchPayment.setName((String) result[6]);
				searchPayment.setMobileNumber((String) result[7]);
				searchPayment.setCourseName((String) result[8]);
				searchPayment.setStatus((String) result[9]);
				searchPayment.setBatchName((String) result[10]);
				searchPayment.setCandidateId((Long) result[11]);

				java.sql.Date paidDate = (java.sql.Date) result[12];

				if (Objects.nonNull(paidDate)) {
					searchPayment.setPaidDate(paidDate.toLocalDate());
				}

				weakListRef.get().add(searchPayment);

				searchPayment = null;

			});

			wrapperDto.setResults(weakListRef.get());
			wrapperDto.setTotalRecords(getSearchPaymentCount(searchPaymentDto));

			paymentList = null;

		} catch (Exception e) {
			logger.error("Repository :: searchPayment :: Exception :: " + e.getMessage());
		}
		logger.debug("Repository :: searchPayment :: Exited");
		return wrapperDto;
	}

	private static final BiFunction<SearchPaymentDto, List<Object>, String> get_payment_count = (searchPaymentDto,
			params) -> {

		StringBuilder sb = new StringBuilder();

		sb.append("select count(*) from payment payment "
				+ "left join candidates candidate on candidate.id = payment.candidate_id "
				+ "left join course course on course.candidate_id = payment.candidate_id  where 1 = 1 ");

		if (searchPaymentDto.getLoggedUserId() != 1) {
			sb.append("and payment.created_username = ? ");
			params.add(searchPaymentDto.getLoggedUserName());
		}

		String parameters = null;
		parameters = search_payment_parameters.apply(searchPaymentDto, params);

		if (Objects.nonNull(parameters) && !parameters.isEmpty()) {
			sb.append(parameters);
		}

		return sb.toString();

	};

	private Long getSearchPaymentCount(SearchPaymentDto searchPaymentDto) {
		logger.error("Repository :: getSearchPaymentCount :: Entered");

		Long totalRecords = null;
		List<Object> params = new ArrayList<>();
		try {

			Query query = entityManager.createNativeQuery(get_payment_count.apply(searchPaymentDto, params));

			int count = 1;
			for (Object param : params) {
				query.setParameter(count++, param);
			}

			totalRecords = (Long) query.getSingleResult();
		} catch (Exception e) {
			logger.error("Repository :: getSearchPaymentCount :: Exception :: " + e.getMessage());
		}
		logger.debug("Repository :: getSearchPaymentCount :: Exited");
		return totalRecords;

	}

}
