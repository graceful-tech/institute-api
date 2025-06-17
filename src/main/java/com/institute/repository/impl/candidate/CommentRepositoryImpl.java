package com.institute.repository.impl.candidate;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.institute.dto.WrapperDto;
import com.institute.dto.candidate.CommentDto;
import com.institute.repository.candidate.CommentRepositoryCustom;
import com.institute.repository.user.UserRepository;
import com.institute.utility.CommonConstants;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public class CommentRepositoryImpl implements CommentRepositoryCustom {

	private static final Logger logger = LoggerFactory.getLogger(CommentRepositoryImpl.class);

	@PersistenceContext
	EntityManager entityManager;

	@Autowired
	UserRepository userRepository;

	public static final BiFunction<CommentDto, List<Object>, String> get_comments_reminder_parameters = (commentDto,
			params) -> {
		StringBuilder sb = new StringBuilder();

		if (Objects.nonNull(commentDto.getFilterType())) {

			if (commentDto.getFilterType().equalsIgnoreCase(CommonConstants.TODAY)) {
				sb.append(" and date(comment.created_date) = curdate()");
			} else if (commentDto.getFilterType().equalsIgnoreCase("THIS WEEK")) {
				sb.append(
						" and month(comment.created_date) = month(curdate()) and year(comment.created_date) = year(curdate()) and week(comment.created_date) = week(now())");
			} else if (commentDto.getFilterType().equalsIgnoreCase(CommonConstants.MONTH)) {
				sb.append(
						" and month(comment.created_date) = month(curdate()) and year(comment.created_date) = year(curdate()) ");

			} else if (commentDto.getFilterType().equalsIgnoreCase(CommonConstants.CUSTOM)) {
				if (Objects.nonNull(commentDto.getFromDate()) && Objects.nonNull(commentDto.getToDate())) {
					sb.append(
							" and (date(comment.created_date) between str_to_date(?, '%Y-%m-%d') and str_to_date(?, '%Y-%m-%d'))");
					params.add(commentDto.getFromDate());
					params.add(commentDto.getToDate());
				}
			} else {
				sb.append(" and comment.created_date >= NOW() - INTERVAL 10 DAY ");
			}

		}

		return sb.toString();
	};
	/*
	 * This method is used to search followup comments.
	 */
	private static final BiFunction<CommentDto, List<Object>, String> get_comments_reminder = (commentDto, params) -> {
		StringBuilder sb = new StringBuilder();
		sb.append("select  candidate.name candidate_name, comment.comment, comment.created_date, "
				+ "user.name user_name , candidate.id candidate_id  from comments comment "
				+ "join candidates candidate on candidate.id = comment.candidate_id "
				+ "join user user on user.id = comment.user_id where 1 = 1 ");

		String parameters = get_comments_reminder_parameters.apply(commentDto, params);

		if (Objects.nonNull(parameters) && !parameters.isEmpty()) {
			sb.append(parameters);
		}

		if (commentDto.getSignedUserId() != 1) {
			sb.append(" and comment.user_id = ? ");

			params.add(commentDto.getSignedUserId());
		}

		if (Objects.nonNull(commentDto.getUserId())) {
			sb.append(" and comment.user_id = ? ");

			params.add(commentDto.getUserId());
		}

		sb.append(" order by comment.created_date desc");

		return sb.toString();
	};

	@SuppressWarnings("unchecked")
	@Override
	public WrapperDto<CommentDto> getComments(CommentDto commentDto, String userName) {
		logger.debug("Repository :: getComments :: Entered");
		WrapperDto<CommentDto> wrapperDto = new WrapperDto<>();
		List<CommentDto> comments = new ArrayList<>();
		List<Object> params = new ArrayList<>();
		try {

			commentDto.setSignedUserId(userRepository.getUserId(userName));

			if (Objects.nonNull(commentDto.getUserName()) && !commentDto.getUserName().isEmpty()) {
				commentDto.setUserId(userRepository.getUserId(commentDto.getUserName()));
			}

			Query query = entityManager.createNativeQuery(get_comments_reminder.apply(commentDto, params));

			int count = 1;
			for (Object param : params) {
				query.setParameter(count++, param);
			}

			List<Object[]> results = query.getResultList();

			results.forEach(result -> {
				CommentDto comment = new CommentDto();

				comment.setCandidateName((String) result[0]);
				comment.setComment((String) result[1]);

				Timestamp createdDate = (Timestamp) result[2];

				if (Objects.nonNull(createdDate)) {
					comment.setCreatedDate(createdDate.toLocalDateTime());
				}
				comment.setUserName((String) result[3]);
				comment.setCandidateId((Long) result[4]);
				comments.add(comment);
			});

			wrapperDto.setResults(comments);

		} catch (Exception e) {
			logger.error("Repository :: getComments :: Exception :: " + e.getMessage());
		}
		logger.debug("Repository :: getComments :: Exited");
		return wrapperDto;
	}

}
