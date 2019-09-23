package ca.sheridancollege.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ca.sheridancollege.dao.ClientDAO;
import ca.sheridancollege.dao.GroupDAO;
import ca.sheridancollege.dao.ProfDAO;
import ca.sheridancollege.dao.ProjectDAO;
import ca.sheridancollege.bean.Client;
import ca.sheridancollege.bean.GroupBean;
import ca.sheridancollege.bean.Professor;
import ca.sheridancollege.bean.Project;

@Controller
public class ProfessorController {
	ProfDAO profDAO = new ProfDAO();
	ProjectDAO projectDAO = new ProjectDAO();
	
	@GetMapping("/prof")
	public String makeAdmin() {
		Professor admin = new Professor(
				"adminName", //professor name
				"admin@sheridancollege.ca", //account
				"admin", //password
				"ADMIN" //program
				);
		try {
			profDAO.addProf(admin);
			return "th_login";
		}catch (Exception e){
			
			return "th_login";
		}
		
		
	}
	
	@RequestMapping("/professor/groupList")
	public String groupHome(Model model) {
		GroupDAO groupDAO = new GroupDAO();
		model.addAttribute("groups", groupDAO.getAllGroups());
		return "professor/th_groupList";
	}
	

	// Register Professor-1.1(form)
	@RequestMapping("/professor/addProf")
	public String goHome(Model model) {
		Professor professor = new Professor();
		model.addAttribute("professor", professor);
		return "professor/th_profSignup";
	}

	// Register Professor-1.2 (saving prof)
	@RequestMapping("/saveProf")
	public String addProfessor(Model model, @ModelAttribute Professor professor) {

		synchronized (Professor.class) {

			// testing using the Professor Validtions
			if (profDAO.validateProfessor(professor).isEmpty()) {
				
				// test if the username is used
				// Catch
				// sends the user back to Sign with new error message
				try {
					
					profDAO.addProf(professor);
					model.addAttribute("errors", professor.getProfName() + " Account was created");
					return "professor/th_profSignup";
				} catch (Exception e) {
					
					model.addAttribute("errors", "This email is already in use");
					return "signup/th_profSignup";
				}
				// Validtion Failed
			} else {
				model.addAttribute("errors", profDAO.validateProfessor(professor));
				return "professor/th_profSignup";
			}
		}
	}

	// Professor's Project List
	@RequestMapping("/professor/listProjects")
	public String displayProjects(Model model) {

		List<Project> projectList = projectDAO.getProjects();
		model.addAttribute("projectList", projectList);
		return "/professor/th_listProjects";
	}

	// Editing Project-1.1
	@RequestMapping("/professor/profEditProject/{projectId}")
	public String editProject(Model model, @PathVariable int projectId) {

		Project project = projectDAO.searchProjectById(projectId);

		model.addAttribute("projectId", projectId);
		model.addAttribute("project", project);

		return "/professor/th_editProject";
	}

	// Editing Project-1.2
	@RequestMapping("/professor/updateProject")
	public String updateProject(Model model, @ModelAttribute Project project, @RequestParam int projectId) {

		String msg = "";
		try {
			projectDAO.editProject(project, project.getProjectId());
			msg = "You have successfully edited this project!";

		} catch (Exception e) {
			msg = "There was an error while updating this project";
		}

		List<Project> projectList = projectDAO.getProjects();
		model.addAttribute("msg", msg);
		model.addAttribute("projectList", projectList);

		return "/professor/th_listProjects";

	}

	// Assign Project to group-1.1
	@RequestMapping("/professor/listApprovedProjects")
	public String displayApprovedProjects(Model model) {

		List<Project> projectList = projectDAO.getApprovedProjects();
		model.addAttribute("projectList", projectList);
		return "/professor/th_listApprovedProjects";
	}

