/**
 * Time creation: Feb 4, 2023, 8:08:05 PM
 * 
 * Package name: com.exam.bean
 */
package com.exam.bean;

import java.io.Serializable;

/**
 * @author Tien Tran
 *
 * class LoginBean
 */
public class LoginBean implements Serializable {
	
	/**
	 * serialVersionUID type long
	 */
	private static final long serialVersionUID = 1L;
	private String email;
	private String password;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
