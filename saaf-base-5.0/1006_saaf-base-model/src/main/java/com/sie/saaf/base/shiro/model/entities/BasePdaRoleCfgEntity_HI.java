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
 * BasePdaRoleCfgEntity_HI Entity Object
 * Tue Mar 27 15:27:42 CST 2018  Auto Generate
 */
@Entity
@Table(name = "BASE_PDA_ROLE_CFG")
public class BasePdaRoleCfgEntity_HI {
    private Integer pdaRoleCfgId; //主键
    private Integer roleId; //角色权限ID
    private Integer organizationId; //库存组织ID
    private String channelCode; //渠道编码
    private String invCode;  //子库编码
    private String invName; //子库名称
    private Integer menuId; //菜单ID
    private String description; //备注
    private String enabled; //是否启用（Y：启用；N：禁用）
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date creationDate; //创建日期
    private Integer createdBy; //创建人
    private Integer lastUpdatedBy; //更新人
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate; //更新日期
    private Integer versionNum; //版本号
    private Integer lastUpdateLogin; //last_update_login
    private Integer operatorUserId;

	public void setPdaRoleCfgId(Integer pdaRoleCfgId) {
		this.pdaRoleCfgId = pdaRoleCfgId;
	}

	@Id	
	@SequenceGenerator(name = "SEQ_BASE_PDA_ROLE_CFG", sequenceName = "SEQ_BASE_PDA_ROLE_CFG", allocationSize = 1)
  	@GeneratedValue(generator = "SEQ_BASE_PDA_ROLE_CFG", strategy = GenerationType.SEQUENCE)	
	@Column(name = "pda_role_cfg_id", nullable = false, length = 11)	
	public Integer getPdaRoleCfgId() {
		return pdaRoleCfgId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	@Column(name = "role_id", nullable = true, length = 11)	
	public Integer getRoleId() {
		return roleId;
	}

	public void setOrganizationId(Integer organizationId) {
		this.organizationId = organizationId;
	}

	@Column(name = "organization_id", nullable = true, length = 11)	
	public Integer getOrganizationId() {
		return organizationId;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	@Column(name = "channel_code", nullable = true, length = 10)	
	public String getChannelCode() {
		return channelCode;
	}

	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}

	@Column(name = "menu_id", nullable = true, length = 11)	
	public Integer getMenuId() {
		return menuId;
	}

	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}

	@Column(name = "enabled", nullable = true, length = 5)	
	public String getEnabled() {
		return enabled;
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

    @Column(name = "DESCRIPTION", nullable = true, length = 400)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "inv_name", nullable = true, length = 250)
    public String getInvName() {
        return invName;
    }

    public void setInvName(String invName) {
        this.invName = invName;
    }

    @Column(name = "inv_code", nullable = true, length = 250)
    public String getInvCode() {
        return invCode;
    }

    public void setInvCode(String invCode) {
        this.invCode = invCode;
    }
}
