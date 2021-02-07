package com.sie.watsons.base.proposal.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.proposal.model.entities.TtaDeptFeeLineEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("ttaDeptFeeLineDAO_HI")
public class TtaDeptFeeLineDAO_HI extends BaseCommonDAO_HI<TtaDeptFeeLineEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaDeptFeeLineDAO_HI.class);

	public TtaDeptFeeLineDAO_HI() {
		super();
	}

}
