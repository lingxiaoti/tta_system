package com.sie.saaf.message.model.entities;

import javax.persistence.*;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * MsgReceiveSqlEntity_HI Entity Object
 * Thu Jun 07 09:37:57 CST 2018  Auto Generate
 */
@Entity
@Table(name = "msg_receive_sql")
public class MsgReceiveSqlEntity_HI {
    private Integer sqlId; //消息配置ID
    private String sqlSentence;
    private String param;
    private String remark;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate; //最后更新时间
    private Integer lastUpdatedBy; //最后更新用户ID
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date creationDate; //创建时间
    private Integer createdBy; //创建用户ID
    private Integer isDelete; //是否已删除
    private Integer operatorUserId;
	private Integer versionNum;
	private String sqlName;

	public void setSqlId(Integer sqlId) {
		this.sqlId = sqlId;
	}

	@Id	
	@SequenceGenerator(name = "SEQ_MSG_RECEIVE_SQL", sequenceName = "SEQ_MSG_RECEIVE_SQL", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_MSG_RECEIVE_SQL", strategy = GenerationType.SEQUENCE)	
	@Column(name = "sql_id", nullable = false, length = 20)	
	public Integer getSqlId() {
		return sqlId;
	}

	public void setSqlSentence(String sqlSentence) {
		this.sqlSentence = sqlSentence;
	}

	@Column(name = "sql_sentence", nullable = true, length = 2048)	
	public String getSqlSentence() {
		return sqlSentence;
	}

	public void setParam(String param) {
		this.param = param;
	}

	@Column(name = "param", nullable = true, length = 512)	
	public String getParam() {
		return param;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "remark", nullable = true, length = 256)	
	public String getRemark() {
		return remark;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	@Column(name = "last_update_date", nullable = false, length = 0)	
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Column(name = "last_updated_by", nullable = false, length = 20)	
	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
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

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	@Version
	@Column(name = "version_num", nullable = true, length = 11)
	public Integer getVersionNum() {
		return versionNum;
	}

	@Column(name = "sql_name", nullable = false, length = 100)
	public String getSqlName() { return sqlName; }

	public void setSqlName(String sqlName) { this.sqlName = sqlName; }
}
