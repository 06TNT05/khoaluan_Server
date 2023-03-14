/**
 * Time creation: Feb 4, 2023, 8:05:02 PM
 * 
 * Package name: com.exam.controller
 */
package com.exam.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.exam.bean.LoginBean;
import com.exam.common.Constants;
import com.exam.common.JwtTokenUtil;
import com.exam.model.LecturerModel;
import com.exam.service.LecturerService;

/**
 * @author Tien Tran
 *
 * class LoginController
 */
@CrossOrigin(maxAge = 3600)
@RestController
public class LoginController {

	@Autowired
	private LecturerService lecturerService;
	
	@Autowired
    private JwtTokenUtil jwtUtil;

	@RequestMapping(value = "/api/login", method = RequestMethod.POST)
	public ResponseEntity<Object> login(@ModelAttribute("LoginBean") LoginBean loginBean, HttpSession session) {

		LecturerModel user = lecturerService.findUser(loginBean.getEmail(), loginBean.getPassword());
		
		if (user == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).contentType(MediaType.valueOf("application/json;charset=UTF-8")).body(Constants.MESSAGE_LOGIN_FAILED);
		}
		
		String token = jwtUtil.generateAccessToken(user);

		Map<String, Object> map = new HashedMap<>();
		map.put("token", token);
		map.put("user", user);
		return ResponseEntity.ok(map);
	}
}
