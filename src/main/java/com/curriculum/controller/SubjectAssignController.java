package com.curriculum.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.curriculum.dto.SubjectAssign;
import com.curriculum.entity.SubjectAssignEntity;
import com.curriculum.exception.BusinessServiceException;
import com.curriculum.exception.ConstraintValidationException;
import com.curriculum.exception.NotFoundException;
import com.curriculum.service.SubjectAssignService;
import com.curriculum.util.Response;
import com.curriculum.util.ResponseUtil;

@RestController
@RequestMapping("api/subjectassign")
@CrossOrigin("http://localhost:4200")
public class SubjectAssignController {

	@Autowired
	private SubjectAssignService subjectAssignService;

	@PostMapping
	public ResponseEntity<Response> addSubjectAssign(@RequestBody SubjectAssign subjectAssign) {
		ResponseEntity<Response> responseEntity = null;
		Long id = null;
		try {
			id = subjectAssignService.addSubjectAssign(subjectAssign);
			if (id > 0) {
				responseEntity = ResponseUtil.getResponse(200, "Subject assigned for class successfully!",
						subjectAssign);
			} else {
				responseEntity = ResponseUtil.getResponse(500, "Internal Server Error!", subjectAssign);
			}
		} catch (BusinessServiceException e) {
			responseEntity = ResponseUtil.getResponse(500, e.getMessage(), subjectAssign);
		} catch (NotFoundException e) {
			if (e instanceof ConstraintValidationException) {
				responseEntity = ResponseUtil.getResponse(422, e.getMessage(), subjectAssign);
			} else {
				responseEntity = ResponseUtil.getResponse(404, e.getMessage(), subjectAssign);
			}
		}
		return responseEntity;
	}

	@GetMapping("/{roomNo}")
	public ResponseEntity<Response> getSubjects(@PathVariable("roomNo") Long roomNo) {
		ResponseEntity<Response> responseEntity = null;
		List<SubjectAssignEntity> subjectsList = new ArrayList<>();
		try {
			subjectsList = subjectAssignService.getSubjects(roomNo);
			if (!subjectsList.isEmpty()) {
				responseEntity = ResponseUtil.getResponse(200, "Success!", subjectsList);
			} else {
				responseEntity = ResponseUtil.getResponse(404, "No subject name found!", subjectsList);
			}
		} catch (BusinessServiceException e) {
			responseEntity = ResponseUtil.getResponse(500, e.getMessage(), subjectsList);
		} catch (NotFoundException e) {
			responseEntity = ResponseUtil.getResponse(404, e.getMessage(), subjectsList);
		}
		return responseEntity;
	}

	@GetMapping("/{roomNo}/{code}")
	public ResponseEntity<Response> getAssignId(@PathVariable("roomNo") Long roomNo,
			@PathVariable("code") String subjectCode) {
		ResponseEntity<Response> responseEntity = null;
		Long assignId = null;
		try {
			assignId = subjectAssignService.getAssignId(roomNo, subjectCode);
			if (assignId != 0) {
				responseEntity = ResponseUtil.getResponse(200, "Success!", assignId);
			} else {
				responseEntity = ResponseUtil.getResponse(404, "No Assign Id found!", assignId);
			}
		} catch (BusinessServiceException e) {
			responseEntity = ResponseUtil.getResponse(500, e.getMessage(), assignId);
		} catch (NotFoundException e) {
			responseEntity = ResponseUtil.getResponse(404, e.getMessage(), assignId);
		}
		return responseEntity;
	}

	@GetMapping("/subject/{id}/{roomNo}")
	public ResponseEntity<Response> getSubjectCode(@PathVariable("id") Long id, @PathVariable("roomNo") Long roomNo) {
		ResponseEntity<Response> responseEntity = null;
		String subjectCode = null;
		try {
			subjectCode = subjectAssignService.getSubjectCode(id, roomNo);
			if (subjectCode != null) {
				responseEntity = ResponseUtil.getResponse(200, "Success!", subjectCode);
			} else {
				responseEntity = ResponseUtil.getResponse(404, "No Assign Id found!" + " " + id + "!", subjectCode);
			}
		} catch (BusinessServiceException e) {
			responseEntity = ResponseUtil.getResponse(500, e.getMessage(), subjectCode);
		} catch (NotFoundException e) {
			responseEntity = ResponseUtil.getResponse(404, e.getMessage(), subjectCode);
		}
		return responseEntity;
	}

