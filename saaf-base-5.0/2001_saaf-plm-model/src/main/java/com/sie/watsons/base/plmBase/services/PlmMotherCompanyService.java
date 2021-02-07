package com.sie.watsons.base.plmBase.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.watsons.base.plmBase.model.entities.PlmMotherCompanyEntity_HI;
import com.sie.watsons.base.plmBase.model.entities.readonly.PlmMotherCompanyEntity_HI_RO;
import com.sie.watsons.base.plmBase.model.inter.IPlmMotherCompany;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;

@RestController
@RequestMapping("/plmMotherCompanyService")
public class PlmMotherCompanyService extends CommonAbstractService {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(PlmMotherCompanyService.class);
	@Autowired
	private IPlmMotherCompany plmMotherCompanyServer;

	public PlmMotherCompanyService() {
		super();
	}

	@Override
	public IBaseCommon getBaseCommonServer() {
		return this.plmMotherCompanyServer;
	}

	/**
	 * 查询
	 * 
	 * @param params
	 *            { teamFrameworkId:框架ID }
	 * @param pageIndex
	 *            当前页码
	 * @param pageRows
	 *            页面行数
	 * @return Pagination
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findPlmMotherCompanyInfo")
	public String findPlmMotherCompanyInfo(
			@RequestParam(required = false) String params,
			@RequestParam(required = false, defaultValue = "1") Integer pageIndex,
			@RequestParam(required = false, defaultValue = "10") Integer pageRows) {
		JSONObject queryParamJSON = parseObject(params);
		Pagination<PlmMotherCompanyEntity_HI_RO> dataList = plmMotherCompanyServer
				.findPlmMotherCompanyInfo(queryParamJSON, pageIndex, pageRows);
		queryParamJSON = (JSONObject) JSON.toJSON(dataList);
		queryParamJSON.put(SToolUtils.STATUS, "S");
		queryParamJSON.put(SToolUtils.MSG, SUCCESS_MSG);
		return queryParamJSON.toString();
	}

	/**
	 * @param params
	 * @return java.lang.String
	 * @description 保存
	 **/
	@RequestMapping(method = RequestMethod.POST, value = "savePlmMotherCompanyInfo")
	public String savePlmMotherCompanyInfo(
			@RequestParam(required = true) String params) {
		try {
			JSONObject parseObject = null;
			if (JSON.parse(params) instanceof JSONArray) {
				parseObject = parseObject(JSON.parseArray(params)
						.getJSONObject(0).toJSONString());
			} else {
				parseObject = parseObject(params);
			}
			int userId = parseObject.getInteger("varUserId");
			if (!parseObject.containsKey("plmMotherCompanyId")) // 新增
			{
				if (parseObject.containsKey("plmMotherCompanyName")) {
					String name = parseObject.getString("plmMotherCompanyName");
					JSONObject jp = new JSONObject();
					jp.put("plmMotherCompanyName", name);
					List<PlmMotherCompanyEntity_HI> plmli = plmMotherCompanyServer
							.findList(jp);
					if (plmli.size() > 0) {
						return SToolUtils.convertResultJSONObj("E",
								"motherCompany名称已经存在!", 0, null).toString();
					}
				}
			} else { // 修改
				if (parseObject.containsKey("plmMotherCompanyName")) {
					String name = parseObject.getString("plmMotherCompanyName");
					JSONObject jp = new JSONObject();
					jp.put("plmMotherCompanyName", name);
					List<PlmMotherCompanyEntity_HI> plmli = plmMotherCompanyServer
							.findList(jp);
//					Integer mothercompanyid = parseObject
//							.getInteger("plmMotherCompanyId");
					if (plmli.size() > 1) {
						
							return SToolUtils.convertResultJSONObj("E",
									"motherCompany名称已经存在!", 0, null).toString();
						
					}
				}
			}
			JSONObject jsonObject = (JSONObject) JSONObject
					.toJSON(plmMotherCompanyServer
							.savePlmMotherCompanyInfo(parseObject));
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0,
					jsonObject).toString();

		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils
					.convertResultJSONObj("M", e.getMessage(), 0, null)
					.toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E",
					"服务异常," + e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 审批流回调方法，更改单据审批状态
	 * 
	 * @param params
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "saveConfirmedPlmMotherCompanyStatus")
	public String saveConfirmedPlmMotherCompanyStatus(
			@RequestParam(required = false) String params) {
		try {
			JSONObject paramsObject = parseObject(params);
			PlmMotherCompanyEntity_HI entity = plmMotherCompanyServer
					.saveConfirmedPlmMotherCompanyStatus(paramsObject);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG,
					1, entity).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS,
					e.getMessage(), 0, null).toString();
		}
	}


	/**
	 * MotherCompany UDA信息同步
	 * @param params
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "syncMotherCompany")
	public String syncMotherCompany(
			@RequestParam(required = false) String params) {
		try {
			params="{}";
			JSONObject parameters = this.parseObject(params);
			//中英文品牌同步接口 掉存储过程
			String result = plmMotherCompanyServer.syncMotherCompany();
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG,
					0, result).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS,
					e.getMessage(), 0, null).toString();
		}
	}

}
