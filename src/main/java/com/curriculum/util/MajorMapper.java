package com.curriculum.util;

import com.curriculum.dto.Major;
import com.curriculum.entity.MajorEntity;

public class MajorMapper {
	public static MajorEntity mapMajor(Major major) {
		MajorEntity majorEntity=new MajorEntity();
		majorEntity.setId(major.getId());
		majorEntity.setName(major.getName());
		return majorEntity;
	}
}
