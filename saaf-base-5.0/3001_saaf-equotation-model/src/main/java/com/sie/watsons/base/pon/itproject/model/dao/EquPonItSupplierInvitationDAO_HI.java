package com.sie.watsons.base.pon.itproject.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.pon.itproject.model.entities.EquPonItSupplierInvitationEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("equPonItSupplierInvitationDAO_HI")
public class EquPonItSupplierInvitationDAO_HI extends BaseCommonDAO_HI<EquPonItSupplierInvitationEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPonItSupplierInvitationDAO_HI.class);

	public EquPonItSupplierInvitationDAO_HI() {
		super();
	}

}
