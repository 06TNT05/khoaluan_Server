/**
 * Time creation: Dec 3, 2022, 3:51:10 PM
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

import com.exam.interfaces.ObjectDAO;
import com.exam.model.SubDepartmentModel;

/**
 * @author Tien Tran
 *
 * class SubDepartmentDAO
 */
@Repository(value = "subDepartmentDAO")
@Transactional(rollbackFor = Exception.class)
public class SubDepartmentDAO implements ObjectDAO<SubDepartmentModel>{
	
	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public SubDepartmentModel findById(String id) {
		
		Session session = sessionFactory.getCurrentSession();
		
		return session.find(SubDepartmentModel.class, id);
	}

	@Override
	public void addObject(SubDepartmentModel object) {
		
		Session session = sessionFactory.getCurrentSession();
		
		session.save(object);
	}

	@Override
	public void updateObject(SubDepartmentModel object) {
		
		Session session = sessionFactory.getCurrentSession();
		
		session.update(object);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SubDepartmentModel> findAllObjects() {
		String queryString = "FROM SubDepartmentModel s order by s.subDepartmentName ASC";
		
		Session session = sessionFactory.getCurrentSession();
		
		Query<SubDepartmentModel> query = session.createQuery(queryString);
		
		List<SubDepartmentModel> subDepartmentList = (List<SubDepartmentModel>) query.getResultList();
		
		return subDepartmentList;
	}

	@Override
	public void removeObjects(String queryString, Byte status) {
		
		Session session = sessionFactory.getCurrentSession();
		Query<?> query = session.createQuery(queryString);
		query.setParameter("status", status);
		query.executeUpdate();
	}
	
	public List<SubDepartmentModel> findSubDepartmentByStatus(Byte status) {
		
		StringBuilder builder = new StringBuilder();
		builder.append("FROM SubDepartmentModel s WHERE s.deleted = :status ");
		builder.append("order by s.subDepartmentName ASC");
		
		Session session = sessionFactory.getCurrentSession();
		Query<?> query = session.createQuery(builder.toString());
		query.setParameter("status", status);
		@SuppressWarnings("unchecked")
		List<SubDepartmentModel> subDepartmentList = (List<SubDepartmentModel>) query.getResultList();
		
		return subDepartmentList;
	}
}
