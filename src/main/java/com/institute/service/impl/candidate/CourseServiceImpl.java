package com.institute.service.impl.candidate;

import java.time.LocalDateTime;
import java.util.Objects;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.institute.dto.candidate.CourseDto;
import com.institute.entity.candidate.CourseEntity;
import com.institute.repository.candidate.CourseRepository;
import com.institute.service.candidate.CourseService;

@Service
public class CourseServiceImpl implements CourseService {

	private static final Logger logger = LoggerFactory.getLogger(CandidateServiceImpl.class);

	@Autowired
	CourseRepository courseRepository;

	@Autowired
	ModelMapper modelMapper;

	@Override
	public Long createCourse(CourseDto courseDto, String username) {
		logger.debug("Service :: createCourse :: Entered");

		CourseEntity courseEntity = null;
		CourseEntity course = null;
		CourseEntity saveCourseEntity = null;
		Long id = null;

		try {
			courseEntity = courseRepository.getCourseByCandidateId(courseDto.getCandidateId());

			if (Objects.nonNull(courseEntity)) {
				course = modelMapper.map(courseDto, CourseEntity.class);
				course.setModifiedDate(LocalDateTime.now());
				course.setModifiedUser(courseDto.getCreatedUser());
				course.setCandidateId(courseDto.getCandidateId());

				saveCourseEntity = courseRepository.save(course);

				id = saveCourseEntity.getId();
			} else {
				course = modelMapper.map(courseDto, CourseEntity.class);
				course.setCreatedUserName(username);
				course.setCreatedDate(LocalDateTime.now());

				saveCourseEntity = courseRepository.save(course);

				id = saveCourseEntity.getId();
			}
			courseEntity = null;
			course = null;
			saveCourseEntity = null;

		} catch (Exception e) {
			logger.error("Service :: createCourse :: Exception :: " + e.getMessage());
		}
		logger.debug("Service :: createCourse :: Exited");
		return id;
	}

	@Override
	public CourseDto getCourseById(Long candidateId) {
		logger.debug("Service :: getCourseById :: Entered");

		CourseEntity courseEntity = null;

		CourseDto course = null;

		try {
			courseEntity = courseRepository.getCourseByCandidateId(candidateId);
			course = modelMapper.map(courseEntity, CourseDto.class);

			courseEntity = null;

		} catch (Exception e) {
			logger.error("Service :: getCourseById :: Exception :: " + e.getMessage());
		}
		return course;
	}

}
