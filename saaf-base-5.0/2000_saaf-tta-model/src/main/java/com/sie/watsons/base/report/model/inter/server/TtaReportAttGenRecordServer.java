package com.sie.watsons.base.report.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.report.model.entities.readonly.TtaReportAttGenRecordEntity_HI_RO;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.base.utils.SToolUtils;
import com.sie.watsons.base.report.model.entities.TtaReportAttGenRecordEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.report.model.inter.ITtaReportAttGenRecord;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Component("ttaReportAttGenRecordServer")
public class TtaReportAttGenRecordServer extends BaseCommonServer<TtaReportAttGenRecordEntity_HI> implements ITtaReportAttGenRecord{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaReportAttGenRecordServer.class);

	public TtaReportAttGenRecordServer() {
		super();
	}

	@Autowired
	private ViewObject<TtaReportAttGenRecordEntity_HI> ttaReportAttGenRecordDAO_HI;

	@Autowired
	private BaseViewObject<TtaReportAttGenRecordEntity_HI_RO> ttaReportAttGenRecordDAO_HI_RO;

	@Override
	public Pagination<TtaReportAttGenRecordEntity_HI_RO> find(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		StringBuffer sql = new StringBuffer();
		sql.append(TtaReportAttGenRecordEntity_HI_RO.getReportSql());
		SaafToolUtils.parperParam(queryParamJSON, "tragr.report_type", "reportType", sql, paramsMap, "=");
		SaafToolUtils.parperParam(queryParamJSON, "to_char(tragr.creation_Date,'YYYY-MM-DD')", "creationDate", sql, paramsMap, "=");
		SaafToolUtils.parperParam(queryParamJSON, "tragr.msg_code", "msgCode", sql, paramsMap, "=");
		SaafToolUtils.changeQuerySort(queryParamJSON, sql, "tragr.last_update_date desc", false);
		Pagination<TtaReportAttGenRecordEntity_HI_RO> pagination = ttaReportAttGenRecordDAO_HI_RO.findPagination(sql, SaafToolUtils.getSqlCountString(sql), paramsMap, pageIndex, pageRows);
		return pagination;
	}

	@Override
	public TtaReportAttGenRecordEntity_HI findById(JSONObject jsonParam) {
		Integer reportAttGenRecordId = jsonParam.getInteger("reportAttGenRecordId");
		 return ttaReportAttGenRecordDAO_HI.getById(reportAttGenRecordId);
	}

	@Override
	public TtaReportAttGenRecordEntity_HI saveOrUpdateInfo(JSONObject jsonParam,Integer userId) throws Exception {
		TtaReportAttGenRecordEntity_HI instance = SaafToolUtils.setEntity(TtaReportAttGenRecordEntity_HI.class, jsonParam, ttaReportAttGenRecordDAO_HI, userId);
		Date startDate = jsonParam.getDate("startDate");
		Date endDate =  jsonParam.getDate("endDate");
		if (null != startDate) {
			instance.setCreationDate(startDate);
		}
		if (null != endDate) {
			instance.setLastUpdateDate(endDate);
		}
		ttaReportAttGenRecordDAO_HI.saveOrUpdate(instance);
		return  instance ;
	}

	@Override
	public void deleteReportAttGenRecord(Integer reportAttGenRecordId) {
		TtaReportAttGenRecordEntity_HI entityHi = ttaReportAttGenRecordDAO_HI.getById(reportAttGenRecordId);
		if (entityHi == null) {
			throw new IllegalArgumentException("参数错误，删除的数据不存在！");
		}
		ttaReportAttGenRecordDAO_HI.delete(reportAttGenRecordId);
	}
}
