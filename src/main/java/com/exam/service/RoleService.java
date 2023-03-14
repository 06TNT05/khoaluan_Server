/**
 * Time creation: Dec 4, 2022, 6:50:05 PM
 * 
 * Package name: com.exam.service
 */
package com.exam.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exam.dao.RoleDAO;
import com.exam.model.RoleModel;

/**
 * @author Tien Tran
 *
 * class RoleService
 */
@Service
public class RoleService {

	@Autowired
	private RoleDAO roleDAO;

	public RoleDAO getRoleDAO() {
		return roleDAO;
	}

	public void setRoleDAO(RoleDAO roleDAO) {
		this.roleDAO = roleDAO;
	}
	
	public List<RoleModel> findAllRoles() {
		
		return roleDAO.findAllRoles();
	}
	
//	public List<RoleModel> findRoleByStatus(Byte status) {
//		
//		return roleDAO.findRoleByStatus(status);
//	}
}