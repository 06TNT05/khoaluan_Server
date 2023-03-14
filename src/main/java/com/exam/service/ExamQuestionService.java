/**
 * Time creation: Feb 27, 2023, 12:30:01 PM
 *
 * Pakage name: com.exam.service
 */
package com.exam.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exam.dao.ExamQuestionDAO;
import com.exam.model.ExamQuestionModel;

/**
 * @author Tien Tran
 *
 * class ExamQuestionService
 */
@Service
public class ExamQuestionService {

	@Autowired
	private ExamQuestionDAO examQuestionDAO;

	public void setExamQuestionDAO(ExamQuestionDAO examQuestionDAO) {
		this.examQuestionDAO = examQuestionDAO;
	}
	
	public void addExamQuestionWithBatch(List<ExamQuestionModel> examQuestionList) {
		
		examQuestionDAO.addExamQuestionWithBatch(examQuestionList);
	}
}
