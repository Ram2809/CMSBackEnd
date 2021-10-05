package com.curriculum.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "Topicstatus")
public class TopicStatusEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Temporal(TemporalType.DATE)
	private Date beginDate;
	private String status;
	@Temporal(TemporalType.DATE)
	private Date completedDate;
	private String remarks;
	@ManyToOne
	@JoinColumn(name = "unitNo")
	private TopicEntity topic;
	@ManyToOne
	@JoinColumn(name = "teacherId")
	private TeacherEntity teacher;
	@ManyToOne
	@JoinColumn(name = "roomNo")
	private ClassEntity classDetail;
}
