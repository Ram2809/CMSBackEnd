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
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
//@ToString
@Entity
@Table(name="StudentPersonal")
public class Student implements Serializable{
	@Id
	//@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long rollNo;
	@NotNull
	@Size(max=20)
	private String firstName;
	@NotNull
	@Size(max=20)
	private String lastName;
	@NotNull
	@Temporal(TemporalType.DATE)
	private Date dateOfBirth;
	@NotNull
	@Size(max=7)
	private String gender;
	@NotNull
	private Long contactNo;
	@NotNull
	private String address;
	@ManyToOne
	@JoinColumn(name="roomNo",nullable=false)
	@JsonIgnore
	private ClassEntity classEntity;
	
}
