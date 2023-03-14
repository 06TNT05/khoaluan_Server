/**
 * Time creation: Mar 7, 2023, 8:35:29 PM
 *
 * Pakage name: com.exam.bean
 */
package com.exam.bean;

import java.io.Serializable;

/**
 * @author Tien Tran
 *
 * class AccountBean
 */
public class AccountBean implements Serializable {

	/**
	 * serialVersionUID type long
	 */
	private static final long serialVersionUID = 1L;

	private String email;
	private String currentPassword;
	private String newPassword;
	private String confirmPassword;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCurrentPassword() {
		return currentPassword;
	}

	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
}
