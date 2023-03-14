/**
 * Time creation: Dec 4, 2022, 11:43:07 PM
 * 
 * Package name: com.exam.service
 */
package com.exam.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.exam.interfaces.ObjectDAO;
import com.exam.model.TopicModel;

/**
 * @author Tien Tran
 *
 * class TopicDAO
 */
@Repository(value = "topicDAO")
@Transactional(rollbackFor = Exception.class)
public class TopicDAO implements ObjectDAO<TopicModel>{

	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public TopicModel findById(String id) {
		
		Session session = sessionFactory.getCurrentSession();
		
		return session.find(TopicModel.class, id);
	}

	@Override
	public void addObject(TopicModel object) {
		
		Session session = sessionFactory.getCurrentSession();
		
		session.save(object);
	}

	@Override
	public void updateObject(TopicModel object) {
		
		Session session = sessionFactory.getCurrentSession();
		
		session.update(object);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TopicModel> findAllObjects() {
		
		String queryString = "FROM TopicModel t order by t.topicName ASC";
		
		Session session = sessionFactory.getCurrentSession();

		Query<?> query = session.createQuery(queryString);
		
		List<TopicModel> topicList = (List<TopicModel>) query.getResultList();
		
		return topicList;
	}

	@Override
	public void removeObjects(String queryString, Byte status) {
		
		Session session = sessionFactory.getCurrentSession();
		
		Query<?> query = session.createQuery(queryString);
		query.setParameter("status", status);
		
		query.executeUpdate();
	}
	
	public List<TopicModel> findBySubjectId(String subjectId, Byte status) {
		
		StringBuilder builder = new StringBuilder();
		
		builder.append("SELECT new TopicModel(t.topicId, t.topicName) FROM TopicModel t ");
		builder.append("WHERE t.subjectId = :subjectId AND t.deleted = :status");
		
		Session session = sessionFactory.getCurrentSession();

		Query<?> query = session.createQuery(builder.toString());
		
		query.setParameter("subjectId", subjectId);
		query.setParameter("status", status);
		
		@SuppressWarnings("unchecked")
		List<TopicModel> topicList = (List<TopicModel>) query.getResultList();
		
		return topicList;
	}
	
	public List<TopicModel> findBySubDepartmentId(String subDepartmentId, Byte status) {
		
		StringBuilder builder = new StringBuilder();
		
		builder.append("FROM TopicModel t ");
		builder.append("WHERE t.subject.subDepartment.subDepartmentId = :subDepartmentId ");
		builder.append(" AND t.deleted = :status");
		
		Session session = sessionFactory.getCurrentSession();

		Query<?> query = session.createQuery(builder.toString());
		
		query.setParameter("subDepartmentId", subDepartmentId);
		query.setParameter("status", status);
		
		@SuppressWarnings("unchecked")
		List<TopicModel> topicList = (List<TopicModel>) query.getResultList();
		
		return topicList;
	}
}
