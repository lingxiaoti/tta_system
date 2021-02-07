package com.sie.watsons.base.pon.project.services;

import com.sie.watsons.base.pon.project.model.entities.EquPonProductSpecsEntity_HI;
import com.sie.watsons.base.pon.project.model.inter.IEquPonProductSpecs;

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
@RequestMapping("/equPonProductSpecsService")
public class EquPonProductSpecsService extends CommonAbstractService{
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPonProductSpecsService.class);

	@Autowired
	private IEquPonProductSpecs equPonProductSpecsServer;

	@Override
	public IBaseCommon getBaseCommonServer(){
		return this.equPonProductSpecsServer;
	}

	/**
	 * 报价管理-产品规格查询，分页查询
	 * @param params 参数：密级Entity中的字段
	 * @param pageIndex
	 * @param pageRows
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findProductSpecs")
	public String findProductSpecs(@RequestParam(required = false) String params,
								  @RequestParam(required = false,defaultValue = "1") Integer pageIndex,
								  @RequestParam(required = false,defaultValue = "999") Integer pageRows) {
		try {
			JSONObject paramsJONS =this.parseJson(params);
			JSONObject result  =equPonProductSpecsServer.findProductSpecs(paramsJONS,pageIndex,pageRows);
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
	 * 报价管理-产品规格查询保存
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "saveProductSpecs")
	public String saveProductSpecs(@RequestParam(required = false) String params) {
		try {
			JSONObject jsonObject = parseObject(params);
			EquPonProductSpecsEntity_HI instance = equPonProductSpecsServer.saveProductSpecs(jsonObject);
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
	 * 报价管理-产品规格查询删除
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "deleteProductSpecs")
	public String deleteProductSpecs(@RequestParam(required = false) String params) {
		try {
			JSONObject jsonObject = parseObject(params);
			equPonProductSpecsServer.deleteProductSpecs(jsonObject);
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 1, null).toString();
		}  catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 批量导入
	 * @param params
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "importProductSpecsInfo")
	public String importProductSpecsInfo(@RequestParam(required = true) String params){
		try{
			JSONObject jsonObject = parseObject(params);
			int size = equPonProductSpecsServer.importProductSpecsInfo(jsonObject);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "总共"+size+"条数据导入成功", size, null).toString();
		}catch (Exception e){
			return SToolUtils.convertResultJSONObj(e.getMessage().startsWith("[{") ? "ERR_IMPORT" : "E", e.getMessage(), 0, e.getMessage()).toString();
		}
	}
}