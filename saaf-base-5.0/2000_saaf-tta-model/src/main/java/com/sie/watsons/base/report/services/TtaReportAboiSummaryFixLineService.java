package com.sie.watsons.base.report.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.sie.saaf.common.util.SaafBeanUtils;
import com.sie.saaf.common.util.SaafDateUtils;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.report.model.entities.TtaReportAboiSummaryFixLineEntity_HI;
import com.sie.watsons.base.report.model.entities.readonly.TtaReportAboiSummaryFixLineEntity_HI_RO;
import com.sie.watsons.base.report.model.inter.ITtaReportAboiSummaryFixLine;

import com.alibaba.fastjson.JSONObject;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.model.inter.IBaseCommon;

@RestController
@RequestMapping("/ttaReportAboiSummaryFixLineService")
public class TtaReportAboiSummaryFixLineService extends CommonAbstractService{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaReportAboiSummaryFixLineService.class);

	@Autowired
	private ITtaReportAboiSummaryFixLine ttaReportAboiSummaryFixLineServer;

	private LinkedBlockingQueue<JSONObject> queue = new LinkedBlockingQueue<JSONObject>(1);

	@Override
	public IBaseCommon getBaseCommonServer(){
		return this.ttaReportAboiSummaryFixLineServer;
	}

	/**
	 * 查找数据
	 * @param params
	 * @param pageIndex
	 * @param pageRows
	 * @return
	 * @author ZhangJun
	 * @创建时间 2017/12/27
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findPagination")
	public String findPagination(@RequestParam(required = false) String params,
								 @RequestParam(required = false,defaultValue = "1") Integer pageIndex,
								 @RequestParam(required = false,defaultValue = "10") Integer pageRows) {
		try{
			JSONObject queryParamJSON = parseObject(params);
			JSONObject findList = ttaReportAboiSummaryFixLineServer.findPagination(queryParamJSON,queue,pageIndex,pageRows);
			queue.poll();
			JSONObject results = JSONObject.parseObject(JSON.toJSONString(findList));
			results.put(SToolUtils.STATUS, SUCCESS_STATUS);
			results.put(SToolUtils.MSG, "成功");
			return results.toString();
		}catch (Exception e){
			queue.poll();
			LOGGER.error(e.getMessage(),e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * @param params ｛
	 *               lookupType
	 *               buOrgId
	 *               ｝
	 * @return
	 * @description 查询数据字典
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findTermsList")
	public String findTermsList(@RequestParam(required = false) String params) {
		try {
			JSONObject jsonObject = new JSONObject();

			if (StringUtils.isNotBlank(params))
				jsonObject = JSON.parseObject(params);
			SaafToolUtils.validateJsonParms(jsonObject,"reportYear");
			List<TtaReportAboiSummaryFixLineEntity_HI_RO> list = ttaReportAboiSummaryFixLineServer.findTermsList(jsonObject);
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, list.size(), list).toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		}  catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "服务异常", 0, null).toString();
		}
	}

	/**
	 * @param params
	 * @description 保存/修改
	 */
	@RequestMapping(method = RequestMethod.POST, value = "saveOrUpdateALL")
	public String saveOrUpdateSplitALL(@RequestParam(required = true) String params) {
		try {
			int userId = getSessionUserId();
			JSONObject jsonObject = parseObject(params);
			List<TtaReportAboiSummaryFixLineEntity_HI> infoList = ttaReportAboiSummaryFixLineServer.saveOrUpdateALL(jsonObject, userId );
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, infoList).toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * @param params    {
	 *                  }
	 * @param pageIndex
	 * @param pageRows
	 * @return
	 * @description 查询列表（带分页）
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findBrandList")
	public String findBrandList(@RequestParam(required = false) String params,
					   @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
					   @RequestParam(required = false, defaultValue = "10") Integer pageRows) {
		try {

			JSONObject jsonObject = new JSONObject();
			jsonObject = parseObject(params);
			SaafToolUtils.validateJsonParms(jsonObject,"reportYear");
			Pagination<TtaReportAboiSummaryFixLineEntity_HI_RO> result = ttaReportAboiSummaryFixLineServer.findBrandList(jsonObject, pageIndex, pageRows);
			jsonObject = (JSONObject) JSON.toJSON(result);
			jsonObject.put(SToolUtils.STATUS, "S");
			jsonObject.put(SToolUtils.MSG, SUCCESS_MSG);
			return jsonObject.toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
		}
	}


	/**
	 * @param params    {
	 *                  }
	 * @param pageIndex
	 * @param pageRows
	 * @return
	 * @description 查询列表（带分页）
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findGroupList")
	public String findGroupList(@RequestParam(required = false) String params,
								@RequestParam(required = false, defaultValue = "1") Integer pageIndex,
								@RequestParam(required = false, defaultValue = "10") Integer pageRows) {
		try {

			JSONObject jsonObject = new JSONObject();
			jsonObject = parseObject(params);
			SaafToolUtils.validateJsonParms(jsonObject,"reportYear");
			Pagination<TtaReportAboiSummaryFixLineEntity_HI_RO> result = ttaReportAboiSummaryFixLineServer.findGroupList(jsonObject, pageIndex, pageRows);
			jsonObject = (JSONObject) JSON.toJSON(result);
			jsonObject.put(SToolUtils.STATUS, "S");
			jsonObject.put(SToolUtils.MSG, SUCCESS_MSG);
			return jsonObject.toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
		}
	}

}