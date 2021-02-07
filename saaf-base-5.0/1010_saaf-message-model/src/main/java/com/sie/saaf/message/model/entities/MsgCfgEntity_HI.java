package com.sie.saaf.message.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.Transient;

/**
 * MsgCfgEntity_HI Entity Object
 * Thu Jun 07 09:29:00 CST 2018  Auto Generate
 */
@Entity
@Table(name = "msg_cfg")
public class MsgCfgEntity_HI {
    private Integer msgCfgId; //消息配置ID
	private String msgCfgName; //消息配置名称
    private Integer orgId; //OU
    private String channelType; //渠道类型
    private String msgTypeCode; //消息类型CODE
    private Integer msgSourceId;
    private Integer templeId;
    private Integer functionId;
    private Integer isTimingDelivery; //定时发送频率,间隔多少分钟
    private String deliveryTime; //定时发送开始时间
    private String enabledFlag; //启用状态:0.已停用 1.启用
    private Integer versionNum;
    private Integer lastUpdateLogin;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate; //最后更新时间
    private Integer lastUpdatedBy; //最后更新用户ID
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date creationDate; //创建时间
    private Integer createdBy; //创建用户ID
    private String blacklistFlag; //是否过滤黑名单
    private Integer isDelete; //是否已删除
    private Integer operatorUserId;
    private String agentId;//微信使用,agent_id
    private String compensateFlag;//是否补偿Y/N

	@Column(name = "compensate_flag", nullable = true, length = 1)
	public String getCompensateFlag() {
		return compensateFlag;
	}

	public void setCompensateFlag(String compensateFlag) {
		this.compensateFlag = compensateFlag;
	}

	@Column(name = "agent_id", nullable = true, length = 15)
    public String getAgentId() {
        return agentId;
    }
    private String enabledFlagName;

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    @Column(name = "blacklist_flag", nullable = true, length = 1)
    public String getBlacklistFlag() {
        return blacklistFlag;
    }

    public void setBlacklistFlag(String blacklistFlag) {
        this.blacklistFlag = blacklistFlag;
    }

    public void setMsgCfgId(Integer msgCfgId) {
        this.msgCfgId = msgCfgId;
    }

    @Id
    @SequenceGenerator(name = "SEQ_MSG_CFG", sequenceName = "SEQ_MSG_CFG", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_MSG_CFG", strategy = GenerationType.SEQUENCE)
    @Column(name = "msg_cfg_id", nullable = false, length = 15)
    public Integer getMsgCfgId() {
        return msgCfgId;
    }

    @Column(name = "msg_cfg_name", nullable = false, length = 255)
	public String getMsgCfgName() { return msgCfgName; }

	public void setMsgCfgName(String msgCfgName) { this.msgCfgName = msgCfgName; }

	public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

	@Column(name = "org_id", nullable = false, length = 15)
	public Integer getOrgId() {
		return orgId;
	}

	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	@Column(name = "channel_type", nullable = false, length = 30)
	public String getChannelType() {
		return channelType;
	}

	public void setMsgTypeCode(String msgTypeCode) {
		this.msgTypeCode = msgTypeCode;
	}

	@Column(name = "msg_type_code", nullable = false, length = 20)
	public String getMsgTypeCode() {
		return msgTypeCode;
	}

	public void setMsgSourceId(Integer msgSourceId) {
		this.msgSourceId = msgSourceId;
	}

	@Column(name = "msg_source_id", nullable = false, length = 20)
	public Integer getMsgSourceId() {
		return msgSourceId;
	}

	public void setTempleId(Integer templeId) {
		this.templeId = templeId;
	}

	@Column(name = "temple_id", nullable = true, length = 15)
	public Integer getTempleId() {
		return templeId;
	}

	public void setFunctionId(Integer functionId) {
		this.functionId = functionId;
	}

	@Column(name = "function_id", nullable = true, length = 15)
	public Integer getFunctionId() {
		return functionId;
	}

	public void setIsTimingDelivery(Integer isTimingDelivery) {
		this.isTimingDelivery = isTimingDelivery;
	}

	@Column(name = "is_timing_delivery", nullable = true, length = 8)
	public Integer getIsTimingDelivery() {
		return isTimingDelivery;
	}

	public void setDeliveryTime(String deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	@Column(name = "delivery_time", nullable = true, length = 5)
	public String getDeliveryTime() {
		return deliveryTime;
	}

	public void setEnabledFlag(String enabledFlag) {
		this.enabledFlag = enabledFlag;
	}

	@Column(name = "enabled_flag", nullable = false, length = 2)
	public String getEnabledFlag() {
		return enabledFlag;
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

	@Transient
	public String getEnabledFlagName() {
		return enabledFlagName;
	}

	public void setEnabledFlagName(String enabledFlagName) {
		this.enabledFlagName = enabledFlagName;
	}
}
