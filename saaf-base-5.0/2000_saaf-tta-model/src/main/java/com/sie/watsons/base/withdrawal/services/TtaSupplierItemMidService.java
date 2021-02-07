package com.sie.watsons.base.withdrawal.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.sie.watsons.base.supplier.model.entities.TtaSupplierEntity_HI;
import com.sie.watsons.base.withdrawal.model.entities.TtaSupplierItemMidEntity_HI;
import com.sie.watsons.base.withdrawal.model.inter.ITtaSupplierItemMid;

import com.alibaba.fastjson.JSONObject;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.lang.StringUtils;
import org.apache.tools.ant.taskdefs.Sleep;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.model.inter.IBaseCommon;

import static java.lang.Thread.sleep;

@RestController
@RequestMapping("/ttaSupplierItemMidService")
public class  TtaSupplierItemMidService extends CommonAbstractService{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaSupplierItemMidService.class);

	@Autowired
	private ITtaSupplierItemMid ttaSupplierItemMidServer;

	@Override
	public IBaseCommon getBaseCommonServer(){
		return this.ttaSupplierItemMidServer;
	}

	/** 保存和修改(指定供应商)
	 * @param params
	 * @description
	 */
	@RequestMapping(method = RequestMethod.POST, value = "saveOrUpdate")
	public String ttaSupplierItemMidUpdateSupplier(@RequestParam(required = true) String params) {
		try {
			int userId = getSessionUserId();
			JSONObject jsonObject = JSON.parseObject(params);
			ttaSupplierItemMidServer.saveOrUpdate(jsonObject,userId);
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, null).toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
		}
	}

	@PostMapping("findSplitDetailList")
	public String findSplitDetailList(@RequestParam(required = false) String params,
														@RequestParam(required = false, defaultValue = "1") Integer pageIndex,
														@RequestParam(required = false, defaultValue = "100") Integer pageRows) {
		try {
			JSONObject parameters = this.parseObject(params);
			Pagination<TtaSupplierItemMidEntity_HI> page = ttaSupplierItemMidServer.findSplitDetailList(parameters, pageIndex, pageRows);
			JSONObject results = JSONObject.parseObject(JSON.toJSONString(page));
			results.put(SToolUtils.STATUS, SUCCESS_STATUS);
			results.put(SToolUtils.MSG, "成功");
			return results.toString();
		} catch (Exception e) {
			LOGGER.error("params_" + params + "_e_" + e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "查询失败", 0, null).toJSONString();
		}
	}

	@PostMapping("saveSpliteIoTest")
	public String findSplitDetailList(@RequestParam(required = false) String params) {
		try {
			JSONObject jsonObject = this.parseJson(params);
			ttaSupplierItemMidServer.saveSpliteIo(jsonObject);
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, null).toString();
		} catch (Exception e) {
			LOGGER.error("params_" + params + "_e_" + e.getMessage(), e);
		}
		return SToolUtils.convertResultJSONObj("E", "查询失败", 0, null).toJSONString();
	}

	/**
	 * 保存拆分条件数据
	 * @param params
	 * @description
	 */
	@RequestMapping(method = RequestMethod.POST, value = "saveSplitConditionDetail")
	public String saveSplitConditionDetail(@RequestParam(required = true) String params) {

		try {
			int userId = getSessionUserId();
			JSONObject jsonObject = JSON.parseObject(params);
			ttaSupplierItemMidServer.saveSplitConditionDetail(jsonObject,userId);
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, null).toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 检查拆分条件明细数据
	 * @param params
	 * @description
	 */
	@RequestMapping(method = RequestMethod.POST, value = "checkSplitConditionDetail")
	public String checkSplitConditionDetail(@RequestParam(required = true) String params) {

		try {
			int userId = getSessionUserId();
			JSONObject jsonObject = JSON.parseObject(params);
			JSONObject result = ttaSupplierItemMidServer.checkSplitConditionDetail(jsonObject,userId);
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, result).toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * Excel导入
	 * @param params
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST,value = "saveWithdrawalSplitDataImport")
	public String saveWithdrawalSplitDataImport(@RequestParam(required = false) String params){
		try{
			JSONObject jsonObject = parseObject(params);
			int userId = getSessionUserId();
			List<TtaSupplierItemMidEntity_HI> list = ttaSupplierItemMidServer.findSplitDetailDataBySupplierItemHId(jsonObject);
			int size = ttaSupplierItemMidServer.saveOrUpdateWithdrawalSplitDataImport(jsonObject,userId,list);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "总共"+size+"条数据导入成功", size, null).toString();
		}catch (Exception e){
			LOGGER.error(e.getMessage());
			return SToolUtils.convertResultJSONObj(e.getMessage().startsWith("[{") ? "ERR_IMPORT" : "E", e.getMessage(), 0, JSONArray.parseArray(e.getMessage())).toString();
		}
	}

}