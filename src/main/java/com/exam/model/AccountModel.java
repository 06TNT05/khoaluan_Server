/**
 * Time creation: Dec 4, 2022, 2:30:58 PM
 * 
 * Package name: com.exam.model
 */
package com.exam.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * @author Tien Tran
 *
 * class AccountModel
 */
@Entity
@Table(name = "tai_khoan")
public class AccountModel implements Serializable {

	/**
	 * serialVersionUID type long
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "TAI_KHOAN")
	private String email;
	@Column(name = "MAT_KHAU")
	private String password;
	@Column(name = "PHAN_QUYEN")
	private Integer roleId;
	@OneToOne
	@JoinColumn(name = "PHAN_QUYEN", insertable = false, updatable = false) // name = its columns
	private RoleModel role;

	public AccountModel() {
	}

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

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public RoleModel getRole() {
		return role;
	}

	public void setRole(RoleModel role) {
		this.role = role;
	}
}
