//package com.curriculum.entity;
//
//import java.io.Serializable;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.OneToOne;
//import javax.persistence.Table;
//import javax.validation.constraints.Size;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import lombok.ToString;
//
//
//
//@Entity
//@Table(name="Login")
//public class Login implements Serializable{
//	@Id
//	@GeneratedValue(strategy=GenerationType.AUTO)
//	private Long loginId;
//	@Column(nullable=false)
//	private String userName;
//	@Column(nullable=false)
//	@Size(min=8)
//	private String password;
//	@OneToOne(mappedBy=)
//	@JoinColumn(name="teacher_id")
//	@JsonIgnore
//	private Teacher teacher; 
//}
