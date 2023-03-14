/**
 * Time creation: Feb 25, 2023, 8:44:37 PM
 *
 * Pakage name: com.exam.dao
 */
package com.exam.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.exam.model.ExamModel;

/**
 * @author Tien Tran
 *
 * class ExamDAO
 */
@Repository(value = "examDAO")
@Transactional(rollbackFor = Exception.class)
public class ExamDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public void addExam(ExamModel exam) {
		
		Session session = sessionFactory.getCurrentSession();
		
		session.save(exam);
	}
}
