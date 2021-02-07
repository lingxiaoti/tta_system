package com.sie.watsons.report.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.watsons.report.model.entities.TtaPrintRecordEntity_HI;
import com.sie.watsons.report.model.inter.ITtaPrintRecord;
import com.yhg.base.utils.SToolUtils;

import java.util.List;

@RestController
@RequestMapping("/ttaPrintRecordService")
public class TtaPrintRecordService extends CommonAbstractService{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaPrintRecordService.class);

	@Autowired
	private ITtaPrintRecord ttaPrintRecordServer;

	@Override
	public IBaseCommon getBaseCommonServer(){
		return this.ttaPrintRecordServer;
	}


	/**
	 * 统计打印次数
	 * @param params
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "saveOrUpdatePrintCount")
	public String saveOrUpdatePrintCount(@RequestParam(required = false) String params) {
		try {
			JSONObject jsonObject = this.parseObject(params);
			TtaPrintRecordEntity_HI entity = JSON.parseObject(jsonObject.toJSONString(), TtaPrintRecordEntity_HI.class);
			int printCount = ttaPrintRecordServer.saveOrUpdatePrintCount(entity);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 0, printCount).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
		}
	}


	@RequestMapping(method = RequestMethod.POST, value = "getPrintCount")
	public String find(@RequestParam(required = false) String params) {
		try {
			int printCount = 0;
			JSONObject jsonObject = this.parseObject(params);
			List<TtaPrintRecordEntity_HI> entityList = ttaPrintRecordServer.findList(new JSONObject().fluentPut("printType",  jsonObject.getString("printType")));
			if (entityList != null &&  !entityList.isEmpty()) {
				printCount = entityList.get(0).getPrintCount();
			}
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 0, printCount).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
		}
	}

}