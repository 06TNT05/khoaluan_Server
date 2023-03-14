/**
 * Time creation: Feb 14, 2023, 12:43:33 PM
 *
 * Pakage name: com.exam.service
 */
package com.exam.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFPicture;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.exam.bean.QuestionBean;
import com.exam.common.Constants;
import com.exam.common.FileCommon;
import com.exam.dao.QuestionDAO;
import com.exam.model.AnswerModel;
import com.exam.model.QuestionModel;

/**
 * @author Tien Tran
 *
 * class QuestionService
 */
@Service
public class QuestionService {
	
	@Autowired
	private FileCommon fileCommon;
	@Autowired
	private QuestionDAO questionDAO;

	public void setQuestionDAO(QuestionDAO questionDAO) {
		this.questionDAO = questionDAO;
	}
	
	public void setFileCommon(FileCommon fileCommon) {
		this.fileCommon = fileCommon;
	}

	public List<QuestionModel> findAllQuestions() {
		
		return questionDAO.findAllQuestions();
	}
	
	public List<QuestionModel> findAllQuestionsPagination(QuestionBean questionBean) {
		
		String role = questionBean.getRole();
		String searchString = questionBean.getSearchString()
							.replaceAll(Constants.SYMBOL_PERCENT,
									Constants.SYMBOL_OPENING_SQUARE_BRACKETS
									+ Constants.SYMBOL_PERCENT
									+ Constants.SYMBOL_OPENING_SQUARE_BRACKETS);
		Integer page = questionBean.getPage();
		String lecturerId = questionBean.getLecturerId();
		
		if (Constants.ROLE_ADMIN.equals(role)) {
			
			return questionDAO.findAllQuestionsPagination(page, searchString);
		}
		
		return questionDAO.findByLecturerId(page, searchString, lecturerId);
	}
	
	public void addQuestion(QuestionBean questionBean, ServletContext context) {
		
		QuestionModel questionModel = convertQuestionBeanToQuestionModel(questionBean);
		
		Set<AnswerModel> answerList = convertQuestionBeanToAnswerModel(questionBean);
		
		questionModel.setAnswers(answerList);
		
		questionDAO.addQuestion(questionModel);
	}
	
	public QuestionModel findById(String questionId) {
		
		return questionDAO.findById(questionId);
	}
	
	public void updateQuestion(QuestionBean questionBean, ServletContext context) {
		
		QuestionModel questionModel = convertQuestionBeanToQuestionModel(questionBean);
		
		Set<AnswerModel> answerList = convertQuestionBeanToAnswerModel(questionBean);
		
		questionModel.setAnswers(answerList);
		
		questionDAO.updateQuestion(questionModel);
	}
	
	public void removeQuestions(String[] questionIdArray) {
		
		StringBuilder builder = new StringBuilder();
		builder.append("UPDATE QuestionModel q SET q.deleted = :status WHERE q.questionId IN (");
		
		for (String id : questionIdArray) {
			
			builder.append("'" + id + "',");
		}
		
		builder.deleteCharAt(builder.toString().length() - 1);
		builder.append(Constants.SYMBOL_CLOSING_BRACKETS);

		questionDAO.removeObjects(builder.toString(), Constants.DELETED);
	}
	
	public void changeQuestionStatus(String questionId) {
		
		QuestionModel questionModel = findById(questionId);
		
		if (questionModel.getDeleted() == Constants.DELETED) {
			questionModel.setDeleted(Constants.NOT_DELETED);
		} else {
			questionModel.setDeleted(Constants.DELETED);
		}
		
		questionDAO.updateQuestion(questionModel);
	}
	
	public Long getTotalRecord(QuestionBean questionBean) {

		String role = questionBean.getRole();
		String searchString = questionBean.getSearchString()
				.replaceAll(Constants.SYMBOL_PERCENT,
						Constants.SYMBOL_OPENING_SQUARE_BRACKETS
						+ Constants.SYMBOL_PERCENT
						+ Constants.SYMBOL_OPENING_SQUARE_BRACKETS);
		String lecturerId = questionBean.getLecturerId();
		
		if (Constants.ROLE_ADMIN.equals(role)) {
			
			return questionDAO.getTotalRecord(searchString);
		}
		
		return questionDAO.getTotalRecord(searchString, lecturerId);
	}
	
