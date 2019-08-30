package ca.sheridancollege.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import ca.sheridancollege.bean.GroupBean;

public class GroupDAO {

	List<GroupBean> groupList = new ArrayList<GroupBean>();

	SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();

	public void addGroup(GroupBean groupBean) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		session.save(groupBean);

		session.getTransaction().commit();
		session.close();
	}
	
	
	
	public void deleteGroup(GroupBean groupBean) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		session.delete(groupBean);

		session.getTransaction().commit();
		session.close();
	}
	
	

	public List<GroupBean> searchGroupByName(String groupName) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		Query query = session.createQuery("from GroupBean where groupName=:groupName");
		query.setParameter("groupName", groupName);
		List<GroupBean> groupList = (List<GroupBean>) query.getResultList();

		session.getTransaction().commit();
		session.close();

		return groupList;
	}
	
	public List<GroupBean> getAllGroups() {
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		Query query = session.createQuery("from GroupBean");
		List<GroupBean> groupList = (List<GroupBean>) query.getResultList();

		session.getTransaction().commit();
		session.close();

		return groupList;
	}

}
