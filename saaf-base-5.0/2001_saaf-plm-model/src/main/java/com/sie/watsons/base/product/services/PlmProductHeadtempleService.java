package com.sie.watsons.base.product.services;

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
import com.sie.watsons.base.product.model.entities.PlmProductHeadtempleEntity_HI;
import com.sie.watsons.base.product.model.entities.readonly.PlmProductHeadtempleEntity_HI_RO;
import com.sie.watsons.base.product.model.inter.IPlmProductHeadtemple;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;

@RestController
@RequestMapping("/plmProductHeadtempleService")
public class PlmProductHeadtempleService extends CommonAbstractService {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(PlmProductHeadtempleService.class);

	@Autowired
	private IPlmProductHeadtemple plmProductHeadtempleServer;

	@Autowired
	private ViewObject<PlmProductHeadtempleEntity_HI> plmProductHeadtempleDAO_HI;

	@Override
	public IBaseCommon getBaseCommonServer() {
		return this.plmProductHeadtempleServer;
	}

	/**
	 * 2018/09/10
	 * 
	 * @Title:
	 * @Description: TODO(商品模板保存方法)
	 * @param @param params
	 * @param @param pageIndex
	 * @param @param pageRows
	 * @param @return 参数
	 * @return String 返回类型
	 * @throws
	 * @author:caojin
	 */
	@RequestMapping(method = RequestMethod.POST, value = "saveProductTemple")
	public String saveProductTemple(
			@RequestParam(required = false) String params) {
		JSONObject queryParamJSON = parseObject(params);
		try {

			PlmProductHeadtempleEntity_HI entity = plmProductHeadtempleServer
					.saveOrUpdate(queryParamJSON);

			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", 1,
					new JSONArray().fluentAdd(entity)).toString();
		} catch (Exception e) {
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, "操作失败", 1,
					e.getMessage()).toString();
		}
	}

	// 查询
	@RequestMapping(method = RequestMethod.POST, value = "findTemple")
	public String findProductTempleByUserId() {
		JSONObject jo = new JSONObject();
		jo.put("createdBy", this.getSessionUserId());
		List<PlmProductHeadtempleEntity_HI> li = plmProductHeadtempleServer
				.findList(jo);
		if (li.size() >= 0) {

			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", 1,
					new JSONArray().fluentAdd(li)).toString();
		}
		return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", 1, null)
				.toString();
	}

	@RequestMapping(method = RequestMethod.POST, value = "findTempleById")
	public String findProductTempleById(
			@RequestParam(required = false) String params) {
		// JSONObject jo = new JSONObject();
		JSONObject jo = parseObject(params);
		Integer id = jo.getInteger("templeId");
		PlmProductHeadtempleEntity_HI entity = plmProductHeadtempleServer
				.getById(id);
		if (entity != null) {
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", 1,
					new JSONArray().fluentAdd(entity)).toString();
		}
		return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", 1, null)
				.toString();
	}

	public static void main(String[] args) {
		JSONObject headjson = new JSONObject();
		headjson.put("templeId", 1);
		headjson.put("productShape", "wrq");
		headjson.put("dayDamage", "qq");
		headjson.put("departmentDescript", "qwr");
		headjson.put("productType", "as");
		headjson.put("orderStatus", 0);
		headjson.put("versionNum", 0);
		System.out.println(headjson.toString());

	}

	/**
	 * 2018/08/30
	 * 
	 * @Title:
	 * @Description: TODO(获取商品列表)
	 * @param @param params
	 * @param @param pageIndex
	 * @param @param pageRows
	 * @param @return 参数
	 * @return json字符串
	 * @throws
	 * @author:caojin
	 */
	@RequestMapping(method = RequestMethod.POST, value = "FindProductTempList")
	public String findProductPage(
			@RequestParam(required = false) String params,
			@RequestParam(required = false, defaultValue = "1") Integer pageIndex,
			@RequestParam(required = false, defaultValue = "10") Integer pageRows) {
		try {
			JSONObject param = parseObject(params);
			// param.put("createdBy", this.getSessionUserId());
			Pagination<PlmProductHeadtempleEntity_HI_RO> results = plmProductHeadtempleServer
					.findProductTempList(param, pageIndex, pageRows);
			String resultString = JSON.toJSONString(results);
			JSONObject jsonObject = JSONObject.parseObject(resultString);
			jsonObject.put(SToolUtils.STATUS, SUCCESS_STATUS);
			jsonObject.put(SToolUtils.MSG, SUCCESS_MSG);
			return jsonObject.toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS,
					e.getMessage(), 0, null).toString();
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "deleteTempleById")
	public String deleteProductTempleById(
			@RequestParam(required = false) String params) {
		// JSONObject jo = new JSONObject();
		JSONObject jo = parseObject(params);
		Integer id = jo.getInteger("templeId");
		// PlmProductHeadtempleEntity_HI entity = plmProductHeadtempleServer
		// .getById(id);
		plmProductHeadtempleServer.deleteById(id);
		// if (entity != null) {
		return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", 1, id)
				.toString();
		// }

	}

}