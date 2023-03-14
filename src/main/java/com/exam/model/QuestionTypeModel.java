/**
 * Time creation: Feb 13, 2023, 2:39:35 PM
 *
 * Pakage name: com.exam.model
 */
package com.exam.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Tien Tran
 *
 * class QuestionTypeModel
 */
@Entity
@Table(name = "loai_cau_hoi")
public class QuestionTypeModel implements Serializable {

	/**
	 * serialVersionUID type long
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "MA_LOAI")
	private String questionTypeId;
	@Column(name = "TEN_LOAI")
	private String questionTypeName;
	@Column(name = "DA_XOA")
	private Byte deleted;

	public String getQuestionTypeId() {
		return questionTypeId;
	}

	public void setQuestionTypeId(String questionTypeId) {
		this.questionTypeId = questionTypeId;
	}

	public String getQuestionTypeName() {
		return questionTypeName;
	}

	public void setQuestionTypeName(String questionTypeName) {
		this.questionTypeName = questionTypeName;
	}

	public Byte getDeleted() {
		return deleted;
	}

	public void setDeleted(Byte deleted) {
		this.deleted = deleted;
	}
}
