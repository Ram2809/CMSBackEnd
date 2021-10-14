package com.curriculum.entity;

import java.util.Date;

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

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "StudentAssign")
public class StudentAssign {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@Column(length=9)
	private String academicYear;
	@Temporal(TemporalType.DATE)
	private Date studentAddedOn;
	@Temporal(TemporalType.DATE)
	private Date studentLeftOn;
	@ManyToOne
	@JoinColumn(name="rollNo")
	private StudentEntity student;
	@ManyToOne
	@JoinColumn(name="roomNo")
	private ClassEntity classDetail;
}
