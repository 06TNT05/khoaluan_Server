/**
 * Time creation: Dec 1, 2022, 10:40:32 PM
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
import com.exam.model.DepartmentModel;

/**
 * @author Tien Tran
 *
 * class DepartmentDAO
 */
@Repository(value = "departmentDAO")
@Transactional(rollbackFor = Exception.class)
public class DepartmentDAO implements ObjectDAO<DepartmentModel>{
	
	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public DepartmentModel findById(String id) {
		
		Session session = sessionFactory.getCurrentSession();
		
		return session.find(DepartmentModel.class, id);
	}

	@Override
	public void addObject(DepartmentModel object) {
		
		Session session = sessionFactory.getCurrentSession();
		
		session.save(object);
	}

	@Override
	public void updateObject(DepartmentModel object) {
		
		Session session = sessionFactory.getCurrentSession();
		
		session.update(object);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DepartmentModel> findAllObjects() {
		
		String queryString = "FROM DepartmentModel d order by d.departmentName ASC";
		
		Session session = sessionFactory.getCurrentSession();
		
		Query<DepartmentModel> query = session.createQuery(queryString);
		
		List<DepartmentModel> departmentList = (List<DepartmentModel>) query.getResultList();
		
		return departmentList;
	}

	@Override
	public void removeObjects(String queryString, Byte status) {
		
		Session session = sessionFactory.getCurrentSession();
		Query<?> query = session.createQuery(queryString);
		query.setParameter("status", status);
		query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	public List<DepartmentModel> findDepartmentByStatus(Byte status) {
		
		StringBuilder builder = new StringBuilder();
		builder.append("FROM DepartmentModel d WHERE d.deleted = :status ");
		builder.append("order by d.departmentName ASC");
		
		Session session = sessionFactory.getCurrentSession();
		Query<?> query = session.createQuery(builder.toString());
		query.setParameter("status", status);
		List<DepartmentModel> departmentList = (List<DepartmentModel>) query.getResultList();
		
		return departmentList;
	}
}
