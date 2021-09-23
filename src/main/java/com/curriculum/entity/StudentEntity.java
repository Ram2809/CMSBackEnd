package com.curriculum.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sun.istack.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
@Table(name = "Student")
public class StudentEntity implements Serializable {
	@Id
	private Long rollNo;
	@NotNull
	private String firstName;
	@NotNull
	private String lastName;
	@NotNull
	@Temporal(TemporalType.DATE)
	private Date dateOfBirth;
	@NotNull
	@Size(max = 7)
	private String gender;
	@NotNull
	@Digits(integer=10,fraction=0)
	private Long contactNo;
	@NotNull
	private String address;
	@ManyToOne
	@JoinColumn(name = "roomNo", nullable = false)
	@JsonBackReference
	private ClassEntity classEntity;

}
