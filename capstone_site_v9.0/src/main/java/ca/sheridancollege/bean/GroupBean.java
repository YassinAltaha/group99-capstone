package ca.sheridancollege.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class GroupBean implements Serializable {

	@Id
	@GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
	private int groupId;
	private String groupName;
	private long groupOwnerStudentId;
	private String program;
	private String passcode;
	private String campus;
	
	@Column(nullable=false ,columnDefinition = "boolean default false")
	private boolean isArchived;
	
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Project project;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "group", fetch = FetchType.EAGER)
	private List<Student> group_members = new ArrayList<Student>();


	@OneToOne(cascade = CascadeType.ALL, targetEntity = Ranking.class, fetch = FetchType.EAGER)
	private Ranking ranking;
	
	public GroupBean(int groupId, String groupName, long groupOwnerStudentId, String program, String passcode) {

		this.groupId = groupId;
		this.groupName = groupName;
		this.groupOwnerStudentId = groupOwnerStudentId;
		this.program = program;
		this.passcode = passcode;
	}

	public GroupBean(int groupId, String groupName) {

		this.groupId = groupId;
		this.groupName = groupName;
	}

	public GroupBean(String groupName) {

		this.groupName = groupName;
	}


}
