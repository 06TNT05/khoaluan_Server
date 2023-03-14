/**
 * Time creation: Feb 13, 2023, 1:55:43 PM
 *
 * Pakage name: com.exam.model
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
 * class LevelModel
 */
@Entity
@Table(name = "muc_do")
public class LevelModel implements Serializable {

	/**
	 * serialVersionUID type long
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "MA_MUC_DO")
	private String levelId;
	@Column(name = "TEN_MUC_DO")
	private String levelName;
	@Column(name = "DA_XOA")
	private Byte deleted = Constants.NOT_DELETED;

	public String getLevelId() {
		return levelId;
	}

	public void setLevelId(String levelId) {
		this.levelId = levelId;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public Byte getDeleted() {
		return deleted;
	}

	public void setDeleted(Byte deleted) {
		this.deleted = deleted;
	}
}
