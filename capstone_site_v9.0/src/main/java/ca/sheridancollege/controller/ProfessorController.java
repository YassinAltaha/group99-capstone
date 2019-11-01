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
import ca.sheridancollege.dao.StudentDAO;
import ca.sheridancollege.bean.Client;
import ca.sheridancollege.bean.GroupBean;
import ca.sheridancollege.bean.Professor;
import ca.sheridancollege.bean.Project;
import ca.sheridancollege.bean.Student;

@Controller
public class ProfessorController {
	
	ProfDAO profDAO = new ProfDAO();
	ProjectDAO projectDAO = new ProjectDAO();
	ClientDAO clientDAO = new ClientDAO();
	StudentDAO studentDAO = new StudentDAO();
	GroupDAO groupDAO = new GroupDAO();
//----------------------------------------------(ADMIN ACCOUNT)---------------------------------------------//
	@GetMapping("prof")
	public String makeAdmin() {
		Professor admin = new Professor("adminName", // professor name
				"admin@sheridancollege.ca", // account
				"admin", // password
				"ADMIN", // program
				"TestCode" // profCode
		);
		try {
			
			profDAO.addProf(admin);
			return "th_login";
		} catch (Exception e) {

			return "th_login";
		}

	}
	
	
//----------------------------- ARCHIVING -------------------- (ADMINISTRATION)---------------------------------------------//
	
	@RequestMapping(value="professor/administrate" ,method = RequestMethod.GET)
	public String goAdminGET(Model model) {

		return "professor/th_administrator";
	}
	
	@RequestMapping(value="professor/administrate" ,method = RequestMethod.POST)
	public String goAdminPOST(Model model, @RequestParam String objType ) {

		switch(objType)
		{
			case "adminStudents":{
				model.addAttribute("studentList", studentDAO.getAllStudents());
				
			}
			case "adminGroups":{
				model.addAttribute("groupList", groupDAO.getAllGroups());
				
				
			}
			case "adminClients":{
				model.addAttribute("clientList", clientDAO.getAllClients());
				
				
			}
			case "adminProjects":{
				model.addAttribute("projectList", projectDAO.getAllProjects());
			
			}
			
		}
		model.addAttribute("objType", objType);
		return "professor/th_administrator";
		
		
	}
	

	@RequestMapping("/professor/archiveClient/{id}")
	public String archiveClient(Model model, @PathVariable int id) {
		
		try {
			Client c = clientDAO.getClientById(id);
			c.setArchived(true);
			clientDAO.updateClient(c);

		}catch(Exception e)
		{
			model.addAttribute("error", "Error Archiving Client");

		}
		model.addAttribute("objType", "adminClients");
		model.addAttribute("clientList", clientDAO.getAllClients());
		return "professor/th_administrator";
	}
	
	@RequestMapping("/professor/unarchiveClient/{id}")
	public String unarchiveClient(Model model, @PathVariable int id) {
		
		try {
			Client c = clientDAO.getClientById(id);
			c.setArchived(false);
			clientDAO.updateClient(c);

		}catch(Exception e)
		{
			model.addAttribute("error", "Error Un-Archiving Client");

		}
		model.addAttribute("objType", "adminClients");
		model.addAttribute("clientList", clientDAO.getAllClients());
		return "professor/th_administrator";
	}
	
	
	@RequestMapping("/professor/archiveProject/{id}")
	public String archiveProject(Model model, @PathVariable int id) {
		
		try {
			Project p = projectDAO.searchProjectById(id);
					
			p.setArchived(true);
			projectDAO.updateProject(p);
			

		}catch(Exception e)
		{
			model.addAttribute("error", "Error Archiving Project");

		}
		model.addAttribute("objType", "adminProjects");
		model.addAttribute("projectList", projectDAO.getAllProjects());
		return "professor/th_administrator";
	}
	
	@RequestMapping("/professor/unarchiveProject/{id}")
	public String unarchiveProject(Model model, @PathVariable int id) {
		
		try {
			Project p = projectDAO.searchProjectById(id);
					
			p.setArchived(false);
			projectDAO.updateProject(p);
			

		}catch(Exception e)
		{
			model.addAttribute("error", "Error Un-Archiving Project");

		}
		model.addAttribute("objType", "adminProjects");
		model.addAttribute("projectList", projectDAO.getAllProjects());
		return "professor/th_administrator";
	}
	
	
	@RequestMapping("/professor/archiveGroup/{id}")
	public String archiveGroup(Model model, @PathVariable int id) {
		
		try {
			GroupBean g = groupDAO.getGroupById(id);
			g.setArchived(true);
			groupDAO.updateGroup(g);
			
		}catch(Exception e)
		{
			model.addAttribute("error", "Error Archiving Groups");

		}
		model.addAttribute("objType", "adminGroups");
		model.addAttribute("groupList", groupDAO.getAllGroups());
		return "professor/th_administrator";
	}
	
