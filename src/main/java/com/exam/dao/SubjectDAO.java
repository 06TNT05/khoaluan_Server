/**
 * Time creation: Dec 4, 2022, 7:01:10 PM
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

import com.exam.common.Constants;
import com.exam.interfaces.ObjectDAO;
import com.exam.model.SubjectModel;

/**
 * @author Tien Tran
 *
 * class SubjectDAO
 */
@Repository(value = "subjectDAO")
@Transactional(rollbackFor = Exception.class)
public class SubjectDAO implements ObjectDAO<SubjectModel>{

	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public SubjectModel findById(String id) {
		
		Session session = sessionFactory.getCurrentSession();
		
		return session.find(SubjectModel.class, id);
	}

	@Override
	public void addObject(SubjectModel subject) {
		
		Session session = sessionFactory.getCurrentSession();
		
		session.save(subject);
	}

	@Override
	public void updateObject(SubjectModel subject) {
		
		Session session = sessionFactory.getCurrentSession();
		
		session.update(subject);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SubjectModel> findAllObjects() {
		
		String queryString = "FROM SubjectModel s order by s.subjectName ASC";
		
		Session session = sessionFactory.getCurrentSession();
		
		Query<SubjectModel> query = session.createQuery(queryString);
		
		List<SubjectModel> subjectList = (List<SubjectModel>) query.getResultList();
		
		return subjectList;
	}
	
	@SuppressWarnings("unchecked")
	public List<SubjectModel> findAllObjectsPagination(Integer page, String searchString) {
		
		StringBuilder builder = new StringBuilder("FROM SubjectModel s");
		
		if (searchString != null) {
			builder.append(String.format(" where s.subjectId like '%%%s%%' or ", searchString));
			builder.append(String.format("s.subjectName like '%%%s%%' or ", searchString));
			builder.append(String.format("s.creditQuantity = '%s' or ", searchString));
			builder.append(String.format("s.subDepartment.subDepartmentName like '%%%s%%' ", searchString));
		}
		
		builder.append("order by s.subjectName ASC");

		Session session = sessionFactory.getCurrentSession();
		
		Query<?> query = session.createQuery(builder.toString());
		query.setFirstResult(Constants.MAX_RESULT * (page - 1));
		query.setMaxResults(Constants.MAX_RESULT);
		
		List<SubjectModel> subjectList = (List<SubjectModel>) query.getResultList();
		
		return subjectList;
	}

	@Override
	public void removeObjects(String queryString, Byte status) {
		
		Session session = sessionFactory.getCurrentSession();
		Query<?> query = session.createQuery(queryString);
		query.setParameter("status", status);
		query.executeUpdate();
	}
	
	@SuppressWarnings("unchecked")
	public List<SubjectModel> findBySubDepartmentId(String subDepartmentId, Byte status) {
		
		StringBuilder builder = new StringBuilder();
		builder.append("FROM SubjectModel s where s.subDepartmentId = :subDepartmentId ");
		builder.append("and s.deleted = :status order by s.subjectName");
		
		Session session = sessionFactory.getCurrentSession();
		
		Query<SubjectModel> query = session.createQuery(builder.toString());
		query.setParameter("subDepartmentId", subDepartmentId);
		query.setParameter("status", status);
		
		List<SubjectModel> subjectList = (List<SubjectModel>) query.getResultList();
		
		return subjectList;
	}
	
	@SuppressWarnings("unchecked")
	public List<SubjectModel> findSubjectByStatus(Byte status) {
		
		StringBuilder builder = new StringBuilder();
		builder.append("FROM SubjectModel s WHERE s.deleted = :status ");
		builder.append("order by s.subjectName ASC");
		
		Session session = sessionFactory.getCurrentSession();
		Query<?> query = session.createQuery(builder.toString());
		query.setParameter("status", status);
		List<SubjectModel> subjectList = (List<SubjectModel>) query.getResultList();
		
		return subjectList;
	}
	
	public void addSubjects(String queryString) {
		
		Session session = sessionFactory.getCurrentSession();
		
		Query<?> query = session.createSQLQuery(queryString);
		
		query.executeUpdate();
	}
	
	public Long getTotalRecord(String searchString) {
		
		StringBuilder builder = new StringBuilder("SELECT COUNT(*) AS totalRecord FROM SubjectModel s");
		
		if (searchString != null) {
			builder.append(String.format(" where s.subjectId like '%%%s%%' or ", searchString));
			builder.append(String.format("s.subjectName like '%%%s%%' or ", searchString));
			builder.append(String.format("s.creditQuantity = '%s' or ", searchString));
			builder.append(String.format("s.subDepartment.subDepartmentName like '%%%s%%'", searchString));
		}
		
		Session session = sessionFactory.getCurrentSession();
		
		Query<?> query = session.createQuery(builder.toString());
		
		return (Long) query.uniqueResult();
	}
	
	public List<SubjectModel> findByLecturerId(String lecturerId, Byte status) {
		
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT new SubjectModel(t.subject.subjectId, t.subject.subjectName) FROM TeachingModel t ");
		builder.append("WHERE t.lecturerId = :lecturerId and t.deleted = :status order by t.subject.subjectName ASC");
		
		Session session = sessionFactory.getCurrentSession();
		
		Query<?> query = session.createQuery(builder.toString());
		
		query.setParameter("lecturerId", lecturerId);
		query.setParameter("status", status);

		@SuppressWarnings("unchecked")
		List<SubjectModel> subjectList = (List<SubjectModel>) query.getResultList();
		
		return subjectList;
	}
}
