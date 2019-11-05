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

import ca.sheridancollege.bean.Client;
import ca.sheridancollege.bean.Professor;
import ca.sheridancollege.bean.Project;
import ca.sheridancollege.bean.Role;
import ca.sheridancollege.bean.Student;

public class StudentDAO {

	SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();

	public void addStudent(Student s) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		String pass = s.getPassword();
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(pass);

		s.setPassword(hashedPassword);

		s.setRole(Role.ROLE_STUDENT);
		session.save(s);
		session.getTransaction().commit();
		session.close();

	}
	
	public Student getStudentById(int id) {

		Session session = sessionFactory.openSession();
		session.beginTransaction();

		Query query = session.createQuery("from Student where id=:sId");
		query.setParameter("sId", id);
		Student s = (Student) query.getSingleResult();

		session.getTransaction().commit();
		session.close();

		return s;
	}

	public Student getStudentByEmail(String email) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		Query query = session.createQuery("From Student where student_email=:student_email");
		query.setParameter("student_email", email);

		Student s = (Student) query.getSingleResult();

		session.getTransaction().commit();
		session.close();

		return s;
	}

	public void updateStudent(Student s) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		session.update(s);
		
		session.getTransaction().commit();
		session.close();
	}

	public Boolean checkStudentByEmail(String email) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		Query query = session.createQuery("from Student where student_email=:student_email");
		query.setParameter("student_email", email);

		List<Student> s = (List<Student>) query.getResultList();
		session.getTransaction().commit();
		session.close();

		if (s.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	public Boolean checkStudenID(String studentID) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		Query query = session.createQuery("from Student where student_id=:studentID");
		query.setParameter("studentID", studentID);

		List<Student> s = (List<Student>) query.getResultList();
		session.getTransaction().commit();
		session.close();

		if (s.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	public List<String> validateStudent(Student s) {
		List<String> errorList = new ArrayList<String>();
		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		Validator validator = validatorFactory.getValidator();

		Set<ConstraintViolation<Student>> violationErrors = validator.validate(s);
		if (!violationErrors.isEmpty()) {
			for (ConstraintViolation<Student> error : violationErrors) {
				errorList.add( error.getMessage());
			}
		}
		return errorList;
	}

	
	public List<Student> getAllStudents() {

		Session session = sessionFactory.openSession();
		session.beginTransaction();

		Query query = session.createQuery("from Student");
		List<Student> studentList = (List<Student>) query.getResultList();

		session.getTransaction().commit();
		session.close();

		if (studentList.isEmpty()) {
			return null;
		} else {
			return studentList;
		}
	}
	
	public Boolean checkProfCode(String profCode) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		Query query = session.createQuery("from Professor where profCode=:profCode");
		query.setParameter("profCode", profCode);

		List<Professor> p = (List<Professor>) query.getResultList();
		session.getTransaction().commit();
		session.close();

		if (p.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

}
