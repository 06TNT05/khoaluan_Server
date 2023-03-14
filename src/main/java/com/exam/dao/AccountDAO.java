/**
 * Time creation: Dec 4, 2022, 3:33:25 PM
 * 
 * Package name: com.exam.dao
 */
package com.exam.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.exam.model.AccountModel;

/**
 * @author Tien Tran
 *
 * class AccountDAO
 */
@Repository(value = "accountDAO")
@Transactional(rollbackFor = Exception.class)
public class AccountDAO {
	
	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void addAccount(AccountModel account) {
		
		Session session = sessionFactory.getCurrentSession();
		
		session.save(account);
	}
	
	public void addAccounts(String queryString) {
		
		Session session = sessionFactory.getCurrentSession();
		
		Query<?> query = session.createSQLQuery(queryString);
		
		query.executeUpdate();
	}
	
	public AccountModel findByEmail(String email) {
		
		Session session = sessionFactory.getCurrentSession();
		
		return session.find(AccountModel.class, email);
	}
	
	public void updateAccount(AccountModel account) {
		
		Session session = sessionFactory.getCurrentSession();
		
		session.update(account);
	}
}
