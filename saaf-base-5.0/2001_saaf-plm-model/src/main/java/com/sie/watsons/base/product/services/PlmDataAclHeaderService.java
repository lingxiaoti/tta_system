package com.sie.watsons.base.product.services;

import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.product.model.entities.PlmDataAclHeaderEntity_HI;
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
import com.sie.watsons.base.product.model.entities.readonly.PlmDataAclHeaderEntity_HI_RO;
import com.sie.watsons.base.product.model.inter.IPlmDataAclHeader;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;

@RestController
@RequestMapping("/plmDataAclHeaderService")
public class PlmDataAclHeaderService extends CommonAbstractService {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(PlmDataAclHeaderService.class);

	@Autowired
	private IPlmDataAclHeader plmDataAclHeaderServer;

	@Override
	public IBaseCommon getBaseCommonServer() {
		return this.plmDataAclHeaderServer;
	}

	/**
	 * 2018/08/30
	 * 
	 * @Title:
	 * @param @param params
	 * @param @param pageIndex
	 * @param @param pageRows
	 * @param @return 参数
	 * @return json字符串
	 * @throws
	 * @author:caojin
	 */
	@RequestMapping(method = RequestMethod.POST, value = "FindDataAclInfo")
	public String findProductPage(
			@RequestParam(required = false) String params,
			@RequestParam(required = false, defaultValue = "1") Integer pageIndex,
			@RequestParam(required = false, defaultValue = "10") Integer pageRows) {
		try {
			JSONObject param = parseObject(params);
			Pagination<PlmDataAclHeaderEntity_HI_RO> results = plmDataAclHeaderServer
					.FindDataAclInfo(param, pageIndex, pageRows);
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
	 * 2020/03/17
	 * 
	 * @Title:
	 * @param @param params
	 * @param @param pageIndex
	 * @param @param pageRows
	 * @param @return 参数
	 * @return json字符串
	 * @throws
	 * @author:caojin
	 */
	@RequestMapping(method = RequestMethod.POST, value = "FindDataAclInfoByDeptOrId")
	public String FindDataAclInfoByDeptId(
			@RequestParam(required = false) String params) {
		try {
			JSONObject param = parseObject(params);
			JSONObject Detail = plmDataAclHeaderServer.DataAclInfo(param);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG,
					1, Detail).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS,
					e.getMessage(), 0, null).toString();
		}
	}


	/**
	 * 2020/07/02
	 *
	 * @Title:
	 * @param @param params
	 * @param @param pageIndex
	 * @param @param pageRows
	 * @param @return 参数
	 * @return json字符串
	 * @throws
	 * @author:caojin
	 */
	@RequestMapping(method = RequestMethod.POST, value = "saveOrUpdateAclHeadInfo")
	public String saveAclHeadInfo(
			@RequestParam(required = false) String params) {
		try {
			JSONObject param = parseObject(params);
			if(!param.containsKey("deptId")){throw new Exception("缺少参数deptId!");}
			if(!param.containsKey("deptName")){throw new Exception("缺少参数deptName!");}

         if(!param.containsKey("headId")) {
			 Integer deptId = param.getInteger("deptId");
			 JSONObject deptidjson = new JSONObject();
			 deptidjson.put("deptId", deptId);
			 if (plmDataAclHeaderServer.findList(deptidjson).size() > 0) {
				 throw new Exception("deptId已经存在!");
			 }
		 }
			PlmDataAclHeaderEntity_HI  headinfo=plmDataAclHeaderServer.saveOrUpdate(param);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG,
					1, headinfo).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS,
					e.getMessage(), 0, null).toString();
		}
	}


	/**
	 * 2020/07/09
	 *
	 * @Title:
	 * @param @param params
	 * @param @param pageIndex
	 * @param @param pageRows
	 * @param @return 参数
	 * @return json字符串
	 * @throws
	 * @author:caojin
	 */
	@RequestMapping(method = RequestMethod.POST, value = "deleteAclHeadById")
	public String deleteAclHeadById(
			@RequestParam(required = false) String params) {
		try {
			JSONObject param = parseObject(params);
			SaafToolUtils.validateJsonParms(param, "headId");

			Integer headId=param.getInteger("headId");
			plmDataAclHeaderServer.deleteById(headId);

			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG,
					1, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS,
					e.getMessage(), 0, null).toString();
		}
	}

}