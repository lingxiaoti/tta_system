package com.sie.watsons.base.contract.services;

import com.alibaba.fastjson.JSON;
import com.sie.watsons.base.contract.model.entities.TtaContractRecordHeaderEntity_HI;
import com.sie.watsons.base.contract.model.entities.readonly.TtaContractRecordHeaderEntity_HI_RO;
import com.sie.watsons.base.contract.model.inter.ITtaContractRecordHeader;

import com.alibaba.fastjson.JSONObject;
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
import java.util.List;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.model.inter.IBaseCommon;

@RestController
@RequestMapping("/ttaContractRecordHeaderService")
public class TtaContractRecordHeaderService extends CommonAbstractService{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaContractRecordHeaderService.class);

	@Autowired
	private ITtaContractRecordHeader ttaContractRecordHeaderServer;

	@Override
	public IBaseCommon getBaseCommonServer(){
		return this.ttaContractRecordHeaderServer;
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
			jsonObject = parseObject(params);
			Pagination<TtaContractRecordHeaderEntity_HI_RO> result = ttaContractRecordHeaderServer.find(jsonObject, pageIndex, pageRows);
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
	@RequestMapping(method = RequestMethod.POST, value = "saveOrUpdateALL")
	public String saveOrUpdateSplitALL(@RequestParam(required = true) String params) {
		try {
			int userId = getSessionUserId();
			JSONObject jsonObject = parseObject(params);
			List<TtaContractRecordHeaderEntity_HI> infoList = ttaContractRecordHeaderServer.saveOrUpdateALL(jsonObject, userId );
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, infoList).toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 检查同一年度是否有相同的供应商领用了合同
	 * @param params
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findContractVendor")
	public String findContractVendor(@RequestParam(required = false) String params) {
		try {
			int userId = getSessionUserId();
			JSONObject jsonObject = this.parseObject(params);
			ttaContractRecordHeaderServer.findContractVendor(jsonObject,userId);
			return SToolUtils.convertResultJSONObj("S", "操作成功" , 0, null).toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "服务异常" + e.getMessage(), 0, null).toString();
		}
	}

}