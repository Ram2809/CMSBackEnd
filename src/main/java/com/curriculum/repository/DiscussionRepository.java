package com.curriculum.repository;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.curriculum.entity.Discussion;

public interface DiscussionRepository {
	ResponseEntity<String> addDiscussionDetails(String unitNo,Discussion discussionDetails);
	ResponseEntity<List<Discussion>> getDiscussionByUnitNo(String unitNo);
}
