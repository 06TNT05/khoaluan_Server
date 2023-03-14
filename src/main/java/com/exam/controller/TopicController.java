/**
 * Time creation: Dec 4, 2022, 11:46:11 PM
 * 
 * Package name: com.exam.controller
 */
package com.exam.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.exam.model.TopicModel;
import com.exam.service.TopicService;

/**
 * @author Tien Tran
 *
 * class TopicController
 */
@CrossOrigin(maxAge = 3600)
@RestController
public class TopicController {

	@Autowired
	private TopicService topicService;
	
	@RequestMapping(value = "/api/topic/find.all", method = RequestMethod.GET)
	public ResponseEntity<List<TopicModel>> findAllTopics() {
		
		List<TopicModel> topicList = topicService.findAllObjects();
		
		if (topicList == null) {
			return new ResponseEntity<List<TopicModel>>(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<List<TopicModel>>(topicList, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/api/topic/find.id", method = RequestMethod.GET)
	public ResponseEntity<TopicModel> findById(@RequestParam("topicId") String topicId) {
		
		TopicModel topic = topicService.findById(topicId);
		
		if (topic == null) {
			return new ResponseEntity<TopicModel>(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<TopicModel>(topic, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/api/topic/find.subdepartmentid", method = RequestMethod.GET)
	public ResponseEntity<List<TopicModel>> findBySubDepartmentId(
			@RequestParam("subDepartmentId") String subDepartmentId) {
		
		List<TopicModel> topicList = topicService.findBySubDepartmentId(subDepartmentId);
		
		if (topicList == null) {
			return new ResponseEntity<List<TopicModel>>(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<List<TopicModel>>(topicList, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/api/topic/add", method = RequestMethod.POST)
	public ResponseEntity<Void> addTopic(@ModelAttribute("TopicModel") TopicModel topic) {
		
		if (topicService.isObjectExist(topic)) {
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}
		
		topicService.addObject(topic);
		
        return new ResponseEntity<Void>(HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/api/topic/update", method = RequestMethod.POST)
	public ResponseEntity<Void> updateTopic(@ModelAttribute("TopicModel") TopicModel topic) {
		
		TopicModel topicbyID = topicService.findById(topic.getTopicId());
		
		if (topicbyID == null) {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
		
		topicService.updateObject(topic);
		
        return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@RequestMapping(value = "/api/topic/remove", method = RequestMethod.POST)
	public ResponseEntity<Void> removeTopic(@RequestParam("topicIdArray") String[] topicIdArray) {
		
		topicService.removeObjects(topicIdArray);
		
        return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@RequestMapping(value = "/api/topic/changeStatus", method = RequestMethod.POST)
	public ResponseEntity<Void> changeTopicStatus(@RequestParam("topicId") String topicId) {
		
		TopicModel topic = topicService.findById(topicId);
		
		topicService.changeObjectStatus(topic);
		
        return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@RequestMapping(value = "/api/topic/find.subjectId", method = RequestMethod.GET)
	public ResponseEntity<List<TopicModel>> findBySubjectId(@RequestParam("subjectId") String subjectId) {
		
		List<TopicModel> topicList = topicService.findBySubjectId(subjectId);
		
		if (topicList == null) {
			return new ResponseEntity<List<TopicModel>>(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<List<TopicModel>>(topicList, HttpStatus.OK);
	}
}
