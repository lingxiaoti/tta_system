package com.sie.watsons.base.pos.qualificationreview.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.pos.qualificationreview.model.entities.EquPosZzscAssociateSuppEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("equPosZzscAssociateSuppDAO_HI")
public class EquPosZzscAssociateSuppDAO_HI extends BaseCommonDAO_HI<EquPosZzscAssociateSuppEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosZzscAssociateSuppDAO_HI.class);

	public EquPosZzscAssociateSuppDAO_HI() {
		super();
	}

}
