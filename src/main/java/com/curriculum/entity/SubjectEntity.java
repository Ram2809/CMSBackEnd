package com.curriculum.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@Entity
@Table(name="Subject")
public class SubjectEntity {
	@Id
	@Size(max = 6)
	private String code;
	@Column(nullable = false)
	private String name;
	@OneToMany(mappedBy="subjectEntity")
	private Set<SubjectAssignEntity> subjects;
	@OneToMany(mappedBy="subject")
	private Set<TopicEntity> topics;
}
