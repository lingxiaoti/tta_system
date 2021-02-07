package com.sie.saaf.base.shiro.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * BaseRoleMenuEntity_HI Entity Object
 * Tue Dec 12 19:08:06 CST 2017  Auto Generate
 */
@Entity
@Table(name = "base_role_menu")
public class BaseRoleMenuEntity_HI {
    private Integer roleMenuId; //主键ID
    private Integer menuId; //节点标识,引用功能，只能引用功能，不能引用菜单
    private Integer roleId; //角色Id
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date creationDate; //创建日期
    private Integer createdBy; //创建人
    private Integer lastUpdatedBy; //更新人
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate; //更新日期
    private Integer versionNum; //版本号
    private Integer lastUpdateLogin; //last_update_login
    private Integer operatorUserId;

	public void setRoleMenuId(Integer roleMenuId) {
		this.roleMenuId = roleMenuId;
	}

	@Id	
	@SequenceGenerator(name = "SEQ_BASE_ROLE_MENU", sequenceName = "SEQ_BASE_ROLE_MENU", allocationSize = 1)
  	@GeneratedValue(generator = "SEQ_BASE_ROLE_MENU", strategy = GenerationType.SEQUENCE)
	@Column(name = "role_menu_id", nullable = false, length = 11)	
	public Integer getRoleMenuId() {
		return roleMenuId;
	}

	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}

	@Column(name = "menu_id", nullable = true, length = 11)	
	public Integer getMenuId() {
		return menuId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	@Column(name = "role_id", nullable = true, length = 11)	
	public Integer getRoleId() {
		return roleId;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name = "creation_date", nullable = true, length = 0)	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "created_by", nullable = true, length = 11)	
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Column(name = "last_updated_by", nullable = true, length = 11)	
	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	@Column(name = "last_update_date", nullable = true, length = 0)	
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	@Version
	@Column(name = "version_num", nullable = true, length = 11)	
	public Integer getVersionNum() {
		return versionNum;
	}

	public void setLastUpdateLogin(Integer lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	@Column(name = "last_update_login", nullable = true, length = 11)	
	public Integer getLastUpdateLogin() {
		return lastUpdateLogin;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
