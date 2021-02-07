package com.sie.watsons.base.report.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.report.model.entities.TtaOiReportGpSimulationEntity_HI;
import com.sie.watsons.base.report.model.entities.TtaReportHeaderEntity_HI;
import com.sie.watsons.base.report.model.entities.readonly.TtaOiReportFieldHeaderEntity_HI_RO;
import com.sie.watsons.base.report.model.entities.readonly.TtaOiReportGpSimulationEntity_HI_RO;
import com.sie.watsons.base.report.model.entities.readonly.TtaOiReportHeaderEntity_HI_RO;
import com.sie.watsons.base.report.model.entities.readonly.TtaReportHeaderEntity_HI_RO;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.base.utils.SToolUtils;
import com.sie.watsons.base.report.model.entities.TtaOiReportHeaderEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.report.model.inter.ITtaOiReportHeader;
import org.springframework.util.Assert;

@Component("ttaOiReportHeaderServer")
public class TtaOiReportHeaderServer implements ITtaOiReportHeader{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaOiReportHeaderServer.class);

	@Autowired
	private ViewObject<TtaOiReportHeaderEntity_HI> ttaOiReportHeaderDAO_HI;

	@Autowired
	private BaseViewObject<TtaOiReportHeaderEntity_HI_RO> ttaOiReportHeaderDAO_RO;


	@Autowired
	private BaseCommonDAO_HI<TtaOiReportGpSimulationEntity_HI> ttaOiReportGpSimulationDAO_HI;


	public TtaOiReportHeaderServer() {
		super();
	}

	public List<TtaOiReportHeaderEntity_HI> findTtaOiReportHeaderInfo(JSONObject queryParamJSON) {
		Map<String, Object> queryParamMap = SToolUtils.fastJsonObj2Map(queryParamJSON);
		List<TtaOiReportHeaderEntity_HI> findListResult = ttaOiReportHeaderDAO_HI.findList("from TtaOiReportHeaderEntity_HI", queryParamMap);
		return findListResult;
	}
	public Object saveTtaOiReportHeaderInfo(JSONObject queryParamJSON) {
		TtaOiReportHeaderEntity_HI ttaOiReportHeaderEntity_HI = JSON.parseObject(queryParamJSON.toString(), TtaOiReportHeaderEntity_HI.class);
		Object resultData = ttaOiReportHeaderDAO_HI.save(ttaOiReportHeaderEntity_HI);
		return resultData;
	}

	@Override
	public Pagination<TtaOiReportHeaderEntity_HI_RO> find(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
		StringBuffer sql = new StringBuffer();
		sql.append(TtaOiReportHeaderEntity_HI_RO.tta_report_header);
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		com.sie.saaf.common.util.SaafToolUtils.parperParam(queryParamJSON, "h.batch_code", "batchCode", sql, paramsMap, "fulllike");
		com.sie.saaf.common.util.SaafToolUtils.parperParam(queryParamJSON, "h.status", "status", sql, paramsMap, "fulllike");
		com.sie.saaf.common.util.SaafToolUtils.parperParam(queryParamJSON, "h.report_type", "reportType", sql, paramsMap, "=");
		com.sie.saaf.common.util.SaafToolUtils.changeQuerySort(queryParamJSON, sql, " h.id desc", false);
		Pagination<TtaOiReportHeaderEntity_HI_RO> findList = ttaOiReportHeaderDAO_RO.findPagination(sql, SaafToolUtils.getSqlCountString(sql), paramsMap, pageIndex, pageRows);
		return findList;
	}


	@Override
	public TtaOiReportHeaderEntity_HI saveOrUpdate(JSONObject paramsJSON, int userId) throws Exception{
		//校验确认状态，确认之前首先确认整个单据是否确认完成
		String confirmStatus = paramsJSON.getString("confirmStatus");
		String batchCode = paramsJSON.getString("batchCode");
		if ("Y".equalsIgnoreCase(confirmStatus)) {
			Map<String, Object> queryMap = new HashMap<>();
			queryMap.put("batchCode", batchCode);
			List<Map<String, Object>> countList = ttaOiReportGpSimulationDAO_HI.queryNamedSQLForList("select count(1) as cnt from tta_oi_report_gp_simulation_line t where t.batch_code=:batchCode and confirm_status='N' ", queryMap);
			int count = Integer.parseInt(countList.get(0).get("CNT") + "");
			if (count > 0) {
				throw new IllegalArgumentException("当前该单据有" + count + "条未确认，请先确认所有明细！");
			}
		}
		if ("N".equalsIgnoreCase(confirmStatus)) {
			String upDateConfirmStatus = TtaOiReportGpSimulationEntity_HI_RO.getUpDateConfirmStatus(batchCode, userId);// 将confirmStatus Y状态更改成N
			ttaOiReportGpSimulationDAO_HI.executeSqlUpdate(upDateConfirmStatus);
		}
		TtaOiReportHeaderEntity_HI instance = SaafToolUtils.setEntity(TtaOiReportHeaderEntity_HI.class, paramsJSON, ttaOiReportHeaderDAO_HI, userId);
		instance.setIsPublish("Y");
		instance.setIsPublishBy(userId);
		instance.setIsPublishDate(new Date());
		ttaOiReportHeaderDAO_HI.saveOrUpdate(instance);
		return instance;
	}
}
