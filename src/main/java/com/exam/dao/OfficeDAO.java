/**
 * Time creation: Dec 3, 2022, 7:51:06 PM
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
import com.exam.model.OfficeModel;

/**
 * @author Tien Tran
 *
 * class OfficeDAO
 */
@Repository(value = "officeDAO")
@Transactional(rollbackFor = Exception.class)
public class OfficeDAO implements ObjectDAO<OfficeModel> {
	
	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public OfficeModel findById(String id) {
		
		Session session = sessionFactory.getCurrentSession();
		
		return session.find(OfficeModel.class, id);
	}

	@Override
	public void addObject(OfficeModel object) {
		
		Session session = sessionFactory.getCurrentSession();
		
		session.save(object);
	}

	@Override
	public void updateObject(OfficeModel object) {
		
		Session session = sessionFactory.getCurrentSession();
		
		session.update(object);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OfficeModel> findAllObjects() {
		
		String queryString = "FROM OfficeModel o order by o.officeName ASC";
		
		Session session = sessionFactory.getCurrentSession();
		
		Query<?> query = session.createQuery(queryString);
		
		List<OfficeModel> officeList = (List<OfficeModel>) query.getResultList();
		
		return officeList;
	}

	@Override
	public void removeObjects(String queryString, Byte status) {
		
		Session session = sessionFactory.getCurrentSession();
		Query<?> query = session.createQuery(queryString);
		query.setParameter("status", status);
		query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	public List<OfficeModel> findOfficeByStatus(Byte status) {
		
		StringBuilder builder = new StringBuilder();
		builder.append("FROM OfficeModel o WHERE o.deleted = :status ");
		builder.append("order by o.officeName ASC");
		
		Session session = sessionFactory.getCurrentSession();
		Query<?> query = session.createQuery(builder.toString());
		query.setParameter("status", status);
		List<OfficeModel> officeList = (List<OfficeModel>) query.getResultList();
		
		return officeList;
	}
}
