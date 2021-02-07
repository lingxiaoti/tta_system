package com.sie.watsons.base.supplement.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.supplement.model.entities.TtaSaNonStandarLineEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("ttaSaNonStandarLineDAO_HI")
public class TtaSaNonStandarLineDAO_HI extends BaseCommonDAO_HI<TtaSaNonStandarLineEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaSaNonStandarLineDAO_HI.class);

	public TtaSaNonStandarLineDAO_HI() {
		super();
	}

}
