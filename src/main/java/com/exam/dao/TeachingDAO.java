/**
 * Time creation: Dec 4, 2022, 4:40:36 PM
 * 
 * Package name: com.exam.dao
 */
package com.exam.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.exam.model.TeachingModel;

/**
 * @author Tien Tran
 *
 * class TeachingDAO
 */
@Repository(value = "teachingDAO")
@Transactional(rollbackFor = Exception.class)
public class TeachingDAO {
	
	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public void addTeaching(TeachingModel teaching) {
		
		Session session = sessionFactory.getCurrentSession();
		
		session.save(teaching);
	}
	
	public void addTeachings(String queryString) {
		
		Session session = sessionFactory.getCurrentSession();
		
		Query<?> query = session.createSQLQuery(queryString);
		
		query.executeUpdate();
	}
	
	public void updateTeachings(String lecturerId, Byte status, String queryString) {
		
		Session session = sessionFactory.getCurrentSession();
		
		Query<?> query = session.createSQLQuery(queryString);
		query.setParameter("lecturerId", lecturerId);
		query.setParameter("status", status);
		
		query.executeUpdate();
	}
	
	public List<TeachingModel> getTeachingListByLecturerId(String lecturerId, Byte status) {
		
		String queryString = 
				"FROM TeachingModel t where t.lecturerId = :lecturerId and t.deleted = :status";
		
		Session session = sessionFactory.getCurrentSession();
		
		Query<?> query = session.createQuery(queryString);
		query.setParameter("lecturerId", lecturerId);
		query.setParameter("status", status);
		
		@SuppressWarnings("unchecked")
		List<TeachingModel> teachingList = (List<TeachingModel>) query.getResultList();
		
		return teachingList;
	}
	
	public List<String> getSubjectIdList(String lecturerId) {
		
		String queryString = 
				"SELECT t.subjectId FROM TeachingModel t where t.lecturerId = :lecturerId";
		
		Session session = sessionFactory.getCurrentSession();
		
		Query<?> query = session.createQuery(queryString);
		query.setParameter("lecturerId", lecturerId);
		
		@SuppressWarnings("unchecked")
		List<String> subjectIdList = (List<String>) query.getResultList();
		
		return subjectIdList;
	}
}
