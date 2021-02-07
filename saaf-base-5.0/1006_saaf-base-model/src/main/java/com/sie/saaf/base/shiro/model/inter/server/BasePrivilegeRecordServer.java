package com.sie.saaf.base.shiro.model.inter.server;

import com.sie.saaf.base.shiro.model.entities.BasePrivilegeRecordEntity_HI;
import com.sie.saaf.base.shiro.model.inter.IBasePrivilegeRecord;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.yhg.hibernate.core.dao.ViewObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("basePrivilegeRecordServer")
public class BasePrivilegeRecordServer extends BaseCommonServer<BasePrivilegeRecordEntity_HI> implements IBasePrivilegeRecord {
//	private static final Logger LOGGER = LoggerFactory.getLogger(BasePrivilegeRecordServer.class);
//	@Autowired
//	private ViewObject<BasePrivilegeRecordEntity_HI> basePrivilegeRecordDAO_HI;
//
//	public BasePrivilegeRecordServer() {
//		super();
//	}

}
