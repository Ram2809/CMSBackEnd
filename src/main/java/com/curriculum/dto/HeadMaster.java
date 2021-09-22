package com.curriculum.dto;

import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class HeadMaster {
	private Long id;
	private String firstName;
	private String lastName;
	private Date dateOfBirth;
	private String gender;
	private String qualification;
	private String major;
	private String email;
	private Long contactNo;
	private String address;
}
