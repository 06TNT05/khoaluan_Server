/**
 * Time creation: Feb 13, 2023, 2:44:40 PM
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

import com.exam.model.QuestionTypeModel;

/**
 * @author Tien Tran
 *
 * class QuestionTypeDAO
 */
@Repository(value = "questionTypeDAO")
@Transactional(rollbackFor = Exception.class)
public class QuestionTypeDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public List<QuestionTypeModel> findQuestionTypesNotDeleted(Byte status) {
		
		String queryString = "FROM QuestionTypeModel q WHERE q.deleted = :status";
		
		Session session = sessionFactory.getCurrentSession();
		
		Query<?> query = session.createQuery(queryString);
		query.setParameter("status", status);
		
		@SuppressWarnings("unchecked")
		List<QuestionTypeModel> questionTypeList = (List<QuestionTypeModel>) query.getResultList();
		
		return questionTypeList;
	}
}
