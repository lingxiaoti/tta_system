package com.sie.saaf.business.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.saaf.business.model.entities.TtaProposalApprovlHeaderEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("ttaProposalApprovlHeaderDAO_HI")
public class TtaProposalApprovlHeaderDAO_HI extends BaseCommonDAO_HI<TtaProposalApprovlHeaderEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaProposalApprovlHeaderDAO_HI.class);

	public TtaProposalApprovlHeaderDAO_HI() {
		super();
	}

}
