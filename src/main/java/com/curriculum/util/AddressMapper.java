package com.curriculum.util;

import com.curriculum.dto.Address;
import com.curriculum.entity.AddressEntity;
import com.curriculum.entity.TeacherEntity;

public class AddressMapper {
	public static AddressEntity mapAddress(Address address) {
		AddressEntity addressEntity = new AddressEntity();
		addressEntity.setId(address.getId());
		addressEntity.setAddressLine(address.getAddressLine());
		addressEntity.setCity(address.getCity());
		addressEntity.setDistrict(address.getDistrict());
		addressEntity.setState(address.getState());
		addressEntity.setCountry(address.getCountry());
		addressEntity.setPinCode(address.getPinCode());
		TeacherEntity teacherEntity = new TeacherEntity();
		teacherEntity.setId(address.getTeacher().getId());
		addressEntity.setTeacherEntity(teacherEntity);
		return addressEntity;
	}
}
