package com.sie.wastons.ttadata.model.entities.readyonly;

import com.sie.wastons.sql.annotation.SqlBinder;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Transient;

/**
 * ActBpmListEntity_HI_RO Entity Object
 * Fri Nov 08 21:33:35 CST 2019  Auto Generate
 */

public class ActBpmListEntity_HI_RO {

	// 供应商编号
	@SqlBinder(sqlColumn = "PROC_INST_ID")
	private String procInstId;
	@SqlBinder(sqlColumn = "LIST_ID")
	private Integer listId;

	public String getProcInstId() {
		return procInstId;
	}

	public void setProcInstId(String procInstId) {
		this.procInstId = procInstId;
	}

	public Integer getListId() {
		return listId;
	}

	public void setListId(Integer listId) {
		this.listId = listId;
	}
}
