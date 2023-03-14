/**
 * Time creation: Dec 3, 2022, 9:12:39 PM
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
import com.exam.model.LecturerModel;

/**
 * @author Tien Tran
 *
 * class LecturerDAO
 */
@Repository(value = "lecturerDAO")
@Transactional(rollbackFor = Exception.class)
public class LecturerDAO implements ObjectDAO<LecturerModel>{

	@Autowired
	SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public LecturerModel findById(String id) {
		
		Session session = sessionFactory.getCurrentSession();
		
		return session.find(LecturerModel.class, id);
	}

	@Override
	public void addObject(LecturerModel object) {
		
		Session session = sessionFactory.getCurrentSession();
		
		session.save(object);
	}

	@Override
	public void updateObject(LecturerModel object) {
		
		Session session = sessionFactory.getCurrentSession();
		
		session.update(object);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LecturerModel> findAllObjects() {
		
		String queryString = "FROM LecturerModel l order by l.lecturerName ASC";
		
		Session session = sessionFactory.getCurrentSession();
		
		Query<?> query = session.createQuery(queryString);
		
		List<LecturerModel> lecturerList = (List<LecturerModel>) query.getResultList();
		
		return lecturerList;
	}
	
	@SuppressWarnings("unchecked")
	public List<LecturerModel> findAllObjectsPagination(Integer page, String searchString) {
		
		StringBuilder builder = new StringBuilder("FROM LecturerModel l ");
		
		if (searchString != null) {
			builder.append(String.format("where l.lecturerId like '%%%s%%' or ", searchString));
			builder.append(String.format("l.lecturerName like '%%%s%%' or ", searchString));
			builder.append(String.format("l.phoneNumber like '%%%s%%' or ", searchString));
			builder.append(String.format("l.email like '%%%s%%' or ", searchString));
			builder.append(String.format("l.subDepartment.subDepartmentName like '%%%s%%' or ", searchString));
			builder.append(String.format("l.office.officeName like '%%%s%%' or ", searchString));
			builder.append(String.format("l.account.role.roleName like '%%%s%%' ", searchString));
		}
		
		builder.append("order by l.firstName ASC");

		Session session = sessionFactory.getCurrentSession();
		
		Query<?> query = session.createQuery(builder.toString());
		query.setFirstResult(Constants.MAX_RESULT * (page - 1));
		query.setMaxResults(Constants.MAX_RESULT);
		
		List<LecturerModel> lecturerList = (List<LecturerModel>) query.getResultList();
		
		return lecturerList;
	}
	
	@Override
	public void removeObjects(String queryString, Byte status) {
		
		Session session = sessionFactory.getCurrentSession();
		Query<?> query = session.createQuery(queryString);
		query.setParameter("status", status);
		query.executeUpdate();
	}
	
	public LecturerModel findByEmail(String email) {
		
		String queryString = "FROM LecturerModel l where l.email = :email";
		
		Session session = sessionFactory.getCurrentSession();
		
		Query<?> query = session.createQuery(queryString);
		query.setParameter("email", email);
		
		LecturerModel lecturer = (LecturerModel) query.uniqueResult();
		
		return lecturer;
	}
	
	public void addLecturers(String queryString) {
		
		Session session = sessionFactory.getCurrentSession();
		
		Query<?> query = session.createSQLQuery(queryString);
		
		query.executeUpdate();
	}
	
	public Long getTotalRecord(String searchString) {
		
		StringBuilder builder = new StringBuilder("SELECT COUNT(*) AS totalRecord FROM LecturerModel l");
		
		if (searchString != null) {
			builder.append(String.format(" where l.lecturerId like '%%%s%%' or ", searchString));
			builder.append(String.format("l.lecturerName like '%%%s%%' or ", searchString));
			builder.append(String.format("l.phoneNumber like '%%%s%%' or ", searchString));
			builder.append(String.format("l.email like '%%%s%%' or ", searchString));
			builder.append(String.format("l.subDepartment.subDepartmentName like '%%%s%%' or ", searchString));
			builder.append(String.format("l.office.officeName like '%%%s%%' or ", searchString));
			builder.append(String.format("l.account.role.roleName like '%%%s%%' ", searchString));
		}
		
		Session session = sessionFactory.getCurrentSession();
		
		Query<?> query = session.createQuery(builder.toString());
		
		return (Long) query.uniqueResult();
	}
	
	public LecturerModel findUser(String email, String password, Byte status) {
		
		StringBuilder builder = new StringBuilder();
		Session session = sessionFactory.getCurrentSession();
		
		builder.append("FROM LecturerModel l WHERE l.account.email = :email AND ");
		builder.append("l.account.password = :password AND l.deleted = :status");
		
		@SuppressWarnings("unchecked")
		Query<LecturerModel> query = session.createQuery(builder.toString());
		query.setParameter("email", email);
		query.setParameter("password", password);
		query.setParameter("status", status);
		
		LecturerModel lecturer = query.uniqueResult();
		
		return lecturer;
	}
}
