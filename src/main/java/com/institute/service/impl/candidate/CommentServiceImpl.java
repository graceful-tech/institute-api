package com.institute.service.impl.candidate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

import com.institute.dto.WrapperDto;
import com.institute.dto.candidate.CommentDto;
import com.institute.entity.candidate.CandidateEntity;
import com.institute.entity.candidate.CommentEntity;
import com.institute.entity.user.UserEntity;
import com.institute.exception.InstituteException;
import com.institute.repository.candidate.CommentRepository;
import com.institute.repository.candidate.CommentRepositoryCustom;
import com.institute.repository.user.UserRepository;
import com.institute.service.candidate.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	private static final Logger logger = LoggerFactory.getLogger(CommentServiceImpl.class);

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private CommentRepositoryCustom commentRepositoryCustom;

	@Override
	public boolean createComment(CommentDto commentDto, String username) {
		logger.debug("Service :: createComment :: Entered");
		boolean status = false;

		try {
			Long userId = userRepository.getUserId(username);

			CommentEntity commentEntity = convertDtoToEntity(commentDto, userId);
			CommentEntity savedEntity = commentRepository.save(commentEntity);

			commentEntity = null;
			savedEntity = null;
			userId = null;
			
			status = true;

		} catch (Exception e) {
			logger.error("Service :: createComment :: Exception :: " + e.getMessage());

			if (e instanceof InstituteException) {
				throw e;
			}
		}
		logger.debug("Service :: createComment :: Exited");
		return status;
	}

	@Override
	public WrapperDto<CommentDto> getComments(CommentDto commentDto) {
		logger.debug("Service :: getComments :: Entered");
		WrapperDto<CommentDto> wrapperDto = new WrapperDto<>();
		List<CommentDto> comments = new ArrayList<>();
		try {
			commentDto.setCandidateId(commentDto.getCandidateId());
			List<CommentEntity> entities = commentRepository.getComments(commentDto.getCandidateId());
			entities.forEach(entity -> {
				comments.add(convertEntityToDto(entity));
			});

			wrapperDto.setResults(comments);
		} catch (Exception e) {
			logger.error("Service :: getComments :: Exception :: " + e.getMessage());
		}
		logger.debug("Service :: getComments :: Exited");
		return wrapperDto;
	}

	public CommentEntity convertDtoToEntity(CommentDto commentDto, Long userId) {
		logger.debug("Service :: convertDtoToEntity :: Entered");
		CommentEntity commentEntity = null;
		try {
			commentEntity = modelMapper.map(commentDto, CommentEntity.class);

			CandidateEntity candidateEntity = new CandidateEntity();
			candidateEntity.setId(commentDto.getCandidateId());
			commentEntity.setCandidate(candidateEntity);

			UserEntity userEntity = new UserEntity();
			userEntity.setId(userId);
			commentEntity.setUser(userEntity);

			commentEntity.setCreatedDate(LocalDateTime.now());
		} catch (Exception e) {
			logger.error("Service :: convertDtoToEntity :: Exception :: " + e.getMessage());
		}
		logger.debug("Service :: convertDtoToEntity :: Exited");
		return commentEntity;
	}

	public CommentDto convertEntityToDto(CommentEntity commentEntity) {
		logger.debug("Service :: convertEntityToDto :: Entered");
		CommentDto commentDto = null;
		try {
			commentDto = modelMapper.map(commentEntity, CommentDto.class);
			commentDto.setUserName(commentEntity.getUser().getName());

		} catch (Exception e) {
			logger.error("Service :: convertEntityToDto :: Exception :: " + e.getMessage());
		}
		logger.debug("Service :: convertEntityToDto :: Exited");
		return commentDto;
	}

	@Override
	public WrapperDto<CommentDto> getCommentRemindInDashboard(CommentDto commentDto,
			@RequestHeader("username") String username) {
		logger.debug("Service :: getCommentRemindInDashboard :: Entered");
		WrapperDto<CommentDto> wrapperDto = null;
		try {
			wrapperDto = commentRepositoryCustom.getComments(commentDto, username);

		} catch (Exception e) {
			logger.error("Service :: getCommentRemindInDashboard :: Exception :: " + e.getMessage());
		}
		logger.debug("Service :: getCommentRemindInDashboard :: Exited");
		return wrapperDto;
	}
}
