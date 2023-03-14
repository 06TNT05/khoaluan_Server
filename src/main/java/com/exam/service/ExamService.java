/**
 * Time creation: Feb 25, 2023, 8:44:25 PM
 *
 * Pakage name: com.exam.service
 */
package com.exam.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exam.bean.ExamBean;
import com.exam.common.Constants;
import com.exam.dao.ExamDAO;
import com.exam.model.ExamModel;
import com.exam.model.ExamQuestionModel;
import com.exam.model.QuestionModel;
import com.itextpdf.html2pdf.HtmlConverter;

/**
 * @author Tien Tran
 *
 * class ExamService
 */
@Service
public class ExamService {

	@Autowired
	private QuestionService questionService;
	@Autowired
	private ExamQuestionService examQuestionService;
	@Autowired
	private ExamDAO examDAO;

	public void setExamDAO(ExamDAO examDAO) {
		this.examDAO = examDAO;
	}
	
	public void setQuestionService(QuestionService questionService) {
		this.questionService = questionService;
	}

	public void setExamQuestionService(ExamQuestionService examQuestionService) {
		this.examQuestionService = examQuestionService;
	}

	public List<QuestionModel> randomCreationExam(ExamBean examBean) {
		
		List<Map<String, Integer>> topics = examBean.getTopics();
		//List<Map<String, Integer>> options = new ArrayList<>();
		List<Set<String>> topicLevelIdList = new ArrayList<>();
		List<List<Integer>> quantityQuestionList = new ArrayList<>();
		List<QuestionModel> questionExamList = new ArrayList<>();
		String questionTypeId = examBean.getQuestionTypeId();

		if (topics != null) {
			
			// get List topicId_LevelId, quantity question
			for (int i = 0; i < topics.size(); i++) {
				
				topicLevelIdList.add(topics.get(i).keySet());
				List<Integer> list = new ArrayList<>();
				for (Integer value : topics.get(i).values()) {

					list.add(value);
				}
				quantityQuestionList.add(list);
			}
			
			// handle to get topicId, levelId, questionQuantity
			for (int i = 0; i < topicLevelIdList.size(); i++) {
				
				Set<String> topicLevelIdKeySet = topicLevelIdList.get(i);
				List<Integer> questionQuantitySet = quantityQuestionList.get(i);
				
				Iterator<String> iteratorTopicLevelId =  topicLevelIdKeySet.iterator();
				Iterator<Integer> iteratorQuestionQuantity = questionQuantitySet.iterator();

				while (iteratorTopicLevelId.hasNext() && iteratorQuestionQuantity.hasNext()) {
					
					String topicLevelId = iteratorTopicLevelId.next();
					Integer topicLevelIdLength = topicLevelId.length();
					
					// get last char
					String levelId = topicLevelId.substring(topicLevelIdLength - 1);
					
					// get string which remove 2 char at last
					String topicId = topicLevelId.substring(0, topicLevelIdLength - 2);
					
					Integer questionQuantity = iteratorQuestionQuantity.next();

					List<QuestionModel> questionList = 
							questionService.getRandomQuetion(topicId, levelId,
									questionTypeId, questionQuantity);
					
					questionExamList.addAll(questionList);
				}
			}
		}
		
		return questionExamList;
	}
	
	public void addExam(ExamBean examBean) {
		
		List<ExamQuestionModel> examQuestionList = new ArrayList<>();
		List<Map<String, Integer>> examQuestionIdList = examBean.getExamQuestionIdList();
		ExamModel examModel = convertExamBeanToModel(examBean);
		String examId = examModel.getExamId();
		
		examDAO.addExam(examModel);

		for (Map<String, Integer> map : examQuestionIdList) {
			
			ExamQuestionModel examQuestionModel = new ExamQuestionModel();
			Set<String> keySet = map.keySet();
			Object[] keyArray = keySet.toArray();
			
			examQuestionModel.setExamId(examId);
			examQuestionModel.setQuestionId(keyArray[0].toString());
			examQuestionModel.setScore(map.get(keyArray[0]));
			
			examQuestionList.add(examQuestionModel);
		}
		
		examQuestionService.addExamQuestionWithBatch(examQuestionList);
	}
	
	public byte[] convertHTMLToPDF(String html) {

        ByteArrayOutputStream pdfOutputStream = new ByteArrayOutputStream();
        HtmlConverter.convertToPdf(html, pdfOutputStream);
        
        return pdfOutputStream.toByteArray();
	}
	
	private ExamModel convertExamBeanToModel(ExamBean examBean) {
		
		ExamModel examModel = new ExamModel();
		
		BeanUtils.copyProperties(examBean, examModel);
		examModel.setExamId(initExamId(examBean));
		examModel.setQuestionQuantity(examBean.getExamQuestionIdList().size());
		
		return examModel;
	}
	
	private String initExamId(ExamBean examBean) {
		
		return examBean.getCreatedLecturerId() + Constants.SYMBOL_UNDERSCORE
				+ examBean.getSubjectId() + Constants.SYMBOL_UNDERSCORE
				+ examBean.getTimeCreation().getTime();
	}
}
