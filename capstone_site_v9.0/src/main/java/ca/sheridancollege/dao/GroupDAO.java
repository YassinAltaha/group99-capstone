package ca.sheridancollege.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import ca.sheridancollege.bean.GroupBean;

public class GroupDAO {


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
	
	public void updateGroup(GroupBean g)
	{
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
			session.update(g);
			
		session.getTransaction().commit();
		session.close();
	}
	
	
	public GroupBean getGroupById(int groupId) {
		
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		Query query = session.createQuery("from GroupBean where groupId=:groupId");
		query.setParameter("groupId", groupId);
		GroupBean group =  (GroupBean) query.getSingleResult();

		session.getTransaction().commit();
		session.close();

		return group;
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
