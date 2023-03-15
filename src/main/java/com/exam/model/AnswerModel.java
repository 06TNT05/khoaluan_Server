/**
 * Time creation: Feb 14, 2023, 1:16:18 PM
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
import javax.persistence.Table;

/**
 * @author Tien Tran
 *
 * class AnswerModel
 */
@Entity
@Table(name = "dap_an")
public class AnswerModel implements Serializable {

	/**
	 * serialVersionUID type long
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "MA_DAP_AN")
	private String answerId;
	@Column(name = "MA_CAU_HOI")
	private String questionId;
	@Column(name = "NOI_DUNG")
	private String answerContent;
	@ManyToOne
	@JoinColumn(name = "MA_CAU_HOI", referencedColumnName = "MA_CAU_HOI", insertable = false, updatable = false)
	private QuestionModel question;

	public String getAnswerId() {
		return answerId;
	}

	public void setAnswerId(String answerId) {
		this.answerId = answerId;
	}

	public String getQuestionId() {
		return questionId;
	}

	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}

	public String getAnswerContent() {
		return answerContent;
	}

	public void setAnswerContent(String answerContent) {
		this.answerContent = answerContent;
	}
}
