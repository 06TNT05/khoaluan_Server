/**
 * Time creation: Dec 3, 2022, 7:46:00 PM
 * 
 * Package name: com.exam.service
 */
package com.exam.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exam.common.Constants;
import com.exam.dao.OfficeDAO;
import com.exam.model.OfficeModel;

/**
 * @author Tien Tran
 *
 * class OfficeService
 */
@Service
public class OfficeService {
	
	@Autowired
	private OfficeDAO officeDAO;

	public OfficeDAO getOfficeDAO() {
		return officeDAO;
	}

	public void setOfficeDAO(OfficeDAO officeDAO) {
		this.officeDAO = officeDAO;
	}

	public OfficeModel findById(String id) {
		
		return officeDAO.findById(id);
	}

	public void addObject(OfficeModel office) {
		
		officeDAO.addObject(office);
	}

	public void updateObject(OfficeModel office) {
		
		officeDAO.updateObject(office);
	}

	public List<OfficeModel> findAllObjects() {
		
		return officeDAO.findAllObjects();
	}

	public boolean isObjectExist(OfficeModel office) {
		
		return findById(office.getOfficeId()) != null;
	}

	public void removeObjects(String[] array) {
		
		StringBuilder builder = new StringBuilder();
		builder.append("UPDATE OfficeModel o SET o.deleted = :status WHERE o.officeId IN (");
		
		for (String id : array) {
			builder.append("'" + id + "',");
		}
		
		builder.deleteCharAt(builder.toString().length() - 1);
		builder.append(Constants.SYMBOL_CLOSING_BRACKETS);

		officeDAO.removeObjects(builder.toString(), Constants.DELETED);
	}

	public void changeObjectStatus(OfficeModel office) {
		
		if (office.getDeleted() == Constants.DELETED) {
			office.setDeleted(Constants.NOT_DELETED);
		} else {
			office.setDeleted(Constants.DELETED);
		}
		
		officeDAO.updateObject(office);
	}

	public List<OfficeModel> findOfficeByStatus(Byte status) {
		
		return officeDAO.findOfficeByStatus(status);
	}
}
