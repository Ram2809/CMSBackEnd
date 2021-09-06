package com.curriculum.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name="Timetable")
public class TimeTable implements Serializable{
	@Id
	@GeneratedValue
	private Long id;
	@NotNull
	private String day;
	@NotNull
	private String periodOne;
	@NotNull
	private String periodTwo;
	@NotNull
	private String periodThree;
	@NotNull
	private String periodFour;
	@NotNull
	private String periodFive;
	@NotNull
	private String periodSix;
	@NotNull
	private String periodSeven;
	@NotNull
	private String periodEight;
	@ManyToOne
	@JoinColumn(name="roomNo",nullable=false)
	@JsonIgnore
	private ClassEntity classRoom;
}
