package com.curriculum.entity;

import java.io.Serializable;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@Entity
@Table(name="Timetable")
public class TimeTableEntity implements Serializable{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@NotNull
	private String day;
	@Column
	@ElementCollection(fetch=FetchType.EAGER)
	@CollectionTable(name="Period")
	private Map<Integer,String> periods;
	@ManyToOne
	@JoinColumn(name="roomNo",nullable=false)
	@JsonBackReference
	private ClassEntity classRoom;
}
