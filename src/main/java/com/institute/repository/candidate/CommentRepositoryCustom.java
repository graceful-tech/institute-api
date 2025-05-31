package com.institute.repository.candidate;

import com.institute.dto.WrapperDto;
import com.institute.dto.candidate.CommentDto;

public interface CommentRepositoryCustom {

	WrapperDto<CommentDto> getComments(CommentDto commentDto);

}
