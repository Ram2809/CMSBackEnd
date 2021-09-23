//package com.curriculum.entity;
//
//import java.util.Date;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.Id;
//import javax.persistence.Table;
//import javax.persistence.Temporal;
//import javax.persistence.TemporalType;
//import javax.validation.constraints.Email;
//import javax.validation.constraints.NotNull;
//import javax.validation.constraints.Size;
//
//import org.hibernate.validator.constraints.Length;
//
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//@Data
//@NoArgsConstructor
//@Entity
//@Table(name = "HeadMaster")
//public class HeadMaster {
//	@Id
//	private Long id;
//	@NotNull
//	private String firstName;
//	@NotNull
//	private String lastName;
//	@NotNull
//	@Temporal(TemporalType.DATE)
//	private Date dateOfBirth;
//	@NotNull
//	private String gender;
//	@NotNull
//	private String qualification;
//	@NotNull
//	private String major;
//	@Email
//	@NotNull
//	@Column(unique = true)
//	private String email;
//	@NotNull
//	@Column(unique = true,length=10)
//	private Long contactNo;
//	@NotNull
//	private String address;
//}
