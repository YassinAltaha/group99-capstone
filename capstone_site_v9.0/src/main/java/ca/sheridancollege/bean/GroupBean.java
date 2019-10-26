package ca.sheridancollege.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

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

	@OneToOne(cascade = CascadeType.ALL)
	private Project project;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "group", fetch = FetchType.EAGER)
	private List<Student> group_members = new ArrayList<Student>();

//	@LazyCollection(LazyCollectionOption.FALSE)
//	@ManyToMany(cascade = CascadeType.MERGE, targetEntity = GroupBean.class)
//	@JoinColumn(name="projectId", nullable = true)
//	private List<Project> projectRankings = new ArrayList<Project>();
	
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
