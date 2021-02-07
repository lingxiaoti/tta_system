package com.sie.watsons.base.supplement.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.supplement.model.entities.TtaSaTabLineEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("ttaSaTabLineDAO_HI")
public class TtaSaTabLineDAO_HI extends BaseCommonDAO_HI<TtaSaTabLineEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaSaTabLineDAO_HI.class);

	public TtaSaTabLineDAO_HI() {
		super();
	}

}
