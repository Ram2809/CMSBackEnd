package com.curriculum.entity;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "StudentAssign",uniqueConstraints=@UniqueConstraint(columnNames= {"academicYear","rollNo"}))
public class StudentAssign {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(length = 9, nullable = false)
	private String academicYear;
	@Column(nullable = false)  
	private LocalDate studentAddedOn;
	private LocalDate studentLeftOn;
	@ManyToOne
	@JoinColumn(name = "rollNo",nullable=false)
	private StudentEntity student;
	@ManyToOne
	@JoinColumn(name = "roomNo",nullable=false)
	private ClassEntity classDetail;
}
