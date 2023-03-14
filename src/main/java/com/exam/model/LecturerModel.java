/**
 * Time creation: Dec 3, 2022, 9:01:40 PM
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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.exam.common.Constants;

/**
 * @author Tien Tran
 *
 * class LecturerModel
 */
@Entity
@Table(name = "giang_vien")
public class LecturerModel implements Serializable {
	
	/**
	 * serialVersionUID type long
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "MA_GV")
	private String lecturerId;
	@Column(name = "TEN")
	private String firstName;
	@Column(name = "HO")
	private String lastName;
	@Column(name = "HO_TEN")
	private String lecturerName;
	@Column(name = "SDT")
	private String phoneNumber;
	@Column(name = "EMAIL")
	private String email;
	@Column(name = "HINH_ANH")
	private String imageSrc;
	@Column(name = "MA_BO_MON")
	private String subDepartmentId;
	@Column(name = "MA_CHUC_VU")
	private String officeId;
	@Column(name = "DA_XOA")
	private Byte deleted = Constants.NOT_DELETED;
	@OneToOne
	@JoinColumn(name = "MA_BO_MON", insertable = false, updatable = false)
	private SubDepartmentModel subDepartment;
	@OneToOne
	@JoinColumn(name = "MA_CHUC_VU", insertable = false, updatable = false)
	private OfficeModel office;
	@OneToOne
	@JoinColumn(name = "EMAIL", insertable = false, updatable = false)
	private AccountModel account;

	public LecturerModel() {
	}

	public String getLecturerId() {
		return lecturerId;
	}

	public void setLecturerId(String lecturerId) {
		this.lecturerId = lecturerId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getLecturerName() {
		return lecturerName;
	}

	public void setLecturerName(String lecturerName) {
		this.lecturerName = lecturerName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getImageSrc() {
		return imageSrc;
	}

	public void setImageSrc(String imageSrc) {
		this.imageSrc = imageSrc;
	}

	public String getSubDepartmentId() {
		return subDepartmentId;
	}

	public void setSubDepartmentId(String subDepartmentId) {
		this.subDepartmentId = subDepartmentId;
	}

	public String getOfficeId() {
		return officeId;
	}

	public void setOfficeId(String officeId) {
		this.officeId = officeId;
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

	public OfficeModel getOffice() {
		return office;
	}

	public void setOffice(OfficeModel office) {
		this.office = office;
	}

	public AccountModel getAccount() {
		return account;
	}

	public void setAccount(AccountModel account) {
		this.account = account;
	}

	@Override
	public int hashCode() {
		return Objects.hash(email, lecturerId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LecturerModel other = (LecturerModel) obj;
		return Objects.equals(email, other.email) && Objects.equals(lecturerId, other.lecturerId);
	}
}
