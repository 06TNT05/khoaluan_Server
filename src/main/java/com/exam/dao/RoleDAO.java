/**
 * Time creation: Dec 4, 2022, 6:50:15 PM
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

import com.exam.model.RoleModel;

/**
 * @author Tien Tran
 *
 * class RoleDAO
 */
@Repository(value = "roleDAO")
@Transactional(rollbackFor = Exception.class)
public class RoleDAO {
	
	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public List<RoleModel> findAllRoles() {
		
		String queryString = "FROM RoleModel r order by r.roleId ASC";
		
		Session session = sessionFactory.getCurrentSession();
		
		Query<?> query = session.createQuery(queryString);
		
		@SuppressWarnings("unchecked")
		List<RoleModel> roleList = (List<RoleModel>) query.getResultList();
		
		return roleList;
	}
	
//	@SuppressWarnings("unchecked")
//	public List<RoleModel> findRoleByStatus(Byte status) {
//		
//		StringBuilder builder = new StringBuilder();
//		builder.append("FROM RoleModel r WHERE r.deleted = :status ");
//		builder.append("order by r.roleName ASC");
//		
//		Session session = sessionFactory.getCurrentSession();
//		Query<?> query = session.createQuery(builder.toString());
//		query.setParameter("status", status);
//		List<RoleModel> roleList = (List<RoleModel>) query.getResultList();
//		
//		return roleList;
//	}
}
