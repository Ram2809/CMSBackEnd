package com.curriculum.dto;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Student {
	private Long rollNo;
	private String firstName;
	private String lastName;
	private Date dateOfBirth;
	private String gender;
	private Long contactNo;
	private String address;
	private Class classDetail;
}
