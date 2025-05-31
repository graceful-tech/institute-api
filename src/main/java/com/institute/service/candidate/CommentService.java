package com.institute.service.candidate;

import com.institute.dto.WrapperDto;
import com.institute.dto.candidate.CommentDto;

public interface CommentService {

	boolean createComment(CommentDto commentDto, String userName);

	WrapperDto<CommentDto> getComments(CommentDto commentDto);
	
	WrapperDto<CommentDto> getCommentRemindInDashboard(CommentDto commentDto);
}
