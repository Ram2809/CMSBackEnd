package com.curriculum.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@Entity
@Table(name="TeacherSubject")
public class TeacherSubject implements Serializable{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@ManyToOne(targetEntity=Teacher.class)
	@JoinColumn(name="teacherId",nullable=false)
	@JsonBackReference(value="teacher")
	private Teacher teacher;
	@ManyToOne(targetEntity=SubjectEntity.class)
	@JoinColumn(name="subjectCode",nullable=false,unique=true)
	@JsonBackReference(value="subject")
	private SubjectEntity subject;
}
