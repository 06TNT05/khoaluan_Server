/**
 * Time creation: Dec 4, 2022, 7:01:23 PM
 * 
 * Package name: com.exam.service
 */
package com.exam.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.exam.common.Constants;
import com.exam.dao.SubjectDAO;
import com.exam.model.SubjectModel;

/**
 * @author Tien Tran
 *
 * class SubjectService
 */
@Service
public class SubjectService {

	@Autowired
	private SubjectDAO subjectDAO;

	public SubjectDAO getSubjectDAO() {
		return subjectDAO;
	}

	public void setSubjectDAO(SubjectDAO subjectDAO) {
		this.subjectDAO = subjectDAO;
	}

	public SubjectModel findById(String id) {
		
		return subjectDAO.findById(id);
	}

	public void addObject(SubjectModel subject) {
		
		subjectDAO.addObject(subject);
	}

	public void updateObject(SubjectModel subject) {
		
		subjectDAO.updateObject(subject);
	}

	public List<SubjectModel> findAllObjects() {
		
		return subjectDAO.findAllObjects();
	}
	
	public List<SubjectModel> findAllObjectsPagination(Integer page, String searchString) {
		
		searchString = searchString.replaceAll(Constants.SYMBOL_PERCENT,
				Constants.SYMBOL_OPENING_SQUARE_BRACKETS
				+ Constants.SYMBOL_PERCENT
				+ Constants.SYMBOL_OPENING_SQUARE_BRACKETS);
		
		return subjectDAO.findAllObjectsPagination(page, searchString);
	}

	public boolean isObjectExist(SubjectModel subject) {
		
		return findById(subject.getSubjectId()) != null;
	}

	public void removeObjects(String[] array) {
		
		StringBuilder builder = new StringBuilder();
		builder.append("UPDATE SubjectModel s SET s.deleted = :status WHERE s.subjectId IN (");
		
		for (String id : array) {
			builder.append("'" + id + "',");
		}
		
		builder.deleteCharAt(builder.toString().length() - 1);
		builder.append(Constants.SYMBOL_CLOSING_BRACKETS);

		subjectDAO.removeObjects(builder.toString(), Constants.DELETED);
	}

	public void changeObjectStatus(SubjectModel subject) {
		
		if (subject.getDeleted() == Constants.DELETED) {
			subject.setDeleted(Constants.NOT_DELETED);
		} else {
			subject.setDeleted(Constants.DELETED);
		}
		
		subjectDAO.updateObject(subject);
	}
	
	public void addSubjects(String queryString) {
		
		subjectDAO.addSubjects(queryString);
	}
	
	public List<SubjectModel> findBySubDepartmentId(String subDepartmentId) {
		
		return subjectDAO.findBySubDepartmentId(subDepartmentId, Constants.NOT_DELETED);
	}
	
	public List<SubjectModel> findSubjectByStatus(Byte status) {
		
		return subjectDAO.findSubjectByStatus(status);
	}
	
	public void readDataExcel(CommonsMultipartFile file, ServletContext context) throws EncryptedDocumentException, IOException {
		
		int count = 0;
		String subjectId;
		String subjectName;
		Byte creditQuantity;
		String subDepartmentId;
		Workbook workbook = null;
		StringBuilder builderSubject = new StringBuilder("insert into HOC_PHAN values ");
		//StringBuilder builderAccount = new StringBuilder("insert into TAI_KHOAN values ");
		
		try {

			workbook = WorkbookFactory.create(file.getInputStream());
			
			Sheet sheet = workbook.getSheetAt(Constants.SHEET_0);
			
			for (Row row : sheet) {
				
				// skip first row
				if (count++ == 0) continue;
				
				Cell cell_0 = row.getCell(0);
				// get value from excel file
				subjectId = cell_0.getCellType().equals(CellType.STRING) 
							? cell_0.getStringCellValue() 
							: String.valueOf((int)cell_0.getNumericCellValue());
				subjectName = row.getCell(1).getStringCellValue();
				creditQuantity = (byte) row.getCell(2).getNumericCellValue();
				subDepartmentId = row.getCell(3).getStringCellValue();
				
				// build query string for insert multi lecturer
				builderSubject.append(String.format("('%s', '%s', '%d', '%s', 0),",
						subjectId, subjectName, creditQuantity, subDepartmentId));
			}
			
			// handle builder for query string correctly
			builderSubject = builderSubject.deleteCharAt(builderSubject.toString().length() - 1);
			addSubjects(builderSubject.toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			workbook.close();
		}
	}
	
	public Long getTotalRecord(String searchString) {
		
		searchString = searchString.replaceAll(Constants.SYMBOL_PERCENT,
				Constants.SYMBOL_OPENING_SQUARE_BRACKETS
				+ Constants.SYMBOL_PERCENT
				+ Constants.SYMBOL_OPENING_SQUARE_BRACKETS);
		
		return subjectDAO.getTotalRecord(searchString);
	}
	
	public List<SubjectModel> findByLecturerId(String lecturerId) {
		
		return subjectDAO.findByLecturerId(lecturerId, Constants.NOT_DELETED);
	}
}
