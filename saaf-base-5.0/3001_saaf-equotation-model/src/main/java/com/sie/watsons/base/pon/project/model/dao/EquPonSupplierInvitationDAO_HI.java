package com.sie.watsons.base.pon.project.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.pon.project.model.entities.EquPonSupplierInvitationEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("equPonSupplierInvitationDAO_HI")
public class EquPonSupplierInvitationDAO_HI extends BaseCommonDAO_HI<EquPonSupplierInvitationEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPonSupplierInvitationDAO_HI.class);

	public EquPonSupplierInvitationDAO_HI() {
		super();
	}

}
