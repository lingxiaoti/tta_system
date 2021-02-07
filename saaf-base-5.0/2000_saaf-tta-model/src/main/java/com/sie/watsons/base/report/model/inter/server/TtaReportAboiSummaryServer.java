package com.sie.watsons.base.report.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.text.SimpleDateFormat;
import java.util.*;

import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.report.model.entities.readonly.TtaReportAboiSummaryFixLineEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.base.utils.SToolUtils;
import com.sie.watsons.base.report.model.entities.TtaReportAboiSummaryEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.report.model.inter.ITtaReportAboiSummary;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("ttaReportAboiSummaryServer")
public class TtaReportAboiSummaryServer extends BaseCommonServer<TtaReportAboiSummaryEntity_HI> implements ITtaReportAboiSummary{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaReportAboiSummaryServer.class);

	@Autowired
	private ViewObject<TtaReportAboiSummaryEntity_HI> ttaReportAboiSummaryDAO_HI;

	public TtaReportAboiSummaryServer() {
		super();
	}


	@Override
	public List<TtaReportAboiSummaryEntity_HI> saveOrUpdateInfoALL(JSONArray jsonArrayLine, int userId) throws Exception {
		ArrayList<TtaReportAboiSummaryEntity_HI> objects = new ArrayList<>();
		JSONArray objects1 = new JSONArray();
		for(int i = 0 ;i<jsonArrayLine.size();i++){
			JSONArray  json = (JSONArray)jsonArrayLine.get(i) ;
			if (null != json) {
				for (int j = 0 ;j<json.size();j++) {
					JSONObject  json1 = (JSONObject)json.get(j) ;
					TtaReportAboiSummaryEntity_HI instance = SaafToolUtils.setEntity(TtaReportAboiSummaryEntity_HI.class, json1, ttaReportAboiSummaryDAO_HI, userId);
					instance.setOperatorUserId(userId);
					objects.add(instance);
				}
			}
		}
		ttaReportAboiSummaryDAO_HI.saveOrUpdateAll(objects);
		return objects;
	}

}
