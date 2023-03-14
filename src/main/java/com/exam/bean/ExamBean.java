/**
 * Time creation: Feb 25, 2023, 9:08:49 PM
 *
 * Pakage name: com.exam.bean
 */
package com.exam.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Tien Tran
 *
 * class ExamBean
 */
public class ExamBean implements Serializable {

	/**
	 * serialVersionUID type long
	 */
	private static final long serialVersionUID = 1L;

	private String examName;
	private String subjectId;
	private String questionTypeId;
	private String createdLecturerId;
	private Date timeCreation;
	private List<Map<String, Integer>> examQuestionIdList;
	private Byte examTime;
	private List<Map<String, Integer>> topics;

	public String getExamName() {
		return examName;
	}

	public void setExamName(String examName) {
		this.examName = examName;
	}

	public String getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}

	public List<Map<String, Integer>> getTopics() {
		return topics;
	}

	public void setTopics(List<Map<String, Integer>> topics) {
		this.topics = topics;
	}

	public String getQuestionTypeId() {
		return questionTypeId;
	}

	public void setQuestionTypeId(String questionTypeId) {
		this.questionTypeId = questionTypeId;
	}

	public String getCreatedLecturerId() {
		return createdLecturerId;
	}

	public void setCreatedLecturerId(String createdLecturerId) {
		this.createdLecturerId = createdLecturerId;
	}

	public Date getTimeCreation() {
		return timeCreation;
	}

	public void setTimeCreation(Date timeCreation) {
		this.timeCreation = timeCreation;
	}

	public List<Map<String, Integer>> getExamQuestionIdList() {
		return examQuestionIdList;
	}

	public void setExamQuestionIdList(List<Map<String, Integer>> examQuestionIdList) {
		this.examQuestionIdList = examQuestionIdList;
	}

	public Byte getExamTime() {
		return examTime;
	}

	public void setExamTime(Byte examTime) {
		this.examTime = examTime;
	}
}
