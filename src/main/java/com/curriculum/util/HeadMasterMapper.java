package com.curriculum.util;

import com.curriculum.dto.HeadMaster;
import com.curriculum.entity.HeadMasterEntity;

public class HeadMasterMapper {
	public static HeadMasterEntity headMasterMapper(HeadMaster headMaster) {
		HeadMasterEntity headMasterEntity = new HeadMasterEntity();
		headMasterEntity.setId(headMaster.getId());
		headMasterEntity.setFirstName(headMaster.getFirstName());
		headMasterEntity.setLastName(headMaster.getLastName());
		headMasterEntity.setDateOfBirth(headMaster.getDateOfBirth());
		headMasterEntity.setGender(headMaster.getGender());
		headMasterEntity.setQualification(headMaster.getQualification());
		headMasterEntity.setMajor(headMaster.getMajor());
		headMasterEntity.setEmail(headMaster.getEmail());
		headMasterEntity.setContactNo(headMaster.getContactNo());
		headMasterEntity.setAddress(headMaster.getAddress());
		return headMasterEntity;
	}
}
