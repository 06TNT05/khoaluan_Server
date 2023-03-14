/**
 * Time creation: Dec 3, 2022, 7:53:30 PM
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
import com.exam.model.OfficeModel;
import com.exam.service.OfficeService;

/**
 * @author Tien Tran
 *
 * class OfficeController
 */
@CrossOrigin(maxAge = 3600)
@RestController
public class OfficeController {

	@Autowired
	private OfficeService officeService;
	
	@RequestMapping(value = "/api/office/find.all", method = RequestMethod.GET)
	public ResponseEntity<List<OfficeModel>> findAllOffices() {
		
		List<OfficeModel> officeList = officeService.findAllObjects();
		
		if (officeList == null) {
			return new ResponseEntity<List<OfficeModel>>(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<List<OfficeModel>>(officeList, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/api/office/find.id", method = RequestMethod.GET)
	public ResponseEntity<OfficeModel> findById(@RequestParam("officeId") String officeId) {
		
		OfficeModel office = officeService.findById(officeId);
		
		if (office == null) {
			return new ResponseEntity<OfficeModel>(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<OfficeModel>(office, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/api/office/find.not.deleted", method = RequestMethod.GET)
	public ResponseEntity<List<OfficeModel>> findDepartmentNotDeleted() {
		
		List<OfficeModel> officeList = 
				officeService.findOfficeByStatus(Constants.NOT_DELETED);
		
		if (officeList == null) {
			return new ResponseEntity<List<OfficeModel>>(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<List<OfficeModel>>(officeList, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/api/office/add", method = RequestMethod.POST)
	public ResponseEntity<Void> addOffice(@ModelAttribute("OfficeModel") OfficeModel office) {
		
		if (officeService.isObjectExist(office)) {
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}
		
		officeService.addObject(office);
		
        return new ResponseEntity<Void>(HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/api/office/update", method = RequestMethod.POST)
	public ResponseEntity<Void> updateOffice(@ModelAttribute("OfficeModel") OfficeModel office) {
		
		OfficeModel officebyID = officeService.findById(office.getOfficeId());
		
		if (officebyID == null) {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
		
		officeService.updateObject(office);
		
        return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@RequestMapping(value = "/api/office/remove", method = RequestMethod.POST)
	public ResponseEntity<Void> removeOffice(@RequestParam("officeIdArray") String[] officeIdArray) {
		
		officeService.removeObjects(officeIdArray);
		
        return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@RequestMapping(value = "/api/office/changeStatus", method = RequestMethod.POST)
	public ResponseEntity<Void> changeOfficeStatus(@RequestParam("officeId") String officeId) {
		
		OfficeModel office = officeService.findById(officeId);
		
		officeService.changeObjectStatus(office);
		
        return new ResponseEntity<Void>(HttpStatus.OK);
	}
}
