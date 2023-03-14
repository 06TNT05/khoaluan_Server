/**
 * Time creation: Dec 3, 2022, 7:47:04 PM
 * 
 * Package name: com.exam.model
 */
package com.exam.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.exam.common.Constants;

/**
 * @author Tien Tran
 *
 * class OfficeModel
 */
@Entity
@Table(name = "chuc_vu")
public class OfficeModel implements Serializable{
	
	/**
	 * serialVersionUID type long
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "MA_CHUC_VU")
	private String officeId;
	@Column(name = "TEN_CHUC_VU")
	private String officeName;
	@Column(name = "DA_XOA")
	private Byte deleted = Constants.NOT_DELETED;

	public String getOfficeId() {
		return officeId;
	}

	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}

	public String getOfficeName() {
		return officeName;
	}

	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}

	public Byte getDeleted() {
		return deleted;
	}

	public void setDeleted(Byte deleted) {
		this.deleted = deleted;
	}
}