	@RequestMapping("/professor/unarchiveGroup/{id}")
	public String unarchiveGroup(Model model, @PathVariable int id) {
		
		try {
			GroupBean g = groupDAO.getGroupById(id);
			g.setArchived(false);
			groupDAO.updateGroup(g);	

		}catch(Exception e)
		{
			model.addAttribute("error", "Error Un-Archiving Groups");

		}
		model.addAttribute("objType", "adminGroups");
		model.addAttribute("groupList", groupDAO.getAllGroups());
		return "professor/th_administrator";
	}
	
	@RequestMapping("/professor/archiveStudent/{id}")
	public String archiveStudent(Model model, @PathVariable int id) {
		
		try {
			Student s = studentDAO.getStudentById(id);
			s.setArchived(true);
			studentDAO.updateStudent(s);

			
		}catch(Exception e)
		{
			model.addAttribute("error", "Error Archiving Student");

		}
		model.addAttribute("objType", "adminStudents");
		model.addAttribute("studentList", studentDAO.getAllStudents());
		return "professor/th_administrator";
	}
	
	@RequestMapping("/professor/unarchiveStudent/{id}")
	public String unarchiveStudent(Model model, @PathVariable int id) {
		
		try {
			Student s = studentDAO.getStudentById(id);
			s.setArchived(false);
			studentDAO.updateStudent(s);

			
		}catch(Exception e)
		{
			model.addAttribute("error", "Error Un-Archiving Student");

		}
		model.addAttribute("objType", "adminStudents");
		model.addAttribute("studentList", studentDAO.getAllStudents());
		return "professor/th_administrator";
	}
//------------------------------------------END OF ARCHIVING------------------------------------------------- //	
	
	@RequestMapping("professor/groupList")
	public String groupHome(Model model) {

		model.addAttribute("groups", groupDAO.getAllGroups());
		return "professor/th_groupList";
	}
	
	
	@RequestMapping("professor/projectToGroup/{id}")
	public String assignGroupAProject(Model model , @PathVariable int id)
	{
		GroupBean g = groupDAO.getGroupById(id);
		List<Project> projectList = projectDAO.getApprovedProjects();
		model.addAttribute("projects", projectList);
		model.addAttribute("group", g);
		return "professor/th_AssignGroupToProject";
	}
	
	@RequestMapping("professor/assignGroup")
	public String finishAssignGroupAProject(Model model, @RequestParam int projectId , @ModelAttribute GroupBean group)
	{
		Project p = projectDAO.searchProjectById(projectId);
		GroupBean g = groupDAO.getGroupById(group.getGroupId());
		try {
			g.setProject(p);
			
			p.setGroupBean(g);
			groupDAO.updateGroup(g);
			projectDAO.updateProject(p);
			
			model.addAttribute("msg", true);
			model.addAttribute("groups", groupDAO.getAllGroups());
			return "professor/th_groupList";
			
		}catch(Exception e)
		{
			List<Project> projectList = projectDAO.getApprovedProjects();
			model.addAttribute("projects", projectList);
			model.addAttribute("group", group);
			return "professor/th_AssignGroupToProject";

		}

	}
	
	
	@RequestMapping("professor/removeProjectFromGroup/{id}")
	public String removeProject(Model model , @PathVariable int id) {
		
		try {
			GroupBean g = groupDAO.getGroupById(id);
			Project p = g.getProject();
			g.setProject(null);
			groupDAO.updateGroup(g);
			p.setGroupBean(null);
			projectDAO.updateProject(p);
			model.addAttribute("msg", true);
			
		}catch(Exception e)
		{
			model.addAttribute("error", "Error removing Project from Group");

		}
		model.addAttribute("groups", groupDAO.getAllGroups());
		return "professor/th_groupList";
	}
	
	
	
