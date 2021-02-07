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
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.watsons.base.plmBase.model.entities.PlmBaseLevelEntity_HI;
import com.sie.watsons.base.plmBase.model.entities.readonly.PlmBaseLevelEntity_HI_RO;
import com.sie.watsons.base.plmBase.model.inter.IPlmBaseLevel;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;

@RestController
@RequestMapping("/plmBaseLevelService")
public class PlmBaseLevelService extends CommonAbstractService {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(PlmBaseLevelService.class);

	@Autowired
	private IPlmBaseLevel plmBaseLevelServer;

	@Override
	public IBaseCommon getBaseCommonServer() {
		return this.plmBaseLevelServer;
	}

	@RequestMapping(method = RequestMethod.POST, value = "findBaseLevelinfo")
	public String findBaseLevelinfo(
			@RequestParam(required = false) String params,
			@RequestParam(required = false, defaultValue = "1") Integer pageIndex,
			@RequestParam(required = false, defaultValue = "10") Integer pageRows) {
		JSONObject queryParamJSON = parseObject(params);
		Pagination<PlmBaseLevelEntity_HI_RO> dataList = plmBaseLevelServer
				.findBaseLevelinfo(queryParamJSON, pageIndex, pageRows);
		queryParamJSON = (JSONObject) JSON.toJSON(dataList);
		queryParamJSON.put(SToolUtils.STATUS, "S");
		queryParamJSON.put(SToolUtils.MSG, SUCCESS_MSG);
		return queryParamJSON.toString();
	}

	@RequestMapping(method = RequestMethod.POST, value = "savePlmlevelInfo")
	public String savePlmlevelInfo(@RequestParam(required = false) String params) {
		JSONObject queryParamJSON = parseObject(params);
		String uname = this.getUserSessionBean().getUserFullName();

		queryParamJSON.put("createdstr", uname);
		try {

			PlmBaseLevelEntity_HI obj = plmBaseLevelServer
					.saveOrUpdate(queryParamJSON);

			if (queryParamJSON.containsKey("levelId")) // 修改
			{
				if (queryParamJSON.containsKey("levelName")) {
					JSONObject levelpa = new JSONObject();
					levelpa.put("parentLevelId",
							queryParamJSON.getInteger("levelId"));
					List<PlmBaseLevelEntity_HI> lilevel = plmBaseLevelServer
							.findList(levelpa);
					for (PlmBaseLevelEntity_HI le : lilevel) {
						le.setParentLevelName(queryParamJSON
								.getString("levelName"));

						plmBaseLevelServer.update(le);
					}
				}
			}

			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", 1,
					obj).toString();
		} catch (Exception e) {
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, "操作失败", 1,
					e.getMessage()).toString();
		}
	}

	// 删除
	@RequestMapping(method = RequestMethod.POST, value = "delPlmlevelInfoById")
	public String delPlmlevelInfoById(
			@RequestParam(required = false) String params) {
		JSONObject queryParamJSON = parseObject(params);
		try {
			Integer id = queryParamJSON.getInteger("levelId");
			plmBaseLevelServer.deleteById(id);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", 1,
					id).toString();
		} catch (Exception e) {
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, "操作失败", 1,
					e.getMessage()).toString();
		}
	}

	// 查找
	// // 查找
	@RequestMapping(method = RequestMethod.POST, value = "findPlmLevelInfoById")
	public String findPlmLevelInfoById(
			@RequestParam(required = false) String params) {
		JSONObject queryParamJSON = parseObject(params);
		try {
			Integer id = queryParamJSON.getInteger("levelId");
			PlmBaseLevelEntity_HI l = plmBaseLevelServer.getById(id);
			return SToolUtils
					.convertResultJSONObj(SUCCESS_STATUS, "操作成功", 1, l)
					.toString();
		} catch (Exception e) {
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, "操作失败", 1,
					e.getMessage()).toString();
		}
	}

}