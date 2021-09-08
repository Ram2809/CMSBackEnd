package com.curriculum.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.curriculum.entity.ClassEntity;
import com.curriculum.service.ClassService;

@RestController
@RequestMapping("/class")
public class ClassController {
	@Autowired
	private ClassService classServiceImpl;

	@PostMapping("/addClassDetails")
	public ResponseEntity<String> addClassDetails(@RequestBody ClassEntity classDetails) {
		System.out.println(classDetails);
		return classServiceImpl.addClassDetails(classDetails);
	}

	@GetMapping("/getAllClassDetails")
	public ResponseEntity<List<ClassEntity>> getAllClassDetails() {
		return classServiceImpl.getAllClassDetails();
	}
	@PutMapping("/updateClassDetails/{roomNo}")
	public ResponseEntity<String> updateClassDetails(@PathVariable("roomNo") Long roomNo,@RequestBody ClassEntity classDetails)
	{
			return classServiceImpl.updateClassDetails(roomNo,classDetails);
	}
	
	@DeleteMapping("/deleteClassDetails/{roomNo}")
	public ResponseEntity<String> deleteClassDetails(@PathVariable("roomNo") Long roomNo)
	{
		return classServiceImpl.deleteClassDetails(roomNo);
	}
	@GetMapping("/getClassDetails/{roomNo}")
	public ResponseEntity<List<ClassEntity>> getParticularClassDetails(@PathVariable("roomNo") Long roomNo) throws ClassNotFoundException
	{
		return classServiceImpl.getParticularClassDetails(roomNo);
	}
	@GetMapping("/getSection/{standard}")
	public ResponseEntity<List<String>> getSection(@PathVariable("standard") String standard)
	{
		return classServiceImpl.getSection(standard);
	}
	@GetMapping("/getClass/{standard}/{section}")
	public ResponseEntity<Long> getClassDetails(@PathVariable("standard") String standard,@PathVariable("section") String section)
	{
		return classServiceImpl.getClassDetails(standard,section);
	}
}
