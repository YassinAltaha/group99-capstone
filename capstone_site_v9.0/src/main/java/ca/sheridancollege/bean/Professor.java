package ca.sheridancollege.bean;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data 
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Professor implements Serializable {
	
	@Id
	@GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
	private long profId;

	@Column(name = "profName", nullable = false, length = 45)
	@NotNull(message = "Please enter a name")
	@Size(min = 2, max = 30, message = "Name must between 2 - 30 letters")
	private String profName;

	@Column(name = "profEmail", unique = true, nullable = false, length = 45)
	@Pattern(regexp = ".*@sheridancollege\\.ca", message = "Email must be a sheridancollege.ca account")
	private String profEmail;

	@Column(name = "password", nullable = false, length = 120)
	@NotNull(message = "Password cannot be left empty")  
	private String password;

	@Column(name = "program", nullable = false, length = 45)
	@NotNull(message = "Program cannot be left empty")
	@Size(min = 2, max = 30, message = "Program must between 2 - 30 letters")
	private String program;

	@Column(name="role", nullable=false)
	@Enumerated(EnumType.STRING)
	private Role Role ;

	public Professor(String profName, String profEmail, String password, String program) {
		this.profName = profName;
		this.profEmail = profEmail;
		this.password = password;
		this.program = program;
	}
}
