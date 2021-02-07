package com.sie.saaf.base.shiro.model.entities;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;

import java.util.Date;

/**
 * BaseMenuEntity_HI Entity Object
 * Tue Dec 12 19:08:04 CST 2017  Auto Generate
 */
@Entity
@Table(name = "base_menu")
public class BaseMenuEntity_HI {
    private Integer menuId; //菜单Id
    private Integer menuParentId; //上级菜单Id
    private String menuCode; //菜单编码
    private Integer orderNo; //排序号
    private String menuName; //菜单名称
    private String menuDesc; //菜单提示信息
    private Integer levelId; //层级ID
    private String menuType; //菜单类型：10-菜单节点；20-功能节点
    private String parameter; //参数
    private String systemCode; //系统编码
    private String imageLink; //图片样式
    private String imageColor; //图标颜色
    private String htmlUrl; //HTML路由链接
    private String fromType; //访问来源
    private String enabled; //是否启用
    @JSONField(format = "yyyy-MM-dd")
    private Date startDateActive; //启用时间
    @JSONField(format = "yyyy-MM-dd")
    private Date endDateActive; //失效时间
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date creationDate; //创建日期
    private Integer createdBy; //创建人
    private Integer lastUpdatedBy; //更新人
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate; //更新日期
    private Integer versionNum; //版本号
    private Integer lastUpdateLogin; //last_update_login
    private Integer operatorUserId;

	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}

	@Id	
	@SequenceGenerator(name = "SEQ_BASE_MENU", sequenceName = "SEQ_BASE_MENU", allocationSize = 1)
  	@GeneratedValue(generator = "SEQ_BASE_MENU", strategy = GenerationType.SEQUENCE)
	@Column(name = "menu_id", nullable = false, length = 11)	
	public Integer getMenuId() {
		return menuId;
	}

	public void setMenuParentId(Integer menuParentId) {
		this.menuParentId = menuParentId;
	}

	@Column(name = "menu_parent_id", nullable = true, length = 11)	
	public Integer getMenuParentId() {
		return menuParentId;
	}

	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
	}

	@Column(name = "menu_code", nullable = true, length = 100)	
	public String getMenuCode() {
		return menuCode;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

	@Column(name = "order_no", nullable = true, length = 11)	
	public Integer getOrderNo() {
		return orderNo;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	@Column(name = "menu_name", nullable = true, length = 100)	
	public String getMenuName() {
		return menuName;
	}

	public void setMenuDesc(String menuDesc) {
		this.menuDesc = menuDesc;
	}

	@Column(name = "menu_desc", nullable = true, length = 256)	
	public String getMenuDesc() {
		return menuDesc;
	}

	public void setLevelId(Integer levelId) {
		this.levelId = levelId;
	}

	@Column(name = "level_id", nullable = true, length = 11)	
	public Integer getLevelId() {
		return levelId;
	}

	public void setMenuType(String menuType) {
		this.menuType = menuType;
	}

	@Column(name = "menu_type", nullable = true, length = 5)	
	public String getMenuType() {
		return menuType;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	@Column(name = "parameter", nullable = true, length = 2000)	
	public String getParameter() {
		return parameter;
	}

	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}

	@Column(name = "system_code", nullable = true, length = 30)	
	public String getSystemCode() {
		return systemCode;
	}

	public void setImageLink(String imageLink) {
		this.imageLink = imageLink;
	}

	@Column(name = "image_link", nullable = true, length = 60)
	public String getImageLink() {
		return imageLink;
	}

	public void setImageColor(String imageColor) {
		this.imageColor = imageColor;
	}

	@Column(name = "image_color", nullable = true, length = 10)
	public String getImageColor() {
		return imageColor;
	}

	public void setHtmlUrl(String htmlUrl) {
		this.htmlUrl = htmlUrl;
	}

	@Column(name = "html_url", nullable = true, length = 240)
	public String getHtmlUrl() {
		return htmlUrl;
	}

	@Column(name = "from_type", nullable = true, length = 10)
	public String getFromType() {
		return fromType;
	}

	public void setFromType(String fromType) {
		this.fromType = fromType;
	}

	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}

	@Column(name = "enabled", nullable = true, length = 10)
	public String getEnabled() {
		return enabled;
	}

	public void setStartDateActive(Date startDateActive) {
		this.startDateActive = startDateActive;
	}

	@Column(name = "start_date_active", nullable = true, length = 0)	
	public Date getStartDateActive() {
		return startDateActive;
	}

	public void setEndDateActive(Date endDateActive) {
		this.endDateActive = endDateActive;
	}

	@Column(name = "end_date_active", nullable = true, length = 0)	
	public Date getEndDateActive() {
		return endDateActive;
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
