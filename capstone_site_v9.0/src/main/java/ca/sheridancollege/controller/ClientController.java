package ca.sheridancollege.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ca.sheridancollege.bean.*;
import ca.sheridancollege.dao.ClientDAO;
import ca.sheridancollege.dao.ProjectDAO;

@Controller
public class ClientController {
	
	//Client Signup(form)
	@RequestMapping("signup")
	public String addClientInfo(Model model) {
		Client client = new Client();
		model.addAttribute("errors", "");
		model.addAttribute("client", client);
		return "signup/th_clientSignup";
	}

	//Client Signup(saving)
	@RequestMapping("saveClientInfo")
	public String saveClientInfo(Model model, @ModelAttribute Client client ,
			@RequestParam String confirm_password) {
		ClientDAO dao = new ClientDAO();

		synchronized (Client.class) {
			
			//testing using the Client Validations 
			if(dao.validateClint(client).isEmpty())
			{
				if(client.getPassword().equals(confirm_password))
				{
					//test if the email is used
					try {
						dao.addClient(client);
						
						//add confirmation message 
						
						model.addAttribute("message", true);;
						
						return "th_login";
						
					//sends the user back to Sign with new error message
					}catch(Exception e)
					{
						model.addAttribute("errors", "This email is already in use");
						return "signup/th_clientSignup";
					}

				}else
				{
					model.addAttribute("errors", "Passwords do not match");
					return "signup/th_clientSignup";
				}
				
				
			}else
			{
				model.addAttribute("errors", dao.validateClint(client));
				return "signup/th_clientSignup";
			}	
		}

	}

	//Add project-1.1
	@RequestMapping("client/addProject")
	public String addProject(Model model) {
		
		Project p = new Project();
		model.addAttribute("project", p);
		model.addAttribute("client", getAuthClient());
		return "client/th_addProject";
	}

	//Add project-1.2
	@RequestMapping(value= "client/saveProject" , method = RequestMethod.POST)
	public String saveProject(Model model, @ModelAttribute Project project) {
		
		ClientDAO dao = new ClientDAO();
		String error = "";
		String msg = "";
		int sP = 0;
		project.setStatus("Pending");
		
		//Getting the Client Using Security Context (Method in page)
		Client c = getAuthClient();
		
		sP = dao.addProject(project, c.getClientId()); // status,
		List<Project> projectList = dao.getMyProjects(c.getClientId());


		if (sP > 0) {
			msg = "You have successfully added a project!";
			model.addAttribute("msg", msg);
			model.addAttribute("project", project);
			model.addAttribute("myProjectList", projectList);
			return "client/th_clientProjects";
		} else {
			error = "Sorry, there was an error adding your project";
			model.addAttribute("client", getAuthClient());
			model.addAttribute("error", error);
			model.addAttribute("project", new Project());
			return "client/th_addProject";
		}
	}
	
	//Editing project-1.1
	@RequestMapping("client/editMyProject/{projectId}")
	public String editContact(Model model, @PathVariable int projectId) {
		
		ClientDAO dao = new ClientDAO();
		Project project = dao.searchProjectById(projectId);
		Client c = getAuthClient();
		if(project == null) {
			List<Project> projectList = dao.getMyProjects(c.getClientId());
			model.addAttribute("myProjectList", projectList);
			model.addAttribute("msg", "Error retriving project");
			return "client/th_clientProjects";
		}else {
			if(c.getClientId() == project.getClient().getClientId())
			{
				model.addAttribute("project", project);
				return "client/th_editProject";
			}else
			{
				List<Project> projectList = dao.getMyProjects(c.getClientId());
				model.addAttribute("myProjectList", projectList);
				model.addAttribute("msg", "Error retriving project");
				return "client/th_clientProjects";
			}
		}
		
	}
	
