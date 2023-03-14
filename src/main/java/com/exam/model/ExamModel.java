/**
 * Time creation: Feb 25, 2023, 8:44:14 PM
 *
 * Pakage name: com.exam.model
 */
package com.exam.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.exam.common.Constants;

/**
 * @author Tien Tran
 *
 * class ExamModel
 */
@Entity
@Table(name = "de_thi")
public class ExamModel implements Serializable {

	/**
	 * serialVersionUID type long
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "MA_DE")
	private String examId;
	@Column(name = "TEN_DE")
	private String examName;
	@Column(name = "SO_LUONG_CAU")
	private Integer questionQuantity;
	@Column(name = "THOI_GIAN_THI")
	private Byte examTime;
	@Column(name = "NGAY_TAO")
	private Date timeCreation;
	@Column(name = "MA_HOC_PHAN")
	private String subjectId;
	@Column(name = "GV_TAO")
	private String createdLecturerId;
	@Column(name = "DA_XOA")
	private Byte deleted = Constants.NOT_DELETED;
	@OneToOne
	@JoinColumn(name = "GV_TAO", referencedColumnName = "MA_GV", insertable = false, updatable = false)
	private LecturerModel createdlecturer;
	@ManyToOne
	@JoinColumn(name = "MA_HOC_PHAN", referencedColumnName = "MA_HOC_PHAN", insertable = false, updatable = false)
	private SubjectModel subject;

	public String getExamId() {
		return examId;
	}

	public void setExamId(String examId) {
		this.examId = examId;
	}

	public String getExamName() {
		return examName;
	}

	public void setExamName(String examName) {
		this.examName = examName;
	}

	public Integer getQuestionQuantity() {
		return questionQuantity;
	}

	public void setQuestionQuantity(Integer questionQuantity) {
		this.questionQuantity = questionQuantity;
	}

	public Byte getExamTime() {
		return examTime;
	}

	public void setExamTime(Byte examTime) {
		this.examTime = examTime;
	}

	public Date getTimeCreation() {
		return timeCreation;
	}

	public void setTimeCreation(Date timeCreation) {
		this.timeCreation = timeCreation;
	}

	public String getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}

	public String getCreatedLecturerId() {
		return createdLecturerId;
	}

	public void setCreatedLecturerId(String createdLecturerId) {
		this.createdLecturerId = createdLecturerId;
	}

	public Byte getDeleted() {
		return deleted;
	}

	public void setDeleted(Byte deleted) {
		this.deleted = deleted;
	}

	public LecturerModel getCreatedlecturer() {
		return createdlecturer;
	}

	public void setCreatedlecturer(LecturerModel createdlecturer) {
		this.createdlecturer = createdlecturer;
	}

	public SubjectModel getSubject() {
		return subject;
	}

	public void setSubject(SubjectModel subject) {
		this.subject = subject;
	}
}
