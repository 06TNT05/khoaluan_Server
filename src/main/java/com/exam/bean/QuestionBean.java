/**
 * Time creation: Feb 13, 2023, 8:39:25 PM
 *
 * Pakage name: com.exam.bean
 */
package com.exam.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * @author Tien Tran
 *
 * class QuestionBean
 */
public class QuestionBean implements Serializable {

	/**
	 * serialVersionUID type long
	 */
	private static final long serialVersionUID = 1L;

	private String questionId;
	private String questionContent;
	private String answerCorrect;
	private Date timeCreation;
	private String subjectId;
	private String lecturerId;
	private String topicId;
	private String questionTypeId;
	private String levelId;
	private String optionA;
	private String optionB;
	private String optionC;
	private String optionD;
	private String answerEssay;
	private List<CommonsMultipartFile> fileList;
	private Byte action;
	private Integer page;
	private String searchString;
	private String role;

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
	
	public String getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
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

	public String getOptionA() {
		return optionA;
	}

	public void setOptionA(String optionA) {
		this.optionA = optionA;
	}

	public String getOptionB() {
		return optionB;
	}

	public void setOptionB(String optionB) {
		this.optionB = optionB;
	}

	public String getOptionC() {
		return optionC;
	}

	public void setOptionC(String optionC) {
		this.optionC = optionC;
	}

	public String getOptionD() {
		return optionD;
	}

	public void setOptionD(String optionD) {
		this.optionD = optionD;
	}

	public Byte getAction() {
		return action;
	}

	public void setAction(Byte action) {
		this.action = action;
	}

	public String getAnswerEssay() {
		return answerEssay;
	}

	public void setAnswerEssay(String answerEssay) {
		this.answerEssay = answerEssay;
	}

	public List<CommonsMultipartFile> getFileList() {
		return fileList;
	}

	public void setFileList(List<CommonsMultipartFile> fileList) {
		this.fileList = fileList;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public String getSearchString() {
		return searchString;
	}

	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}
