/**
 * Time creation: Dec 4, 2022, 4:40:15 PM
 * 
 * Package name: com.exam.service
 */
package com.exam.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exam.common.Constants;
import com.exam.dao.TeachingDAO;
import com.exam.model.TeachingModel;

/**
 * @author Tien Tran
 *
 * class TeachingService
 */
@Service
public class TeachingService {
	
	@Autowired
	private TeachingDAO teachingDAO;

	public TeachingDAO getTeachingDAO() {
		return teachingDAO;
	}

	public void setTeachingDAO(TeachingDAO teachingDAO) {
		this.teachingDAO = teachingDAO;
	}
	
	public void addTeaching(TeachingModel teachingModel) {
		
		teachingDAO.addTeaching(teachingModel); 
	}
	
	public void addTeachings(String queryString) {
		
		teachingDAO.addTeachings(queryString); 
	}
	
	public void updateTeachings(String lecturerId, Byte status, String queryString) {
		
		teachingDAO.updateTeachings(lecturerId, status, queryString); 
	}
	
	public List<TeachingModel> getTeachingListByLecturerId(String lecturerId) {
		
		return teachingDAO.getTeachingListByLecturerId(lecturerId, Constants.NOT_DELETED);
	}
	
	public List<String> getSubjectIdList(String lecturerId) {
		
		return teachingDAO.getSubjectIdList(lecturerId);
	}
	
	public String buildQueryAddTeaching(String lecturerId, List<String> subjectIdArray) {
		StringBuilder builderTeaching = new StringBuilder("insert into GIANG_DAY values ");
		
		for (String subjectId : subjectIdArray) {
			builderTeaching.append(String.format("('%s', '%s', %d),", lecturerId, subjectId, Constants.NOT_DELETED));
		}
		
		builderTeaching = builderTeaching.deleteCharAt(builderTeaching.toString().length() - 1);
		
		return builderTeaching.toString();
	}
	
	public String buildQueryUpdateTeaching(String lecturerId, List<String> subjectIdArray) {
		
		StringBuilder builderTeaching = new StringBuilder("update GIANG_DAY set DA_XOA = :status ");
		builderTeaching.append("where MA_GV = :lecturerId and MA_HOC_PHAN in(");
		
		for (String subjectId : subjectIdArray) {
			builderTeaching.append(String.format("'%s', ", subjectId));
		}
		
		// delete 2 char ", " at the end of builder
		String queryString = builderTeaching.substring(0, builderTeaching.toString().length() - 2);
		
		// add char ")" to queryString
		queryString += Constants.SYMBOL_CLOSING_BRACKETS;
		
		return queryString;
	}
	
	/**
	 * method get list subjectId to add Teaching to Teaching table
	 * 
	 * @param subjectIdArray contains subjectId which client send to server 
	 * @param subjectIdTeaching contains subjectId which lecturer was teaching
	 * @return
	 */
	public List<String> getSubjectIdAdd(String[] subjectIdArrayClient, List<String> subjectIdTeaching) {
		
		// list subjectIdAdd contains some subjectId for add Teaching to Teaching table
		List<String> subjectIdAdd = new ArrayList<String>();
		
		// if subjectIdTeaching don't contains subjectId in subjectIdArray,
		// this subjectId is used to add 
		for (String subjectId : subjectIdArrayClient) {
			
			if (!subjectIdTeaching.contains(subjectId)) {
				
				subjectIdAdd.add(subjectId);
			}
		}
		
		return subjectIdAdd;
	}
	
	public List<String> getSubjectIdUpdateDeleted(String[] subjectIdArrayClient, List<String> subjectIdTeaching) {
		
		// list subjectIdUpdate contains some subjectId for update Teaching to Teaching table
		List<String> subjectIdUpdateDeleted = new ArrayList<String>();
		
		for (String subjectId : subjectIdTeaching) {
			
			if (!Arrays.asList(subjectIdArrayClient).contains(subjectId)) {
				
				subjectIdUpdateDeleted.add(subjectId);
			}
		}
		
		return subjectIdUpdateDeleted;
	}
	
	public List<String> getSubjectIdUpdateNoDeleted(String[] subjectIdArrayClient, List<String> subjectIdTeaching) {
		
		// list subjectIdUpdate contains some subjectId for update Teaching to Teaching table
		List<String> subjectIdUpdateNoDeleted = new ArrayList<String>();
		
		for (String subjectId : subjectIdTeaching) {
			
			if (Arrays.asList(subjectIdArrayClient).contains(subjectId)) {
				
				subjectIdUpdateNoDeleted.add(subjectId);
			}
		}
		
		return subjectIdUpdateNoDeleted;
	}
}
