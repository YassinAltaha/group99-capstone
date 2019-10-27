package ca.sheridancollege.bean;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
public class Project implements Serializable {
	@Id
	@GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
	private int projectId;
	private String title;
	@Lob
	private String description;
	private String status;
	private String profNote;
	
	@Column(nullable=false ,columnDefinition = "boolean default false")
	private boolean isArchived;
	
	@Transient
	private int GroupId;

	@ManyToOne(cascade = CascadeType.ALL, targetEntity = Client.class, fetch = FetchType.EAGER)
	@JoinColumn(name = "clientId")
	private Client client;

	@OneToOne(cascade = CascadeType.ALL, targetEntity = GroupBean.class, fetch = FetchType.EAGER)
	private GroupBean groupBean;
	

	public Project(String title, String description) {

		this.title = title;
		this.description = description;
	}

	public Project(String title, String description, String status, Client client) {

		this.title = title;
		this.description = description;
		this.status = status;
		this.client = client;
	}

	public Project(String title, String description, String status, String profNote) {

		this.title = title;
		this.description = description;
		this.status = status;
		this.profNote = profNote;
	}

	@Transient
	public String[] statusList = { "Pending", "Approved", "Rejected", "Completed" };
}
