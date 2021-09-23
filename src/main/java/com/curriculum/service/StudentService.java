//package com.curriculum.service;
//
//import java.util.List;
//
//import org.springframework.http.ResponseEntity;
//
//import com.curriculum.entity.Student;
//import com.curriculum.exception.BusinessServiceException;
//import com.curriculum.exception.StudentNotFoundException;
//
//public interface StudentService {
//	Student addStudent(Student studentDetails) throws BusinessServiceException;
//
//	ResponseEntity<List<Student>> getAllStudentDetails();
//
//	ResponseEntity<String> updateStudentDetails(Long rollNo, Student studentDetails) throws StudentNotFoundException;
//
//	Student deleteStudent(Long rollNo) throws BusinessServiceException;
//
//	ResponseEntity<Student> getParticularStudentDetails(Long rollNo) throws StudentNotFoundException;
//
//	List<Student> getStudentByClass(Long roomNo) throws BusinessServiceException;
//}
