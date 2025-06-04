package com.institute.controller.candidate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.institute.controller.BaseController;
import com.institute.dto.WrapperDto;
import com.institute.dto.candidate.CommentDto;
import com.institute.service.candidate.CommentService;
import com.institute.utility.CommonConstants;

@RestController
@RequestMapping("/comments")
public class CommentController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(CommentController.class);

	@Autowired
	CommentService commentService;

	@PostMapping("/create")
	public ResponseEntity<?> createComment(@RequestBody CommentDto commentDto,
			@RequestHeader("username") String username) throws Exception {
		logger.debug("Controller :: createComment :: Entered");

		boolean status = commentService.createComment(commentDto, username);

		logger.debug("Controller :: createComment :: Exited");

		if (!status) {
			return new ResponseEntity<>(buildResponse(CommonConstants.I_0001), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(buildResponse(CommonConstants.I_0002), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<?> getComments(@RequestBody CommentDto commentDto) {
		logger.debug("Controller :: getComments :: Entered");

		WrapperDto<CommentDto> comments = commentService.getComments(commentDto);

		logger.debug("Controller :: getComments :: Exited");
		return new ResponseEntity<>(comments, HttpStatus.OK);
	}

	@PostMapping("/dashboardComment")
	public ResponseEntity<?> getCommentRemindInDashboard(@RequestBody CommentDto commentDto,
			@RequestHeader("username") String username) {
		logger.debug("Controller :: getCommentRemindInDashboard :: Entered");

		WrapperDto<CommentDto> comments = commentService.getCommentRemindInDashboard(commentDto, username);

		logger.debug("Controller :: getCommentRemindInDashboard :: Exited");
		return new ResponseEntity<>(comments, HttpStatus.OK);
	}
}
