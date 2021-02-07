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
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.watsons.base.product.model.entities.PlmProductUpdatepropertisEntity_HI;
import com.sie.watsons.base.product.model.entities.readonly.PlmProductHeadEntity_HI_RO;
import com.sie.watsons.base.product.model.entities.readonly.PlmProductUpdatepropertisEntity_HI_RO;
import com.sie.watsons.base.product.model.inter.IPlmProductUpdatepropertis;
import com.sie.watsons.base.redisUtil.ResultUtils;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;

@RestController
@RequestMapping("/plmProductUpdatepropertisService")
public class PlmProductUpdatepropertisService extends CommonAbstractService {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(PlmProductUpdatepropertisService.class);

	@Autowired
	private IPlmProductUpdatepropertis plmProductUpdatepropertisServer;

	@Override
	public IBaseCommon getBaseCommonServer() {
		return this.plmProductUpdatepropertisServer;
	}

	/**
	 * 2018/09/28
	 * 
	 * @Title:
	 * @Description: TODO(获取商品修改字段列表)
	 * @param @param params
	 * @param @param pageIndex
	 * @param @param pageRows
	 * @param @return 参数
	 * @return json字符串
	 * @throws
	 * @author:caojin
	 */
	@RequestMapping(method = RequestMethod.POST, value = "FindProductUpdateList")
	public String findProductPage(
			@RequestParam(required = false) String params,
			@RequestParam(required = false, defaultValue = "1") Integer pageIndex,
			@RequestParam(required = false, defaultValue = "10") Integer pageRows) {
		try {
			JSONObject param = parseObject(params);
			Pagination<PlmProductUpdatepropertisEntity_HI_RO> results = plmProductUpdatepropertisServer
					.findProductUpdateList(param, pageIndex, pageRows);
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

	/**
	 * 2018/09/28
	 * 
	 * @Title:
	 * @Description: TODO(根据businesskey查询信息)
	 * @param @param params
	 * @param @param pageIndex
	 * @param @param pageRows
	 * @param @return 参数
	 * @return json字符串
	 * @throws
	 * @author:caojin
	 */
	@RequestMapping(method = RequestMethod.POST, value = "FindUpdateByHeadid")
	public String FindUpdateByHeadid(
			@RequestParam(required = false) String params,
			@RequestParam(required = false, defaultValue = "1") Integer pageIndex,
			@RequestParam(required = false, defaultValue = "10") Integer pageRows) {
		try {
			JSONObject param = parseObject(params);
			PlmProductUpdatepropertisEntity_HI r = new PlmProductUpdatepropertisEntity_HI();
			String productHeadkey = param.getString("id");
			JSONObject jo = new JSONObject();
			jo.put("productHeadId", productHeadkey);
			List<PlmProductUpdatepropertisEntity_HI> lidata = plmProductUpdatepropertisServer
					.findList(jo);
			if (lidata.size() > 0) {
				r = lidata.get(0);
			}
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS,
					SUCCESS_STATUS, 0, r).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS,
					e.getMessage(), 0, null).toString();
		}
	}

	// 保存
	@RequestMapping(method = RequestMethod.POST, value = "saveUpdateinfo")
	public String savehead(
			@RequestParam(required = false) String params,
			@RequestParam(required = false, defaultValue = "1") Integer pageIndex,
			@RequestParam(required = false, defaultValue = "10") Integer pageRows) {
		try {
			JSONObject param = parseObject(params);
			PlmProductUpdatepropertisEntity_HI pup = plmProductUpdatepropertisServer
					.saveOrUpdate(param);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS,
					SUCCESS_STATUS, 0, pup).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS,
					e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 2018/09/28
	 * 
	 * @Title:
	 * @Description: TODO(获取商品修改字段列表)
	 * @param @param params
	 * @param @param pageIndex
	 * @param @param pageRows
	 * @param @return 参数
	 * @return json字符串
	 * @throws
	 * @author:caojin
	 */
	@RequestMapping(method = RequestMethod.POST, value = "FindUpdateByPropertiesList")
	public String FindUpdateByPropertiesList(
			@RequestParam(required = false) String params,
			@RequestParam(required = false, defaultValue = "1") Integer pageIndex,
			@RequestParam(required = false, defaultValue = "10") Integer pageRows) {
		try {
			JSONObject param = parseObject(params);
			Pagination<PlmProductHeadEntity_HI_RO> results = plmProductUpdatepropertisServer
					.FindUpdateByPropertiesList(param, pageIndex, pageRows);
			// ResultUtils.getLookUpValue("PLM_PRODUT_ORDERSTATUS");
			ResultUtils.getLookUpValue("PLM_PRODUCT_PURCHASE");
			ResultUtils.getLookUpValue("PLM_PRODUCT_ORTHERINFO");
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

}