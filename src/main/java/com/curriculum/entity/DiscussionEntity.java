package com.curriculum.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Discussion")
public class DiscussionEntity implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long questionNo;
	@Column(nullable = false)
	private String question;
	@Column(nullable = false)
	private String answer;
	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date date;
	@ManyToOne
	@JoinColumn(name = "unitNo")
	@JsonIgnore
	private TopicEntity topic;
	@ManyToOne
	@JoinColumn(name = "teacherId")
	@JsonIgnore
	private TeacherEntity teacher;
	@ManyToOne
	@JoinColumn(name="roomNo")
	private ClassEntity classDetail;

}
