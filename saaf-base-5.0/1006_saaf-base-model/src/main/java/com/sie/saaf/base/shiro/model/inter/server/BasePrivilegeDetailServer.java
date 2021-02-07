package com.sie.saaf.base.shiro.model.inter.server;


import com.sie.saaf.base.shiro.model.entities.BasePrivilegeDetailEntity_HI;
import com.sie.saaf.base.shiro.model.inter.IBasePrivilegeDetail;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.yhg.hibernate.core.dao.ViewObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("basePrivilegeDetailServer")
public class BasePrivilegeDetailServer extends BaseCommonServer<BasePrivilegeDetailEntity_HI> implements IBasePrivilegeDetail {
//	private static final Logger LOGGER = LoggerFactory.getLogger(BasePrivilegeDetailServer.class);
//	@Autowired
//	private ViewObject<BasePrivilegeDetailEntity_HI> basePrivilegeDetailDAO_HI;
//	public BasePrivilegeDetailServer() {
//		super();
//	}

}
