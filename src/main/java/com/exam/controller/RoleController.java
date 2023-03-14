/**
 * Time creation: Dec 4, 2022, 6:57:09 PM
 * 
 * Package name: com.exam.controller
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

import com.exam.model.RoleModel;
import com.exam.service.RoleService;

/**
 * @author Tien Tran
 *
 * class RoleController
 */
@CrossOrigin(maxAge = 3600)
@RestController
public class RoleController {
	
	@Autowired
	private RoleService roleService;
	
	@RequestMapping(value = "/api/role/find.all", method = RequestMethod.GET)
	public ResponseEntity<List<RoleModel>> findAllRoles() {
		
		List<RoleModel> roleList = roleService.findAllRoles();
		
		if (roleList == null) {
			return new ResponseEntity<List<RoleModel>>(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<List<RoleModel>>(roleList, HttpStatus.OK);
	}
	
//	@RequestMapping(value = "/api/role/find.not.deleted", method = RequestMethod.GET)
//	public ResponseEntity<List<RoleModel>> findRoleNotDeleted() {
//		
//		List<RoleModel> roleList = roleService.findRoleByStatus(Constants.NOT_DELETED);
//		
//		if (roleList == null) {
//			return new ResponseEntity<List<RoleModel>>(HttpStatus.NO_CONTENT);
//		}
//		
//		return new ResponseEntity<List<RoleModel>>(roleList, HttpStatus.OK);
//	}
}
