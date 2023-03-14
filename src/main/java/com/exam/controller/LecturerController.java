/**
 * Time creation: Dec 3, 2022, 9:13:39 PM
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

import com.exam.bean.AccountBean;
import com.exam.bean.LecturerBean;
import com.exam.common.Constants;
import com.exam.interfaces.RoleAuthorized;
import com.exam.model.LecturerModel;
import com.exam.model.TeachingModel;
import com.exam.service.AccountService;
import com.exam.service.LecturerService;
import com.exam.service.TeachingService;

/**
 * @author Tien Tran
 *
 * class LecturerrController
 */
@CrossOrigin(maxAge = 3600)
@RestController
public class LecturerController {
	
	@Autowired
    private ServletContext context;
	@Autowired
	private LecturerService lecturerService;
	@Autowired
	private TeachingService teachingService;
	@Autowired
	private AccountService accountService;

	public void setContext(ServletContext context) {
		this.context = context;
	}

	@RoleAuthorized({"Admin"})
	@RequestMapping(value = "/api/lecturer/find.all", method = RequestMethod.GET)
	public ResponseEntity<List<LecturerModel>> findAllLecturers() {
		
		List<LecturerModel> lecturerList = lecturerService.findAllObjects();
		
		if (lecturerList == null) {
			return new ResponseEntity<List<LecturerModel>>(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<List<LecturerModel>>(lecturerList, HttpStatus.OK);
	}
	
	@RoleAuthorized({"Admin"})
	@RequestMapping(value = "/api/lecturer/find.all.pagination", method = RequestMethod.GET)
	public ResponseEntity<List<LecturerModel>> findAllLecturersPagination(
			@RequestParam("page") Integer page, @RequestParam("searchString") String searchString) {
		
		List<LecturerModel> lecturerList = lecturerService.findAllObjectsPagination(page, searchString);
		
		if (lecturerList == null) {
			return new ResponseEntity<List<LecturerModel>>(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<List<LecturerModel>>(lecturerList, HttpStatus.OK);
	}
	
	@RoleAuthorized({"Admin"})
	@RequestMapping(value = "/api/lecturer/find.id", method = RequestMethod.GET)
	public ResponseEntity<LecturerModel> findById(@RequestParam("lecturerId") String lecturerId) {
		
		LecturerModel lecturer = lecturerService.findById(lecturerId);
		
		if (lecturer == null) {
			return new ResponseEntity<LecturerModel>(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<LecturerModel>(lecturer, HttpStatus.OK);
	}
	
	@RoleAuthorized({"Admin"})
	@RequestMapping(value = "/api/lecturer/add", method = RequestMethod.POST)
	public ResponseEntity<Void> addLecturer(@ModelAttribute("LecturerBean") LecturerBean lecturerBean) throws IOException {

		// get LecturerModel from LecturerBean
		LecturerModel lecturerModel = lecturerService.convertBeantoModel(lecturerBean);
		
		// check existing of LecturerModel
		if (lecturerService.isObjectExist(lecturerModel)) {
			
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}
		
		lecturerService.addLecturer(lecturerBean, lecturerModel, context);
		
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}
	
	@RoleAuthorized({"Admin"})
	@RequestMapping(value = "/api/lecturer/update", method = RequestMethod.POST)
	public ResponseEntity<Void> updateLecturer(@ModelAttribute("LecturerBean") LecturerBean lecturerBean) throws IOException {
		
		lecturerService.updateLecturer(lecturerBean, context);
		
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@RoleAuthorized({"Admin"})
	@RequestMapping(value = "/api/lecturer/remove", method = RequestMethod.POST)
	public ResponseEntity<Void> removeLecturer(@RequestParam("lecturerIdArray") String[] lecturerIdArray) {
		
		lecturerService.removeObjects(lecturerIdArray);
		
        return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@RoleAuthorized({"Admin"})
	@RequestMapping(value = "/api/lecturer/upload", method = RequestMethod.POST)
	public ResponseEntity<Void> uploadFile(@RequestParam("file") CommonsMultipartFile file) throws IOException {
		
		//fileService.uploadFile(file, Constants.FILES_DIRECTORY, context);
		
		if (file.getSize() > Constants.MAX_SIZE_IMAGE) {
			return new ResponseEntity<Void>(HttpStatus.PAYLOAD_TOO_LARGE);
		}
		
		lecturerService.readDataExcel(file, context);
		
        return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@RoleAuthorized({"Admin"})
	@RequestMapping(value = "/api/lecturer/changeStatus", method = RequestMethod.POST)
	public ResponseEntity<Void> changeLecturerStatus(@RequestParam("lecturerId") String lecturerId) {
		
		LecturerModel lecturer = lecturerService.findById(lecturerId);
		
		lecturerService.changeObjectStatus(lecturer);
		
        return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@RoleAuthorized({"Admin"})
	@RequestMapping(value = "/api/lecturer/getTotalRecord", method = RequestMethod.GET)
	public ResponseEntity<Long> getTotalRecord(@RequestParam("searchString") String searchString) {
		
		Long totalRecord = lecturerService.getTotalRecord(searchString);
		
        return new ResponseEntity<Long>(totalRecord, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/api/lecturer/getTeachingList", method = RequestMethod.GET)
	public ResponseEntity<List<TeachingModel>> getTeachingList(@RequestParam("lecturerId") String lecturerId) {
		
		List<TeachingModel> teachingList =
				teachingService.getTeachingListByLecturerId(lecturerId);
		
        return new ResponseEntity<List<TeachingModel>>(teachingList, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/api/lecturer/changePassword", method = RequestMethod.POST)
	public ResponseEntity<Void> changePassword(@ModelAttribute AccountBean account) {
		
		accountService.changePassword(account);
		
        return new ResponseEntity<Void>(HttpStatus.OK);
	}
}
