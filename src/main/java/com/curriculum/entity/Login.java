package com.curriculum.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

@Entity
@Table(name="Login")
public class Login implements Serializable{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long loginId;
	@Column(nullable=false)
	@Size(min=8)
	private String password;
	@OneToOne(targetEntity=Teacher.class,optional=false)
	@JoinColumn(name="teacher_id",referencedColumnName="id",nullable=false)
	@JsonBackReference
	private Teacher teacher; 
}