	private QuestionModel convertQuestionBeanToQuestionModel(QuestionBean questionBean) {
		
		String questionId = initQuestionId(questionBean);
		String questionContent = questionBean.getQuestionContent();
		String questionTypeId = questionBean.getQuestionTypeId();
		String answerCorrect = Constants.ID_ESSAY.equals(questionTypeId)
							   ? questionId + Constants.SYMBOL_UNDERSCORE + Constants.ID_ESSAY 
							   : questionId + Constants.SYMBOL_UNDERSCORE + questionBean.getAnswerCorrect();
		Date timeCreation = questionBean.getTimeCreation();
		String lecturerId = questionBean.getLecturerId();
		String topicId = questionBean.getTopicId();
		String levelId = questionBean.getLevelId();
		
		QuestionModel questionModel = new QuestionModel();
		
		questionModel.setQuestionId(questionId);
		questionModel.setQuestionContent(questionContent);
		questionModel.setAnswerCorrect(answerCorrect);
		questionModel.setTimeCreation(timeCreation);
		questionModel.setLecturerId(lecturerId);
		questionModel.setTopicId(topicId);
		questionModel.setQuestionTypeId(questionTypeId);
		questionModel.setLevelId(levelId);
		
		// Reuse questionId for answer
		questionBean.setQuestionId(questionId);
		
		return questionModel;
	}
	
	private Set<AnswerModel> convertQuestionBeanToAnswerModel(QuestionBean questionBean) {
		
		Set<AnswerModel> answerList = new HashSet<>();
		
		String questionTypeId = questionBean.getQuestionTypeId();
		
		if (Constants.ID_MULTI_CHOICE_OPTION.equals(questionTypeId)) {
			
			String optionA = questionBean.getOptionA();
			String optionB = questionBean.getOptionB();
			String optionC = questionBean.getOptionC();
			String optionD = questionBean.getOptionD();
			
			// optionA
			AnswerModel answerA = initAnswerModel(questionBean, Constants.OPTION_A, optionA);
			answerList.add(answerA);
			
			// optionB
			AnswerModel answerB = initAnswerModel(questionBean, Constants.OPTION_B, optionB);
			answerList.add(answerB);
			
			// optionC
			AnswerModel answerC = initAnswerModel(questionBean, Constants.OPTION_C, optionC);
			answerList.add(answerC);
			
			// optionD
			AnswerModel answerD = initAnswerModel(questionBean, Constants.OPTION_D, optionD);
			answerList.add(answerD);
		} else {
			
			String answerContent = questionBean.getAnswerEssay();
			
			AnswerModel answerEssay = initAnswerModel(questionBean, Constants.ID_ESSAY, answerContent);
			answerList.add(answerEssay);
		}
		
		return answerList;
	}
	
	private AnswerModel initAnswerModel(QuestionBean questionBean, String answerId, String answerContent) {
		
		AnswerModel answerModel = new AnswerModel();
		
		answerModel.setAnswerId(initQuestionId(questionBean) + Constants.SYMBOL_UNDERSCORE + answerId);
		answerModel.setQuestionId(questionBean.getQuestionId());
		answerModel.setAnswerContent(answerContent);

		return answerModel;
	}
	
	private String initQuestionId(QuestionBean questionBean) {
		
		Byte action = questionBean.getAction();
		// if action = 0 (add), generate questionId. Else get questionId in Bean 
		return action == null ? (questionBean.getLecturerId() + Constants.SYMBOL_UNDERSCORE
				+ questionBean.getTopicId() + Constants.SYMBOL_UNDERSCORE
				+ questionBean.getTimeCreation().getTime())
				: questionBean.getQuestionId();
	}
	
	public String getPathToShowImage(HttpServletRequest request, ServletContext context, String pathToDataBase) {
		
		String schemaString = request.getScheme();
		String hostName = request.getServerName();
		String servletName = context.getContextPath();
		int port = request.getServerPort();
		String pathToShowImage = schemaString + "://" + hostName + Constants.SYMBOL_COLON + port + servletName + pathToDataBase;
		
		return pathToShowImage;
	}
	
	public List<QuestionModel> getRandomQuetion(String topicId, String levelId,
			String questionTypeId, Integer questionQuantity) {
		
		return questionDAO.getRandomQuetion(topicId, levelId, questionTypeId, questionQuantity);
	}

