/**
 * Time creation: Feb 26, 2023, 8:47:59 PM
 *
 * Pakage name: com.exam.model
 */
package com.exam.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * @author Tien Tran
 *
 * class ExamQuestionModel
 */
@Entity
@Table(name = "cau_hoi_de_thi")
public class ExamQuestionModel implements Serializable {
	
	/**
	 * serialVersionUID type long
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "MA_DE")
	private String examId;
	@Id
	@Column(name = "MA_CAU_HOI")
	private String questionId;
	@Column(name = "DIEM")
	private Integer score;
	@ManyToOne
	@JoinColumn(name = "MA_DE", insertable = false, updatable = false)
	private ExamModel exam;
	@OneToOne
	@JoinColumn(name = "MA_CAU_HOI", insertable = false, updatable = false)
	private QuestionModel question;

	public String getExamId() {
		return examId;
	}

	public void setExamId(String examId) {
		this.examId = examId;
	}

	public String getQuestionId() {
		return questionId;
	}

	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public ExamModel getExam() {
		return exam;
	}

	public void setExam(ExamModel exam) {
		this.exam = exam;
	}

	public QuestionModel getQuestion() {
		return question;
	}

	public void setQuestion(QuestionModel question) {
		this.question = question;
	}
}
