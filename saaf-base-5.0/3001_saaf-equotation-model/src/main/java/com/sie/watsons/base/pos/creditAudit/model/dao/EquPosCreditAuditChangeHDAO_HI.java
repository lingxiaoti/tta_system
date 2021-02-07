package com.sie.watsons.base.pos.creditAudit.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.pos.creditAudit.model.entities.EquPosCreditAuditChangeHEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("equPosCreditAuditChangeHDAO_HI")
public class EquPosCreditAuditChangeHDAO_HI extends BaseCommonDAO_HI<EquPosCreditAuditChangeHEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosCreditAuditChangeHDAO_HI.class);

	public EquPosCreditAuditChangeHDAO_HI() {
		super();
	}

}
