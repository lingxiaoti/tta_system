package com.sie.watsons.base.pon.information.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.pon.information.model.entities.EquPonInformationApprovalConfEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("equPonInformationApprovalConfDAO_HI")
public class EquPonInformationApprovalConfDAO_HI extends BaseCommonDAO_HI<EquPonInformationApprovalConfEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPonInformationApprovalConfDAO_HI.class);

	public EquPonInformationApprovalConfDAO_HI() {
		super();
	}

}
