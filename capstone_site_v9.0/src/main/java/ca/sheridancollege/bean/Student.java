package ca.sheridancollege.bean;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Student implements Serializable { 

	@Id
	@GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
	private int id;

	
	@Column(unique=true)
	@NotNull(message = "Please enter your Sheridan student number")
	@Pattern(regexp = "^[0-9]{9}$", message = "Student # must be 9 digits")
	private String student_id;
	
	@Column(name = "name", nullable = false, length = 45)
	@NotNull(message = "Please enter your name")
	@Size(min = 2, max = 30, message = "Name must between 2 - 30 letters")
	private String name;
	
	@Column(name = "program", nullable = false, length = 45)
	@NotNull(message = "Please enter your program")
	@Size(min = 2, max = 30, message = "Program must between 2 - 30 letters")
	private String program;

	private boolean isGroupLeader;
	
	@NotNull(message = "Please Select Campus")
	private String campus;
	
	@NotNull(message = "Please enter your GPA")
	private double gpa;

	@NotNull(message = "Please enter a skill")
	@Size(min = 2, max = 20, message = "Skill must between 2 - 20 letters")
	private String skill;
		
	
	//Access points
	@Column(unique=true)
	@NotNull(message = "Please enter your email")
	@Pattern(regexp = ".*@sheridancollege.ca", message = "Ensure email ends with sheridancollege.ca")
	private String student_email;

	@Column(name = "password", nullable = false, length = 120)
	@NotNull(message = "Password cannot be empty")
	@Size(min = 4, max = 120, message = "Password must be at least 4 characters long")
	private String password;

	// Role + Relations
	@Column(name = "role", nullable = false)
	@Enumerated(EnumType.STRING)
	private Role role;

	@ManyToOne(targetEntity = ca.sheridancollege.bean.GroupBean.class)
	@JoinColumn(name = "groupId")
	private GroupBean group;

	// Constructor
	public Student(String student_id,
			String name,
			String program,
			String campus,
			double gpa,
			String skill,
			String student_email,
			String password) {
		this.student_id = student_id;
		this.name = name;
		this.program = program;
		this.campus = campus;
		this.gpa = gpa;
		this.skill = skill;
		this.student_email = student_email;
		this.password = password;
		

	}
	
	@Transient
	public String[] campusList = { "Trafalgar", "Davis", "Hazel" };

}
