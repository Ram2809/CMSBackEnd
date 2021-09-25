package com.curriculum.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name="SubjectAssign")
public class SubjectAssignEntity {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	@JoinColumn(name="roomNo")
	private ClassEntity classEntity;
	@ManyToOne
	@JoinColumn(name="subjectCode")
	private SubjectEntity subjectEntity;
	@OneToMany(mappedBy="subjectAssign")
	private Set<TeacherAssignEntity> teacherAssign;
}
