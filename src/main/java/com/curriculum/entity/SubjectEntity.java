package com.curriculum.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name="Subject")
public class SubjectEntity implements Serializable{
	@Id
	@Size(max=6)
	private String code;
	@Column(nullable=false)
	private String name;
	@ManyToOne
	@JoinColumn(name="roomNo",nullable=false)
	@JsonBackReference
	private ClassEntity classRoom;
//	@OneToMany(mappedBy="subject",fetch=FetchType.LAZY,cascade=CascadeType.ALL,orphanRemoval=true)
//	@JsonIgnore
//	private Set<TeacherSubject> subjects;
//	@OneToMany(mappedBy="subject",fetch=FetchType.LAZY,cascade=CascadeType.ALL,orphanRemoval=true)
//	@JsonIgnore
//	private Set<TopicEntity> topics;
}
