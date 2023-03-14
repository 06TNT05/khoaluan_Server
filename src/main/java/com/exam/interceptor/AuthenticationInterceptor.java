/**
 * Time creation: Feb 8, 2023, 12:37:07 PM
 *
 * Pakage name: com.exam.interceptor
 */
package com.exam.interceptor;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.exam.common.Constants;
import com.exam.common.JwtTokenUtil;
import com.exam.interfaces.RoleAuthorized;

/**
 * @author Tien Tran
 *
 * class AuthenticationInterceptor
 */
@Component
public class AuthenticationInterceptor implements HandlerInterceptor {
	
	@Autowired
    private JwtTokenUtil jwtUtil;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		String tokenRequest = request.getParameter("token");

		if (tokenRequest == null || !tokenRequest.startsWith("Bearer ")) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
			return false;
		}

		String token = tokenRequest.replace(Constants.BEARER, Constants.STRING_EMPTY);
		
		HandlerMethod handlerMethod = null;
		
		try {
			handlerMethod = (HandlerMethod) handler;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		RoleAuthorized roleAuthorized = handlerMethod.getMethodAnnotation(RoleAuthorized.class);
		
		if (roleAuthorized != null) {
			String role = (String) request.getParameter("role");
			List<String> roles = Arrays.asList(roleAuthorized.value());
			if (role == null || !roles.contains(role)) {
				response.setStatus(HttpStatus.FORBIDDEN.value());
				return false;
			}
		}
		
		try {
			
			if (!jwtUtil.validateAccessToken(token)) {
				throw new Exception("Invalid token");
			}

			return true;
		} catch (Exception e) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
			return false;
		}
	}

}