package com.curriculum.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name="Topic")
public class Topic {
	@Id
	@Size(max=8)
	private String unitNo;
	@NotNull
	private String unitName;
	@Temporal(TemporalType.DATE)
	private Date beginDate;
	private String status;
	@ManyToOne
	@JoinColumn(name="subjectCode",nullable=false)
	@JsonIgnore
	private Subject subject;
	public Topic(@Size(max = 8) String unitNo, @NotNull String unitName, @NotNull Date beginDate,
			@NotNull String status) {
		super();
		this.unitNo = unitNo;
		this.unitName = unitName;
		this.beginDate = beginDate;
		this.status = status;
	}
	
	
}