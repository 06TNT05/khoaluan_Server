/**
 * Time creation: Dec 4, 2022, 3:32:39 PM
 * 
 * Package name: com.exam.service
 */
package com.exam.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exam.bean.AccountBean;
import com.exam.bean.LecturerBean;
import com.exam.common.Constants;
import com.exam.dao.AccountDAO;
import com.exam.model.AccountModel;

/**
 * @author Tien Tran
 *
 * class AccountService
 */
@Service
public class AccountService {
	
	@Autowired
	private AccountDAO accountDAO;
	
	public AccountDAO getAccountDAO() {
		return accountDAO;
	}

	public void setAccountDAO(AccountDAO accountDAO) {
		this.accountDAO = accountDAO;
	}

	public void addAccount(AccountModel account) {
		
		accountDAO.addAccount(account);
	}
	
	public void addAccounts(String queryString) {
		
		accountDAO.addAccounts(queryString);
	}
	
	public AccountModel findByEmail(String email) {
		
		return accountDAO.findByEmail(email);
	}
	
	public AccountModel getAccountFromBean(LecturerBean lecturer) {
		
		String email = lecturer.getEmail();
		
		// set pass = first part of Email (before @)
		String password = handlePassword(email);
		Integer roleId = lecturer.getRoleId();
		
		AccountModel account = new AccountModel();
		
		account.setEmail(email);
		account.setPassword(password);
		account.setRoleId(roleId);
		
		return account;
	}
	
	String handlePassword(String pass) {
		return pass.toLowerCase().split(Constants.SYMBOL_HASH)[0];
	}
	
	public void changePassword(AccountBean accountBean) {
		
		AccountModel accountModel = findByEmail(accountBean.getEmail());
		
		accountModel.setPassword(accountBean.getNewPassword());
		
		accountDAO.updateAccount(accountModel);
	}
	
	public void updateAccount(AccountModel account) {
		
		accountDAO.updateAccount(account);
	}
}
