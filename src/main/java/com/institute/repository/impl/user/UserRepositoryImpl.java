package com.institute.repository.impl.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.institute.dto.WrapperDto;
import com.institute.dto.user.UserDto;
import com.institute.entity.user.UserEntity;
import com.institute.repository.user.UserRepositoryCustom;
import com.institute.utility.CommonUtils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public class UserRepositoryImpl implements UserRepositoryCustom {
	private static final Logger logger = LoggerFactory.getLogger(UserRepositoryImpl.class);

	@PersistenceContext
	EntityManager entityManager;

	private static final BiFunction<UserDto, List<Object>, String> search_users = (searchUserDto, params) -> {
		StringBuilder sb = new StringBuilder();
		sb.append("select user.id, user.name, user.mobile_number, user.email, user.user_name from user as user "
				+ " where 1 = 1 ");

		if (Objects.nonNull(searchUserDto.getSearch()) && !searchUserDto.getSearch().isEmpty()) {
			sb.append(" and (upper(user.name) like upper(?) or upper(user.mobile_number) like upper(?) "
					+ "or upper(user.email) like upper(?)) or upper(user.user_name) like upper(?) ");
			params.add(CommonUtils.appendLikeOperator(searchUserDto.getSearch()));
			params.add(CommonUtils.appendLikeOperator(searchUserDto.getSearch()));
			params.add(CommonUtils.appendLikeOperator(searchUserDto.getSearch()));
			params.add(CommonUtils.appendLikeOperator(searchUserDto.getSearch()));
		}

		return sb.toString();
	};

	@SuppressWarnings("unchecked")
	@Override
	public WrapperDto<UserDto> searchUsers(UserDto searchUserDto, UserEntity userEntity, Pageable pageable) {
		logger.debug("Repository :: searchUsers :: Entered");
		WrapperDto<UserDto> wrapperDto = new WrapperDto<>();
		List<UserDto> users = new ArrayList<>();
		List<Object> params = new ArrayList<>();
		try {
//			searchUserDto.setSignedUserName(userEntity.getUserName());

			Query query = entityManager.createNativeQuery(search_users.apply(searchUserDto, params));

			int count = 1;
			for (Object param : params) {
				query.setParameter(count++, param);
			}

			query.setFirstResult((pageable.getPageNumber() - 1) * pageable.getPageSize());
			query.setMaxResults(pageable.getPageSize());

			List<Object[]> results = query.getResultList();

			results.forEach(result -> {
				UserDto user = new UserDto();

				Long userId = (Long) result[0];
				user.setId(userId);

				user.setName((String) result[1]);
				user.setMobileNumber((String) result[2]);
				user.setEmail((String) result[3]);
				user.setUserName((String) result[4]);

				users.add(user);
				user = null;
			});

			wrapperDto.setResults(users);
			wrapperDto.setTotalRecords(getUsersCount(searchUserDto));
		} catch (Exception e) {
			logger.error("Repository :: searchUsers :: Exception :: " + e.getMessage());
		}
		logger.debug("Repository :: searchUsers :: Exited");
		return wrapperDto;
	}

	private Long getUsersCount(UserDto searchUserDto) {
		logger.debug("Repository :: getUsersCount :: Entered");
		Long totalRecords = null;
		List<Object> params = new ArrayList<>();
		try {

			Query query = entityManager.createNativeQuery(get_users_count.apply(searchUserDto, params));

			int count = 1;
			for (Object param : params) {
				query.setParameter(count++, param);
			}

			totalRecords = (Long) query.getSingleResult();

		} catch (Exception e) {
			logger.error("Repository :: getUsersCount :: Exception :: " + e.getMessage());
		}
		logger.debug("Repository :: getUsersCount :: Exited");
		return totalRecords;
	}

	private static final BiFunction<UserDto, List<Object>, String> get_users_count = (searchUserDto, params) -> {
		StringBuilder sb = new StringBuilder();
		sb.append("select count(*) from user as user " + " where 1 = 1 ");

		if (Objects.nonNull(searchUserDto.getSearch()) && !searchUserDto.getSearch().isEmpty()) {
			sb.append(" and (upper(user.name) like upper(?) or upper(user.mobile_number) like upper(?) "
					+ "or upper(user.email) like upper(?)) or upper(user.user_name) like upper(?) ");
			params.add(CommonUtils.appendLikeOperator(searchUserDto.getSearch()));
			params.add(CommonUtils.appendLikeOperator(searchUserDto.getSearch()));
			params.add(CommonUtils.appendLikeOperator(searchUserDto.getSearch()));
			params.add(CommonUtils.appendLikeOperator(searchUserDto.getSearch()));
		}

		return sb.toString();
	};

}