	// Assign Project to group-1.2
	@RequestMapping("/professor/profAssignProject/{projectId}")
	public String editAssignProject(Model model, @PathVariable int projectId) {
		
		// get all groups without projects
		GroupDAO groupDAO = new GroupDAO();
		List<GroupBean> list = new ArrayList<GroupBean>();
		for (GroupBean group : groupDAO.getAllGroups()) {
			if (group.getProject() == null) {
				list.add(group);
			}
		}
		Project project = projectDAO.searchProjectById(projectId);
		model.addAttribute("groups", list);
		model.addAttribute("project", project);
		return "/professor/th_assignProject";
	}

	// Assign Project to group-1.3
	@RequestMapping("/professor/assignProjectForm")
	public String assignProject(Model model, @ModelAttribute Project project, @RequestParam int projectId,
			@RequestParam int groupId) {
		
		Project p = projectDAO.searchProjectById(projectId);
		//If project is NOT null 
		if(p != null)
		{
			GroupDAO groupDAO = new GroupDAO();
			GroupBean g = groupDAO.getGroupById(groupId);
			//if Project DOES have a Group
			if(p.getGroupBean() != null)
			{
				GroupBean oldGroup = groupDAO.getGroupById(p.getGroupBean().getGroupId());
				oldGroup.setProject(null);
				groupDAO.updateGroup(oldGroup);
			}
				
				p.setGroupBean(g);
				projectDAO.updateProject(p);
				g.setProject(p);
				groupDAO.updateGroup(g);
			model.addAttribute("projectList", projectDAO.getApprovedProjects());
			return "/professor/th_listApprovedProjects";
		}else
		{
			model.addAttribute("error", "Sorry, project ID is not avaiable");
			model.addAttribute("project", p);
			return "/professor/th_assignProject";
		}
	
	}
	
	
	
	//Change Password 1.1
	@RequestMapping(value="/professor/change_password" ,method = RequestMethod.GET)
	public String changePassword(Model model)
	{
		return "/professor/th_changePassword";
	}
	
	//Change Password 1.2
	@RequestMapping(value="/professor/change_password" ,method=RequestMethod.POST)
	public String changePassword_POST(Model model,
			@RequestParam String old_password,
			@RequestParam String new_password, 
			@RequestParam String confirm_password
			)
	{
		
		if(new_password.equals(confirm_password))
		{
			Professor p = getAuthProf();
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			
			if(passwordEncoder.matches(old_password, p.getPassword()))
			{	
				try {
				String new_encoded_pass = passwordEncoder.encode(new_password);
				p.setPassword(new_encoded_pass);
				profDAO.updateProfessor(p);
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
		
		return "/professor/th_changePassword";
		
	}
	
	

	// GO TO - Report page
	@RequestMapping("/professor/report")
	public String goReport() {
		return "/professor/th_report";
	}

	// GO TO - Report status
	@RequestMapping("/professor/reportStatus")
	public String reportParties(Model model) {
		List<Double> statusPerList = projectDAO.statusPercentage();
		model.addAttribute("statusPerList", statusPerList);
		return "/professor/th_report";
	}

	// Main Pages
	@RequestMapping("/professor")
	public String goStudentHome() {
		return "/professor/common/th_about";
	}

	@RequestMapping("/professor/about")
	public String goStudentAbout() {
		return "/professor/common/th_about";
	}

	@RequestMapping("/professor/meetProfs")
	public String goStudentProfs() {
		return "/professor/common/th_profs";
	}

	@RequestMapping("/professor/projects")
	public String goStudentPastProjects() {
		return "/professor/common/th_pastProject";
	}

	@RequestMapping("/professor/faq")
	public String goStudentFaq() {
		return "/professor/common/th_faqs";
	}

	@RequestMapping("/professor/contact")
	public String goStudentContact() {
		return "/professor/common/th_contact";
	}
	
	
	
	public Professor getAuthProf()
	{
		ProfDAO dao = new ProfDAO();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication(); 
		String username = auth.getName().toString();		
		Professor professor = dao.findProfByEmail(username); 
		return professor;
		
	}

}
