package com.sie.watsons.base.withdrawal.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.sie.watsons.base.exclusive.model.entities.TtaSoleSupplierEntity_HI;
import com.sie.watsons.base.supplier.model.entities.readonly.TtaRelSupplierEntity_HI_RO;
import com.sie.watsons.base.supplier.model.entities.readonly.TtaSupplierEntity_HI_RO;
import com.sie.watsons.base.supplier.model.inter.ITtaRelSupplier;
import com.sie.watsons.base.withdrawal.model.entities.TtaSupplierItemRelSupplierEntity_HI;
import com.sie.watsons.base.withdrawal.model.entities.readonly.TtaSupplierItemRelSupplierEntity_HI_RO;
import com.sie.watsons.base.withdrawal.model.inter.ITtaSupplierItemRelSupplier;

import com.alibaba.fastjson.JSONObject;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.model.inter.IBaseCommon;

/**
 * proposal拆分与合并模块:供应商关联关联供应商表查询
 */
@RestController
@RequestMapping("/ttaSupplierItemRelSupplierService")
public class TtaSupplierItemRelSupplierService extends CommonAbstractService{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaSupplierItemRelSupplierService.class);

	@Autowired
	private ITtaSupplierItemRelSupplier ttaSupplierItemRelSupplierServer;


	@Override
	public IBaseCommon getBaseCommonServer(){
		return this.ttaSupplierItemRelSupplierServer;
	}

	/**
	 * @param params    {
	 *                  }
	 * @param pageIndex
	 * @param pageRows
	 * @return
	 * @description 查询关联供应商列表（带分页）
	 */
	@RequestMapping(method = RequestMethod.POST, value = "find")
	public String find(@RequestParam(required = false) String params,
					   @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
					   @RequestParam(required = false, defaultValue = "10") Integer pageRows) {
		try {

			JSONObject jsonObject = this.parseObject(params);
			Pagination<TtaSupplierItemRelSupplierEntity_HI_RO> supplierItemRelSupplierList = ttaSupplierItemRelSupplierServer.findSupplierItemRelSupplierList(jsonObject, pageIndex, pageRows);
			Pagination<TtaSupplierItemRelSupplierEntity_HI_RO> result = supplierItemRelSupplierList;
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
	 * @description 根据头信息的供应商查询关联供应商（带分页）
	 */
	@RequestMapping(method = RequestMethod.POST, value = "selectSupplierItemRelSupplierList")
	public String selectSupplierItemRelSupplierList(@RequestParam(required = false) String params,
					   @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
					   @RequestParam(required = false, defaultValue = "10") Integer pageRows) {
		try {

			JSONObject jsonObject = new JSONObject();
			if (StringUtils.isNotBlank(params)) {
				jsonObject = JSON.parseObject(params);
			}
			Pagination<TtaRelSupplierEntity_HI_RO> result = ttaSupplierItemRelSupplierServer.selectSupplierItemRelSupplierList(jsonObject, pageIndex, pageRows);
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
	 * proposal拆分与合并,保存关联供应商数据
	 * @param params
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST,value = "ttaSupplierItemRelSupplierSave")
	public String ttaSupplierItemRelSupplierSave(@RequestParam(required = true) String params){
		try{
			JSONObject queryParamJSON = parseObject(params);
			List<TtaSupplierItemRelSupplierEntity_HI> list= ttaSupplierItemRelSupplierServer.ttaSupplierItemRelSupplierSave(queryParamJSON);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", 1,list).toString();
		}catch(Exception e){
			LOGGER.error(e.getMessage(),e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * proposal拆分与合并,删除关联供应商数据
	 * @param params
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "ttaSupplierItemRelSupplierDelete")
	public String ttaSupplierItemRelSupplierDelete(@RequestParam(required = false) String params) {
		String result = null;
		try {
			JSONObject jsonObject = this.parseObject(params);
			Integer id = jsonObject.getInteger("id");
			Assert.notNull(id, "参数id错误");
			TtaSupplierItemRelSupplierEntity_HI ttaSoleSupplierEntity_hi = ttaSupplierItemRelSupplierServer.ttaSupplierItemRelSupplierDelete(id);
			result = this.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, ttaSoleSupplierEntity_hi);
		} catch (Exception e) {
			LOGGER.error(".ttaSupplierItemRelSupplierDelete:{}" , e);
			result = this.convertResultJSONObj(ERROR_STATUS, ERROR_MSG, null);
		}
		LOGGER.info(".ttaSupplierItemRelSupplierDelete 入参信息:{},出参信息:{}", new Object[]{params, result});
		return result;
	}

}