package com.institute.service.candidate;

import com.institute.dto.candidate.CourseDto;

public interface CourseService {

	Long createCourse(CourseDto courseDto, String username);

}
