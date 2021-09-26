package com.curriculum.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Teacher")
public class TeacherEntity implements Serializable {
	@Id
	private Long id;
	@NotNull
	private String firstName;
	@NotNull
	private String lastName;
	@NotNull
	@Temporal(TemporalType.DATE)
	private Date dateOfBirth;
	@NotNull
	private String gender;
	@NotNull
	private String qualification;
	@NotNull
	private String major;
	@Email(message = "Enter the valid email id!")
	@NotNull
	@Column(unique = true)
	private String email;
	@NotNull
	@Digits(integer = 10, fraction = 0)
	@Column(unique = true)
	private Long contactNo;
	@OneToMany(mappedBy = "teacher", fetch = FetchType.EAGER)
	private Set<TeacherAssignEntity> teacherAssign;
	@OneToMany(mappedBy = "teacher", fetch = FetchType.EAGER)
	private Set<DiscussionEntity> discussions;
	@OneToOne(mappedBy = "teacher", fetch = FetchType.EAGER)
	private LoginEntity login;
	@OneToOne(mappedBy="teacherEntity")
	private AddressEntity address;
}
