/**
 * Time creation: Dec 3, 2022, 3:52:37 PM
 * 
 * Package name: com.exam.model
 */
package com.exam.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.exam.common.Constants;

/**
 * @author Tien Tran
 *
 * class SubDepartmentModel
 */
@Entity
@Table(name = "to_bo_mon")
public class SubDepartmentModel implements Serializable {
	
	/**
	 * serialVersionUID type long
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "MA_BO_MON")
	private String subDepartmentId;
	@Column(name = "TEN_BO_MON")
	private String subDepartmentName;
	@Column(name = "MA_KHOA")
	private String departmentId;
	@Column(name = "DA_XOA")
	private Byte deleted = Constants.NOT_DELETED;
	@ManyToOne
	@JoinColumn(name = "MA_KHOA", insertable=false, updatable=false)
	private DepartmentModel department;

	public String getSubDepartmentId() {
		return subDepartmentId;
	}

	public void setSubDepartmentId(String subDepartmentId) {
		this.subDepartmentId = subDepartmentId;
	}

	public String getSubDepartmentName() {
		return subDepartmentName;
	}

	public void setSubDepartmentName(String subDepartmentName) {
		this.subDepartmentName = subDepartmentName;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public Byte getDeleted() {
		return deleted;
	}

	public void setDeleted(Byte deleted) {
		this.deleted = deleted;
	}

	public DepartmentModel getDepartment() {
		return department;
	}

	public void setDepartment(DepartmentModel department) {
		this.department = department;
	}
}
