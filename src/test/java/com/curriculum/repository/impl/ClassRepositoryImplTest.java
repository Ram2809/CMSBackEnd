package com.curriculum.repository.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import com.curriculum.dto.Class;

import com.curriculum.exception.DatabaseException;
import com.curriculum.exception.NotAllowedException;
import com.curriculum.repository.ClassRepository;

class ClassRepositoryImplTest {
	private ClassRepository classRepository;
	@Autowired
	private SessionFactory sessionFactory;
	@BeforeEach
	void setUp() throws Exception {
		classRepository = new ClassRepositoryImpl();
	}

	@AfterEach
	void tearDown() throws Exception {
		classRepository = null;
	}

	@Test
	void testAddClass() throws NotAllowedException, DatabaseException {
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("SELECT COUNT(c) FROM ClassEntity c");
		Long countBeforeAdd=(Long) query.uniqueResult();
		System.out.println(countBeforeAdd);
		Class classEntity=new Class(100l,"I","Z");
		classRepository.addClass(classEntity);
		Query countQuery=session.createQuery("SELECT COUNT(c) FROM ClassEntity c");
		Long countAfterAdd=(Long) countQuery.uniqueResult();
		System.out.println(countAfterAdd);
		assertEquals(countBeforeAdd,countAfterAdd);
	}

//	@Test
//	void testGetAllClass() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testUpdateClass() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testCheckClassRoom() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testCheckStandard() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testCheckSection() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testDeleteClass() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testGetParticularClass() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testGetClassList() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testGetClassRoomNo() {
//		fail("Not yet implemented");
//	}

}
