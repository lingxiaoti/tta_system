package com.sie.watsons.base.newbreedextend.model.inter.server;

import com.alibaba.fastjson.JSONObject;

import java.util.*;

import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.newbreedextend.model.entities.readonly.TtaNewbreedExtendLineEntity_HI_RO;
import com.yhg.hibernate.core.dao.BaseViewObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.sie.watsons.base.newbreedextend.model.entities.TtaNewbreedExtendLineEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.newbreedextend.model.inter.ITtaNewbreedExtendLine;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("ttaNewbreedExtendLineServer")
public class TtaNewbreedExtendLineServer extends BaseCommonServer<TtaNewbreedExtendLineEntity_HI> implements ITtaNewbreedExtendLine{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaNewbreedExtendLineServer.class);

	@Autowired
	private ViewObject<TtaNewbreedExtendLineEntity_HI> ttaNewbreedExtendLineDAO_HI;

	@Autowired
	private BaseViewObject<TtaNewbreedExtendLineEntity_HI_RO> ttaNewbreedExtendLineDAO_HI_RO;


	public TtaNewbreedExtendLineServer() {
		super();
	}

	@Override
	public List<TtaNewbreedExtendLineEntity_HI_RO> findById(JSONObject queryParamJSON,Integer userId) throws Exception {
		SaafToolUtils.validateJsonParms(queryParamJSON, "newbreedExtendHId");
		StringBuffer sql = new StringBuffer();
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		sql.append(TtaNewbreedExtendLineEntity_HI_RO.TTA_NBEL_LIST);
		SaafToolUtils.parperParam(queryParamJSON, "tnel.newbreed_extend_h_id", "newbreedExtendHId", sql, paramsMap, "=");
		return  ttaNewbreedExtendLineDAO_HI_RO.findList(sql, paramsMap);
	}


}
