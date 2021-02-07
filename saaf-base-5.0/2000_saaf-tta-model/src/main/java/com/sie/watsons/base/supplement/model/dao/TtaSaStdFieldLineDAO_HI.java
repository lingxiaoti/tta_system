package com.sie.watsons.base.supplement.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.supplement.model.entities.TtaSaStdFieldLineEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("ttaSaStdFieldLineDAO_HI")
public class TtaSaStdFieldLineDAO_HI extends BaseCommonDAO_HI<TtaSaStdFieldLineEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaSaStdFieldLineDAO_HI.class);

	public TtaSaStdFieldLineDAO_HI() {
		super();
	}

}
