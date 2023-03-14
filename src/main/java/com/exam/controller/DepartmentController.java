/**
 * Time creation: Dec 1, 2022, 10:39:55 PM
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
import com.exam.model.DepartmentModel;
import com.exam.service.DepartmentService;

/**
 * @author Tien Tran
 *
 * class DepartmentController
 */
@CrossOrigin(maxAge = 3600)
@RestController
public class DepartmentController {
	
	@Autowired
	private DepartmentService departmentService;
	
	@RequestMapping(value = "/api/department/find.all", method = RequestMethod.GET)
	public ResponseEntity<List<DepartmentModel>> findAllDepartments() {
		
		List<DepartmentModel> departmentList = departmentService.findAllObjects();
		
		if (departmentList == null) {
			return new ResponseEntity<List<DepartmentModel>>(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<List<DepartmentModel>>(departmentList, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/api/department/find.id", method = RequestMethod.GET)
	public ResponseEntity<DepartmentModel> findById(@RequestParam("departmentId") String departmentId) {
		
		DepartmentModel department = departmentService.findById(departmentId);
		
		if (department == null) {
			return new ResponseEntity<DepartmentModel>(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<DepartmentModel>(department, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/api/department/find.not.deleted", method = RequestMethod.GET)
	public ResponseEntity<List<DepartmentModel>> findDepartmentNotDeleted() {
		
		List<DepartmentModel> departmentList = 
				departmentService.findDepartmentByStatus(Constants.NOT_DELETED);
		
		if (departmentList == null) {
			return new ResponseEntity<List<DepartmentModel>>(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<List<DepartmentModel>>(departmentList, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/api/department/add", method = RequestMethod.POST)
	public ResponseEntity<Void> addDepartment(@ModelAttribute("DepartmentModel") DepartmentModel department) {
		
		if (departmentService.isObjectExist(department)) {
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}
		
		departmentService.addObject(department);
		
        return new ResponseEntity<Void>(HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/api/department/update", method = RequestMethod.POST)
	public ResponseEntity<Void> updateDepartment(@ModelAttribute("DepartmentModel") DepartmentModel department) {
		
		DepartmentModel departmentbyID = departmentService.findById(department.getDepartmentId());
		
		if (departmentbyID == null) {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
		
		departmentService.updateObject(department);
		
        return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@RequestMapping(value = "/api/department/remove", method = RequestMethod.POST)
	public ResponseEntity<Void> removeDepartment(@RequestParam("departmentIdArray") String[] departmentIdArray) {
		
		departmentService.removeObjects(departmentIdArray);
		
        return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@RequestMapping(value = "/api/department/changeStatus", method = RequestMethod.POST)
	public ResponseEntity<Void> changeDepartmentStatus(@RequestParam("departmentId") String departmentId) {
		
		DepartmentModel department = departmentService.findById(departmentId);
		
		departmentService.changeObjectStatus(department);
		
        return new ResponseEntity<Void>(HttpStatus.OK);
	}
}
