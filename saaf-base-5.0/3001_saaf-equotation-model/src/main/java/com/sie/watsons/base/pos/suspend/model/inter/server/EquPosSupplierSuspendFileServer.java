package com.sie.watsons.base.pos.suspend.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.base.utils.SToolUtils;
import com.sie.watsons.base.pos.suspend.model.entities.EquPosSupplierSuspendFileEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.pos.suspend.model.inter.IEquPosSupplierSuspendFile;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("equPosSupplierSuspendFileServer")
public class EquPosSupplierSuspendFileServer extends BaseCommonServer<EquPosSupplierSuspendFileEntity_HI> implements IEquPosSupplierSuspendFile{
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosSupplierSuspendFileServer.class);

	@Autowired
	private ViewObject<EquPosSupplierSuspendFileEntity_HI> equPosSupplierSuspendFileDAO_HI;

	public EquPosSupplierSuspendFileServer() {
		super();
	}

}
