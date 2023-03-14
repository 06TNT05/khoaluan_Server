/**
 * Time creation: Feb 14, 2023, 12:44:36 PM
 *
 * Pakage name: com.exam.dao
 */
package com.exam.dao;

import java.io.IOException;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.exam.common.Constants;
import com.exam.model.QuestionModel;

/**
 * @author Tien Tran
 *
 * class QuestionDAO
 */
@Repository(value = "questionDAO")
@Transactional(rollbackFor = Exception.class)
public class QuestionDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public List<QuestionModel> findAllQuestions() {
		
		String queryString = "FROM QuestionModel q ORDER BY q.timeCreation DESC";
		
		Session session = sessionFactory.getCurrentSession();
		
		@SuppressWarnings("unchecked")
		Query<QuestionModel> query = session.createQuery(queryString);
		
		List<QuestionModel> questionList = (List<QuestionModel>) query.getResultList();
		
		return questionList;
	}
	
	@SuppressWarnings("unchecked")
	public List<QuestionModel> findAllQuestionsPagination(Integer page, String searchString) {
		
		StringBuilder builder = new StringBuilder("FROM QuestionModel q ");
		
		if (!searchString.isEmpty()) {
			builder.append(String.format("where q.questionId like '%%%s%%' or ", searchString));
			builder.append(String.format("q.questionContent like '%%%s%%' or ", searchString));
			builder.append(String.format("q.answerCorrect like '%%%s%%' or ", searchString));
			builder.append(String.format("q.timeCreation like '%%%s%%' or ", searchString));
			builder.append(String.format("q.topic.topicName like '%%%s%%' or ", searchString));
			builder.append(String.format("q.questionType.questionTypeName like '%%%s%%' or ", searchString));
			builder.append(String.format("q.level.levelName like '%%%s%%' or ", searchString));
			builder.append(String.format("q.lecturer.lecturerName like '%%%s%%' ", searchString));
		}
		
		builder.append("order by q.timeCreation DESC, q.topic.topicName ASC");

		Session session = sessionFactory.getCurrentSession();
		
		Query<?> query = session.createQuery(builder.toString());
		query.setFirstResult(Constants.MAX_RESULT * (page - 1));
		query.setMaxResults(Constants.MAX_RESULT);
		
		List<QuestionModel> questionList = (List<QuestionModel>) query.getResultList();
		
		return questionList;
	}
	
	public List<QuestionModel> findByLecturerId(Integer page, String searchString, String lecturerId) {
		
		StringBuilder builder = new StringBuilder("FROM QuestionModel q WHERE q.lecturerId = :lecturerId ");
		
		if (!searchString.isEmpty()) {
			builder.append(String.format("AND (q.questionId like '%%%s%%' or ", searchString));
			builder.append(String.format("q.questionContent like '%%%s%%' or ", searchString));
			builder.append(String.format("q.answerCorrect like '%%%s%%' or ", searchString));
			builder.append(String.format("q.timeCreation like '%%%s%%' or ", searchString));
			builder.append(String.format("q.topic.topicName like '%%%s%%' or ", searchString));
			builder.append(String.format("q.questionType.questionTypeName like '%%%s%%' or ", searchString));
			builder.append(String.format("q.level.levelName like '%%%s%%' or ", searchString));
			builder.append(String.format("q.lecturer.lecturerName like '%%%s%%') ", searchString));
		}
		
		builder.append("order by q.timeCreation DESC, q.topic.topicName ASC");

		Session session = sessionFactory.getCurrentSession();
		
		Query<?> query = session.createQuery(builder.toString());
		
		query.setParameter("lecturerId", lecturerId);
		query.setFirstResult(Constants.MAX_RESULT * (page - 1));
		query.setMaxResults(Constants.MAX_RESULT);
		
		@SuppressWarnings("unchecked")
		List<QuestionModel> questionList = (List<QuestionModel>) query.getResultList();
		
		return questionList;
	}
	
	public void addQuestion(QuestionModel question) {
		
		Session session = sessionFactory.getCurrentSession();
		
		session.save(question);
	}
	
	public void addQuestionWithBatch(List<QuestionModel> questionList) throws IOException {
		
		Session session = sessionFactory.getCurrentSession();
		int count = 0;
		for (QuestionModel question : questionList) {
			
			session.save(question);
			
			if (++count % Constants.BATCH_SIZE == Constants.ZERO) {
				
				//flush a batch of inserts and release memory:
	            session.flush();
	            session.clear();
			}
		}
	}
	
	public Session getCurrentSessin() {
		
		return sessionFactory.getCurrentSession();
	}
	
	public QuestionModel findById(String questionId) {
		
		Session session = sessionFactory.getCurrentSession();
		
		return session.find(QuestionModel.class, questionId);
	}
	
	public void updateQuestion(QuestionModel question) {
		
		Session session = sessionFactory.getCurrentSession();
		
		session.update(question);
	}

	public void removeObjects(String queryString, Byte status) {
		
		Session session = sessionFactory.getCurrentSession();
		Query<?> query = session.createQuery(queryString);
		query.setParameter("status", status);
		query.executeUpdate();
	}
	
	public Long getTotalRecord(String searchString) {
		
		StringBuilder builder = new StringBuilder("SELECT COUNT(*) AS totalRecord FROM QuestionModel q ");
		
		if (!searchString.isEmpty()) {
			builder.append(String.format("where q.questionId like '%%%s%%' or ", searchString));
			builder.append(String.format("q.questionContent like '%%%s%%' or ", searchString));
			builder.append(String.format("q.answerCorrect like '%%%s%%' or ", searchString));
			builder.append(String.format("q.timeCreation like '%%%s%%' or ", searchString));
			builder.append(String.format("q.topic.topicName like '%%%s%%' or ", searchString));
			builder.append(String.format("q.questionType.questionTypeName like '%%%s%%' or ", searchString));
			builder.append(String.format("q.level.levelName like '%%%s%%' or ", searchString));
			builder.append(String.format("q.lecturer.lecturerName like '%%%s%%'", searchString));
		}
		
		Session session = sessionFactory.getCurrentSession();
		
		Query<?> query = session.createQuery(builder.toString());
		
		return (Long) query.uniqueResult();
	}
	
	public Long getTotalRecord(String searchString, String lecturerId) {
		
		StringBuilder builder = new StringBuilder("SELECT COUNT(*) AS totalRecord FROM QuestionModel q ");
		builder.append("WHERE q.lecturerId = :lecturerId ");
		
		if (!searchString.isEmpty()) {
			builder.append(String.format("AND (q.questionId like '%%%s%%' or ", searchString));
			builder.append(String.format("q.questionContent like '%%%s%%' or ", searchString));
			builder.append(String.format("q.answerCorrect like '%%%s%%' or ", searchString));
			builder.append(String.format("q.timeCreation like '%%%s%%' or ", searchString));
			builder.append(String.format("q.topic.topicName like '%%%s%%' or ", searchString));
			builder.append(String.format("q.questionType.questionTypeName like '%%%s%%' or ", searchString));
			builder.append(String.format("q.level.levelName like '%%%s%%' or", searchString));
			builder.append(String.format("q.lecturer.lecturerName like '%%%s%%')", searchString));
		}
		
		Session session = sessionFactory.getCurrentSession();
		
		Query<?> query = session.createQuery(builder.toString());
		query.setParameter("lecturerId", lecturerId);
		
		return (Long) query.uniqueResult();
	}
	
	public List<QuestionModel> getRandomQuetion(String topicId, String levelId,
			String questionTypeId, Integer questionQuantity) {
		
		StringBuilder builder = new StringBuilder();
		Session session = sessionFactory.getCurrentSession();
		
		builder.append("FROM QuestionModel q WHERE q.topicId = :topicId AND ");
		builder.append("q.levelId = :levelId AND q.questionTypeId = :questionTypeId ORDER BY RAND()");
		
		Query<?> query = session.createQuery(builder.toString());
		
		query.setParameter("topicId", topicId);
		query.setParameter("levelId", levelId);
		query.setParameter("questionTypeId", questionTypeId);
		query.setMaxResults(questionQuantity);
		
		@SuppressWarnings("unchecked")
		List<QuestionModel> questionList = (List<QuestionModel>) query.getResultList();
		
		return questionList;
	}
}
