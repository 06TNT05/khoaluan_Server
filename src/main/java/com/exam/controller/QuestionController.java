/**
 * Time creation: Feb 14, 2023, 12:45:57 PM
 *
 * Pakage name: com.exam.controller
 */
package com.exam.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.exam.bean.QuestionBean;
import com.exam.common.Constants;
import com.exam.common.FileCommon;
import com.exam.model.QuestionModel;
import com.exam.service.QuestionService;

/**
 * @author Tien Tran
 *
 * class QuestionController
 */
@CrossOrigin(maxAge = 3600)
@RestController
public class QuestionController {
	
	@Autowired
	private ServletContext context;
	@Autowired
    private HttpServletRequest request;
	@Autowired
	private QuestionService questionService;
	@Autowired
	private FileCommon fileCommon;

	@RequestMapping(value = "/api/question/add", method = RequestMethod.POST)
	public ResponseEntity<Void> addQuestion(@ModelAttribute("QuestionBean") QuestionBean questionBean) {
		
		questionService.addQuestion(questionBean, context);
		
        return new ResponseEntity<Void>(HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/api/question/find.all", method = RequestMethod.GET)
	public ResponseEntity<List<QuestionModel>> findAllQuestions() {
		
		List<QuestionModel> questionList = questionService.findAllQuestions();
		
        return new ResponseEntity<List<QuestionModel>>(questionList, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/api/question/find.all.pagination", method = RequestMethod.GET)
	public ResponseEntity<List<QuestionModel>> findAllQuestionsPagination(
			@ModelAttribute("QuestionBean") QuestionBean questionBean) {
		
		List<QuestionModel> questionList = questionService.findAllQuestionsPagination(questionBean);
		
		if (questionList == null) {
			return new ResponseEntity<List<QuestionModel>>(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<List<QuestionModel>>(questionList, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/api/question/find.id", method = RequestMethod.GET)
	public ResponseEntity<QuestionModel> findById(@RequestParam("questionId") String questionId) {
		
		QuestionModel questionModel = questionService.findById(questionId);
		
		if (questionModel == null) {
			
			return new ResponseEntity<QuestionModel>(HttpStatus.NO_CONTENT);
		}
		
        return new ResponseEntity<QuestionModel>(questionModel, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/api/question/update", method = RequestMethod.POST)
	public ResponseEntity<Void> updateQuestion(@ModelAttribute("QuestionBean") QuestionBean questionBean) {
		
		questionService.updateQuestion(questionBean, context);
		
        return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@RequestMapping(value = "/api/question/remove", method = RequestMethod.POST)
	public ResponseEntity<Void> removeOffice(@RequestParam("questionIdArray") String[] questionIdArray) {
		
		questionService.removeQuestions(questionIdArray);
		
        return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@RequestMapping(value = "/api/question/uploadImage", method = RequestMethod.POST)
	public ResponseEntity<String> uploadImage(@RequestParam("fileImage") CommonsMultipartFile fileImage) {
		
		String pathToUpload = 
				fileCommon.getPathToUpload(fileImage.getOriginalFilename(), Constants.FILE_ATTACH_QUESTION_DIRECTORY, context);
		
		String pathToDataBase = fileCommon.getPathToDatabase(Constants.FILE_ATTACH_QUESTION_DIRECTORY, fileImage);
		
		String pathToShowImage = questionService.getPathToShowImage(request, context, pathToDataBase);
		
		try {
			fileCommon.uploadFile(fileImage.getBytes(), pathToUpload);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
        return new ResponseEntity<String>(pathToShowImage, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/api/question/changeStatus", method = RequestMethod.POST)
	public ResponseEntity<Void> changeStatus(@RequestParam("questionId") String questionId) {
		
		questionService.changeQuestionStatus(questionId);
		
        return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@RequestMapping(value = "/api/question/getTotalRecord", method = RequestMethod.GET)
	public ResponseEntity<Long> getTotalRecord(@ModelAttribute("QuestionBean") QuestionBean questionBean) {
		
		Long totalRecord = questionService.getTotalRecord(questionBean);
		
        return new ResponseEntity<Long>(totalRecord, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/api/question/uploadFile", method = RequestMethod.POST)
	public ResponseEntity<Void> uploadFile(@ModelAttribute("QuestionBean") QuestionBean questionBean) {
		
		try {
			questionService.uploadQuestionFile(questionBean, request, context);
		} catch (InvalidFormatException | IOException e) {

			e.printStackTrace();
		}
		
        return new ResponseEntity<Void>(HttpStatus.OK);
	}
}
