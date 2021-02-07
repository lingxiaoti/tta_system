package com.sie.watsons.base.supplement.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.supplement.model.entities.TtaSaStdProposalLineEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("ttaSaStdProposalLineDAO_HI")
public class TtaSaStdProposalLineDAO_HI extends BaseCommonDAO_HI<TtaSaStdProposalLineEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaSaStdProposalLineDAO_HI.class);

	public TtaSaStdProposalLineDAO_HI() {
		super();
	}

}
