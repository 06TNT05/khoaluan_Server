/**
 * Time creation: Dec 4, 2022, 3:04:57 PM
 * 
 * Package name: com.exam.bean
 */
package com.exam.bean;

import java.io.Serializable;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * @author Tien Tran
 *
 * class LecturerBean
 */
public class LecturerBean implements Serializable {

	/**
	 * serialVersionUID type long
	 */
	private static final long serialVersionUID = 1L;
	
	private String lecturerId;
	private String lecturerName;
	private String phoneNumber;
	private String email;
	private CommonsMultipartFile file;
	private String subDepartmentId;
	private String officeId;
	private String[] subjects;
	private Integer page;
	private Integer roleId;
	private Boolean isExternalSubject;

	public String getLecturerId() {
		return lecturerId;
	}

	public void setLecturerId(String lecturerId) {
		this.lecturerId = lecturerId;
	}

	public String getLecturerName() {
		return lecturerName;
	}

	public void setLecturerName(String lecturerName) {
		this.lecturerName = lecturerName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public CommonsMultipartFile getFile() {
		return file;
	}

	public void setFile(CommonsMultipartFile file) {
		this.file = file;
	}

	public String getSubDepartmentId() {
		return subDepartmentId;
	}

	public void setSubDepartmentId(String subDepartmentId) {
		this.subDepartmentId = subDepartmentId;
	}

	public String getOfficeId() {
		return officeId;
	}

	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}

	public String[] getSubjects() {
		return subjects;
	}

	public void setSubjects(String[] subjects) {
		this.subjects = subjects;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Boolean getIsExternalSubject() {
		return isExternalSubject;
	}

	public void setIsExternalSubject(Boolean isExternalSubject) {
		this.isExternalSubject = isExternalSubject;
	}
}