	@GetMapping("/subject/{id}")
	public ResponseEntity<Response> getRoomNo(@PathVariable("id") Long id) {
		ResponseEntity<Response> responseEntity = null;
		Long roomNo = null;
		try {
			roomNo = subjectAssignService.getRoomNo(id);
			if (roomNo != 0) {
				responseEntity = ResponseUtil.getResponse(200, "Success!", roomNo);
			} else {
				responseEntity = ResponseUtil.getResponse(404, "No Assign Id found!" + " " + id + "!", roomNo);
			}
		} catch (BusinessServiceException e) {
			responseEntity = ResponseUtil.getResponse(500, e.getMessage(), roomNo);
		} catch (NotFoundException e) {
			responseEntity = ResponseUtil.getResponse(404, e.getMessage(), roomNo);
		}
		return responseEntity;
	}

	@DeleteMapping("/{roomNo}")
	public ResponseEntity<Response> deleteSubjectAssign(@PathVariable("roomNo") Long roomNo) {
		ResponseEntity<Response> responseEntity = null;
		Long noOfRowsDeleted = null;
		try {
			noOfRowsDeleted = subjectAssignService.deleteSubjectAssign(roomNo);
			if (noOfRowsDeleted > 0) {
				responseEntity = ResponseUtil.getResponse(200, "Subject Assign details deleted successfully!",
						noOfRowsDeleted);
			}
		} catch (BusinessServiceException e) {
			responseEntity = ResponseUtil.getResponse(500, e.getMessage(), noOfRowsDeleted);
		} catch (NotFoundException e) {
			responseEntity = ResponseUtil.getResponse(404, e.getMessage(), noOfRowsDeleted);
		}
		return responseEntity;
	}

	@GetMapping("/list/{assignList}")
	public ResponseEntity<Response> getRoomNoList(@PathVariable("assignList") List<Long> assignList) {
		ResponseEntity<Response> responseEntity = null;
		List<Long> roomNoList = null;
		try {
			roomNoList = subjectAssignService.getRoomNoList(assignList);
			if (!roomNoList.isEmpty()) {
				responseEntity = ResponseUtil.getResponse(200, "Success!", roomNoList);
			} else {
				responseEntity = ResponseUtil.getResponse(404, "No Assign Id found!", roomNoList);
			}
		} catch (BusinessServiceException e) {
			responseEntity = ResponseUtil.getResponse(500, e.getMessage(), roomNoList);
		} catch (NotFoundException e) {
			responseEntity = ResponseUtil.getResponse(404, e.getMessage(), roomNoList);
		}
		return responseEntity;
	}

	@GetMapping("/list/{assignList}/{roomNo}")
	public ResponseEntity<Response> getSubjectCodeList(@PathVariable("assignList") List<Long> assignList,
			@PathVariable("roomNo") Long roomNo) {
		ResponseEntity<Response> responseEntity = null;
		List<String> subjectCodeList = null;
		try {
			subjectCodeList = subjectAssignService.getSubjectCodeList(assignList, roomNo);
			if (!subjectCodeList.isEmpty()) {
				responseEntity = ResponseUtil.getResponse(200, "Success!", subjectCodeList);
			} else {
				responseEntity = ResponseUtil.getResponse(404, "No Assign Id found!", subjectCodeList);
			}
		} catch (BusinessServiceException e) {
			responseEntity = ResponseUtil.getResponse(500, e.getMessage(), subjectCodeList);
		} catch (NotFoundException e) {
			responseEntity = ResponseUtil.getResponse(404, e.getMessage(), subjectCodeList);
		}
		return responseEntity;
	}

	@GetMapping("/listAll/{assignList}")
	public ResponseEntity<Response> getAllSubjectCodeList(@PathVariable("assignList") List<Long> assignList) {
		ResponseEntity<Response> responseEntity = null;
		List<String> subjectCodeList = null;
		try {
			subjectCodeList = subjectAssignService.getAllSubjectCodeList(assignList);
			if (!subjectCodeList.isEmpty()) {
				responseEntity = ResponseUtil.getResponse(200, "Success!", subjectCodeList);
			} else {
				responseEntity = ResponseUtil.getResponse(404, "No Assign Id found!", subjectCodeList);
			}
		} catch (BusinessServiceException e) {
			responseEntity = ResponseUtil.getResponse(500, e.getMessage(), subjectCodeList);
		} catch (NotFoundException e) {
			responseEntity = ResponseUtil.getResponse(404, e.getMessage(), subjectCodeList);
		}
		return responseEntity;
	}
	@GetMapping("/count/{roomNo}")
	public ResponseEntity<Response> countOfAssignIds(@PathVariable("roomNo") Long roomNo) {
		ResponseEntity<Response> responseEntity = null;
		Long count = null;
		try {
			count = subjectAssignService.countOfAssignIds(roomNo);
			if (count > 0) {
				responseEntity = ResponseUtil.getResponse(200, "Count is fetched successfully!",
						count);
			}
		} catch (BusinessServiceException e) {
			responseEntity = ResponseUtil.getResponse(500, e.getMessage(), count);
		} catch (NotFoundException e) {
			responseEntity = ResponseUtil.getResponse(404, e.getMessage(), count);
		}
		return responseEntity;
	}

}
