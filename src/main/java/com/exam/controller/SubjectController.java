/**
 * Time creation: Dec 4, 2022, 10:33:19 PM
 * 
 * Package name: com.exam.controller
 */
package com.exam.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;

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

import com.exam.common.Constants;
import com.exam.model.SubjectModel;
import com.exam.service.SubjectService;

/**
 * @author Tien Tran
 *
 * class SubjectController
 */
@CrossOrigin(maxAge = 3600)
@RestController
public class SubjectController {
	
	@Autowired
    private ServletContext context;
	@Autowired
	private SubjectService subjectService;
	
	@RequestMapping(value = "/api/subject/find.all", method = RequestMethod.GET)
	public ResponseEntity<List<SubjectModel>> findAllSubjects() {
		
		List<SubjectModel> subjectList = subjectService.findAllObjects();
		
		if (subjectList == null) {
			return new ResponseEntity<List<SubjectModel>>(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<List<SubjectModel>>(subjectList, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/api/subject/find.all.pagination", method = RequestMethod.GET)
	public ResponseEntity<List<SubjectModel>> findAllLecturersPagination(
			@RequestParam("page") Integer page, @RequestParam("searchString") String searchString) {
		
		List<SubjectModel> subjectList = 
				subjectService.findAllObjectsPagination(page, searchString);
		
		if (subjectList == null) {
			return new ResponseEntity<List<SubjectModel>>(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<List<SubjectModel>>(subjectList, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/api/subject/find.id", method = RequestMethod.GET)
	public ResponseEntity<SubjectModel> findById(@RequestParam("subjectId") String subjectId) {
		
		SubjectModel subject = subjectService.findById(subjectId);
		
		if (subject == null) {
			return new ResponseEntity<SubjectModel>(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<SubjectModel>(subject, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/api/subject/add", method = RequestMethod.POST)
	public ResponseEntity<Void> addSubject(@ModelAttribute("SubjectModel") SubjectModel subject) {
		
		if (subjectService.isObjectExist(subject)) {
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}
		
		subjectService.addObject(subject);
		
        return new ResponseEntity<Void>(HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/api/subject/update", method = RequestMethod.POST)
	public ResponseEntity<Void> updateSubject(@ModelAttribute("SubjectModel") SubjectModel subject) {
		
		SubjectModel subjectbyID = subjectService.findById(subject.getSubjectId());
		
		if (subjectbyID == null) {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
		
		subjectService.updateObject(subject);
		
        return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@RequestMapping(value = "/api/subject/remove", method = RequestMethod.POST)
	public ResponseEntity<Void> removeSubject(@RequestParam("subjectIdArray") String[] subjectIdArray) {
		
		subjectService.removeObjects(subjectIdArray);
		
        return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@RequestMapping(value = "/api/subject/find.subdepartmentid", method = RequestMethod.GET)
	public ResponseEntity<List<SubjectModel>> findBySubDepartmentId(
			@RequestParam("subDepartmentId") String subDepartmentId) {
		
		List<SubjectModel> subjectList = subjectService.findBySubDepartmentId(subDepartmentId);
		
		return new ResponseEntity<List<SubjectModel>>(subjectList, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/api/subject/find.not.deleted", method = RequestMethod.GET)
	public ResponseEntity<List<SubjectModel>> findSubjectNotDeleted() {
		
		List<SubjectModel> subjectList = 
				subjectService.findSubjectByStatus(Constants.NOT_DELETED);
		
		if (subjectList == null) {
			return new ResponseEntity<List<SubjectModel>>(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<List<SubjectModel>>(subjectList, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/api/subject/upload", method = RequestMethod.POST)
	public ResponseEntity<Void> uploadFile(@RequestParam("file") CommonsMultipartFile file) throws IOException {
		
		//fileService.uploadFile(file, Constants.FILES_DIRECTORY, context);
		
		if (file.getSize() > Constants.MAX_SIZE_IMAGE) {
			return new ResponseEntity<Void>(HttpStatus.PAYLOAD_TOO_LARGE);
		}
		
		subjectService.readDataExcel(file, context);
		
        return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@RequestMapping(value = "/api/subject/changeStatus", method = RequestMethod.POST)
	public ResponseEntity<Void> changeSubjectStatus(@RequestParam("subjectId") String subjectId) {
		
		SubjectModel subject = subjectService.findById(subjectId);
		
		subjectService.changeObjectStatus(subject);
		
        return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@RequestMapping(value = "/api/subject/getTotalRecord", method = RequestMethod.GET)
	public ResponseEntity<Long> getTotalRecord(@RequestParam("searchString") String searchString) {
		
		Long totalRecord = subjectService.getTotalRecord(searchString);
		
        return new ResponseEntity<Long>(totalRecord, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/api/subject/find.lecturerId", method = RequestMethod.GET)
	public ResponseEntity<List<SubjectModel>> findByLecturerId(@RequestParam("lecturerId") String lecturerId) {
		
		List<SubjectModel> subjectList = subjectService.findByLecturerId(lecturerId);
		
        return new ResponseEntity<List<SubjectModel>>(subjectList, HttpStatus.OK);
	}
}
