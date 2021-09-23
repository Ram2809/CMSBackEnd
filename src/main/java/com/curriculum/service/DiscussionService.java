//package com.curriculum.service;
//
//import java.util.List;
//
//import org.springframework.http.ResponseEntity;
//
//import com.curriculum.entity.Discussion;
//import com.curriculum.exception.BusinessServiceException;
//import com.curriculum.exception.NotFoundException;
//
//public interface DiscussionService {
//	Discussion addDiscussion(Discussion discussionDetails) throws BusinessServiceException, NotFoundException;
//
//	List<Discussion> getDiscussionByUnitNo(String unitNo) throws BusinessServiceException;
//
//	Discussion updateDiscussion(Long questionNo, Discussion discussionDetails) throws BusinessServiceException;
//
//	Discussion deleteDiscussion(Long questionNo) throws BusinessServiceException;
//
//	Discussion getParticularDiscussion(Long questionNo) throws BusinessServiceException;
//}
