package com.sie.watsons.base.pos.supplierinfo.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.pos.supplierinfo.model.entities.EquPosCredentialsAttachmentEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("equPosCredentialsAttachmentDAO_HI")
public class EquPosCredentialsAttachmentDAO_HI extends BaseCommonDAO_HI<EquPosCredentialsAttachmentEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosCredentialsAttachmentDAO_HI.class);

	public EquPosCredentialsAttachmentDAO_HI() {
		super();
	}

}