	// Register Professor-1.1(form)
	@RequestMapping("professor/addProf")
	public String goHome(Model model) {
		Professor professor = new Professor();
		model.addAttribute("professor", professor);
		return "professor/th_profSignup";
	}

	// Register Professor-1.2 (saving prof)
	@RequestMapping("saveProf")
	public String addProfessor(Model model, @ModelAttribute Professor professor , @RequestParam String confirm_password) {

		synchronized (Professor.class) {

			// testing using the Professor Validtions
			if (profDAO.validateProfessor(professor).isEmpty()) {
				if(professor.getPassword().equals(confirm_password))
				{
					// test if the username is used
					// Catch
					// sends the user back to Sign with new error message
					try {
	
						profDAO.addProf(professor);
						model.addAttribute("success", professor.getProfName() + " Account was created");
						return "professor/th_profSignup";
					} catch (Exception e) {
	
						model.addAttribute("errors", "This email is already in use");
						return "professor/th_profSignup";
					}
				}else {
					model.addAttribute("errors", "Passwords must match");
					return "professor/th_profSignup";
				}
			// Validtion Failed
			} else {
				model.addAttribute("errors", profDAO.validateProfessor(professor));
				return "professor/th_profSignup";
			}
		}
	}

	// Professor's Project List
	@RequestMapping("professor/listProjects")
	public String displayProjects(Model model) {

		List<Client> clientList = clientDAO.getAllClients();
		model.addAttribute("clientList", clientList);
		return "professor/th_listProjects";
	}

	// Editing Project-1.1
	@RequestMapping("professor/profEditProject/{projectId}")
	public String editProject(Model model, @PathVariable int projectId) {

		Project project = projectDAO.searchProjectById(projectId);

		model.addAttribute("projectId", projectId);
		model.addAttribute("project", project);

		return "professor/th_editProject";
	}

	// Editing Project-1.2
	@RequestMapping("professor/updateProject")
	public String updateProject(Model model, @ModelAttribute Project project, @RequestParam int projectId) {

		String msg = "";
		try {
			projectDAO.editProject(project, project.getProjectId());
			msg = "You have successfully edited this project!";

		} catch (Exception e) {
			msg = "There was an error while updating this project";
		}

		List<Client> clientList = clientDAO.getAllClients();
		model.addAttribute("msg", msg);
		model.addAttribute("clientList", clientList);

		return "professor/th_listProjects";

	}

	// Assign Project to group-1.1
	@RequestMapping("professor/listApprovedProjects")
	public String displayApprovedProjects(Model model) {

		List<Project> projectList = projectDAO.getApprovedProjects();
		model.addAttribute("projectList", projectList);
		return "professor/th_listApprovedProjects";
	}

	// Assign Project to group-1.2
	@RequestMapping("professor/profAssignProject/{projectId}")
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

		return "professor/th_assignProject";
	}

	// Assign Project to group-1.3
	@RequestMapping("professor/assignProjectForm")
	public String assignProject(Model model, @ModelAttribute Project project, @RequestParam int projectId,
			@RequestParam int groupId) {

		Project p = projectDAO.searchProjectById(projectId);
		// If project is NOT null
		if (p != null) {
			GroupDAO groupDAO = new GroupDAO();
			GroupBean g = groupDAO.getGroupById(groupId);
			// if Project DOES have a Group
			if (p.getGroupBean() != null) {
				GroupBean oldGroup = groupDAO.getGroupById(p.getGroupBean().getGroupId());
				oldGroup.setProject(null);
				groupDAO.updateGroup(oldGroup);
			}

			p.setGroupBean(g);
			projectDAO.updateProject(p);
			g.setProject(p);
			groupDAO.updateGroup(g);
			model.addAttribute("projectList", projectDAO.getApprovedProjects());
			return "professor/th_listApprovedProjects";
		} else {
			model.addAttribute("error", "Sorry, project ID is not avaiable");
			model.addAttribute("project", p);
			return "professor/th_assignProject";
		}

	}

	// Change Password 1.1
	@RequestMapping(value = "professor/change_password", method = RequestMethod.GET)
	public String changePassword(Model model) {
		return "professor/th_changePassword";
	}

	// Change Password 1.2
	@RequestMapping(value = "professor/change_password", method = RequestMethod.POST)
	public String changePassword_POST(Model model, @RequestParam String old_password, @RequestParam String new_password,
			@RequestParam String confirm_password) {

		if (new_password.equals(confirm_password)) {
			Professor p = getAuthProf();
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

			if (passwordEncoder.matches(old_password, p.getPassword())) {
				try {
					String new_encoded_pass = passwordEncoder.encode(new_password);
					p.setPassword(new_encoded_pass);
					profDAO.updateProfessor(p);
					model.addAttribute("msg", "Password was successfully updated");
				} catch (Exception e) {

					model.addAttribute("error", "Error updating password");
					System.out.println(e);
				}
			} else {
				model.addAttribute("error", "Old password is incorrect");
			}
		} else {
			model.addAttribute("error", "Sorry, passwords don't match");
		}

		return "professor/th_changePassword";

	}
	
	// Change ProfCode
	@RequestMapping(value = "professor/change_profCode", method = RequestMethod.POST)
	public String changeProfCode_POST(Model model, @RequestParam String new_profCode) {

		Professor p = getAuthProf();

		try {
			p.setProfCode(new_profCode);
			profDAO.updateProfessor(p);
			model.addAttribute("msg", "Pass code was successfully updated");
		} catch (Exception e) {
			model.addAttribute("error", "Error updating Pass Code");
			System.out.println(e);
		}
		
		return "professor/th_changePassword";
	}

