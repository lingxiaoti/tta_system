package com.sie.saaf.message.model.entities;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;

import java.util.Date;

/**
 * MsgTempleCfgEntity_HI Entity Object
 * Thu Jun 07 09:53:17 CST 2018  Auto Generate
 */
@Entity
@Table(name = "msg_temple_cfg")
public class MsgTempleCfgEntity_HI {
    private Integer templeId; //消息配置ID
    private String templeName;
    private String templeSubject;//主题
    private String templeContent; //内容
    private Integer isHtml;
    private String msgType; //OU
    private Integer orgId;
    private String channel;
    private String business;
    private Integer versionNum;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date lastUpdateDate; //最后更新时间
    private Integer lastUpdatedBy; //最后更新用户ID
    private Integer lastUpdateLogin;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date creationDate; //创建时间
    private Integer createdBy; //创建用户ID
    private Integer isDelete; //是否已删除
    private Integer operatorUserId;
	private String msgUrl;//消息链接

	@Column(name = "msg_url", nullable = true, length = 255)
	public String getMsgUrl() {
		return msgUrl;
	}

	public void setMsgUrl(String msgUrl) {
		this.msgUrl = msgUrl;
	}

	public void setTempleId(Integer templeId) {
		this.templeId = templeId;
	}

	@Id	
	@SequenceGenerator(name = "SEQ_MSG_TEMPLE_CFG", sequenceName = "SEQ_MSG_TEMPLE_CFG", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_MSG_TEMPLE_CFG", strategy = GenerationType.SEQUENCE)
	@Column(name = "temple_id", nullable = false, length = 15)	
	public Integer getTempleId() {
		return templeId;
	}

	public void setTempleName(String templeName) {
		this.templeName = templeName;
	}

	@Column(name = "temple_name", nullable = true, length = 20)	
	public String getTempleName() {
		return templeName;
	}

	public void setTempleContent(String templeContent) {
		this.templeContent = templeContent;
	}

	@Column(name = "temple_content", nullable = false, length = 0)	
	public String getTempleContent() {
		return templeContent;
	}

	public void setIsHtml(Integer isHtml) {
		this.isHtml = isHtml;
	}

	@Column(name = "is_html", nullable = true, length = 5)	
	public Integer getIsHtml() {
		return isHtml;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	@Column(name = "msg_type", nullable = true, length = 20)	
	public String getMsgType() {
		return msgType;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	@Column(name = "org_id", nullable = false, length = 15)
	public Integer getOrgId() {
		return orgId;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	@Column(name = "channel", nullable = true, length = 20)	
	public String getChannel() {
		return channel;
	}

	public void setBusiness(String business) {
		this.business = business;
	}

	@Column(name = "business", nullable = true, length = 20)	
	public String getBusiness() {
		return business;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}
	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	@Column(name = "last_update_date", nullable = false, length = 0)
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	@Version
	@Column(name = "version_num", nullable = true, length = 11)	
	public Integer getVersionNum() {
		return versionNum;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Column(name = "last_updated_by", nullable = false, length = 20)	
	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdateLogin(Integer lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	@Column(name = "last_update_login", nullable = true, length = 11)	
	public Integer getLastUpdateLogin() {
		return lastUpdateLogin;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name = "creation_date", nullable = false, length = 0)	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "created_by", nullable = false, length = 20)	
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	@Column(name = "is_delete", nullable = false, length = 11)
	public Integer getIsDelete() {
		return isDelete;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Column(name = "temple_subject", nullable = false, length = 250)
	public String getTempleSubject() {
		return templeSubject;
	}

	public void setTempleSubject(String templeSubject) {
		this.templeSubject = templeSubject;
	}

	@Transient
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
