package com.curriculum.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.ColumnDefault;

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
//@Table(name="Topic")
public class Topic implements Serializable{
	@Id
	@Size(max=8)
	private String unitNo;
	@NotNull
	private String unitName;
	@Size(max=200)
	private String description;
	@Temporal(TemporalType.DATE)
	private Date beginDate;
	private String status;
	@Temporal(TemporalType.DATE)
	private Date endDate;
	@ManyToOne
	@JoinColumn(name="subjectCode",nullable=false)
	@JsonIgnore
	private Subject subject;
	@OneToMany(mappedBy="topic",cascade=CascadeType.ALL,fetch=FetchType.LAZY,orphanRemoval=true)
	@JsonIgnore
	private Set<Discussion> discussion;
	public Topic(@Size(max = 8) String unitNo, @NotNull String unitName, @NotNull String description, Date beginDate,
			String status, Date endDate, Subject subject, Set<Discussion> discussion) {
		super();
		this.unitNo = unitNo;
		this.unitName = unitName;
		this.description = description;
		this.beginDate = beginDate;
		this.status = status;
		this.endDate = endDate;
		this.subject = subject;
		this.discussion = discussion;
	}
	
}
