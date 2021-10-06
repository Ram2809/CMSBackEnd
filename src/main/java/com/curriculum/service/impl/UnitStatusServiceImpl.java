package com.curriculum.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.curriculum.dto.UnitStatus;
import com.curriculum.entity.UnitStatusEntity;
import com.curriculum.exception.BusinessServiceException;
import com.curriculum.exception.DatabaseException;
import com.curriculum.exception.NotAllowedException;
import com.curriculum.exception.NotFoundException;
import com.curriculum.repository.ClassRepository;
import com.curriculum.repository.TeacherRepository;
import com.curriculum.repository.UnitRepository;
import com.curriculum.repository.UnitStatusRepository;
import com.curriculum.service.UnitStatusService;

@Service
public class UnitStatusServiceImpl implements UnitStatusService {
	@Autowired
	private UnitStatusRepository unitStatusRepository;
	@Autowired
	private UnitRepository unitRepository;
	@Autowired
	private TeacherRepository teacherRepository;
	@Autowired
	private ClassRepository classRepository;

	@Override
	public Long addUnitStatus(UnitStatus unitStatus)
			throws NotFoundException, BusinessServiceException, NotAllowedException {
		unitRepository.checkUnit(unitStatus.getUnit().getUnitNo());
		teacherRepository.checkTeacher(unitStatus.getTeacher().getId());
		classRepository.checkClassRoom(unitStatus.getClassDetail().getRoomNo());
		try {
			return unitStatusRepository.addUnitStatus(unitStatus);
		} catch (DatabaseException e) {
			throw new BusinessServiceException(e.getMessage());
		}
	}

	@Override
	public UnitStatusEntity getStatusByUnitNo(String unitNo, Long staffId, Long roomNo)
			throws NotFoundException, BusinessServiceException {
		unitRepository.checkUnit(unitNo);
		teacherRepository.checkTeacher(staffId);
		classRepository.checkClassRoom(roomNo);
		try {
			return unitStatusRepository.getStatusByUnitNo(unitNo, staffId, roomNo);
		} catch (DatabaseException e) {
			throw new BusinessServiceException(e.getMessage());
		}
	}

	@Override
	public UnitStatusEntity getUnitStatus(Long id) throws BusinessServiceException, NotFoundException {
		try {
			return unitStatusRepository.getUnitStatus(id);
		} catch (DatabaseException e) {
			throw new BusinessServiceException(e.getMessage());
		}
	}

	@Override
	public UnitStatusEntity updateUnitStatus(Long id, UnitStatus unitStatus)
			throws BusinessServiceException, NotFoundException {
		try {
			return unitStatusRepository.updateUnitStatus(id, unitStatus);
		} catch (DatabaseException e) {
			throw new BusinessServiceException(e.getMessage());
		}
	}

	@Override
	public UnitStatusEntity deleteUnitStatus(Long id) throws BusinessServiceException, NotFoundException {
		try {
			return unitStatusRepository.deleteUnitStatus(id);
		} catch (DatabaseException e) {
			throw new BusinessServiceException(e.getMessage());
		}
	}

}
