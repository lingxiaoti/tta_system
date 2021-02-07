package com.sie.watsons.base.supplement.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.product.model.entities.PlmProductFileEntity_HI;
import com.sie.watsons.base.supplement.model.entities.PlmSupplementLineFileEntity_HI;
import com.sie.watsons.base.supplement.model.entities.readonly.PlmSupplementLineEntity_HI_RO;
import com.sie.watsons.base.supplement.model.inter.IPlmSupplementLineFile;
import com.yhg.hibernate.core.dao.ViewObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("plmSupplementLineFileServer")
public class PlmSupplementLineFileServer extends BaseCommonServer<PlmProductFileEntity_HI> implements IPlmSupplementLineFile {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmSupplementLineFileServer.class);

	@Autowired
	private ViewObject<PlmSupplementLineFileEntity_HI> plmSupplementLineFileDAO_HI;

	@Override
	public void saveFile(JSONObject queryParamJSON) {
		String headId = queryParamJSON.getString("plmSupplementHeadId");
		JSONArray lines = queryParamJSON.getJSONArray("lines");
		for (int i = 0; i < lines.size(); i++) {
			JSONObject line = lines.getJSONObject(i);
			PlmSupplementLineFileEntity_HI l = JSON.parseObject(line.toString(), PlmSupplementLineFileEntity_HI.class);
			l.setlineId(headId);
			plmSupplementLineFileDAO_HI.saveOrUpdate(l);
		}
	}

	@Override
	public void deleteFile(JSONObject param) {
		PlmSupplementLineFileEntity_HI file = plmSupplementLineFileDAO_HI.getById(param.getInteger("fileId"));
		plmSupplementLineFileDAO_HI.delete(file);
	}

	public List<PlmSupplementLineFileEntity_HI> getFiles(JSONObject o) {
		Map<String, Object> queryParamMap = new HashMap<String, Object>();
		StringBuffer sql = new StringBuffer(" from PlmSupplementLineFileEntity_HI where 1=1 ");
		SaafToolUtils.parperHbmParam(PlmSupplementLineFileEntity_HI.class, o, sql, queryParamMap);
		List<PlmSupplementLineFileEntity_HI> findListResult = plmSupplementLineFileDAO_HI.findList(sql, queryParamMap);
		return findListResult;
	}

	public PlmSupplementLineFileServer() {
		super();
	}

}
