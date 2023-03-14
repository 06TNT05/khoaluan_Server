/**
 * Time creation: Feb 13, 2023, 2:13:16 PM
 *
 * Pakage name: com.exam.service
 */
package com.exam.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exam.dao.LevelDAO;
import com.exam.model.LevelModel;

/**
 * @author Tien Tran
 *
 * class LevelService
 */
@Service
public class LevelService {
	
	@Autowired
	private LevelDAO levelDAO;

	public LevelDAO getLevelDAO() {
		return levelDAO;
	}

	public void setLevelDAO(LevelDAO levelDAO) {
		this.levelDAO = levelDAO;
	}
	
	public List<LevelModel> findLevelsNotDeleted(Byte status) {
		
		return levelDAO.findLevelsNotDeleted(status);
	}
}
