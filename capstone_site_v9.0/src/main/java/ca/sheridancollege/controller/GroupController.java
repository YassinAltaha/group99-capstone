package ca.sheridancollege.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import ca.sheridancollege.bean.GroupBean;
import ca.sheridancollege.dao.GroupDAO;

@Controller
public class GroupController {

	GroupDAO groupDAO = new GroupDAO();

	@RequestMapping("/createGroup")
	public String goAboutStudent(Model model) {

		// give a new Group Bean
		GroupBean g = new GroupBean();
		// Attach the Group Bean to The model with "groupBean"
		model.addAttribute("groupBean", g);
		return "/student/createGroup";
	}

	@RequestMapping("/addGroup")
	public String goAddGroup(Model model, @ModelAttribute GroupBean groupBean) {

		String name = groupBean.getGroupName().toString();
		// testing if the group name is used
		if (groupDAO.searchGroupByName(name).isEmpty()) {

			groupDAO.addGroup(groupBean);
			model.addAttribute("groupSuccess", "Group successfully created");

		} else {
			model.addAttribute("groupSuccess", "Sorry, this group name is already in use");
		}

		return "/student/createGroup";
	}

	@RequestMapping("/deleteGroup")
	public String goDeleteGroup() {
		return "student/deleteGroup";
	}

	@RequestMapping("/leaveGroup")
	public String goleaveGroup() {
		return "student/leaveGroup";
	}

	@RequestMapping("/joinGroup")
	public String goJoinGroup() {
		return "student/joinGroup";
	}

	@RequestMapping("/viewGroup")
	public String goViewGroup() {
		return "student/viewGroup";
	}

	@RequestMapping("/viewProjects")
	public String goViewProjects() {
		return "student/viewProjects";
	}

}
