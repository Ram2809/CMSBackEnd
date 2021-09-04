package com.curriculum.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.curriculum.entity.HeadMaster;
import com.curriculum.exception.HeadMasterNotFoundException;
import com.curriculum.repository.HeadMasterRepository;
import com.curriculum.service.HeadMasterService;

@Service
public class HeadMasterServiceImpl implements HeadMasterService{
	@Autowired
	private HeadMasterRepository headMasterRepositoryImpl;

	public ResponseEntity<String> addHeadMasterDetails(HeadMaster headMasterDetails) {
		// TODO Auto-generated method stub
		return headMasterRepositoryImpl.addHeadMasterDetails(headMasterDetails);
	}

	public ResponseEntity<String> updateHeadMasterDetails(Long id, HeadMaster headMasterDetails) throws HeadMasterNotFoundException {
		// TODO Auto-generated method stub
		return headMasterRepositoryImpl.updateHeadMasterDetails(id, headMasterDetails);
	}

	public ResponseEntity<String> deleteHeadMasterDetails(Long id) {
		// TODO Auto-generated method stub
		return headMasterRepositoryImpl.deleteHeadMasterDetails(id);
	}

	public ResponseEntity<HeadMaster> getParticularHeadMasterDetails(Long id) throws HeadMasterNotFoundException {
		// TODO Auto-generated method stub
		return headMasterRepositoryImpl.getParticularHeadMasterDetails(id);
	}
}
