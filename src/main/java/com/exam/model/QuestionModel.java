/**
 * Time creation: Feb 13, 2023, 8:22:38 PM
 *
 * Pakage name: com.exam.model
 */
package com.exam.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author Tien Tran
 *
 * class QuestionModel
 */
@Entity
@Table(name = "cau_hoi")
public class QuestionModel implements Serializable {

	/**
	 * serialVersionUID type long
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "MA_CAU_HOI")
	private String questionId;
	@Column(name = "NOI_DUNG")
	private String questionContent;
	@Column(name = "DAP_AN_DUNG")
	private String answerCorrect;
	@Column(name = "NGAY_TAO")
	private Date timeCreation;
	@Column(name = "MA_GV")
	private String lecturerId;
	@Column(name = "MA_CHU_DE")
	private String topicId;
	@Column(name = "MA_LOAI")
	private String questionTypeId;
	@Column(name = "MA_MUC_DO")
	private String levelId;
	@ManyToOne
	@JoinColumn(name = "MA_GV", insertable=false, updatable=false)
	private LecturerModel lecturer;
	@ManyToOne
	@JoinColumn(name = "MA_CHU_DE", insertable=false, updatable=false)
	private TopicModel topic;
	@ManyToOne
	@JoinColumn(name = "MA_LOAI", insertable=false, updatable=false)
	private QuestionTypeModel questionType;
	@ManyToOne
	@JoinColumn(name = "MA_MUC_DO", insertable=false, updatable=false)
	private LevelModel level;
	@OneToMany(mappedBy = "question", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<AnswerModel> answers;
	@Column(name = "DA_XOA")
	private Byte deleted = 0;

	public String getQuestionId() {
		return questionId;
	}

	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}

	public String getQuestionContent() {
		return questionContent;
	}

	public void setQuestionContent(String questionContent) {
		this.questionContent = questionContent;
	}

	public String getAnswerCorrect() {
		return answerCorrect;
	}

	public void setAnswerCorrect(String answerCorrect) {
		this.answerCorrect = answerCorrect;
	}

	public Date getTimeCreation() {
		return timeCreation;
	}

	public void setTimeCreation(Date timeCreation) {
		this.timeCreation = timeCreation;
	}

	public String getLecturerId() {
		return lecturerId;
	}

	public void setLecturerId(String lecturerId) {
		this.lecturerId = lecturerId;
	}

	public String getTopicId() {
		return topicId;
	}

	public void setTopicId(String topicId) {
		this.topicId = topicId;
	}

	public String getQuestionTypeId() {
		return questionTypeId;
	}

	public void setQuestionTypeId(String questionTypeId) {
		this.questionTypeId = questionTypeId;
	}

	public String getLevelId() {
		return levelId;
	}

	public void setLevelId(String levelId) {
		this.levelId = levelId;
	}

	public LecturerModel getLecturer() {
		return lecturer;
	}

	public void setLecturer(LecturerModel lecturer) {
		this.lecturer = lecturer;
	}

	public TopicModel getTopic() {
		return topic;
	}

	public void setTopic(TopicModel topic) {
		this.topic = topic;
	}

	public QuestionTypeModel getQuestionType() {
		return questionType;
	}

	public void setQuestionType(QuestionTypeModel questionType) {
		this.questionType = questionType;
	}

	public LevelModel getLevel() {
		return level;
	}

	public void setLevel(LevelModel level) {
		this.level = level;
	}

	public Byte getDeleted() {
		return deleted;
	}

	public void setDeleted(Byte deleted) {
		this.deleted = deleted;
	}

	public Set<AnswerModel> getAnswers() {
		return answers;
	}

	public void setAnswers(Set<AnswerModel> answers) {
		this.answers = answers;
	}
}
