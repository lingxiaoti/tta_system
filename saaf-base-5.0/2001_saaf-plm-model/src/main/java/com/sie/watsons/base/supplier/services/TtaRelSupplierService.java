package com.sie.watsons.base.supplier.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.watsons.base.supplier.model.entities.TtaRelSupplierEntity_HI;
import com.sie.watsons.base.supplier.model.entities.readonly.TtaRelSupplierEntity_HI_RO;
import com.sie.watsons.base.supplier.model.inter.ITtaRelSupplier;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ttaRelSupplierService")
public class TtaRelSupplierService extends CommonAbstractService{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaRelSupplierService.class);

	@Autowired
	private ITtaRelSupplier ttaRelSupplierServer;

	@Override
	public IBaseCommon getBaseCommonServer(){
		return this.ttaRelSupplierServer;
	}


	/**
	 * @param params    {
	 *                  }
	 * @param pageIndex
	 * @param pageRows
	 * @return
	 * @description 查询列表（带分页）
	 */
	@RequestMapping(method = RequestMethod.POST, value = "find")
	public String find(@RequestParam(required = false) String params,
					   @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
					   @RequestParam(required = false, defaultValue = "10") Integer pageRows) {
		try {

			JSONObject jsonObject = new JSONObject();
			if (StringUtils.isNotBlank(params)) {
				jsonObject = JSON.parseObject(params);
			}
			Pagination<TtaRelSupplierEntity_HI_RO> result = ttaRelSupplierServer.find(jsonObject, pageIndex, pageRows);
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
	 * @param params
	 * @description 保存/修改
	 */
	@RequestMapping(method = RequestMethod.POST, value = "saveOrUpdate")
	public String saveOrUpdate(@RequestParam(required = true) String params) {
		try {
			int userId = getSessionUserId();
			JSONObject jsonObject = JSON.parseObject(params);
			TtaRelSupplierEntity_HI instance = ttaRelSupplierServer.saveOrUpdate(jsonObject, userId);
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, instance).toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * @param params- 主键
	 * @description 删除数据
	 */
	@RequestMapping(method = RequestMethod.POST, value = "delete")
	public String delete(@RequestParam(required = false) String params) {
		try {
			if (StringUtils.isBlank(params)) {
				return SToolUtils.convertResultJSONObj("F", "缺少参数:id", 0, null).toString();
			}
			JSONObject jsonObject = JSON.parseObject(params);
			String[] ids = jsonObject.getString("ids").split(",");
			for (String pkId : ids) {
				ttaRelSupplierServer.delete(Integer.parseInt(pkId));
			}
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, null).toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
		}
	}
	/**
	 * 查询详情
	 *
	 * @param params
	 *
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findById")
	@Override
	public String findById(String params) {
		try{
			JSONObject jsonObject = JSON.parseObject(params);
			TtaRelSupplierEntity_HI_RO instance = ttaRelSupplierServer.findByRoId(jsonObject);
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, instance).toString();

		}catch (Exception e){
			LOGGER.error(e.getMessage(),e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}

}