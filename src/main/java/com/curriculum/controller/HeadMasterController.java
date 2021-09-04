package com.curriculum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.curriculum.entity.HeadMaster;
import com.curriculum.exception.HeadMasterNotFoundException;
import com.curriculum.service.HeadMasterService;
@RestController
@RequestMapping("/headMaster")
public class HeadMasterController {
	@Autowired
	private HeadMasterService headMasterServiceImpl;
	@PostMapping("/addHeadMasterDetails")
	public ResponseEntity<String> addHeadMasterDetails(@RequestBody HeadMaster headMasterDetails)
	{
		return headMasterServiceImpl.addHeadMasterDetails(headMasterDetails);
	}
	@PutMapping("/updateHeadMasterDetails/{id}")
	public ResponseEntity<String> updateHeadMasterDetails(@PathVariable("id") Long id,@RequestBody HeadMaster headMasterDetails) throws HeadMasterNotFoundException
	{
		return headMasterServiceImpl.updateHeadMasterDetails(id,headMasterDetails);
	}
	@DeleteMapping("/deleteHeadMasterDetails/{id}")
	public ResponseEntity<String> deleteHeadMasterDetails(@PathVariable("id") Long id)
	{
		return headMasterServiceImpl.deleteHeadMasterDetails(id);
	}
	@GetMapping("/getHeadMasterDetails/{id}")
	public ResponseEntity<HeadMaster> getParticularHeadMasterDetails(@PathVariable("id") Long id) throws HeadMasterNotFoundException
	{
		return headMasterServiceImpl.getParticularHeadMasterDetails(id);
	}
}