	public void uploadQuestionFile(QuestionBean questionBean, HttpServletRequest request,
			ServletContext context) throws IOException, InvalidFormatException {
		
		String extension = Constants.STRING_EMPTY;
		String html = Constants.STRING_EMPTY;
		String questionDetail = Constants.STRING_EMPTY;
		List<CommonsMultipartFile> fileList = questionBean.getFileList();
		Integer count = 0;
		
		for (CommonsMultipartFile file : fileList) {
			
			extension = FilenameUtils.getExtension(file.getOriginalFilename());
			
			if (extension.equals("xls") || extension.equals("xlsx")) {
				
				readDataExcel(questionBean, file);
				continue;
			}
			
			if (extension.equals("docx")) {
				
				html = readDataWord(file, request, context);
				
				// have format {topicId|levelId}
				questionDetail = getQuestionDetail(file, request, context);
				
				String[] questionDetailArray = questionDetail.split(Constants.SYMBOL_VERTICAL_LINE);
					
				questionBean.setTopicId(questionDetailArray[0]);
				questionBean.setTimeCreation(new Date(questionBean.getTimeCreation().getTime()+ ++count));
				QuestionModel questionModel = convertQuestionBeanToQuestionModel(questionBean);
				questionModel.setQuestionContent(html);
				questionModel.setQuestionTypeId(Constants.ID_ESSAY);
				questionModel.setAnswerCorrect(questionModel.getQuestionId() 
						+ Constants.SYMBOL_UNDERSCORE + Constants.ID_ESSAY);
				questionModel.setLevelId(questionDetailArray[1]);
				
				questionDAO.addQuestion(questionModel);
				
				continue;
			}
		}
	}
	
	private void readDataExcel(QuestionBean questionBean, CommonsMultipartFile file) throws IOException {
		
		Workbook workbook = null;
		Integer count = 0;
		
		List<QuestionModel> questionList = new ArrayList<>();
		Integer serial;
		String questionId = Constants.STRING_EMPTY;
		String questionContent = Constants.STRING_EMPTY;;
		String answerCorrect = Constants.STRING_EMPTY;;
		Date timeCreation = questionBean.getTimeCreation();
		Long timeCreationMiliseconds = timeCreation.getTime();
		String subjectId;
		String lecturerId = questionBean.getLecturerId();
		String topicId;
		String questionTypeId;
		String levelId;
		String optionA;
		String optionB;
		String optionC;
		String optionD;
		
		InputStream inputStream = file.getInputStream();
		
		try {
			
			workbook = WorkbookFactory.create(inputStream);

			Sheet sheet = workbook.getSheetAt(Constants.SHEET_0);
			
			for (Row row : sheet) {
				
				// skip first row
				if (count++ == 0) continue;
				
				QuestionModel questionModel = new QuestionModel();
				
				// cell serial
				Cell cell_0 = row.getCell(0);
				// cell question content
				Cell cell_1 = row.getCell(1);
				// cell answer A
				Cell cell_2 = row.getCell(2);
				// cell answer B
				Cell cell_3 = row.getCell(3);
				// cell answer C
				Cell cell_4 = row.getCell(4);
				// cell answer D
				Cell cell_5 = row.getCell(5);
				// cell answer correct
				Cell cell_6 = row.getCell(6);
				// cell topicId
				Cell cell_7 = row.getCell(7);
				// cell questionTypeId
				Cell cell_8 = row.getCell(8);
				// cell levelId
				Cell cell_9 = row.getCell(9);
				
				if (cell_0 == null || cell_1 == null || cell_2 == null || cell_3 == null || cell_4 == null
					|| cell_5 == null || cell_6 == null || cell_7 == null || cell_8 == null || cell_9 == null) {
					
					break;
				}
				
				serial = (int) cell_0.getNumericCellValue();
				questionContent = getCellStringValue(cell_1);
				optionA = getCellStringValue(cell_2);
				optionB = getCellStringValue(cell_3);
				optionC = getCellStringValue(cell_4);
				optionD = getCellStringValue(cell_5);
				answerCorrect = getCellStringValue(cell_6);
				topicId = getCellStringValue(cell_7);
				questionTypeId = getCellStringValue(cell_8);
				levelId = getCellStringValue(cell_9);
				
				questionId = lecturerId + Constants.SYMBOL_UNDERSCORE + topicId
						+ Constants.SYMBOL_UNDERSCORE + (timeCreationMiliseconds + serial);
				
				// set value for question model
				questionModel.setQuestionId(questionId);
				questionModel.setQuestionContent(questionContent);
				questionModel.setAnswerCorrect(questionId + Constants.SYMBOL_UNDERSCORE + answerCorrect);
				questionModel.setTimeCreation(timeCreation);
				questionModel.setLecturerId(lecturerId);
				questionModel.setTopicId(topicId);
				questionModel.setQuestionTypeId(questionTypeId);
				questionModel.setLevelId(levelId);
					
				AnswerModel answerA = new AnswerModel();
				answerA.setAnswerId(questionId + Constants.SYMBOL_UNDERSCORE + Constants.OPTION_A);
				answerA.setAnswerContent(optionA);
				answerA.setQuestionId(questionId);
				
				AnswerModel answerB = new AnswerModel();
				answerB.setAnswerId(questionId + Constants.SYMBOL_UNDERSCORE + Constants.OPTION_B);
				answerB.setAnswerContent(optionB);
				answerB.setQuestionId(questionId);
				
				AnswerModel answerC = new AnswerModel();
				answerC.setAnswerId(questionId + Constants.SYMBOL_UNDERSCORE + Constants.OPTION_C);
				answerC.setAnswerContent(optionC);
				answerC.setQuestionId(questionId);
				
				AnswerModel answerD = new AnswerModel();
				answerD.setAnswerId(questionId + Constants.SYMBOL_UNDERSCORE + Constants.OPTION_D);
				answerD.setAnswerContent(optionD);
				answerD.setQuestionId(questionId);
				
				Set<AnswerModel> answerList = new HashSet<>();
				
				answerList.add(answerA);
				answerList.add(answerB);
				answerList.add(answerC);
				answerList.add(answerD);
				
				questionModel.setAnswers(answerList);
				
				questionList.add(questionModel);
				
			}
			
			questionDAO.addQuestionWithBatch(questionList);
			
		} catch (Exception e) {
			
			e.printStackTrace();
		} finally {
			
			inputStream.close();
			workbook.close();
		}
	}
	
