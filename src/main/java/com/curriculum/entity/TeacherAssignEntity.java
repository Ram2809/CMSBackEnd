package com.curriculum.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "TeacherAssign")
public class TeacherAssignEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	@JoinColumn(name = "teacherId")
	@JsonIgnore
	private TeacherEntity teacher;
	@ManyToOne
	@JoinColumn(name = "subjectCode", unique = true)
	@JsonIgnore
	private SubjectAssignEntity subjectAssign;
}
