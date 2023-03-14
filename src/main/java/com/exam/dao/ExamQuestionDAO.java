/**
 * Time creation: Feb 27, 2023, 12:30:20 PM
 *
 * Pakage name: com.exam.dao
 */
package com.exam.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.exam.common.Constants;
import com.exam.model.ExamQuestionModel;

/**
 * @author Tien Tran
 *
 * class ExamQuestionDAO
 */
@Repository(value = "examQuestionDAO")
@Transactional(rollbackFor = Exception.class)
public class ExamQuestionDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public void addExamQuestionWithBatch(List<ExamQuestionModel> examQuestionList) {
		
		Integer count = 0;
		Session session = sessionFactory.getCurrentSession();
		
		for (ExamQuestionModel examQuestion : examQuestionList) {
			
			session.save(examQuestion);
			
			if (++count % Constants.BATCH_SIZE == Constants.ZERO) {
				
				//flush a batch of inserts and release memory:
	            session.flush();
	            session.clear();
			}
		}
	}
}
