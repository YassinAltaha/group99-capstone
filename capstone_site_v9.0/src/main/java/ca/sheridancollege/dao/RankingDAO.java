package ca.sheridancollege.dao;

import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import ca.sheridancollege.bean.GroupBean;
import ca.sheridancollege.bean.Ranking;

public class RankingDAO {
	SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();

	public void addRanking(Ranking ranking) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		session.save(ranking);

		session.getTransaction().commit();
		session.close();
	}
	
	public void deleteRanking(Ranking ranking) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		session.delete(ranking);

		session.getTransaction().commit();
		session.close();
	}
	
	public void updateRanking(Ranking ranking) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		session.update(ranking);

		session.getTransaction().commit();
		session.close();
	}
	
	public Ranking getRankingById(int rankId) {

		Session session = sessionFactory.openSession();
		session.beginTransaction();

		Query query = session.createQuery("from Ranking where rankId=:rankId");
		query.setParameter("rankId", rankId);
		Ranking rank = (Ranking) query.getSingleResult();

		session.getTransaction().commit();
		session.close();

		return rank;
	}
	
}
