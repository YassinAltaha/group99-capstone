package ca.sheridancollege.controller;

import java.util.List;
import java.util.Random;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ca.sheridancollege.bean.Client;
import ca.sheridancollege.bean.GroupBean;
import ca.sheridancollege.bean.Student;
import ca.sheridancollege.dao.GroupDAO;
import ca.sheridancollege.dao.StudentDAO;

@Controller
public class StudentController {

	StudentDAO dao = new StudentDAO();

	// Signup 1.1
	// Student Sign-up(form)
	@RequestMapping("/addStudent")
	public String addStudent(Model model) {
		Student student = new Student();
		model.addAttribute("errors", "");
		model.addAttribute("student", student);
		return "/signup/th_studentSignup";
	}

	// Signup 1.2
	// Student Sign-up(saving)
	// Checking Validation
	// Checking Email
	// Checking StudentID
	@RequestMapping("/saveStudent")
	public String saveStudent(Model model, @ModelAttribute Student student) {

		
//			 incase teachers request that we limit the use of the dao
//		if(!dao.validateStudent(student).isEmpty()) {
//			
//			model.addAttribute("Errors", dao.validateStudent(student));
//			return "/signup/th_studentSignup";
//			
//		}else {
//			List<Student> studentList = dao.getAllStudents();			
//			for(Student s : studentList)
//			{
//				if(s.getStudent_email() == student.getStudent_email())
//				{
//					model.addAttribute("Errors", "Email already used");
//					return "/signup/th_studentSignup";
//				}else if (s.getStudent_id() == student.getStudent_id())
//				{
//					model.addAttribute("Errors", "Student ID is used");
//					return "/signup/th_studentSignup";
//				}
//			}
//			synchronized (Student.class)
//			{
//				dao.addStudent(student);
//				return "th_login";	
//			}
//		}

		synchronized (Student.class) {
			// student validation
			if (dao.validateStudent(student).isEmpty()) {
				// Checking if email is unique
				if (dao.checkStudentByEmail(student.getStudent_email()) == false) {
					// email IS new
					// Checking if studentID is unique
					if (dao.checkStudenID(student.getStudent_id()) == false) {
						dao.addStudent(student);
						return "th_login";
					} else {
						// StudentID is used
						model.addAttribute("errors", "Student ID is already registered");
						return "/signup/th_studentSignup";
					}
				} else {
					// email is used
					model.addAttribute("errors", "This email is already in use");
					return "/signup/th_studentSignup";
				}
			}
			// test if the email is used
			else {
				model.addAttribute("errors", dao.validateStudent(student));
				return "/signup/th_studentSignup";
			}
		}
	}

	// TODO FINISH MAIN PAGE
	// ADD delete Group
	@RequestMapping("/student/group_info")
	public String groupInfo(Model model) {

		Student student = getAuthStudent();
		model.addAttribute("student", student);
		return "/student/th_group_info";
	}

	@RequestMapping("/student/joinGroup")
	public String JoinGroup(Model model) {

		GroupDAO groupDAO = new GroupDAO();
		List<GroupBean> groupList = groupDAO.getAllGroups();
		model.addAttribute("groupList", groupList);
		return "/student/th_join_group";
	}

	@RequestMapping(value = "/student/join_group/{id}", method = RequestMethod.GET)
	public String JoinGroup(Model model, @PathVariable int id) {

		GroupDAO groupDAO = new GroupDAO();

		try {
			GroupBean group = groupDAO.getGroupById(id);
			model.addAttribute("GroupInfo", group);
			return "/student/th_join_group_portal";
			
		}catch(Exception e)
		{
			model.addAttribute("error", "Sorry, there was an error joining this group");
			return "/student/th_join_group";
		}

	}

