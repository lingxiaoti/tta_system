package com.sie.watsons.base.pos.creditAudit.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.pos.creditAudit.model.entities.EquPosCreditAuditChangeEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("equPosCreditAuditChangeDAO_HI")
public class EquPosCreditAuditChangeDAO_HI extends BaseCommonDAO_HI<EquPosCreditAuditChangeEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosCreditAuditChangeDAO_HI.class);

	public EquPosCreditAuditChangeDAO_HI() {
		super();
	}

}
