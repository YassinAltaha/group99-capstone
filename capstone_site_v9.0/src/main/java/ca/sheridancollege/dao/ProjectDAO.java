package ca.sheridancollege.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import ca.sheridancollege.bean.GroupBean;
import ca.sheridancollege.bean.Project;

public class ProjectDAO {
	SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();

	public List<Project> getProjects() {
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		Query query = session.createQuery("from Project");
		List<Project> projectList = (List<Project>) query.getResultList();

		session.getTransaction().commit();
		session.close();

		if (projectList.isEmpty()) {
			return null;
		} else {
			return projectList;
		}
	}

	public List<Project> getApprovedProjects() {
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		Query query = session.createQuery("from Project where status=:status");
		query.setParameter("status", "Approved");
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

	public void editProject(Project project, int projectId) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		Project p = (Project) session.get(Project.class, projectId);
		p.setStatus(project.getStatus());
		p.setProfNote(project.getProfNote());

		session.getTransaction().commit();
		session.close();
	}

	public void clientUpdateProject(Project project, int projectId) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		Project p = (Project) session.get(Project.class, projectId);
		p.setDescription(project.getDescription());
		p.setTitle(project.getTitle());

		session.getTransaction().commit();
		session.close();
	}

	public void updateProject(Project p) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		session.update(p);

		session.getTransaction().commit();
		session.close();
	}

	public int assignProject(int projectId, int groupId) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		Query query = session.createQuery("from GroupBean where groupId=:groupId");
		query.setParameter("groupId", groupId);

		GroupBean g = (GroupBean) query.getSingleResult();

		Project p = (Project) session.get(Project.class, projectId);
		p.setGroupBean(g);
		g.setProject(p);
//		p.setSta("Assigned");

		session.getTransaction().commit();
		session.close();
		return 1;
	}

	public List<Double> statusPercentage() {
		List<Double> statusPerList = new ArrayList<Double>();

		Session session = sessionFactory.openSession();
		session.beginTransaction();

		Query query1 = session.createQuery("SELECT COUNT(*) from Project WHERE status='Pending'");
		long penNum = (long) query1.getSingleResult();
		Query query2 = session.createQuery("SELECT COUNT(*) from Project WHERE status='Approved'");
		long appNum = (long) query2.getSingleResult();
		Query query3 = session.createQuery("SELECT COUNT(*) from Project WHERE status='Rejected'");
		long rejNum = (long) query3.getSingleResult();
		Query query4 = session.createQuery("SELECT COUNT(*) from Project WHERE status='Completed'");
		long comNum = (long) query4.getSingleResult();

		Query query = session.createQuery("SELECT COUNT(*) from Project WHERE status is not null");
		long total = (long) query.getSingleResult();

		double penNumber = (double) penNum / total;
		double appNumber = (double) appNum / total;
		double rejNumber = (double) rejNum / total;
		double comNumber = (double) comNum / total;

		double pP = Math.round(penNumber * 100);
		double aP = Math.round(appNumber * 100);
		double rP = Math.round(rejNumber * 100);
		double cP = Math.round(comNumber * 100);

		statusPerList.add(pP);
		statusPerList.add(aP);
		statusPerList.add(rP);
		statusPerList.add(cP);

		session.getTransaction().commit();
		session.close();
		return statusPerList;
	}

	public void deleteProject(Project p) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		session.delete(p);

		session.getTransaction().commit();
		session.close();
	}

	public void deleteProjectById(int pid) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		Project p = session.get(Project.class, pid);
		session.delete(p);

		session.getTransaction().commit();
		session.close();
	}

}
