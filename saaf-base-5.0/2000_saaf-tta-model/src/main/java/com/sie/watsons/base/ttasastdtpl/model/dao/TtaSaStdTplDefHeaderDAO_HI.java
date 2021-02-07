package com.sie.watsons.base.ttasastdtpl.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.ttasastdtpl.model.entities.TtaSaStdTplDefHeaderEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("ttaSaStdTplDefHeaderDAO_HI")
public class TtaSaStdTplDefHeaderDAO_HI extends BaseCommonDAO_HI<TtaSaStdTplDefHeaderEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaSaStdTplDefHeaderDAO_HI.class);

	public TtaSaStdTplDefHeaderDAO_HI() {
		super();
	}

}
