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
import com.sie.watsons.base.plmBase.model.entities.PlmBrandInfoEntity_HI;
import com.sie.watsons.base.plmBase.model.entities.PlmGroupBrandEntity_HI;
import com.sie.watsons.base.plmBase.model.entities.PlmMotherCompanyEntity_HI;
import com.sie.watsons.base.plmBase.model.entities.readonly.PlmGroupBrandEntity_HI_RO;
import com.sie.watsons.base.plmBase.model.inter.IPlmBrandInfo;
import com.sie.watsons.base.plmBase.model.inter.IPlmGroupBrand;
import com.sie.watsons.base.plmBase.model.inter.IPlmMotherCompany;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;

@RestController
@RequestMapping("/plmGroupBrandService")
public class PlmGroupBrandService extends CommonAbstractService {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(PlmGroupBrandService.class);
	@Autowired
	private IPlmGroupBrand plmGroupBrandServer;

	@Autowired
	private IPlmBrandInfo plmBrandInfoServer;

	@Autowired
	private IPlmMotherCompany plmMotherCompanyServer;

	public PlmGroupBrandService() {
		super();
	}

	@Override
	public IBaseCommon getBaseCommonServer() {
		return this.plmGroupBrandServer;
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
	@RequestMapping(method = RequestMethod.POST, value = "findPlmGroupBrandInfo")
	public String findPlmGroupBrandInfo(
			@RequestParam(required = false) String params,
			@RequestParam(required = false, defaultValue = "1") Integer pageIndex,
			@RequestParam(required = false, defaultValue = "10") Integer pageRows) {
		JSONObject queryParamJSON = parseObject(params);
		Pagination<PlmGroupBrandEntity_HI_RO> dataList = plmGroupBrandServer
				.findPlmGroupBrandInfo(queryParamJSON, pageIndex, pageRows);
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
	@RequestMapping(method = RequestMethod.POST, value = "savePlmGroupBrandInfo")
	public String savePlmGroupBrandInfo(
			@RequestParam(required = true) String params) {
		try {
			JSONObject parseObject = null;
			if (JSON.parse(params) instanceof JSONArray) {
				parseObject = parseObject(JSON.parseArray(params)
						.getJSONObject(0).toJSONString());
			} else {
				parseObject = parseObject(params);
			}
			// plmGroupBrandId
			if (!parseObject.containsKey("plmGroupBrandId")) // 新增
			{
				String plmGroupBrandName = parseObject
						.getString("plmGroupBrandName");
				JSONObject param = new JSONObject();
				param.put("plmGroupBrandName", plmGroupBrandName);
				List<PlmGroupBrandEntity_HI> groupli = plmGroupBrandServer
						.findList(param);
				if (groupli.size() > 0) {
					return SToolUtils.convertResultJSONObj("E",
							"GroupBrand名称已经存在!", 0, null).toString();
				}
			} else {
				String plmGroupBrandName = parseObject
						.getString("plmGroupBrandName");
				Integer groupbrandid = parseObject
						.getInteger("plmGroupBrandId");
				JSONObject param = new JSONObject();
				param.put("plmGroupBrandName", plmGroupBrandName);
				List<PlmGroupBrandEntity_HI> groupli = plmGroupBrandServer
						.findList(param);
				if (groupli.size() > 0) {
					if (groupbrandid != groupli.get(0).getPlmGroupBrandId()) {
						return SToolUtils.convertResultJSONObj("E",
								"GroupBrand名称已经存在!", 0, null).toString();
					}
				}
			}

			int userId = parseObject.getInteger("varUserId");
			JSONObject jsonObject = (JSONObject) JSONObject
					.toJSON(plmGroupBrandServer
							.savePlmGroupBrandInfo(parseObject));
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
	@RequestMapping(method = RequestMethod.POST, value = "saveConfirmedPlmGroupBrandStatus")
	public String saveConfirmedPlmGroupBrandStatus(
			@RequestParam(required = false) String params) {
		try {
			JSONObject paramsObject = parseObject(params);

			String processname = paramsObject.getString("processname");
			if (processname.equals("groupbrand")) {
				PlmGroupBrandEntity_HI entity = plmGroupBrandServer
						.saveConfirmedPlmGroupStatus(paramsObject);
				return SToolUtils.convertResultJSONObj(SUCCESS_STATUS,
						SUCCESS_MSG, 1, entity).toString();
			} else if (processname.equals("brandinfo")) {
				PlmBrandInfoEntity_HI entity = plmBrandInfoServer
						.saveConfirmedPlmBrandStatus(paramsObject);
				return SToolUtils.convertResultJSONObj(SUCCESS_STATUS,
						SUCCESS_MSG, 1, entity).toString();
			} else if (processname.equals("mother")) {
				PlmMotherCompanyEntity_HI entity = plmMotherCompanyServer
						.saveConfirmedPlmMotherCompanyStatus(paramsObject);
				return SToolUtils.convertResultJSONObj(SUCCESS_STATUS,
						SUCCESS_MSG, 1, entity).toString();
			}
			return null;
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS,
					e.getMessage(), 0, null).toString();
		}
	}
}
