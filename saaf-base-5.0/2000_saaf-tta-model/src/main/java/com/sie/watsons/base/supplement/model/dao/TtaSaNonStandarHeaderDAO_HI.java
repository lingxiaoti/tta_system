package com.sie.watsons.base.supplement.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.supplement.model.entities.TtaSaNonStandarHeaderEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("ttaSaNonStandarHeaderDAO_HI")
public class TtaSaNonStandarHeaderDAO_HI extends BaseCommonDAO_HI<TtaSaNonStandarHeaderEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaSaNonStandarHeaderDAO_HI.class);

	public TtaSaNonStandarHeaderDAO_HI() {
		super();
	}

}
