/**
 * Time creation: Dec 4, 2022, 4:22:15 PM
 * 
 * Package name: com.exam.model
 */
package com.exam.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.exam.common.Constants;

/**
 * @author Tien Tran
 *
 * class TeachingModel
 */
@Entity
@Table(name = "giang_day")
public class TeachingModel implements Serializable {

	/**
	 * serialVersionUID type long
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "MA_GV")
	private String lecturerId;
	@Id
	@Column(name = "MA_HOC_PHAN")
	private String subjectId;
	@Column(name = "DA_XOA")
	private Byte deleted = Constants.NOT_DELETED;
//	@ManyToOne
//	@JoinColumn(name = "MA_GV", insertable = false, updatable = false)
//	private LecturerModel lecturer;
	@ManyToOne
	@JoinColumn(name = "MA_HOC_PHAN", insertable = false, updatable = false)
	private SubjectModel subject;

	public String getLecturerId() {
		return lecturerId;
	}

	public void setLecturerId(String lecturerId) {
		this.lecturerId = lecturerId;
	}

	public String getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}

//	public LecturerModel getLecturer() {
//		return lecturer;
//	}
//
//	public void setLecturer(LecturerModel lecturer) {
//		this.lecturer = lecturer;
//	}

	public SubjectModel getSubject() {
		return subject;
	}

	public void setSubject(SubjectModel subject) {
		this.subject = subject;
	}

	public Byte getDeleted() {
		return deleted;
	}

	public void setDeleted(Byte deleted) {
		this.deleted = deleted;
	}
}
