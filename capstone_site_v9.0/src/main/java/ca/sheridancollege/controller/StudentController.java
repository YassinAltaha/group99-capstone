package ca.sheridancollege.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

import ca.sheridancollege.bean.GroupBean;
import ca.sheridancollege.bean.Project;
import ca.sheridancollege.bean.Ranking;
import ca.sheridancollege.bean.Student;
import ca.sheridancollege.dao.GroupDAO;
import ca.sheridancollege.dao.ProjectDAO;
import ca.sheridancollege.dao.RankingDAO;
import ca.sheridancollege.dao.StudentDAO;

@Controller
public class StudentController {

	StudentDAO dao = new StudentDAO();
	ProjectDAO projectDAO = new ProjectDAO();
	GroupDAO groupDAO = new GroupDAO();
	RankingDAO rankingDAO = new RankingDAO();

	// Signup 1.1
	// Student Sign-up(form)
	@RequestMapping("addStudent")
	public String addStudent(Model model) {
		Student student = new Student();
		model.addAttribute("errors", "");
		model.addAttribute("student", student);
		return "signup/th_studentSignup";
	}

	// Signup 1.2
	// Student Sign-up(saving)
	// Checking Validation
	// Checking Email
	// Checking StudentID
	@RequestMapping("saveStudent")
	public String saveStudent(Model model, @ModelAttribute Student student, @RequestParam String confirm_password, @RequestParam String prof_code) {
		
		synchronized (Student.class) {
			// student validation
			if (dao.validateStudent(student).isEmpty()) {
				// Checking if email is unique
				if (dao.checkStudentByEmail(student.getStudent_email()) == false) {

					
					if(student.getPassword().equals(confirm_password))
					{
						// Checking if Professor Code is matched
						if(dao.checkProfCode(prof_code))
						{
							// email IS new
							// Checking if studentID is unique
							if (dao.checkStudenID(student.getStudent_id()) == false) {
								dao.addStudent(student);
								return "th_login";
							} else {
								// StudentID is used
								model.addAttribute("errors", "Student ID is already registered");
								return "signup/th_studentSignup";
							}

						} else {
							// Professor Code is not matched 
							model.addAttribute("errors", "Professor Code does not Exist");
							return "signup/th_studentSignup";
						}
					} else {
						model.addAttribute("errors", "Passwords do not match");
						return "signup/th_studentSignup";
					}

				} else {
					// email is used
					model.addAttribute("errors", "This email is already in use");
					return "signup/th_studentSignup";
				}
			}
			// test if the email is used
			else {
				model.addAttribute("errors", dao.validateStudent(student));
				return "signup/th_studentSignup";
			}
		}
	}

	// ADD delete Group
	@RequestMapping("student/group_info")
	public String groupInfo(Model model) {

		Student student = getAuthStudent();
		model.addAttribute("student", student);
		return "student/th_group_info";
	}

	@RequestMapping("student/joinGroup")
	public String JoinGroup(Model model) {

		List<GroupBean> raw_groupList = groupDAO.getAllGroups();
		List<GroupBean> groupList = new ArrayList<GroupBean>();

		for(GroupBean group : raw_groupList)
		{
			if(group.getGroup_members().size() < 4)
			{

				groupList.add(group);
			}
		}
		model.addAttribute("groupList", groupList);
		return "student/th_join_group";
	}

	@RequestMapping(value = "student/join_group/{id}", method = RequestMethod.GET)
	public String JoinGroup(Model model, @PathVariable int id) {

		try {
			GroupBean group = groupDAO.getGroupById(id);
			if (group.getGroup_members().size() == 4) {
				model.addAttribute("student", getAuthStudent());
				return "student/th_group_info";
			}
			model.addAttribute("GroupInfo", group);
			return "student/th_join_group_portal";

		} catch (Exception e) {
			model.addAttribute("error", "Sorry, there was an error joining this group");
			return "student/th_join_group";
		}
	}

