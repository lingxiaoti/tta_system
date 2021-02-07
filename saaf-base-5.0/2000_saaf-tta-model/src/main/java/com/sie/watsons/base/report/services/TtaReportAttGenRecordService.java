package com.sie.watsons.base.report.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.watsons.base.report.model.entities.TtaReportAttGenRecordEntity_HI;
import com.sie.watsons.base.report.model.entities.readonly.TtaReportAttGenRecordEntity_HI_RO;
import com.sie.watsons.base.report.model.inter.ITtaReportAttGenRecord;
import com.yhg.base.utils.SToolUtils;
import com.yhg.base.utils.StringUtils;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ttaReportAttGenRecordService")
public class TtaReportAttGenRecordService extends CommonAbstractService{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaReportAttGenRecordService.class);

	@Autowired
	private ITtaReportAttGenRecord ttaReportAttGenRecordServer;

	@Override
	public IBaseCommon getBaseCommonServer(){
		return this.ttaReportAttGenRecordServer;
	}


	@RequestMapping(method = RequestMethod.POST, value = "find")
	public String find(@RequestParam(required = false) String params,
					   @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
					   @RequestParam(required = false, defaultValue = "10") Integer pageRows) {
		try {
			JSONObject jsonObject = this.parseObject(params);
			Pagination<TtaReportAttGenRecordEntity_HI_RO> result = ttaReportAttGenRecordServer.find(jsonObject, pageIndex, pageRows);
			jsonObject = (JSONObject) JSON.toJSON(result);
			jsonObject.put(SToolUtils.STATUS, "S");
			jsonObject.put(SToolUtils.MSG, SUCCESS_MSG);
			return jsonObject.toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
		}
	}


	@RequestMapping(method = RequestMethod.POST, value = "findById")
	public String findById(@RequestParam("params")
												String params) {
		try {
			String msg = "";
			JSONObject jsonParam = this.parseObject(params);
			TtaReportAttGenRecordEntity_HI entity = ttaReportAttGenRecordServer.findById(jsonParam);
			if (entity != null && StringUtils.isNotEmpty(entity.getMsgRemark())) {
				msg = entity.getMsgRemark();
			}
			return SToolUtils.convertResultJSONObj("S", "查询成功！", 0, msg).toString();
		} catch (Exception e) {
			LOGGER.error("findById 失败，" + e.toString() + "，Exception：" + e);
			return SToolUtils.convertResultJSONObj("E", "查询失败！", 0, null).toString();
		}
	}

	@RequestMapping( "deleteReportAttGenRecord")
	public String deleteReportHeader(@RequestParam(required = true) String params) {
		try {
			JSONObject jsonObject = this.parseObject(params);
			Integer reportAttGenRecordId = jsonObject.getInteger("reportAttGenRecordId");
			ttaReportAttGenRecordServer.deleteReportAttGenRecord(reportAttGenRecordId);
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, null).toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
		}
	}

}