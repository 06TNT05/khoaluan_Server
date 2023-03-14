/**
 * Time creation: Dec 3, 2022, 9:12:21 PM
 * 
 * Package name: com.exam.service
 */
package com.exam.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletContext;

import org.apache.commons.io.FileUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.exam.bean.LecturerBean;
import com.exam.common.Constants;
import com.exam.common.FileCommon;
import com.exam.dao.LecturerDAO;
import com.exam.model.AccountModel;
import com.exam.model.LecturerModel;

/**
 * @author Tien Tran
 *
 * class LecturerService
 */
@Service
public class LecturerService {
	
	@Autowired
	private LecturerDAO lecturerDAO;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private FileCommon fileCommon;
	
	@Autowired
	private TeachingService teachingService;

	// setter injection
	@Autowired
	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}
	
	public void setTeachingService(TeachingService teachingService) {
		this.teachingService = teachingService;
	}

	@Autowired
	public void setFileCommon(FileCommon fileCommon) {
		this.fileCommon = fileCommon;
	}

	public LecturerDAO getLecturerDAO() {
		return lecturerDAO;
	}

	public void setLecturerDAO(LecturerDAO lecturerDAO) {
		this.lecturerDAO = lecturerDAO;
	}

	public LecturerModel findById(String id) {
		
		return lecturerDAO.findById(id);
	}

	public void addObject(LecturerModel lecturer) {
		
		lecturerDAO.addObject(lecturer);
	}
	
	public void addLecturer(LecturerBean lecturerBean, LecturerModel lecturerModel, ServletContext context) throws IOException {
		
		// get list subject which lecturer is teaching
		String[] subjectIdArray = lecturerBean.getSubjects();
		String lecturerId = lecturerBean.getLecturerId();
		
		// get file from LecturerBean
		CommonsMultipartFile file = lecturerBean.getFile();
		
		// if don't chose file, set imgSrc = empty
		lecturerModel.setImageSrc(Constants.STRING_EMPTY);
		
		if (file.getSize() > Constants.ZERO) {
			
			// get path from file to save database
			String imageSrc = fileCommon.getPathToDatabase(Constants.AVATARS_DIRECTORY, file);
			
			// set path to LecturerModel
			lecturerModel.setImageSrc(imageSrc);
			
			// get real path string for upload to server
			String pathToUpload = 
					fileCommon.getPathToUpload(file.getOriginalFilename(), Constants.AVATARS_DIRECTORY, context);
			
			// upload file to server
			fileCommon.uploadFile(file.getBytes(), pathToUpload);
		}
		
		// get AccountModel from LecturerBean
		AccountModel account = accountService.getAccountFromBean(lecturerBean);
		
		// add LecturerModel
		lecturerDAO.addObject(lecturerModel);
		
		// add account for lecturer
		accountService.addAccount(account);

		// add subject for lecturer
		if (subjectIdArray.length > Constants.ZERO) {
			
			String queryString = teachingService.buildQueryAddTeaching(lecturerId, Arrays.asList(subjectIdArray));
			
			teachingService.addTeachings(queryString);
		}
	}

	public void updateObject(LecturerModel lecturer) {

		lecturerDAO.updateObject(lecturer);
	}
	
	public void updateLecturer(LecturerBean lecturerBean, ServletContext context) throws IOException {
		
		// get LecturerModel from LecturerBean
		LecturerModel lecturerModel = convertBeantoModel(lecturerBean);
		
		String lecturerId = lecturerBean.getLecturerId();
		
		LecturerModel lecturerbyID = findById(lecturerId);
		
		/* handle external subject
		   if you want to add external subject, you must set the subDepartmentId which is the old lecturerModel.
		   This will be not set to the another subdepartmentId*/
		if (lecturerBean.getIsExternalSubject()) {
			
			lecturerModel.setSubDepartmentId(lecturerbyID.getSubDepartmentId());
		}
		
		// list subject which client send to server
		String[] subjectIdArray = lecturerBean.getSubjects();
		
		// get file from LecturerBean
		CommonsMultipartFile file = lecturerBean.getFile();
		
		// if don't change image, set image source = old image source
		lecturerModel.setImageSrc(lecturerbyID.getImageSrc());
		
		if (file.getSize() > Constants.ZERO) {
			
			// get path from file to save database
			String imageSrc = fileCommon.getPathToDatabase(Constants.AVATARS_DIRECTORY, file);
			
			// set path to LecturerModel
			lecturerModel.setImageSrc(imageSrc);
			
			// get real path string for upload to server
			String pathToUpload = 
					fileCommon.getPathToUpload(file.getOriginalFilename(), Constants.AVATARS_DIRECTORY, context);
			
			// upload file to server
			fileCommon.uploadFile(file.getBytes(), pathToUpload);
		}
		
		String lecturerName = lecturerModel.getLecturerName();
		List<String> lecturerNameList = Arrays.asList(lecturerName.split(Constants.SYMBOL_SPACE));
		int lastIndex = lecturerNameList.size() - 1;
		// get last element
		String firstName = lecturerNameList.get(lastIndex);
		// join array with space to last name
		String lastName = String.join(Constants.SYMBOL_SPACE,
				lecturerNameList.subList(Constants.ZERO, lastIndex));
		
		lecturerModel.setFirstName(firstName);
		lecturerModel.setLastName(lastName);
		
		lecturerDAO.updateObject(lecturerModel);
		
		// this part to update subject of lecturer
		List<String> subjectIdTeaching = 
				teachingService.getSubjectIdList(lecturerBean.getLecturerId());

		List<String> subjectIdAdd = teachingService.getSubjectIdAdd(subjectIdArray, subjectIdTeaching);
		
		List<String> subjectIdUpdateDeleted = 
				teachingService.getSubjectIdUpdateDeleted(subjectIdArray, subjectIdTeaching);
		
		List<String> subjectIdUpdateNoDeleted = 
				teachingService.getSubjectIdUpdateNoDeleted(subjectIdArray, subjectIdTeaching);
		
		// What subject to add is subject which don't contain in subjectIdArray
		if (!subjectIdAdd.isEmpty()) {
			
			String queryAddTeaching = teachingService.buildQueryAddTeaching(lecturerId, subjectIdAdd);
			teachingService.addTeachings(queryAddTeaching);
		}
		
		// What subject to update to deleted is subject which contain in subjectIdArray,
		// but don't contain in subjectIdTeaching
		if (!subjectIdUpdateDeleted.isEmpty()) {
			
			String queryUpdateTeaching = 
					teachingService.buildQueryUpdateTeaching(lecturerId, subjectIdUpdateDeleted);

			teachingService.updateTeachings(lecturerId, Constants.DELETED, queryUpdateTeaching);
		}
		
		// What subject to update to deleted is subject which contain in subjectIdArray,
		// and contain in subjectIdTeaching
		if (!subjectIdUpdateNoDeleted.isEmpty()) {
			
			String queryUpdateTeaching = 
					teachingService.buildQueryUpdateTeaching(lecturerId, subjectIdUpdateNoDeleted);

			teachingService.updateTeachings(lecturerId, Constants.NOT_DELETED, queryUpdateTeaching);
		}
		
		AccountModel accountModel = accountService.getAccountFromBean(lecturerBean);
		accountModel.setPassword(lecturerbyID.getAccount().getPassword());
		accountService.updateAccount(accountModel);
	}

	public List<LecturerModel> findAllObjects() {
		
		return lecturerDAO.findAllObjects();
	}
	
	public List<LecturerModel> findAllObjectsPagination(Integer page, String searchString) {
		
		searchString = searchString.replaceAll(Constants.SYMBOL_PERCENT,
						Constants.SYMBOL_OPENING_SQUARE_BRACKETS
						+ Constants.SYMBOL_PERCENT
						+ Constants.SYMBOL_OPENING_SQUARE_BRACKETS);
		
		return lecturerDAO.findAllObjectsPagination(page, searchString);
	}
	
	public void removeObjects(String[] array) {
		
		StringBuilder builder = new StringBuilder();
		builder.append("UPDATE LecturerModel l SET l.deleted = :status WHERE l.lecturerId IN (");
		
		for (String id : array) {
			builder.append("'" + id + "',");
		}
		
		builder = builder.deleteCharAt(builder.toString().length() - 1);
		builder.append(Constants.SYMBOL_CLOSING_BRACKETS);

		lecturerDAO.removeObjects(builder.toString(), Constants.DELETED);
	}

	public boolean isObjectExist(LecturerModel lecturer) {
		
		return findById(lecturer.getLecturerId()) != null
				|| findByEmail(lecturer.getEmail()) != null;
	}
	
	public LecturerModel findByEmail(String email) {
		
		return lecturerDAO.findByEmail(email);
	}
	
	public void addLecturers(String queryString) {
		
		lecturerDAO.addLecturers(queryString);
	}
	
	public LecturerModel convertBeantoModel(LecturerBean lecturer) {
		
		String lecturerId = lecturer.getLecturerId();
		String lecturerName = lecturer.getLecturerName();
		List<String> lecturerNameList = Arrays.asList(lecturerName.split(Constants.SYMBOL_SPACE));
		int lastIndex = lecturerNameList.size() - 1;
		// get last element
		String firstName = lecturerNameList.get(lastIndex);
		// join array with space to last name
		String lastName = String.join(Constants.SYMBOL_SPACE,
				lecturerNameList.subList(Constants.ZERO, lastIndex));
		String phoneNumber = lecturer.getPhoneNumber();
		String email = lecturer.getEmail();
		String subDepartmentId = lecturer.getSubDepartmentId();
		String officeId = lecturer.getOfficeId();
		
		LecturerModel lecturerModel = new LecturerModel();
		lecturerModel.setLecturerId(lecturerId);
		lecturerModel.setFirstName(firstName);
		lecturerModel.setLastName(lastName);
		lecturerModel.setLecturerName(lecturerName);
		lecturerModel.setPhoneNumber(phoneNumber);
		lecturerModel.setEmail(email);
		lecturerModel.setSubDepartmentId(subDepartmentId);
		lecturerModel.setOfficeId(officeId);
		
		return lecturerModel;
	}
	
	public void readDataExcel(CommonsMultipartFile file, ServletContext context) throws IOException, EncryptedDocumentException {
		
		int count = 0;
		String lecturerId;
		String lecturerName;
		String phoneNumber;
		String email;
		String realPath;
		String subDepartmentId;
		String officeId;
		Integer roleId;
		String subjectIds;
		InputStream inputStream = file.getInputStream();
		Workbook workbook = null;
		StringBuilder builderLecturer = new StringBuilder("insert into GIANG_VIEN values ");
		StringBuilder builderAccount = new StringBuilder("insert into TAI_KHOAN values ");
		
		try {
			
			workbook = WorkbookFactory.create(inputStream);

			Sheet sheet = workbook.getSheetAt(Constants.SHEET_0);
			
			for (Row row : sheet) {
				
				String imageSrc = Constants.STRING_EMPTY;
				
				// skip first row
				if (count++ == 0) continue;
				
				// get value from excel file
				lecturerId = row.getCell(0).getStringCellValue();
				
				// case: if sheet still run when it have done, break loop
				if (lecturerId.isEmpty()) {
					
					break;
				}
				
				lecturerName = row.getCell(1).getStringCellValue();
				
				List<String> lecturerNameList = Arrays.asList(lecturerName.split(Constants.SYMBOL_SPACE));
				int lastIndex = lecturerNameList.size() - 1;
				// get last element
				String firstName = lecturerNameList.get(lastIndex);
				// join array with space to last name
				String lastName = String.join(Constants.SYMBOL_SPACE,
						lecturerNameList.subList(Constants.ZERO, lastIndex));
				
				phoneNumber = row.getCell(2).getStringCellValue();
				email = row.getCell(3).getStringCellValue();
				
				realPath = row.getCell(4) != null ? row.getCell(4).getStringCellValue() : Constants.STRING_EMPTY;
				
				subDepartmentId = row.getCell(5).getStringCellValue();
				officeId = row.getCell(6).getStringCellValue();
				roleId = (int) row.getCell(7).getNumericCellValue();
				
				if (!Constants.STRING_EMPTY.equals(realPath)) {
					
					// handle image source
					String[] array = realPath.split(Constants.SYMBOL_DOUBLE_BACKSLASH);
					String fileName = array[array.length - 1];
					imageSrc = Constants.AVATARS_DIRECTORY + fileName;
					
					File fileImage = new File(realPath);
					byte[] bytes = FileUtils.readFileToByteArray(fileImage);
					
					// get real path string for upload to server
					String pathToUpload = 
							fileCommon.getPathToUpload(file.getOriginalFilename(), Constants.AVATARS_DIRECTORY, context);
					
					// upload file to server
					fileCommon.uploadFile(bytes, pathToUpload);
				}
				
				// build query string for insert multi lecturer
				builderLecturer.append(String.format("('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', 0),",
			    		lecturerId, lastName, firstName, lecturerName, phoneNumber, email, imageSrc, subDepartmentId, officeId));
				
				// build query string for insert multi account
				builderAccount.append(String.format("('%s', '%s', %d),", email, accountService.handlePassword(email), roleId));
			}
			
			// handle builder for query string correctly
			builderLecturer = builderLecturer.deleteCharAt(builderLecturer.toString().length() - 1);
			builderAccount = builderAccount.deleteCharAt(builderAccount.toString().length() - 1);
			
			addLecturers(builderLecturer.toString());
			accountService.addAccounts(builderAccount.toString());
			
			// reset count
			count = 0;
			
			for (Row row: sheet) {
				
				// skip first row
				if (count++ == 0) continue;
				
				lecturerId = row.getCell(0).getStringCellValue();
				
				// case: if sheet still run when it have done, break loop
				if (lecturerId.isEmpty()) {
					
					break;
				}
				
				subjectIds = row.getCell(8).getStringCellValue();
				
				if (!Constants.STRING_EMPTY.equals(subjectIds)) {
					
					String[] subjectIdArray = subjectIds.split(Constants.SYMBOL_COMMA);
					
					// add subject to teaching table
					String queryAddTeaching = teachingService.buildQueryAddTeaching(lecturerId, Arrays.asList(subjectIdArray));
					teachingService.addTeachings(queryAddTeaching);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			inputStream.close();
			workbook.close();
		}
	}

	public void changeObjectStatus(LecturerModel lecturer) {
		
		if (lecturer.getDeleted() == Constants.DELETED) {
			lecturer.setDeleted(Constants.NOT_DELETED);
		} else {
			lecturer.setDeleted(Constants.DELETED);
		}
		
		lecturerDAO.updateObject(lecturer);
	}
	
	public Long getTotalRecord(String searchString) {
		
		searchString = searchString.replaceAll(Constants.SYMBOL_PERCENT,
						Constants.SYMBOL_OPENING_SQUARE_BRACKETS
						+ Constants.SYMBOL_PERCENT
						+ Constants.SYMBOL_OPENING_SQUARE_BRACKETS);

		return lecturerDAO.getTotalRecord(searchString);
	}
	
	public LecturerModel findUser(String email, String password) {
		
		return lecturerDAO.findUser(email, password, Constants.NOT_DELETED);
	}
}
