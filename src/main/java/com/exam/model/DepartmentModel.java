/**
 * Time creation: Dec 1, 2022, 10:31:07 PM
 * 
 * Package name: com.exam.model
 */
package com.exam.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.exam.common.Constants;

/**
 * @author Tien Tran
 *
 * class Department
 */
@Entity
@Table(name = "khoa")
public class DepartmentModel implements Serializable {

	/**
	 * serialVersionUID type long
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "MA_KHOA")
	private String departmentId;
	@Column(name = "TEN_KHOA")
	private String departmentName;
	@Column(name = "DA_XOA")
	private Byte deleted = Constants.NOT_DELETED;

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public Byte getDeleted() {
		return deleted;
	}

	public void setDeleted(Byte deleted) {
		this.deleted = deleted;
	}

	@Override
	public int hashCode() {
		return Objects.hash(departmentId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DepartmentModel other = (DepartmentModel) obj;
		return Objects.equals(departmentId, other.departmentId);
	}

}
