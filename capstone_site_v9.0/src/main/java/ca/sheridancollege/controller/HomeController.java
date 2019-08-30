package ca.sheridancollege.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ca.sheridancollege.dao.ProfDAO;

@Controller
public class HomeController {

	
	//Main Page
	@GetMapping("/")
	public String goMainIndex() {
		return "default/th_about";
	}

	@GetMapping("/login")
	public String goLogin() {
		return "th_login";
	}

	@GetMapping("/access_denied")
	public String goError() {
		return "error/th_access_denied";
	}

	// GUEST PROCESS
	@RequestMapping("/about")
	public String goAbout() {
		return "/default/th_about";
	}

	@RequestMapping("/meetProfs")
	public String goProfs() {
		return "/default/th_prof";
	}

	@RequestMapping("/projects")
	public String goPastProjects() {
		return "/default/th_pastProject";
	}

	@RequestMapping("/faq")
	public String goFaq() {
		return "/default/th_faq";
	}

	@RequestMapping("/contact")
	public String goContact() {
		return "/default/th_contact";
	}


}
