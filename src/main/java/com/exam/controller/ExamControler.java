/**
 * Time creation: Feb 25, 2023, 8:44:04 PM
 *
 * Pakage name: com.exam.controller
 */
package com.exam.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.exam.bean.ExamBean;
import com.exam.model.QuestionModel;
import com.exam.service.ExamService;

/**
 * @author Tien Tran
 *
 * class ExamControler
 */
@CrossOrigin(maxAge = 3600)
@RestController
public class ExamControler {

	@Autowired
	private ExamService examService;
	
	@RequestMapping(value = "/api/exam/random-creation", method = RequestMethod.GET)
	public ResponseEntity<List<QuestionModel>> randomCreationExam(@ModelAttribute ExamBean examBean) {
		
		List<QuestionModel> questionList = examService.randomCreationExam(examBean);
		
		return new ResponseEntity<List<QuestionModel>>(questionList, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/api/exam/add", method = RequestMethod.POST)
	public ResponseEntity<Void> addExam(@ModelAttribute ExamBean examBean) {
		
		examService.addExam(examBean);
		
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@RequestMapping(value = "/api/exam/export", method = RequestMethod.POST)
	public ResponseEntity<Resource> exportPDFFile(@RequestParam String html) {
		
		byte[] bytes = null;
		
		bytes = examService.convertHTMLToPDF(html);

		ByteArrayResource resource = new ByteArrayResource(bytes);
		
		// Set the response headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "exam.pdf");
        headers.setContentLength(resource.contentLength());

        // Return the PDF file as a byte array
        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
	}
}
