package com.sie.watsons.base.pos.creditAudit.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.base.utils.SToolUtils;
import com.sie.watsons.base.pos.creditAudit.model.entities.EquPosCreditAuditChangeEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.pos.creditAudit.model.inter.IEquPosCreditAuditChange;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("equPosCreditAuditChangeServer")
public class EquPosCreditAuditChangeServer extends BaseCommonServer<EquPosCreditAuditChangeEntity_HI> implements IEquPosCreditAuditChange{
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosCreditAuditChangeServer.class);

	@Autowired
	private ViewObject<EquPosCreditAuditChangeEntity_HI> equPosCreditAuditChangeDAO_HI;

	public EquPosCreditAuditChangeServer() {
		super();
	}

}
