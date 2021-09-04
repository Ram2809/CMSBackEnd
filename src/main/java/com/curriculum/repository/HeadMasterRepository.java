package com.curriculum.repository;

import org.springframework.http.ResponseEntity;

import com.curriculum.entity.HeadMaster;
import com.curriculum.exception.HeadMasterNotFoundException;

public interface HeadMasterRepository {
	ResponseEntity<String> addHeadMasterDetails(HeadMaster headMasterDeteails);
	ResponseEntity<String> updateHeadMasterDetails(Long id,HeadMaster headMasterDetails) throws HeadMasterNotFoundException;
	ResponseEntity<String> deleteHeadMasterDetails(Long id);
	ResponseEntity<HeadMaster> getParticularHeadMasterDetails(Long id) throws HeadMasterNotFoundException;
}
