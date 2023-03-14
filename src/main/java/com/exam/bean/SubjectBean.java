/**
 * Time creation: Dec 4, 2022, 11:04:07 PM
 * 
 * Package name: com.exam.bean
 */
package com.exam.bean;

import java.io.Serializable;

/**
 * @author Tien Tran
 *
 * class SubjectBean
 */
public class SubjectBean implements Serializable {

	/**
	 * serialVersionUID type long
	 */
	private static final long serialVersionUID = 1L;

	private String subjectId;
	private String subjectName;
	private Byte creditQuantity;
	private String subDepartmentId;
	private Byte deleted;
	private Integer page;

	public String getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public Byte getCreditQuantity() {
		return creditQuantity;
	}

	public void setCreditQuantity(Byte creditQuantity) {
		this.creditQuantity = creditQuantity;
	}

	public String getSubDepartmentId() {
		return subDepartmentId;
	}

	public void setSubDepartmentId(String subDepartmentId) {
		this.subDepartmentId = subDepartmentId;
	}

	public Byte getDeleted() {
		return deleted;
	}

	public void setDeleted(Byte deleted) {
		this.deleted = deleted;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

}
