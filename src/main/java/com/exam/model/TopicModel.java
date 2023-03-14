/**
 * Time creation: Dec 4, 2022, 11:39:30 PM
 * 
 * Package name: com.exam.model
 */
package com.exam.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.exam.common.Constants;

/**
 * @author Tien Tran
 *
 *         class TopicModel
 */
@Entity
@Table(name = "chu_de")
public class TopicModel implements Serializable {

	/**
	 * serialVersionUID type long
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "MA_CHU_DE")
	private String topicId;
	@Column(name = "TEN_CHU_DE")
	private String topicName;
	@Column(name = "MA_HOC_PHAN")
	private String subjectId;
	@Column(name = "DA_XOA")
	private Byte deleted = Constants.NOT_DELETED;
	@OneToOne
	@JoinColumn(name = "MA_HOC_PHAN", insertable = false, updatable = false)
	private SubjectModel subject;

	public TopicModel() {

	}
	
	public TopicModel(String topicId, String topicName) {
		this.topicId = topicId;
		this.topicName = topicName;
	}

	public String getTopicId() {
		return topicId;
	}

	public void setTopicId(String topicId) {
		this.topicId = topicId;
	}

	public String getTopicName() {
		return topicName;
	}

	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}

	public String getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}

	public Byte getDeleted() {
		return deleted;
	}

	public void setDeleted(Byte deleted) {
		this.deleted = deleted;
	}

	public SubjectModel getSubject() {
		return subject;
	}

	public void setSubject(SubjectModel subject) {
		this.subject = subject;
	}
}
