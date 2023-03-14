/**
 * Time creation: Feb 13, 2023, 2:17:47 PM
 *
 * Pakage name: com.exam.dao
 */
package com.exam.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.exam.model.LevelModel;

/**
 * @author Tien Tran
 *
 * class LevelDAO
 */
@Repository(value = "levelDAO")
@Transactional(rollbackFor = Exception.class)
public class LevelDAO {

	@Autowired
	SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public List<LevelModel> findLevelsNotDeleted(Byte status) {
		
		String queryString = "FROM LevelModel l WHERE l.deleted = :status";
		
		Session session = sessionFactory.getCurrentSession();
		
		Query<?> query = session.createQuery(queryString);
		query.setParameter("status", status);
		
		@SuppressWarnings("unchecked")
		List<LevelModel> levelList = (List<LevelModel>) query.getResultList();
		
		return levelList;
	}
}
