package com.curriculum.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Subject")
public class SubjectEntity {
	@Id
	@Size(max = 6)
	private String code;
	@Column(nullable = false)
	private String name;
	@OneToMany(mappedBy = "subject", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private Set<SubjectAssignEntity> subjects;
	@OneToMany(mappedBy = "subject", fetch = FetchType.EAGER)
	private Set<TopicEntity> topics;
}
