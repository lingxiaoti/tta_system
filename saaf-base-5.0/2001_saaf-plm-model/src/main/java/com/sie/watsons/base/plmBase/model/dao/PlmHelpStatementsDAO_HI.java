package com.sie.watsons.base.plmBase.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.plmBase.model.entities.PlmHelpStatementsEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("plmHelpStatementsDAO_HI")
public class PlmHelpStatementsDAO_HI extends BaseCommonDAO_HI<PlmHelpStatementsEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmHelpStatementsDAO_HI.class);
	public PlmHelpStatementsDAO_HI() {
		super();
	}

	@Override
	public Object save(PlmHelpStatementsEntity_HI entity) {
		return super.save(entity);
	}
}
