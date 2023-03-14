/**
 * Time creation: Dec 4, 2022, 4:25:09 PM
 * 
 * Package name: com.exam.model
 */
package com.exam.model;

import java.io.Serializable;
import java.util.Objects;

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
 * class SubjectModel
 */
@Entity
@Table(name = "hoc_phan")
public class SubjectModel implements Serializable {
	
	/**
	 * serialVersionUID type long
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "MA_HOC_PHAN")
	private String subjectId;
	@Column(name = "TEN_HOC_PHAN")
	private String subjectName;
	@Column(name = "SO_TIN_CHI")
	private Byte creditQuantity;
	@Column(name = "MA_BO_MON")
	private String subDepartmentId;
	@Column(name = "DA_XOA")
	private Byte deleted = Constants.NOT_DELETED;
	@ManyToOne
	@JoinColumn(name = "MA_BO_MON", insertable = false, updatable = false)
	private SubDepartmentModel subDepartment;

	public SubjectModel() {

	}
	
	public SubjectModel(String subjectId, String subjectName) {
		this.subjectId = subjectId;
		this.subjectName = subjectName;
	}

	public String getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public Byte getCreditQuantity() {
		return creditQuantity;
	}

	public void setCreditQuantity(Byte creditQuantity) {
		this.creditQuantity = creditQuantity;
	}

	public String getSubDepartmentId() {
		return subDepartmentId;
	}

	public void setSubDepartmentId(String subDepartmentId) {
		this.subDepartmentId = subDepartmentId;
	}

	public Byte getDeleted() {
		return deleted;
	}

	public void setDeleted(Byte deleted) {
		this.deleted = deleted;
	}

	public SubDepartmentModel getSubDepartment() {
		return subDepartment;
	}

	public void setSubDepartment(SubDepartmentModel subDepartment) {
		this.subDepartment = subDepartment;
	}

	@Override
	public int hashCode() {
		return Objects.hash(subjectId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SubjectModel other = (SubjectModel) obj;
		return Objects.equals(subjectId, other.subjectId);
	}
}