	//editing project-1.2
	@RequestMapping("client/updateProject")
	public String updateProject(Model model, @ModelAttribute Project project) {
		
		ProjectDAO dao = new ProjectDAO();
		dao.clientUpdateProject(project, project.getProjectId());
		List<Project> projectList = new ArrayList<Project>();
		
		ClientDAO clientDao = new ClientDAO();
		//Getting the Client Using Security Context (Method in page)
		Client c = getAuthClient();
		
		//getting list of projects based on the project's client's ID
		projectList =clientDao.getMyProjects(c.getClientId());
		
		model.addAttribute("myProjectList", projectList);
		return "client/th_clientProjects";
	}
	
	
	//Deleting project-1.1
	//ONLY PENDING/REJECTED PROJECTS CAN GET THIS LINK
	@RequestMapping("client/deleteMyProject/{projectId}")
	public String deleteProject(Model model, @PathVariable int projectId) {
		ClientDAO dao = new ClientDAO();
		Project project = dao.searchProjectById(projectId);
		Client c = getAuthClient();
		if(project == null )
		{
			List<Project> projectList = dao.getMyProjects(c.getClientId());
			model.addAttribute("myProjectList", projectList);
			model.addAttribute("msg", "Error retriving project");
			return "client/th_clientProjects";
		}else
		{
			if(project.getClient().getClientId() == c.getClientId())	
			{
				//INCASE CLIENTS TRY TO DELETE PROJECT USING THE URL
				if(project.getStatus().equals("Pending") || project.getStatus().equals("Rejected"))
				{
					model.addAttribute("project", project);
					return "client/th_delete_confirmation";
				}else
				{
					List<Project> projectList = dao.getMyProjects(c.getClientId());
					model.addAttribute("myProjectList", projectList);
					model.addAttribute("msg", "Sorry, this project cannot be deleted. Contact a professor for more info.");
					return "client/th_clientProjects";
				}
			}else {
				
				List<Project> projectList = dao.getMyProjects(c.getClientId());
				model.addAttribute("myProjectList", projectList);
				model.addAttribute("msg", "Error retriving project");
				return "client/th_clientProjects";
				
			}
		}
		
		
		
		
	}
	
	
	
	//Deleting project-1.2
	@RequestMapping("client/deleteProject")
	public String confdeleteProject(Model model, @ModelAttribute Project project) {
		
		//deleting the project after confirmation
		ProjectDAO dao = new ProjectDAO();
		dao.deleteProject(project);
		
		//getting list of projects based on the project's client's ID
		List<Project> projectList = new ArrayList<Project>();
		ClientDAO clientDao = new ClientDAO();
		projectList = clientDao.getMyProjects(getAuthClient().getClientId());
		
		model.addAttribute("msg" , "Project successfully deleted");
		model.addAttribute("myProjectList", projectList);
		return "client/th_clientProjects";
	}

	
	//Client's List of Projects
	@RequestMapping("client/myProject")
	public String myProject(Model model, @ModelAttribute Project project) {
		
		ClientDAO dao = new ClientDAO();
		
		//Getting the Client Using Security Context (Method in page)
		Client c = getAuthClient();
		
		int clientId = c.getClientId();
		List<Project> projectList = new ArrayList<Project>();
		projectList = dao.getMyProjects(clientId);

		model.addAttribute("myProjectList", projectList);
		return "client/th_clientProjects";
	}
	
	
	//Change Password 1.1
		@RequestMapping(value="client/change_password" ,method = RequestMethod.GET)
		public String changePassword(Model model)
		{
			return "client/th_change_password";
		}
		
		//Change Password 1.2
			@RequestMapping(value="client/change_password" ,method=RequestMethod.POST)
			public String changePassword_POST(Model model,
					@RequestParam String old_password,
					@RequestParam String new_password, 
					@RequestParam String confirm_password
					)
			{
				ClientDAO dao = new ClientDAO();
				if(new_password.equals(confirm_password))
				{
					Client c = getAuthClient();
					BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
					
					if(passwordEncoder.matches(old_password, c.getPassword()))
					{	
						try {
						String new_encoded_pass = passwordEncoder.encode(new_password);
						c.setPassword(new_encoded_pass);
						dao.updateClient(c);
						model.addAttribute("error", "Password was successfully updated");
						}
						catch(Exception e){
							
							model.addAttribute("error", "Error updating password");
							System.out.println(e);
						}
					}else
					{
						model.addAttribute("error", "Old password is incorrect");
					}
				}else
				{
					model.addAttribute("error", "Sorry, passwords don't match");
				}
				
				return "client/th_change_password";
				
			}
	

	//Main pages
	@RequestMapping("client")
	public String goStudentHome() {
		return "client/common/th_about";
	}

	@RequestMapping("client/about")
	public String goStudentAbout() {
		return "client/common/th_about";
	}

	@RequestMapping("client/meetProfs")
	public String goStudentProfs() {
		return "client/common/th_profs";
	}

	@RequestMapping("client/projects")
	public String goStudentPastProjects() {
		return "client/common/th_pastProject";
	}

	@RequestMapping("client/faq")
	public String goStudentFaq() {
		return "client/common/th_faqs";
	}

	@RequestMapping("client/contact")
	public String goStudentContact() {
		return "client/common/th_contact";
	}
	
	
	
	//THIS METHOD USES THE SECURITYCONTEXTHOLDER 
	//RETURN THE CLIENT OBJECT AFTER CLIENT HAS LOGGED IN
	public Client getAuthClient()
	{
		ClientDAO dao = new ClientDAO();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication(); 
		String username = auth.getName().toString();		
		Client client = dao.getClientByEmail(username);
		return client;
		
	}
	
	
	

}
