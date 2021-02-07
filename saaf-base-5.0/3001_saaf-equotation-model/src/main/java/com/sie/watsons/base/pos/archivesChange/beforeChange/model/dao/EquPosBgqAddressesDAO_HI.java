package com.sie.watsons.base.pos.archivesChange.beforeChange.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.pos.archivesChange.beforeChange.model.entities.EquPosBgqAddressesEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("equPosBgqAddressesDAO_HI")
public class EquPosBgqAddressesDAO_HI extends BaseCommonDAO_HI<EquPosBgqAddressesEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosBgqAddressesDAO_HI.class);

	public EquPosBgqAddressesDAO_HI() {
		super();
	}

}
