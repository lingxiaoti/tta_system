package com.sie.watsons.base.plmBase.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.watsons.base.plmBase.model.entities.readonly.PlmRmsSupplierInfoEntity_HI_RO;
import com.sie.watsons.base.plmBase.model.inter.IPlmRmsSupplierInfo;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/plmRmsSupplierInfoService")
public class PlmRmsSupplierInfoService extends CommonAbstractService {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmRmsSupplierInfoService.class);
	@Autowired
	private IPlmRmsSupplierInfo plmRmsSupplierInfoServer;
	public PlmRmsSupplierInfoService() {
		super();
	}
	@Override
	public IBaseCommon getBaseCommonServer(){
		return this.plmRmsSupplierInfoServer;
	}
	/**
	 * 查询
	 *
	 * @param params
	 * {
	 *     teamFrameworkId:框架ID
	 * }
	 * @param pageIndex 当前页码
	 * @param pageRows 页面行数
	 * @return Pagination
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findPlmRmsSupplierInfoInfo")
	public String findPlmRmsSupplierInfoInfo(@RequestParam(required = false) String params,@RequestParam(required = false,defaultValue = "1") Integer pageIndex,
											 @RequestParam(required = false,defaultValue = "10") Integer pageRows){
		JSONObject queryParamJSON = parseObject(params);
		Pagination<PlmRmsSupplierInfoEntity_HI_RO> dataList = plmRmsSupplierInfoServer.findPlmRmsSupplierInfoInfo(queryParamJSON,pageIndex,pageRows);
		queryParamJSON = (JSONObject) JSON.toJSON(dataList);
		queryParamJSON.put(SToolUtils.STATUS, "S");
		queryParamJSON.put(SToolUtils.MSG, SUCCESS_MSG);
		return queryParamJSON.toString();
	}

	/**
	 * @param params
	 * @return java.lang.String
	 * @description   保存
	 **/
	@RequestMapping(method = RequestMethod.POST,value = "savePlmRmsSupplierInfoInfo")
	public String savePlmRmsSupplierInfoInfo(@RequestParam(required = true) String params){
		try{
			JSONObject parseObject = parseObject(params);
			int userId = parseObject.getInteger("varUserId");
			JSONObject jsonObject = (JSONObject)JSONObject.toJSON(plmRmsSupplierInfoServer.savePlmRmsSupplierInfoInfo(parseObject));
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, jsonObject).toString();

		}catch (IllegalArgumentException e){
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("M", e.getMessage(), 0, null).toString();
		}catch (Exception e){
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
		}
	}

}
