package com.sie.watsons.base.pos.qualificationreview.services;

import com.sie.watsons.base.pos.qualificationreview.model.entities.EquPosZzscAssociateSuppEntity_HI;
import com.sie.watsons.base.pos.qualificationreview.model.inter.IEquPosZzscAssociateSupp;

import com.alibaba.fastjson.JSONObject;
import com.yhg.base.utils.SToolUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.model.inter.IBaseCommon;

@RestController
@RequestMapping("/equPosZzscAssociateSuppService")
public class EquPosZzscAssociateSuppService extends CommonAbstractService{
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosZzscAssociateSuppService.class);

	@Autowired
	private IEquPosZzscAssociateSupp equPosZzscAssociateSuppServer;

	@Override
	public IBaseCommon getBaseCommonServer(){
		return this.equPosZzscAssociateSuppServer;
	}

	/**
	 * 资质审查-供应商关联工厂查询
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findZzscAssociatedSupplier")
	public String findZzscAssociatedSupplier(@RequestParam(required = false) String params,
										 @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
										 @RequestParam(required = false, defaultValue = "10") Integer pageRows) {
		try {
			JSONObject paramsJONS =this.parseJson(params);
			JSONObject result  =equPosZzscAssociateSuppServer.findZzscAssociatedSupplier(paramsJONS,pageIndex,pageRows);
			result.put(SToolUtils.STATUS, "S");
			result.put(SToolUtils.MSG, SUCCESS_MSG);
			return result.toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 资质审查-供应商关联工厂保存
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "saveZzscAssociatedSupplier")
	public String saveZzscAssociatedSupplier(@RequestParam(required = false) String params) {
		try {
			JSONObject jsonObject = parseObject(params);
			EquPosZzscAssociateSuppEntity_HI instance = equPosZzscAssociateSuppServer.saveZzscAssociatedSupplier(jsonObject);
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 1, instance).toString();
		}  catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 资质审查-供应商关联工厂删除
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "deleteZzscAssociatedSupplier")
	public String deleteZzscAssociatedSupplier(@RequestParam(required = true) String params) {
		try {
			JSONObject jsonObject = parseObject(params);
			return equPosZzscAssociateSuppServer.deleteZzscAssociatedSupplier(jsonObject);
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
		}
	}
}