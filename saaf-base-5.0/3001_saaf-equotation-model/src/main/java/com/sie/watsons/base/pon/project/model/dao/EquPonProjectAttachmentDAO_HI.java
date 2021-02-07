package com.sie.watsons.base.pon.project.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.pon.project.model.entities.EquPonProjectAttachmentEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("equPonProjectAttachmentDAO_HI")
public class EquPonProjectAttachmentDAO_HI extends BaseCommonDAO_HI<EquPonProjectAttachmentEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPonProjectAttachmentDAO_HI.class);

	public EquPonProjectAttachmentDAO_HI() {
		super();
	}

}
