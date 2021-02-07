package com.sie.watsons.base.pos.qualificationreview.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.pos.qualificationreview.model.entities.EquPosZzscCredentialAttachEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("equPosZzscCredentialAttachDAO_HI")
public class EquPosZzscCredentialAttachDAO_HI extends BaseCommonDAO_HI<EquPosZzscCredentialAttachEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosZzscCredentialAttachDAO_HI.class);

	public EquPosZzscCredentialAttachDAO_HI() {
		super();
	}

}
