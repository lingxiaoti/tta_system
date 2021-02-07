package com.sie.saaf.bpm.model.entities.readonly;

import java.util.List;

/**
 * @author laoqunzhao
 * @createTime 2018-05-07
 * @description 实现任务节点和用户数据的绑定
 */
public class ActUserTaskEntity_RO{

	private String id;//任务节点ID
	private String name;//任务名称
	private List<ActIdUserEntity_RO> users;//办理人
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public List<ActIdUserEntity_RO> getUsers() {
		return users;
	}
	
	public void setUsers(List<ActIdUserEntity_RO> users) {
		this.users = users;
	}

}
