package com.curriculum.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="Class")
@Getter
@Setter
//@ToString
@NoArgsConstructor
public class ClassEntity {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long roomNo;
	@Column(nullable=false)
	@Size(max=3)
	private String standard;
	@Column(nullable=false)
	@Size(max=2)
	private String section;
	@OneToMany(mappedBy="classEntity")
	private Set<Student> student;
	//,fetch=FetchType.LAZY,cascade=CascadeType.ALL,orphanRemoval=true
	
	public ClassEntity(Long roomNo, String standard, String section) {
		super();
		this.roomNo = roomNo;
		this.standard = standard;
		this.section = section;
	}
	
}
