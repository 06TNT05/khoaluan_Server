/**
 * Time creation: Feb 13, 2023, 2:21:08 PM
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
import com.exam.model.LevelModel;
import com.exam.service.LevelService;

/**
 * @author Tien Tran
 *
 * class LevelController
 */
@CrossOrigin(maxAge = 3600)
@RestController
public class LevelController {

	@Autowired
	private LevelService levelService;
	
	@RequestMapping(value = "/api/level/find.not.deleted", method = RequestMethod.GET)
	public ResponseEntity<List<LevelModel>> findLevelsNotDeleted() {
		
		List<LevelModel> levelList = levelService.findLevelsNotDeleted(Constants.NOT_DELETED);
		
		if (levelList == null) {
			return new ResponseEntity<List<LevelModel>>(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<List<LevelModel>>(levelList, HttpStatus.OK);
	}
}
