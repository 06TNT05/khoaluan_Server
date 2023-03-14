/**
 * Time creation: Dec 4, 2022, 11:44:25 PM
 * 
 * Package name: com.exam.service
 */
package com.exam.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exam.common.Constants;
import com.exam.dao.TopicDAO;
import com.exam.model.TopicModel;

/**
 * @author Tien Tran
 *
 * class TopicService
 */
@Service
public class TopicService {

	@Autowired
	private TopicDAO topicDAO;

	public TopicDAO getTopicDAO() {
		return topicDAO;
	}

	public void setTopicDAO(TopicDAO topicDAO) {
		this.topicDAO = topicDAO;
	}

	public TopicModel findById(String id) {
		
		return topicDAO.findById(id);
	}

	public void addObject(TopicModel topic) {
		
		topicDAO.addObject(topic);
	}

	public void updateObject(TopicModel topic) {
		
		topicDAO.updateObject(topic);
	}

	public List<TopicModel> findAllObjects() {
		
		return topicDAO.findAllObjects();
	}
	
	public boolean isObjectExist(TopicModel topic) {
		
		return findById(topic.getTopicId()) != null;
	}

	public void removeObjects(String[] array) {
		
		StringBuilder builder = new StringBuilder();
		builder.append("UPDATE TopicModel t SET t.deleted = :status WHERE t.topicId IN (");
		
		for (String id : array) {
			builder.append("'" + id + "',");
		}
		
		builder.deleteCharAt(builder.toString().length() - 1);
		builder.append(Constants.SYMBOL_CLOSING_BRACKETS);

		topicDAO.removeObjects(builder.toString(), Constants.DELETED);
	}

	public void changeObjectStatus(TopicModel topic) {
		
		if (topic.getDeleted() == Constants.DELETED) {
			topic.setDeleted(Constants.NOT_DELETED);
		} else {
			topic.setDeleted(Constants.DELETED);
		}
		
		topicDAO.updateObject(topic);
	}

	public List<TopicModel> findBySubjectId(String subjectId) {
		
		return topicDAO.findBySubjectId(subjectId, Constants.NOT_DELETED);
	}
	
	public List<TopicModel> findBySubDepartmentId(String subDepartmentId) {
		
		return topicDAO.findBySubDepartmentId(subDepartmentId, Constants.NOT_DELETED);
	}
}
