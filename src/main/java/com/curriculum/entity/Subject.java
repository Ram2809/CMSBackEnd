package com.curriculum.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name="Subject")
public class Subject implements Serializable{
	@Id
	@Size(max=6)
	@Column(nullable=false)
	private String code;
	@Column(nullable=false)
	private String name;
	@ManyToOne
	@JoinColumn(name="roomNo",nullable=false)
	@JsonIgnore
	private ClassEntity classRoom;
	@OneToMany(mappedBy="subject",fetch=FetchType.LAZY,cascade=CascadeType.ALL,orphanRemoval=true)
	@JsonIgnore
	private Set<TeacherSubject> subjects;
	@OneToMany(mappedBy="subject",fetch=FetchType.LAZY,cascade=CascadeType.ALL,orphanRemoval=true)
	@JsonIgnore
	private Set<Topic> topics;
}
