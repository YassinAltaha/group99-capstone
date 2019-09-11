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
	@Size(min = 2, max = 30, message = "Name must between 2 - 30 letters")
	@Pattern(regexp = "^\\(?([0-9]{3})\\)?[-.\\s]?([0-9]{3})[-.\\s]?([0-9]{4})$", message = "Valid format 000-000-0000")
	private String clientContact;

	private String clientAddress;

	// check Regex for future development
	// regex needs to handle any email
	@Column(name = "clientEmail", unique = true, nullable = false, length = 45)
	@NotNull(message = "Plese enter a valid email")
	@Pattern(regexp = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$", message = "Please ensure Email is formated proparly")
	private String clientEmail;

	@Column(name = "password", nullable = false, length = 120)
	@NotNull(message = "Password cannot be empty")
	private String password;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "client")
	private List<Project> projectList = new ArrayList<Project>();

	@Column(name = "role", nullable = false)
	@Enumerated(EnumType.STRING)
	private Role Role;

	// Client Constructor
	public Client(String clientName, String clientCompany, String clientContact, String clientAddress,
			String clientEmail, String password) {

		this.clientName = clientName;
		this.clientCompany = clientCompany;
		this.clientContact = clientContact;
		this.clientAddress = clientAddress;
		this.clientEmail = clientEmail;
		this.password = password;
	}

}
