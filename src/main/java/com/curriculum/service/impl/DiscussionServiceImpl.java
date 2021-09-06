package com.curriculum.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.curriculum.entity.Discussion;
import com.curriculum.repository.DiscussionRepository;
import com.curriculum.service.DiscussionService;
@Service
public class DiscussionServiceImpl implements DiscussionService{
	@Autowired
	private DiscussionRepository discussionRepositoryImpl;
	@Override
	public ResponseEntity<String> addDiscussionDetails(String unitNo, Discussion discussionDetails) {
		// TODO Auto-generated method stub
		return discussionRepositoryImpl.addDiscussionDetails(unitNo,discussionDetails);
	}
	@Override
	public ResponseEntity<List<Discussion>> getDiscussionByUnitNo(String unitNo) {
		// TODO Auto-generated method stub
		return discussionRepositoryImpl.getDiscussionByUnitNo(unitNo);
	}

}
