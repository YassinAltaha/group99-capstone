package ca.sheridancollege.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.Query;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import ca.sheridancollege.bean.GroupBean;
import ca.sheridancollege.bean.Professor;
import ca.sheridancollege.bean.Project;
import ca.sheridancollege.bean.Role;
import ca.sheridancollege.bean.Student;

public class ProfDAO {
	SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();

	public Professor findProfByEmail(String profEmail) {
		Session session = sessionFactory.openSession();
		List<Professor> profs = session.createQuery("from Professor where profEmail=:profEmail")
				.setParameter("profEmail", profEmail).list();
		if (profs.size() > 0) {
			Professor pr = profs.get(0);
			session.close();
			return pr;
		} else {
			System.out.println("*** NO PROF FOUND ***");
			session.close();
			return null;
		}
	}

	public void addProf(Professor p) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		
		String pass = p.getPassword();
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hasedPassword = passwordEncoder.encode(pass);
		
		p.setPassword(hasedPassword);
		p.setRole(Role.ROLE_ADMIN);

		session.save(p);
		
		session.getTransaction().commit();
		session.close();
	}
	
	public void updateProfessor(Professor p) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		session.update(p);
		
		session.getTransaction().commit();
		session.close();
	}

	public List<String> validateProfessor(Professor p) {
		List<String> errorList = new ArrayList<String>();
		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		Validator validator = validatorFactory.getValidator();

		Set<ConstraintViolation<Professor>> violationErrors = validator.validate(p);
		if (!violationErrors.isEmpty()) {
			for (ConstraintViolation<Professor> error : violationErrors) {
				errorList.add(error.getMessage());
			}
		}
		validatorFactory.close();
		return errorList;
	}
	
}
