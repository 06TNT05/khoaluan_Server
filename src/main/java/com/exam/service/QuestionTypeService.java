/**
 * Time creation: Feb 13, 2023, 2:42:41 PM
 *
 * Pakage name: com.exam.service
 */
package com.exam.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exam.dao.QuestionTypeDAO;
import com.exam.model.QuestionTypeModel;

/**
 * @author Tien Tran
 *
 * class QuestionTypeService
 */
@Service
public class QuestionTypeService {

	@Autowired
	private QuestionTypeDAO questionTypeDAO;

	public QuestionTypeDAO getQuestionTypeDAO() {
		return questionTypeDAO;
	}

	public void setQuestionTypeDAO(QuestionTypeDAO questionTypeDAO) {
		this.questionTypeDAO = questionTypeDAO;
	}
	
	public List<QuestionTypeModel> findQuestionTypesNotDeleted(Byte status) {
		
		return questionTypeDAO.findQuestionTypesNotDeleted(status);
	}
}
