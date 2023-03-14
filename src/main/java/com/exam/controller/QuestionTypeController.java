/**
 * Time creation: Feb 13, 2023, 2:47:18 PM
 *
 * Pakage name: com.exam.controller
 */
package com.exam.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.exam.common.Constants;
import com.exam.model.QuestionTypeModel;
import com.exam.service.QuestionTypeService;

/**
 * @author Tien Tran
 *
 * class QuestionTypeController
 */
@CrossOrigin(maxAge = 3600)
@RestController
public class QuestionTypeController {

	@Autowired
	private QuestionTypeService questionTypeService;
	
	@RequestMapping(value = "/api/question-type/find.not.deleted", method = RequestMethod.GET)
	public ResponseEntity<List<QuestionTypeModel>> findQuestionTypesNotDeleted() {
		
		List<QuestionTypeModel> questionTypeList = 
				questionTypeService.findQuestionTypesNotDeleted(Constants.NOT_DELETED);
		
		if (questionTypeList == null) {
			return new ResponseEntity<List<QuestionTypeModel>>(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<List<QuestionTypeModel>>(questionTypeList, HttpStatus.OK);
	}
}
