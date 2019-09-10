package ca.sheridancollege;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ca.sheridancollege.bean.*;
import ca.sheridancollege.dao.ClientDAO;
import ca.sheridancollege.dao.ProfDAO;
import ca.sheridancollege.dao.StudentDAO;

@Service
public class MyUserDetails implements UserDetailsService {
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		ProfDAO profDAO = new ProfDAO();
		ClientDAO clientDAO = new ClientDAO();
		StudentDAO studentDAO = new StudentDAO();
		
		//determining if the user's email exists 
		//create the User object with the proper GrantedAuthorities 
		if(profDAO.findProfByEmail(email) != null)
		{
			//Getting the Prof Object From the email
			ca.sheridancollege.bean.Professor prof = profDAO.findProfByEmail(email);
			
			//line 112
			//Create the List of Granted Authorities with a Role Prof
			List<GrantedAuthority> authorityList = buildUserAuthorities(prof.getRole());
			
			//line 85
			//create the User object with the proper GrantedAuthorities (Professor)
			return buildProfUserForAuthentication(prof, authorityList);
			
		}
		else if(clientDAO.findClientByEmail(email) != null)
		{
			//Getting the Client Object From the email
			ca.sheridancollege.bean.Client client = clientDAO.findClientByEmail(email);
			
			//line 112
			//Create the List of Granted Authorities with a Role Client
			List<GrantedAuthority> authorityList = buildUserAuthorities(client.getRole());
			
			//line 94
			//create the User object with the proper GrantedAuthorities (Client)
			return buildClientUserForAuthentication(client, authorityList);
			
		}
		else if(studentDAO.getStudentByEmail(email) != null)
		{
			//Getting the Student Object From the email
			ca.sheridancollege.bean.Student student = studentDAO.getStudentByEmail(email);
			
			//line 112
			//Create the List of Granted Authorities with a Role Client
			List<GrantedAuthority> authorityList = buildUserAuthorities(student.getRole());
			
			
			//create the User object with the proper GrantedAuthorities (Student)
			//line 103
			return buildStudentUserForAuthentication(student, authorityList);
		}else
		{
			throw new UsernameNotFoundException("User name not found");
		}				
	
	}
	
	
	
	//Functions that would return User object using the objects provided
	//Build User with PROFESSOR email + password
	private User buildProfUserForAuthentication(ca.sheridancollege.bean.Professor prof,
			List<GrantedAuthority> authorityList) { // import from securty.cores

		// this User is Security Core User
		return new User(prof.getProfEmail(), prof.getPassword(), true, true, true, true, authorityList);
	}
	
	
	//Build User with CLIENT email + password
	private User buildClientUserForAuthentication(ca.sheridancollege.bean.Client client,
			List<GrantedAuthority> authorityList) { // import from securty.cores

		// this User is Security Core User
		return new User(client.getClientEmail(), client.getPassword(), true, true, true, true, authorityList);
	}
	
	
	//Build User with STUDENT email + password
	private User buildStudentUserForAuthentication(ca.sheridancollege.bean.Student student,
			List<GrantedAuthority> authorityList) { // import from securty.cores

		// this User is Security Core User
		return new User(student.getStudent_email(), student.getPassword(), true, true, true, true, authorityList);
	}
	
	//this method is used for all users to make sure that all the roles are set
	//changes the role from enums to GrantedAuthority to be used with spring
	private List<GrantedAuthority> buildUserAuthorities(Role role) {
		
		Set<GrantedAuthority> grantedAuthorities = new HashSet<GrantedAuthority>();
			grantedAuthorities.add(new SimpleGrantedAuthority(role.toString()));
		List<GrantedAuthority> authorityList = new ArrayList<GrantedAuthority>(grantedAuthorities);
		return authorityList;
	}
}
