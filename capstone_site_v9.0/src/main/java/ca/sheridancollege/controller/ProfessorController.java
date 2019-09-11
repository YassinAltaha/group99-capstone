package ca.sheridancollege.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ca.sheridancollege.dao.GroupDAO;
import ca.sheridancollege.dao.ProfDAO;
import ca.sheridancollege.dao.ProjectDAO;
import ca.sheridancollege.bean.GroupBean;
import ca.sheridancollege.bean.Professor;
import ca.sheridancollege.bean.Project;

@Controller
public class ProfessorController {
	ProfDAO profDAO = new ProfDAO();
	ProjectDAO projectDAO = new ProjectDAO();

	@GetMapping("/prof")
	public String gohome() {
		return "professor/common/th_about";
	}

	// Register Professor-1.1(form)
	@RequestMapping("/signup")
	public String goHome(Model model) {
		Professor professor = new Professor();
		model.addAttribute("professor", professor);
		return "signup/th_profSignup";
	}

	// Register Professor-1.2 (saving prof)
	@RequestMapping("/saveProf")
	public String addProfessor(Model model, @ModelAttribute Professor professor) {

		synchronized (Professor.class) {

			// testing using the Professor Validtions
			if (profDAO.validateProfessor(professor).isEmpty()) {
				// test if the username is used
				try {

					profDAO.addProf(professor);

					// Catch
					// sends the user back to Sign with new error message
				} catch (Exception e) {

					model.addAttribute("errors", "Email Already used");
					return "signup/th_profSignup";
				}
				// Validtion Failed
			} else {
				model.addAttribute("errors", profDAO.validateProfessor(professor));
				return "signup/th_profSignup";
			}
			return "th_login";
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
			msg = "ERROR while updating Project";
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

		model.addAttribute("groupId", groupId);
		int a = 0;
		String msg = "";
		a = projectDAO.assignProject(projectId, groupId);
		if (a > 0) {
			msg = "You have successfully assigned a group to a project!";
		} else {
			msg = "There has been an error updating this project";
		}
		List<Project> projectList = projectDAO.getApprovedProjects();
		model.addAttribute("msg", msg);
		model.addAttribute("projectList", projectList);
		return "/professor/th_listApprovedProjects";
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

}
