package com.curriculum.service;

import org.springframework.http.ResponseEntity;

import com.curriculum.entity.HeadMaster;
import com.curriculum.exception.HeadMasterNotFoundException;


public interface HeadMasterService {
	ResponseEntity<String> addHeadMasterDetails(HeadMaster headMasterDetails);
	ResponseEntity<String> updateHeadMasterDetails(Long id,HeadMaster headMasterDetails) throws HeadMasterNotFoundException;
	ResponseEntity<String> deleteHeadMasterDetails(Long id);
	ResponseEntity<HeadMaster> getParticularHeadMasterDetails(Long id) throws HeadMasterNotFoundException;
}