	@RequestMapping(value = "student/join_group/{id}", method = RequestMethod.POST)
	public String testPasscode(Model model, @PathVariable int id, @RequestParam(value = "password_test") int pass) {

		String passcode = Integer.toString(pass);



		// making sure the Path Variable is not being used without
		if (groupDAO.getGroupById(id) != null) {
			GroupBean group = groupDAO.getGroupById(id);
			String test = group.getPasscode();

			// Testing the passcode input
			if (test.equals(passcode)) {

				Student s = getAuthStudent();

				s.setGroup(group);
				dao.updateStudent(s);

				group.getGroup_members().add(s);
				groupDAO.updateGroup(group);

//				model.addAttribute("inGroup", true);
//				model.addAttribute("GroupInfo", group);
				model.addAttribute("student", s);
				return "student/th_group_info";
			} else {

				model.addAttribute("error", "Incorrect passcode");
				model.addAttribute("GroupInfo", group);
				return "student/th_join_group_portal";

			}
		} else {
			model.addAttribute("error", "Sorry, there was an error joining this group");
			model.addAttribute("groupList", groupDAO.getAllGroups());
			return "student/th_join_group";
		}
	}

	// Create Group 1.1
	@RequestMapping("student/createGroup")
	public String goStudentCreateGroup(Model model) {

		// TEST if students try to add group using links
		if (getAuthStudent().getGroup() != null) {
			model.addAttribute("student", getAuthStudent());
			return "student/th_group_info";
		}

		model.addAttribute("group", new GroupBean());
		return "student/th_create_group";
	}

	// Create Group 1.2
	@RequestMapping("student/addGroup")
	public String goStudentAddGroup(Model model, @ModelAttribute GroupBean group) {

		Student s = getAuthStudent();

		// Create passcode
		// add Student to the group
		// set the group program based on student program
		// set passcode
		// Add group to DB
		// set the student to the group
		// check groupName if it exists

		if (groupDAO.searchGroupByName(group.getGroupName()).isEmpty()) {
			// Creating a 4 digit pass code for group
			Random rn = new Random();
			int range = 9999 - 1000 + 1;
			int pass = rn.nextInt(range) + 1000;

			GroupBean g = new GroupBean();
			
			g.setGroupName(group.getGroupName());
			g.setCampus(s.getCampus());
			g.setPasscode(Integer.toString(pass));
			g.setGroupOwnerStudentId(s.getId());
			g.setProgram(s.getProgram());
			g.getGroup_members().add(s);
			
			groupDAO.addGroup(g);
			// updating student
			s.setGroup(g);
			dao.updateStudent(s);
		

			model.addAttribute("student", s);
			return "student/th_group_info";

		} else {
			model.addAttribute("error", "Sorry, this group name is already registered");
			model.addAttribute("group", new GroupBean());
			return "student/th_create_group";
		}
	}

	@RequestMapping("student/leave_Group")
	public String leaveGroup(Model model) {

		Student s = getAuthStudent();

		if (s.getGroup() != null) {
			GroupBean g = s.getGroup();
			if (g.getGroupOwnerStudentId() == s.getId()) {

				if (g.getGroup_members().size() == 1) {
					s.setGroup(null);
					dao.updateStudent(s);

					g.setGroup_members(null);
					g.setGroupOwnerStudentId(0);

					groupDAO.deleteGroup(g);

					model.addAttribute("student", s);
					return "student/th_group_info";

					
				}else
				{
					g.getGroup_members().remove(s);
					g.setGroupOwnerStudentId(g.getGroup_members().get(0).getId());
					groupDAO.updateGroup(g);
					s.setGroup(null);
					dao.updateStudent(s);
					model.addAttribute("student", s);
					return "student/th_group_info";
				}

			} else {
				s.setGroup(null);
				dao.updateStudent(s);
				model.addAttribute("student", s);
				return "student/th_group_info";
			}


		} else {
			model.addAttribute("error", "Student is not part of a group");
			model.addAttribute("student", s);
			return "student/th_group_info";
		}
	}