	private String readDataWord(CommonsMultipartFile file, HttpServletRequest request, ServletContext context)
			throws IOException, InvalidFormatException {
		
		String html = parseWordFileToHtml(file, request, context);
		
		return html;
	}
	
	private String getQuestionDetail(CommonsMultipartFile file,
			HttpServletRequest request, ServletContext context)
					throws InvalidFormatException, IOException {
		
    	try (XWPFDocument docx = new XWPFDocument(file.getInputStream())) {
			
			for (XWPFParagraph paragraph : docx.getParagraphs()) {
				
				return paragraph.getText();
			}
		}
	
    	return Constants.STRING_EMPTY;
	}
	
	private String parseWordFileToHtml(CommonsMultipartFile file,
			HttpServletRequest request, ServletContext context)
					throws InvalidFormatException, IOException {

		Integer count = 0;
		
    	try (XWPFDocument docx = new XWPFDocument(file.getInputStream())) {
			StringBuilder sb = new StringBuilder();
			
			for (XWPFParagraph paragraph : docx.getParagraphs()) {
				
				if (count++ == 0) continue;
				
		        // Check if the paragraph has numbering or bullet
		        String numFmt = paragraph.getNumFmt();
		        if (numFmt != null) {
		            sb.append("<p>");
		            sb.append("<" + numFmt + ">");
		        } else {
		            sb.append("<p>");
		        }
			    
			    XWPFRunLoop:
			    for (XWPFRun run : paragraph.getRuns()) {
			    	
			    	for (XWPFPicture picture : run.getEmbeddedPictures()) {
			        	
			        	sb.append(parsePictureToHtml(file, picture, request, context));
			        	continue XWPFRunLoop;
			        }
			    	
			        sb.append("<span>");
			        String text = run.getText(0) != null ? run.getText(0) : "";
			        
			        sb.append(text);
			        sb.append("</span>");
			    }
			    sb.append("</p>");
			}
			
			return sb.toString().replaceAll("<p></p>", Constants.STRING_EMPTY);
		}
	}
	
	private String parsePictureToHtml(CommonsMultipartFile file, XWPFPicture picture,
			HttpServletRequest request, ServletContext context)
			throws InvalidFormatException, IOException {
		
		StringBuilder sb = new StringBuilder();
		String imageFileName = picture.getPictureData().getFileName();
		
		String pathToUpload = 
    			fileCommon.getPathToUpload(imageFileName,
    					Constants.FILE_ATTACH_QUESTION_DIRECTORY, context);
    	String pathToDataBase = Constants.FILE_ATTACH_QUESTION_DIRECTORY + imageFileName;
    	
    	fileCommon.uploadFile(picture.getPictureData().getData(), pathToUpload);
    	
    	String pathToShowImage = getPathToShowImage(request, context, pathToDataBase);
        // Generate the HTML code
        sb.append("<img src='" + pathToShowImage + "' style='width: 100%'>");
        
        return sb.toString();
	}
	
	private String getCellStringValue(Cell cell) {
		
		return cell.getCellType() == CellType.STRING
				? cell.getStringCellValue()
				: cell.getCellType() == CellType.NUMERIC
				? String.valueOf(cell.getNumericCellValue())
				: cell.getStringCellValue();
	}
}
