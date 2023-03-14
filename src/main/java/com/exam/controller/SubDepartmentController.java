/**
 * Time creation: Dec 3, 2022, 3:50:06 PM
 * 
 * Package name: com.exam.controller
 */
package com.exam.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.exam.common.Constants;
import com.exam.model.SubDepartmentModel;
import com.exam.service.SubDepartmentService;

/**
 * @author Tien Tran
 *
 * class SubDepartmentController
 */
@CrossOrigin(maxAge = 3600)
@RestController
public class SubDepartmentController {
	
	@Autowired
	private SubDepartmentService subDepartmentService;
	
	@RequestMapping(value = "/api/sub-department/find.all", method = RequestMethod.GET)
	public ResponseEntity<List<SubDepartmentModel>> findAllSubDepartments() {
		
		List<SubDepartmentModel> subDepartmentList = subDepartmentService.findAllObjects();
		
		if (subDepartmentList == null) {
			return new ResponseEntity<List<SubDepartmentModel>>(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<List<SubDepartmentModel>>(subDepartmentList, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/api/sub-department/find.id", method = RequestMethod.GET)
	public ResponseEntity<SubDepartmentModel> findById(@RequestParam("subDepartmentId") String subDepartmentId) {
		
		SubDepartmentModel department = subDepartmentService.findById(subDepartmentId);
		
		if (department == null) {
			return new ResponseEntity<SubDepartmentModel>(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<SubDepartmentModel>(department, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/api/sub-department/find.not.deleted", method = RequestMethod.GET)
	public ResponseEntity<List<SubDepartmentModel>> findSubDepartmentNotDeleted() {
		
		List<SubDepartmentModel> subDepartmentList = 
				subDepartmentService.findSubDepartmentByStatus(Constants.NOT_DELETED);
		
		if (subDepartmentList == null) {
			return new ResponseEntity<List<SubDepartmentModel>>(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<List<SubDepartmentModel>>(subDepartmentList, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/api/sub-department/add", method = RequestMethod.POST)
	public ResponseEntity<Void> addDepartment(@ModelAttribute("SubDepartmentModel") SubDepartmentModel subDepartment) {
		
		if (subDepartmentService.isObjectExist(subDepartment)) {
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}
		
		subDepartmentService.addObject(subDepartment);
		
        return new ResponseEntity<Void>(HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/api/sub-department/update", method = RequestMethod.POST)
	public ResponseEntity<Void> updateSubDepartment(@ModelAttribute("SubDepartmentModel") SubDepartmentModel subDepartment) {
		
		SubDepartmentModel subDepartmentbyID = subDepartmentService.findById(subDepartment.getSubDepartmentId());
		
		if (subDepartmentbyID == null) {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
		
		subDepartmentService.updateObject(subDepartment);
		
        return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@RequestMapping(value = "/api/sub-department/remove", method = RequestMethod.POST)
	public ResponseEntity<Void> removeDepartment(@RequestParam("subDepartmentIdArray") String[] subDepartmentIdArray) {
	
		subDepartmentService.removeObjects(subDepartmentIdArray);
		
        return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@RequestMapping(value = "/api/sub-department/changeStatus", method = RequestMethod.POST)
	public ResponseEntity<Void> changeSubDepartmentStatus(@RequestParam("subDepartmentId") String subDepartmentId) {
		
		SubDepartmentModel department = subDepartmentService.findById(subDepartmentId);
		
		subDepartmentService.changeObjectStatus(department);
		
        return new ResponseEntity<Void>(HttpStatus.OK);
	}
}