	// Change Password 1.1
	@RequestMapping(value = "student/change_password", method = RequestMethod.GET)
	public String changePassword(Model model) {
		return "student/th_change_password";
	}

	// Change Password 1.2
	@RequestMapping(value = "student/change_password", method = RequestMethod.POST)
	public String changePassword_POST(Model model, @RequestParam String old_password, @RequestParam String new_password,
			@RequestParam String confirm_password) {

		if (new_password.equals(confirm_password)) {
			Student s = getAuthStudent();
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

			if (passwordEncoder.matches(old_password, s.getPassword())) {
				try {
					String new_encoded_pass = passwordEncoder.encode(new_password);
					s.setPassword(new_encoded_pass);
					dao.updateStudent(s);
					model.addAttribute("error", "Password was successfully updated");
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

		return "student/th_change_password";

	}

	@RequestMapping(value = "student/rank_projects", method = RequestMethod.GET)
	public String rankProjects(Model model) {
		Student s = getAuthStudent();

		// Check student is in a group
		if (s.getGroup() != null) {

			// Display approved projects
			try {
				List<Project> projectList = projectDAO.getApprovedProjects();
				model.addAttribute("projectList", projectList);
			} catch (Exception e) {
				model.addAttribute("error", "There are no projects available yet");
			}
			
		} else {
			model.addAttribute("error", "You must be in a group to rank projects");
		}
		return "student/th_rank_projects";
	}

	@RequestMapping(value = "student/rank_projects", method = RequestMethod.POST)
	public String submitForm(Model model, @RequestParam int proj1, @RequestParam int proj2, @RequestParam int proj3,
			@RequestParam int proj4, @RequestParam int proj5) {

		Student s = getAuthStudent();
		GroupBean g = s.getGroup();
		
		if (g.getRanking() == null) {
			g.setRanking(new Ranking());
			groupDAO.updateGroup(g);
		} else {
			g.getRanking().getRankings().clear();
		}
		
		Ranking rank = g.getRanking();

		Project p1 = projectDAO.searchProjectById(proj1);
		Project p2 = projectDAO.searchProjectById(proj2);
		Project p3 = projectDAO.searchProjectById(proj3);
		Project p4 = projectDAO.searchProjectById(proj4);
		Project p5 = projectDAO.searchProjectById(proj5);
		
		rank.getRankings().add(p1);
		rank.getRankings().add(p2);
		rank.getRankings().add(p3);
		rank.getRankings().add(p4);
		rank.getRankings().add(p5);
				
		rankingDAO.updateRanking(rank);
		
		model.addAttribute("success", "Project rankings saved");


		// Display approved projects
		List<Project> projectList = projectDAO.getApprovedProjects();
		model.addAttribute("projectList", projectList);

		return "student/th_rank_projects";
	}

	@RequestMapping("student/deleteGroup")
	public String deleteGroup() {

		return "student/common/th_about";
	}

	@RequestMapping("student")
	public String goStudentHome() {
		return "student/common/th_about";
	}

	@RequestMapping("student/about")
	public String goStudentAbout() {
		return "/student/common/th_about";
	}

	
	@RequestMapping("student/meetProfs")
	public String goStudentProfs() {
		return "student/common/th_profs";
	}

	@RequestMapping("student/projects")
	public String goStudentPastProjects() {
		return "student/common/th_pastProject";
	}

	@RequestMapping("student/faq")
	public String goStudentFaq() {
		return "student/common/th_faqs";
	}

	@RequestMapping("student/contact")
	public String goStudentContact() {
		return "student/common/th_contact";
	}

	public Student getAuthStudent() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName().toString();
		Student student = dao.getStudentByEmail(username);
		return student;

	}
}
