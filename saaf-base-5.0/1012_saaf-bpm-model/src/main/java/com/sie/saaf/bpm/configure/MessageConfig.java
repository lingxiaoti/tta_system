package com.sie.saaf.bpm.configure;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

@Configuration
@ImportResource(locations={"classpath:com/sie/saaf/app/config/message.cfg.xml"})
public class MessageConfig {

	private String synchro;
	private String url;
	private String pcJumpUrl;
	private String mobJumpUrl;

	private String todoTurn;
	private String todoChannel;
	private String todoTitle;
	private String todoContent;
	//委托审批
	private String delegateTurn;
	private String delegateChannel;
	private String delegateTitle;
	private String delegateContent;
	//审批通过
	private String endTurn;
	private String endChannel;
	private String endTitle;
	private String endContent;
	//审批驳回
	private String rejectTurn;
	private String rejectChannel;
	private String rejectTitle;
	private String rejectContent;
	//待阅
	private String ccTurn;
	private String ccChannel;
	private String ccTitle;
	private String ccContent;
	//发起沟通
	private String communicateTurn;
	private String communicateChannel;
	private String communicateTitle;
	//催办
	private String urgeTurn;
	private String urgeChannel;
	private String urgeTitle;
	private String urgeContent;
	private List<String> configs;

	private List<JSONObject> configsJSON;


	public String getSynchro() {
		return synchro;
	}

	public void setSynchro(String synchro) {
		this.synchro = synchro;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPcJumpUrl() {
		return pcJumpUrl;
	}

	public void setPcJumpUrl(String pcJumpUrl) {
		this.pcJumpUrl = pcJumpUrl;
	}

	public String getMobJumpUrl() {
		return mobJumpUrl;
	}

	public void setMobJumpUrl(String mobJumpUrl) {
		this.mobJumpUrl = mobJumpUrl;
	}

	public String getTodoChannel() {
		return todoChannel;
	}

	public void setTodoChannel(String todoChannel) {
		this.todoChannel = todoChannel;
	}

	public String getTodoTitle() {
		return todoTitle;
	}

	public void setTodoTitle(String todoTitle) {
		this.todoTitle = todoTitle;
	}

	public String getTodoContent() {
		return todoContent;
	}

	public void setTodoContent(String todoContent) {
		this.todoContent = todoContent;
	}

	public String getDelegateChannel() {
		return delegateChannel;
	}

	public void setDelegateChannel(String delegateChannel) {
		this.delegateChannel = delegateChannel;
	}

	public String getDelegateTitle() {
		return delegateTitle;
	}

	public void setDelegateTitle(String delegateTitle) {
		this.delegateTitle = delegateTitle;
	}

	public String getDelegateContent() {
		return delegateContent;
	}

	public void setDelegateContent(String delegateContent) {
		this.delegateContent = delegateContent;
	}

	public String getEndChannel() {
		return endChannel;
	}

	public void setEndChannel(String endChannel) {
		this.endChannel = endChannel;
	}

	public String getEndTitle() {
		return endTitle;
	}

	public void setEndTitle(String endTitle) {
		this.endTitle = endTitle;
	}

	public String getEndContent() {
		return endContent;
	}

	public void setEndContent(String endContent) {
		this.endContent = endContent;
	}

	public String getRejectChannel() {
		return rejectChannel;
	}

	public void setRejectChannel(String rejectChannel) {
		this.rejectChannel = rejectChannel;
	}

	public String getRejectTitle() {
		return rejectTitle;
	}

	public void setRejectTitle(String rejectTitle) {
		this.rejectTitle = rejectTitle;
	}

	public String getRejectContent() {
		return rejectContent;
	}

	public void setRejectContent(String rejectContent) {
		this.rejectContent = rejectContent;
	}

	public String getCcChannel() {
		return ccChannel;
	}

	public void setCcChannel(String ccChannel) {
		this.ccChannel = ccChannel;
	}

	public String getCcTitle() {
		return ccTitle;
	}

	public void setCcTitle(String ccTitle) {
		this.ccTitle = ccTitle;
	}

	public String getCcContent() {
		return ccContent;
	}

	public void setCcContent(String ccContent) {
		this.ccContent = ccContent;
	}

	public String getCommunicateChannel() {
		return communicateChannel;
	}

	public void setCommunicateChannel(String communicateChannel) {
		this.communicateChannel = communicateChannel;
	}

	public String getCommunicateTitle() {
		return communicateTitle;
	}

	public void setCommunicateTitle(String communicateTitle) {
		this.communicateTitle = communicateTitle;
	}

	public String getUrgeChannel() {
		return urgeChannel;
	}

	public void setUrgeChannel(String urgeChannel) {
		this.urgeChannel = urgeChannel;
	}

	public String getUrgeTitle() {
		return urgeTitle;
	}

	public void setUrgeTitle(String urgeTitle) {
		this.urgeTitle = urgeTitle;
	}

	public String getUrgeContent() {
		return urgeContent;
	}

	public void setUrgeContent(String urgeContent) {
		this.urgeContent = urgeContent;
	}

	public List<String> getConfigs() {
		return configs;
	}

	public void setConfigs(List<String> configs) {
		this.configs = configs;
	}

	public String getTodoTurn() {
		return todoTurn;
	}

	public void setTodoTurn(String todoTurn) {
		this.todoTurn = todoTurn;
	}

	public String getDelegateTurn() {
		return delegateTurn;
	}

	public void setDelegateTurn(String delegateTurn) {
		this.delegateTurn = delegateTurn;
	}

	public String getEndTurn() {
		return endTurn;
	}

	public void setEndTurn(String endTurn) {
		this.endTurn = endTurn;
	}

	public String getRejectTurn() {
		return rejectTurn;
	}

	public void setRejectTurn(String rejectTurn) {
		this.rejectTurn = rejectTurn;
	}

	public String getCcTurn() {
		return ccTurn;
	}

	public void setCcTurn(String ccTurn) {
		this.ccTurn = ccTurn;
	}

	public String getCommunicateTurn() {
		return communicateTurn;
	}

	public void setCommunicateTurn(String communicateTurn) {
		this.communicateTurn = communicateTurn;
	}

	public String getUrgeTurn() {
		return urgeTurn;
	}

	public void setUrgeTurn(String urgeTurn) {
		this.urgeTurn = urgeTurn;
	}

	public List<JSONObject> getConfigsJSON() {
		if(configsJSON == null || configsJSON.isEmpty()) {
			configsJSON = new ArrayList<JSONObject>();
			if(configs != null && !configs.isEmpty()) {
				for(String config: configs) {
					if(StringUtils.isBlank(config)) {
						continue;
					}
					JSONObject configJSON = JSON.parseObject(config);
					configsJSON.add(configJSON);
				}
			}
		}
		return configsJSON;
	}

}
