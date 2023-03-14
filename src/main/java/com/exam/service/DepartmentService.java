/**
 * Time creation: Dec 1, 2022, 10:37:17 PM
 * 
 * Package name: com.exam.service
 */
package com.exam.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exam.common.Constants;
import com.exam.dao.DepartmentDAO;
import com.exam.model.DepartmentModel;

/**
 * @author Tien Tran
 *
 * class DepartmentService
 */
@Service
public class DepartmentService {

	@Autowired
	private DepartmentDAO departmentDAO;

	public DepartmentDAO getDepartmentDAO() {
		return departmentDAO;
	}

	public void setDepartmentDAO(DepartmentDAO departmentDAO) {
		this.departmentDAO = departmentDAO;
	}

	public DepartmentModel findById(String id) {
		
		return departmentDAO.findById(id);
	}

	public void addObject(DepartmentModel department) {
		
		departmentDAO.addObject(department);
	}

	public void updateObject(DepartmentModel department) {
		
		departmentDAO.updateObject(department);
	}

	public List<DepartmentModel> findAllObjects() {
		
		return departmentDAO.findAllObjects();
	}

	public boolean isObjectExist(DepartmentModel department) {
		
		return findById(department.getDepartmentId())!=null;
	}

	public void removeObjects(String[] array) {
		
		StringBuilder builder = new StringBuilder();
		builder.append("UPDATE DepartmentModel d SET d.deleted = :status WHERE d.departmentId IN (");
		
		for (String id : array) {
			builder.append("'" + id + "',");
		}
		
		builder.deleteCharAt(builder.toString().length() - 1);
		builder.append(Constants.SYMBOL_CLOSING_BRACKETS);

		departmentDAO.removeObjects(builder.toString(), Constants.DELETED);
	}

	public void changeObjectStatus(DepartmentModel department) {
		
		if (department.getDeleted() == Constants.DELETED) {
			department.setDeleted(Constants.NOT_DELETED);
		} else {
			department.setDeleted(Constants.DELETED);
		}
		
		departmentDAO.updateObject(department);
	}
	
	public List<DepartmentModel> findDepartmentByStatus(Byte status) {
		
		return departmentDAO.findDepartmentByStatus(status);
	}
}
