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
	@Pattern(regexp = "^[0-9]{9}$", message = "Student# can only be 9 digits")
	private String student_id;
	
	
	@NotNull(message = "Please enter your name")
	private String name;
	
	@NotNull(message = "Please enter your program")
	private String program;
	
	
	private boolean isGroupLeader;
	
	@NotNull(message = "Please enter your GPA")
	private double gpa;
	
	@NotNull(message = "Please enter a skill")
	@Size(min = 2, max = 20, message = "Skill must between 2 - 20 letters")
	private String skill;
	
	
	
	//Acess points
	@Column(unique=true)
	@NotNull(message = "Please enter your email")
	@Pattern(regexp = ".*@sheridancollege.ca", message = "Ensure email ends with sheridancollege.ca")
	private String student_email;
	
	@Column(name = "password", nullable = false, length = 120)
	@NotNull(message = "Password cannot be empty")
	private String password;
	
	
	//Role + Relations 
	@Column(name="role", nullable=false)
	@Enumerated(EnumType.STRING)
	private Role role;
	
	@ManyToOne(targetEntity = ca.sheridancollege.bean.GroupBean.class)
	@JoinColumn(name="groupId")
	private GroupBean group;
	
	
	
	//Constructor
	public Student(
			String student_id, 
			String name,
			String program,
			double gpa,
			String skill,
			String student_email,
			String password
			)
	{
		this.student_id = student_id;
		this.name = name;
		this.program = program;
		this.gpa = gpa;
		this.skill = skill;
		this.student_email = student_email;
		this.password = password;
		
	}
	
	
	
	
	
	
}
