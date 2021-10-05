package com.curriculum.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Topic")
public class TopicEntity {
	@Id
	@Size(max = 8, message = "Unit Number not exceeds the 8 characters!")
	private String unitNo;
	@NotNull
	private String unitName;
	private String description;
	private String month;
	@ManyToOne
	@JoinColumn(name = "subjectCode")
	@JsonIgnore
	private SubjectEntity subject;
	@OneToMany(mappedBy = "topic", fetch = FetchType.EAGER)
	private Set<DiscussionEntity> discussion;
}
