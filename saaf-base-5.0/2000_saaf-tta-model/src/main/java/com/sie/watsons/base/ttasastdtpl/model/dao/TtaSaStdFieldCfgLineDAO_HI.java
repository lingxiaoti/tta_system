package com.sie.watsons.base.ttasastdtpl.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.ttasastdtpl.model.entities.TtaSaStdFieldCfgLineEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("ttaSaStdFieldCfgLineDAO_HI")
public class TtaSaStdFieldCfgLineDAO_HI extends BaseCommonDAO_HI<TtaSaStdFieldCfgLineEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaSaStdFieldCfgLineDAO_HI.class);

	public TtaSaStdFieldCfgLineDAO_HI() {
		super();
	}

}
