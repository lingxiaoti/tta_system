package com.sie.watsons.base.supplement.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.supplement.model.entities.TtaAttachmentDownloadEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("ttaAttachmentDownloadDAO_HI")
public class TtaAttachmentDownloadDAO_HI extends BaseCommonDAO_HI<TtaAttachmentDownloadEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaAttachmentDownloadDAO_HI.class);

	public TtaAttachmentDownloadDAO_HI() {
		super();
	}

}
