package com.curriculum.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.curriculum.dto.Teacher;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Address")
public class AddressEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private String addressLine;
	private String city;
	@Column(nullable = false)
	private String district;
	@Column(nullable = false)
	private String state;
	@Column(nullable = false)
	private String country;
	@Column(length = 6)
	private Long pinCode;
	@OneToOne(targetEntity = TeacherEntity.class,fetch=FetchType.EAGER)
	@JoinColumn(name = "teacherId", unique = true)
	@JsonIgnore
	private TeacherEntity teacher;

}