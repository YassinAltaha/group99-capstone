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

import ca.sheridancollege.bean.*;

public class ClientDAO {

	SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();

	public void addClient(Client c) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		String pass = c.getPassword();
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hasedPassword = passwordEncoder.encode(pass);

		Client client = new Client(c.getClientName(), c.getClientCompany(), c.getClientContact(), c.getClientAddress(), 
				c.getClientIndustry(), c.getClientScale(), c.getClientEmail(), hasedPassword);

		client.setRole(Role.ROLE_CLIENT);
		session.save(client);
		session.getTransaction().commit();
		session.close();

	}

	public Client findClientByEmail(String email) {
		Session session = sessionFactory.openSession();
		
		List<Client> client = session.createQuery("from Client where clientEmail=:clientEmail")
				.setParameter("clientEmail", email).list();
		if (client.size() > 0) {
			Client cl = client.get(0);
			session.close();
			return cl;
		} else {
			System.out.println("*** NO CLIENT FOUND ***");
			session.close();
			return null;
		}
	}

	public int addProject(Project p, int clientId) { // , String status,
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		Query query = session.createQuery("FROM Client where clientId=:clientId");
		query.setParameter("clientId", clientId);
		List<Client> resultList = (List<Client>) query.getResultList();

		Client client = resultList.get(0);

		p.setClient(client);
//		p.setStatus(status);

		client.getProjectList().add(p);
		session.update(client);
		session.save(p);
		int aP = 1;

		session.getTransaction().commit();
		session.close();

		return aP;
	}

	public List<Project> getMyProjects(int clientId) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		Query query = session.createQuery("from Project where client.clientId=:clientId"); // client.clientId
		query.setParameter("clientId", clientId);
		List<Project> projectList = (List<Project>) query.getResultList();

		session.getTransaction().commit();
		session.close();

		if (projectList.isEmpty()) {
			return null;
		} else {
			return projectList;
		}
	}

	public Project searchProjectById(int projectId) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		Query query = session.createQuery("from Project where projectId=:projectId");
		query.setParameter("projectId", projectId);
		List<Project> projectList = (List<Project>) query.getResultList();

		session.getTransaction().commit();
		session.close();

		if (projectList.isEmpty()) {
			return null;
		} else {
			return projectList.get(0);
		}
	}

	public int getClientId(int projectId) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		Query query = session.createQuery("Select client.clientId from Project where projectId=:projectId");
		query.setParameter("projectId", projectId);

		int clientId = (Integer) query.getSingleResult();
		session.getTransaction().commit();
		session.close();

		return clientId;

	}

	public Client getClientByEmail(String clientEmail) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		Query query = session.createQuery("from Client where clientEmail=:clientEmail");
		query.setParameter("clientEmail", clientEmail);

		Client c = (Client) query.getSingleResult();
		session.getTransaction().commit();
		session.close();

		return c;

	}
	
	public void updateClient(Client c) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		session.update(c);
		session.getTransaction().commit();
		session.close();
	}

	public void editMyProject(Project project, int projectId) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		Project p = (Project) session.get(Project.class, projectId);
		p.setTitle(project.getTitle());
		p.setDescription(project.getDescription());

		session.getTransaction().commit();
		session.close();
	}
	
	public List<Client> getAllClients() {
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		Query query = session.createQuery("from Client");
		List<Client> groupList = (List<Client>) query.getResultList();

		session.getTransaction().commit();
		session.close();

		return groupList;
	}
	
	public Client getClientById(int id) {

		Session session = sessionFactory.openSession();
		session.beginTransaction();

		Query query = session.createQuery("from Client where clientId=:clientId");
		query.setParameter("clientId", id);
		Client c = (Client) query.getSingleResult();

		session.getTransaction().commit();
		session.close();

		return c;
	}

	public List<String> validateClint(Client c) {
		List<String> errorList = new ArrayList<String>();
		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		Validator validator = validatorFactory.getValidator();

		Set<ConstraintViolation<Client>> violationErrors = validator.validate(c);
		if (!violationErrors.isEmpty()) {
			for (ConstraintViolation<Client> error : violationErrors) {
				errorList.add(error.getMessage());
			}
		}
		validatorFactory.close();
		return errorList;
	}
	
	public List<String> validateProject(Project p) {
		List<String> errorList = new ArrayList<String>();
		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		Validator validator = validatorFactory.getValidator();

		Set<ConstraintViolation<Project>> violationErrors = validator.validate(p);
		if (!violationErrors.isEmpty()) {
			for (ConstraintViolation<Project> error : violationErrors) {
				errorList.add(error.getMessage());
			}
		}
		validatorFactory.close();
		return errorList;
	}
	
	

}
