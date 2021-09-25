package com.curriculum.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.curriculum.dto.Discussion;
import com.curriculum.entity.DiscussionEntity;
import com.curriculum.exception.BusinessServiceException;
import com.curriculum.exception.ConstraintValidationException;
import com.curriculum.exception.NotFoundException;
import com.curriculum.exception.UnitNotFoundException;
import com.curriculum.exception.QuestionNotFoundException;
import com.curriculum.service.DiscussionService;
import com.curriculum.util.Response;
import com.curriculum.util.ResponseUtil;

@RestController
@RequestMapping("api/discussion")
public class DiscussionController {
	@Autowired
	private DiscussionService discussionService;
	private Logger logger = Logger.getLogger(DiscussionController.class);

	@PostMapping
	public ResponseEntity<Response> addDiscussion(@Valid @RequestBody Discussion discussion) {
		Response response = new Response();
		ResponseEntity<Response> responseEntity = null;
		Long questionNo = null;
		try {
			questionNo = discussionService.addDiscussion(discussion);
			discussion.setQuestionNo(questionNo);
			responseEntity = ResponseUtil.getResponse(200, "Discussion details added successfully!", discussion);
		} catch (BusinessServiceException e) {
			responseEntity = ResponseUtil.getResponse(500, e.getMessage());
		} catch (NotFoundException e) {
			if (e instanceof ConstraintValidationException) {
				responseEntity = ResponseUtil.getResponse(422, e.getMessage());
			} else {
				responseEntity = ResponseUtil.getResponse(404, e.getMessage());
			}
		}
		return responseEntity;
	}

	@GetMapping("/{unitNo}")
	public ResponseEntity<Response> getDiscussionByUnitNo(@PathVariable("unitNo") String unitNo) {
		ResponseEntity responseEntity = null;
		List<DiscussionEntity> discussionsList = new ArrayList<>();
		try {
			discussionsList = discussionService.getDiscussionByUnitNo(unitNo);
			if (!discussionsList.isEmpty()) {
				responseEntity = ResponseUtil.getResponse(200, "Success!", discussionsList);
			} else {
				responseEntity = ResponseUtil.getResponse(404, "No discussion found!");
			}
		} catch (BusinessServiceException e) {
			responseEntity = ResponseUtil.getResponse(500, e.getMessage());
		} catch (NotFoundException e) {
			responseEntity = ResponseUtil.getResponse(404, e.getMessage());
		}
		return responseEntity;
	}

	@PutMapping("/{questionNo}")
	public ResponseEntity<Response> updateDiscussion(@PathVariable("questionNo") Long questionNo,
			@RequestBody Discussion discussion) {
		ResponseEntity<Response> responseEntity = null;
		DiscussionEntity discussionEntity = null;
		try {
			discussionEntity = discussionService.updateDiscussion(questionNo, discussion);
			responseEntity = ResponseUtil.getResponse(200, "Discussion details updated successfully!",
					discussionEntity);
		} catch (BusinessServiceException e) {
			responseEntity = ResponseUtil.getResponse(500, e.getMessage());
		} catch (NotFoundException e) {
			if (e instanceof ConstraintValidationException) {
				responseEntity = ResponseUtil.getResponse(422, e.getMessage());
			} else {
				responseEntity = ResponseUtil.getResponse(404, e.getMessage());
			}
		}
		return responseEntity;
	}

	@DeleteMapping("/{questionNo}")
	public ResponseEntity<Response> deleteDiscussion(@PathVariable("questionNo") Long questionNo) {
		Response response = new Response();
		ResponseEntity<Response> responseEntity = null;
		DiscussionEntity discussionEntity = null;
		try {
			discussionEntity = discussionService.deleteDiscussion(questionNo);
			if (discussionEntity != null) {
				responseEntity = ResponseUtil.getResponse(200, "Discussion details deleted successfully!",
						discussionEntity);
			} else {
				responseEntity = ResponseUtil.getResponse(500, "Internal Server Error!");
			}
		} catch (BusinessServiceException e) {
			responseEntity = ResponseUtil.getResponse(500, e.getMessage());
		} catch (NotFoundException e) {
			responseEntity = ResponseUtil.getResponse(404, e.getMessage());
		}
		return responseEntity;
	}

	@GetMapping
	public ResponseEntity<Response> getParticularDiscussion(@RequestParam("questionNo") Long questionNo) {
		ResponseEntity<Response> responseEntity = null;
		DiscussionEntity discussionEntity = null;
		try {
			discussionEntity = discussionService.getParticularDiscussion(questionNo);
			responseEntity = ResponseUtil.getResponse(200, "Success!", discussionEntity);
		} catch (BusinessServiceException e) {
			responseEntity = ResponseUtil.getResponse(500, e.getMessage());
		} catch (NotFoundException e) {
			responseEntity = ResponseUtil.getResponse(404, e.getMessage());
		}
		return responseEntity;
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Response> validationFailed(MethodArgumentNotValidException e) {
		logger.error("Validation fails, Check your input!");
		ResponseEntity<Response> responseEntity = null;
		responseEntity = ResponseUtil.getResponse(422, "Validation fails!");
		return responseEntity;
	}
}