//-------------------------------------------- GO TO - Report page-------------------------------------------------------------//
	@RequestMapping("professor/report")
	public String goReport() {
		return "professor/th_report";
	}

	// GO TO - Report status
	@RequestMapping("professor/reportStatus")
	public String reportParties(Model model, @RequestParam String reportType) {

		switch (reportType) {

		case "noGroupStudent": {
			List<Student> studentList = studentDAO.getAllStudents();
			List<Student> noGroupList = new ArrayList<Student>();
			for (Student s : studentList) {
				if (s.getGroup() == null) {
					noGroupList.add(s);
				}
			}
			model.addAttribute("studentList", noGroupList);
			break;
		}
		case "noProjectGroup": {
			List<GroupBean> allGroups = groupDAO.getAllGroups();
			List<GroupBean> noProjectList = new ArrayList<GroupBean>();
			for (GroupBean g : allGroups) {
				if (g.getProject() == null) {
					noProjectList.add(g);
				}
			}
			model.addAttribute("noProjectList", noProjectList);
			break;
		}

		case "allGroups": {
			model.addAttribute("groupList", groupDAO.getAllGroups());
			break;
		}

		case "approvedProjects": {
			List<Project> allProjects = projectDAO.getAllProjects();
			List<Project> pList = new ArrayList<Project>();
			for (Project p : allProjects) {
				if (p.getStatus().equals("Approved")) {
					pList.add(p);
				}
			}
			model.addAttribute("approvedProjects", pList);
			break;
		}
		case "rejectedProjects": {
			List<Project> allProjects = projectDAO.getAllProjects();
			List<Project> pList = new ArrayList<Project>();
			for (Project p : allProjects) {
				if (p.getStatus().equals("Rejected")) {
					pList.add(p);
				}
			}
			model.addAttribute("rejectedProjects", pList);
			break;
		}
		case "pendingProjects": {
			List<Project> allProjects = projectDAO.getAllProjects();
			List<Project> pList = new ArrayList<Project>();
			for (Project p : allProjects) {
				if (p.getStatus().equals("Pending")) {
					pList.add(p);
				}
			}
			model.addAttribute("pendingProjects", pList);
			break;
		}

		}

		model.addAttribute("reportType", reportType);
		return "professor/th_report";
	}
//-------------------------------------------- END OF - Report page-------------------------------------------------------------//

	// Main Pages
	@RequestMapping("professor")
	public String goStudentHome() {
		return "professor/common/th_about";
	}

	@RequestMapping("professor/about")
	public String goStudentAbout() {
		return "professor/common/th_about";
	}

	@RequestMapping("professor/meetProfs")
	public String goStudentProfs() {
		return "professor/common/th_profs";
	}

	@RequestMapping("professor/projects")
	public String goStudentPastProjects() {
		return "professor/common/th_pastProject";
	}

	@RequestMapping("professor/faq")
	public String goStudentFaq() {
		return "professor/common/th_faqs";
	}

	@RequestMapping("professor/contact")
	public String goStudentContact() {
		return "professor/common/th_contact";
	}

	public Professor getAuthProf() {
		ProfDAO dao = new ProfDAO();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName().toString();
		Professor professor = dao.findProfByEmail(username);
		return professor;

	}

}
