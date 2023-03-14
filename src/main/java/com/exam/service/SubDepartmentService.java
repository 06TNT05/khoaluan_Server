/**
 * Time creation: Dec 3, 2022, 3:52:01 PM
 * 
 * Package name: com.exam.service
 */
package com.exam.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exam.common.Constants;
import com.exam.dao.SubDepartmentDAO;
import com.exam.model.SubDepartmentModel;

/**
 * @author Tien Tran
 *
 * class SubDepartmentService
 */
@Service
public class SubDepartmentService {
	
	@Autowired
	private SubDepartmentDAO subDepartmentDAO;
	
	public SubDepartmentDAO getSubDepartmentDAO() {
		return subDepartmentDAO;
	}

	public void setSubDepartmentDAO(SubDepartmentDAO subDepartmentDAO) {
		this.subDepartmentDAO = subDepartmentDAO;
	}

	public SubDepartmentModel findById(String id) {
		
		return subDepartmentDAO.findById(id);
	}
	
	public void addObject(SubDepartmentModel subDepartment) {
		
		subDepartmentDAO.addObject(subDepartment);
	}

	public void updateObject(SubDepartmentModel subDepartment) {
		
		subDepartmentDAO.updateObject(subDepartment);
	}

	public List<SubDepartmentModel> findAllObjects() {
		
		return subDepartmentDAO.findAllObjects();
	}

	public boolean isObjectExist(SubDepartmentModel subDepartment) {
		
		return findById(subDepartment.getSubDepartmentId()) != null;
	}

	public void removeObjects(String[] array) {
		
		StringBuilder builder = new StringBuilder();
		builder.append("UPDATE SubDepartmentModel s SET s.deleted = :status WHERE s.subDepartmentId IN (");
		
		for (String id : array) {
			builder.append("'" + id + "',");
		}
		
		builder.deleteCharAt(builder.toString().length() - 1);
		builder.append(Constants.SYMBOL_CLOSING_BRACKETS);

		subDepartmentDAO.removeObjects(builder.toString(), Constants.DELETED);
	}

	public void changeObjectStatus(SubDepartmentModel subDepartment) {
		
		if (subDepartment.getDeleted() == Constants.DELETED) {
			subDepartment.setDeleted(Constants.NOT_DELETED);
		} else {
			subDepartment.setDeleted(Constants.DELETED);
		}
		
		subDepartmentDAO.updateObject(subDepartment);
	}

	public List<SubDepartmentModel> findSubDepartmentByStatus(Byte status) {
		
		return subDepartmentDAO.findSubDepartmentByStatus(status);
	}
}
