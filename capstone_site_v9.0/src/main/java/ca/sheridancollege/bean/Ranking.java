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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderColumn;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Ranking implements Serializable {
	
	@Id
	@GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
	private int rankId;
	
	@OneToOne(cascade = CascadeType.ALL)
	private GroupBean group;
	
	@ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@OrderColumn(name="proj_index")
	@JoinTable(name="project_ranking", joinColumns=@JoinColumn(name="rankId"), inverseJoinColumns=@JoinColumn(name="projectId"))  
	private List<Project> rankings = new ArrayList<Project>();
	
	public Ranking(int id) {
		this.rankId = id;
	}
	
}
