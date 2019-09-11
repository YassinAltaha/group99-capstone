package ca.sheridancollege;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class SuccessLoginHandler implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		boolean admin = false;
		boolean client = false;
		boolean student = false;

		// SEARCHING THE AUTHENTICATION FOR THE USER'S ROLE
		// assign the boolean values based on the role
		for (GrantedAuthority auth : authentication.getAuthorities()) {
			if (auth.getAuthority() == "ROLE_ADMIN") {
				admin = true;
				break;
			} else if (auth.getAuthority() == "ROLE_CLIENT") {
				client = true;
				break;
			} else if (auth.getAuthority() == "ROLE_STUDENT") {
				student = true;
				break;
			}
		}

		// REDIRECTION based on the role of the authentication authorities role
		if (admin) {
			response.sendRedirect("/professor/about/");
		} else if (client) {
			response.sendRedirect("/client/myProject");
		} else if (student) {
			response.sendRedirect("/student/group_info/");
		}

	}

}
