package com.curriculum.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "SubjectAssign")
public class SubjectAssignEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	@JoinColumn(name = "roomNo")
	private ClassEntity classDetail;
	@ManyToOne
	@JoinColumn(name = "subjectCode")
	private SubjectEntity subject;
	@OneToMany(mappedBy = "subjectAssign", fetch = FetchType.EAGER)
	private Set<TeacherAssignEntity> teacherAssign;
}
