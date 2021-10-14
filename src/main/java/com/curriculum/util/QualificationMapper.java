package com.curriculum.util;

import com.curriculum.dto.Qualification;
import com.curriculum.entity.QualificationEntity;

public class QualificationMapper {
	public static QualificationEntity mapQualification(Qualification qualification) {
		QualificationEntity qualificationEntity=new QualificationEntity();
		qualificationEntity.setId(qualification.getId());
		qualificationEntity.setName(qualification.getName());
		return qualificationEntity;
	}
}
