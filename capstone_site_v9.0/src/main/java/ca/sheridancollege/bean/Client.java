package ca.sheridancollege.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
public class Client implements Serializable {

	@Id
	@GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
	private int clientId;

	@Column(name = "clientName", nullable = false, length = 45)
	@NotNull(message = "Please enter a name")
	@Size(min = 2, max = 30, message = "Name must between 2 - 30 letters")
	private String clientName;

	private String clientCompany;

	@Column(name = "clientContact", nullable = false, length = 45)
	@NotNull(message = "Please provide a valid phone number")
	@Size(min = 2, max = 30, message = "Please provide a valid phone number")
	@Pattern(regexp = "^\\(?([0-9]{3})\\)?[-.\\s]?([0-9]{3})[-.\\s]?([0-9]{4})$", message = "Valid format is 000-000-0000")
	private String clientContact;

	private String clientAddress;
	
	private int clientLimit;
	
	@Column(nullable=false ,columnDefinition = "boolean default false")
	private boolean isArchived;
	
	
	@NotNull(message = "Please Select Industry")
	private String clientIndustry;
	
	@NotNull(message = "Please Select Organization Size")
	private String clientScale;
	
	// check Regex for future development
	// regex needs to handle any email
	@Column(name = "clientEmail", unique = true, nullable = false, length = 45)
	@NotNull(message = "Plese enter a valid email")
	@Pattern(regexp = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$", message = "Please ensure email is valid")
	private String clientEmail;

	@Column(name = "password", nullable = false, length = 120)
	@NotNull(message = "Password cannot be empty")
	@Size(min = 4, max = 120, message = "Password must be at least 4 characters long")
	private String password;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "client")
	private List<Project> projectList = new ArrayList<Project>();

	@Column(name = "role", nullable = false)
	@Enumerated(EnumType.STRING)
	private Role Role;

	// Client Constructor
	public Client(String clientName, String clientCompany, String clientContact, String clientAddress, String clientIndustry, String clientScale,
			String clientEmail, String password) {

		this.clientName = clientName;
		this.clientCompany = clientCompany;
		this.clientContact = clientContact;
		this.clientAddress = clientAddress;
		this.clientIndustry = clientIndustry;
		this.clientScale = clientScale;
		this.clientEmail = clientEmail;
		this.password = password;
		this.clientLimit = 3;
	}

	@Transient 
	public String[] industryList = {"Agriculture/Food Technology","Building Technology","Environmental Technology","Medical/Heath Science Technology","Information/Communication Technology","Manufacturing Technology"};

	@Transient 
	public String[] scaleList = {"Large Enterprise","Small-to-Medium Enterprise","Startup(1-4 employees)","Not Yet Registered","Individual Client","NGO/Not for Profit"};

}