	@RequestMapping(value = "/student/join_group/{id}", method = RequestMethod.POST)
	public String testPasscode(Model model, @PathVariable int id, @RequestParam(value = "password_test") int pass) {

		String passcode = Integer.toString(pass);

		GroupDAO groupDAO = new GroupDAO();

		// making sure the Path Variable is not being used without
		if (groupDAO.getGroupById(id) != null) {
			GroupBean group = groupDAO.getGroupById(id);
			String test = group.getPasscode();

			// Testing the passcode input
			if (test.equals(passcode)) {

				Student s = getAuthStudent();

				s.setGroup(group);
				dao.updateStudent(s);

//				model.addAttribute("inGroup", true);
//				model.addAttribute("GroupInfo", group);
				model.addAttribute("student", s);
				return "/student/th_group_info";
			}else {
				
				model.addAttribute("error", "Incorrect passcode");
				model.addAttribute("GroupInfo", group);
				return "/student/th_join_group_portal";	
				
			}
		} else {
			model.addAttribute("error", "Sorry, there was an error joining this group");
			model.addAttribute("groupList", groupDAO.getAllGroups());
			return "/student/th_join_group";
		}
	}

	// Create Group 1.1
	@RequestMapping("/student/createGroup")
	public String goStudentCreateGroup(Model model) {

		// TEST if students try to add group using links
		if (getAuthStudent().getGroup() != null) {
			model.addAttribute("student", getAuthStudent());
			return "/student/th_group_info";
		}

		model.addAttribute("group", new GroupBean());
		return "/student/th_create_group";
	}

	// Create Group 1.2
	@RequestMapping("/student/addGroup")
	public String goStudentAddGroup(Model model, @ModelAttribute GroupBean group) {

		GroupDAO groupDAO = new GroupDAO();
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

			// Adding Group info based on the Student's
			group.setGroupOwnerStudentId(s.getId());
			group.setProgram(s.getProgram());

			group.setPasscode(Integer.toString(pass));
			groupDAO.addGroup(group);
			// updating student
			s.setGroup(group);
			s.setGroupLeader(true);
			dao.updateStudent(s);

//			model.addAttribute("inGroup", true);
//			model.addAttribute("GroupInfo", s.getGroup());
			
			model.addAttribute("student", s);
			return "/student/th_group_info";

		} else {
			model.addAttribute("error", "Sorry, this group name is already registered");
			model.addAttribute("group", new GroupBean());
			return "/student/th_create_group";
		}
	}
	
	
	@RequestMapping("/student/leave_Group")
	public String leaveGroup(Model model) {
		
		Student s = getAuthStudent();
		
		if(s.getGroup() != null)
		{
			if(s.getGroup().getGroupOwnerStudentId() == s.getId())
			{
				model.addAttribute("error", "Group Leader cannot leave group if it has members");
				model.addAttribute("student", s);
				return "/student/th_group_info";
				
			}else
			{
				s.setGroup(null);
				dao.updateStudent(s);
				model.addAttribute("student", s);
				return "/student/th_group_info";
			}

		}else
		{
			model.addAttribute("error", "Student is not part of a group");
			model.addAttribute("student", s);
			return "/student/th_group_info";
		}	
	}


	@RequestMapping("/student/deleteGroup")
	public String deleteGroup() {

		return "/student/common/th_about";
	}

	@RequestMapping("/student")
	public String goStudentHome() {
		return "/student/common/th_about";
	}

	@RequestMapping("/student/about")
	public String goStudentAbout() {
		return "/student/common/th_about";
	}

	@RequestMapping("/student/meetProfs")
	public String goStudentProfs() {
		return "/student/common/th_profs";
	}

	@RequestMapping("/student/projects")
	public String goStudentPastProjects() {
		return "/student/common/th_pastProject";
	}

	@RequestMapping("/student/faq")
	public String goStudentFaq() {
		return "/student/common/th_faqs";
	}

	@RequestMapping("/student/contact")
	public String goStudentContact() {
		return "/student/common/th_contact";
	}

	public Student getAuthStudent() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName().toString();
		Student student = dao.getStudentByEmail(username);
		return student;

	}
}
