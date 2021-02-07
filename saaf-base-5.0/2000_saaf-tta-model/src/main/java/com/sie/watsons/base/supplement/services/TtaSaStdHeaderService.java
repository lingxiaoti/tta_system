package com.sie.watsons.base.supplement.services;

import com.alibaba.fastjson.JSON;
import com.sie.saaf.common.bean.UserSessionBean;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.proposal.model.entities.TtaProposalHeaderEntity_HI;
import com.sie.watsons.base.supplement.model.entities.TtaSaStdHeaderEntity_HI;
import com.sie.watsons.base.supplement.model.entities.readonly.TtaSaStdHeaderEntity_HI_RO;
import com.sie.watsons.base.supplement.model.inter.ITtaSaStdHeader;

import com.alibaba.fastjson.JSONObject;
import com.sie.watsons.base.ttasastdtpl.model.entities.readonly.TtaSaStdFieldCfgLineEntity_HI_RO;
import com.sie.watsons.base.ttasastdtpl.model.entities.readonly.TtaSaStdTplDefHeaderEntity_HI_RO;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.model.inter.IBaseCommon;

/**
 * 补充协议标准信息头信息
 */
@RestController
@RequestMapping("/ttaSaStdHeaderService")
public class TtaSaStdHeaderService extends CommonAbstractService{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaSaStdHeaderService.class);

	@Autowired
	private ITtaSaStdHeader ttaSaStdHeaderServer;

	@Override
	public IBaseCommon getBaseCommonServer(){
		return this.ttaSaStdHeaderServer;
	}

	/**
	 * 查询补充协议头信息
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@PostMapping("find")
	public String findTtaSupplierItemHeaderEntityHIPage(@RequestParam(required = false) String params,
														@RequestParam(required = false, defaultValue = "1") Integer pageIndex,
														@RequestParam(required = false, defaultValue = "10") Integer pageRows) throws Exception {
		try {
			JSONObject parameters = this.parseObject(params);
			Pagination<TtaSaStdHeaderEntity_HI_RO> page = ttaSaStdHeaderServer.find(parameters, pageIndex, pageRows);
			JSONObject results = JSONObject
					.parseObject(JSON.toJSONString(page));
			results.put(SToolUtils.STATUS, SUCCESS_STATUS);
			results.put(SToolUtils.MSG, "成功");
			return results.toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error("params_" + params + "_e_" + e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "查询失败", 0, e)
					.toJSONString();
		}
	}

	/**
	 * 补充协议模板树查询
	 * @param params
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findTemplateTreeData")
	public String findTemplateTreeData(@RequestParam(required = false) String params) {
		try {
			JSONObject queryParamJSON = parseObject(params);
			List<TtaSaStdTplDefHeaderEntity_HI_RO> findList = ttaSaStdHeaderServer.findSupplementAgrTemlateTreeList(queryParamJSON);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", findList.size(), findList).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * @param params
	 * @description 补充协议标准保存/修改
	 */
	@RequestMapping(method = RequestMethod.POST, value = "saveOrUpdate")
	public String saveOrUpdate(@RequestParam(required = true) String params) {
		try {
			//int userId = getSessionUserId();
			JSONObject jsonObject = JSON.parseObject(params);
			UserSessionBean userSessionBean = this.getUserSessionBean();
			TtaSaStdHeaderEntity_HI instance = ttaSaStdHeaderServer.saveOrUpdate(jsonObject, userSessionBean);
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
	 * 查询详情
	 * @param params
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findById")
	@Override
	public String findById(String params) {
		try{
			JSONObject jsonObject = this.parseObject(params);
			Integer saStdHeaderId = jsonObject.getInteger("saStdHeaderId");
			//这里做判断,是为了解决流程中代办页面进来,数据错乱的问题
			if (SaafToolUtils.isNullOrEmpty(saStdHeaderId)) {
				jsonObject.fluentPut("saStdHeaderId", jsonObject.getInteger("id"));
			}
			TtaSaStdHeaderEntity_HI_RO instance = ttaSaStdHeaderServer.findByRoId(jsonObject);
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, instance).toString();
		}catch (Exception e){
			LOGGER.error(e.getMessage(),e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * @param params
	 * @description 节点点击,查询补充协议拓展信息
	 */
	@RequestMapping(method = RequestMethod.POST, value = "supplementExpandFind")
	public String supplementExpandFind(@RequestParam(required = true) String params) {
		try {
			int userId = getSessionUserId();
			JSONObject jsonObject = JSON.parseObject(params);
			List<TtaSaStdFieldCfgLineEntity_HI_RO> list = ttaSaStdHeaderServer.saveFindSupplementExpandInfo(jsonObject,userId);
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, list).toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 作废单据
	 * @param params
	 * @return
	 */
	@PostMapping("disSicrad")
	public String disSicrad(@RequestParam(required = true) String params) {
		try {
			JSONObject parameters = this.parseObject(params);
			int userId = this.getSessionUserId();
			TtaSaStdHeaderEntity_HI instance = ttaSaStdHeaderServer.saveByDisSicradHeader(parameters, userId);
			return SToolUtils.convertResultJSONObj("S", "操作成功", 1, instance).toJSONString();
		} catch (Exception e) {
			LOGGER.error("params_" + params + "_e_" + e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toJSONString();
		}
	}

	/**
	 * 更新单据状态
	 * @param params
	 * @return
	 */
	@PostMapping("updateStatus")
	public String updateStatus(@RequestParam(required = true) String params) {
		try {
			JSONObject parameters = this.parseObject(params);
			int userId = this.getSessionUserId();
			ttaSaStdHeaderServer.updateStatus(parameters, userId,getUserSessionBean());
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 1, null).toJSONString();
		} catch (Exception e) {
			LOGGER.error("params_" + params + "_e_" + e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "服务异常:" + e.getMessage(), 0, null).toJSONString();
		}
	}

	/**
	 * 变更单据
	 * @param params
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST,value = "supplementAgreementStandardChange")
	public String changProposalBillStatus(@RequestParam(required = true) String params){
		try {
			Integer userId = getSessionUserId();
			JSONObject jsonObject =this.parseObject(params);
			JSONObject instance = ttaSaStdHeaderServer.callSupplementAgreementStandardChangStatus(jsonObject, userId);
			instance.put(SToolUtils.STATUS, "S");
			instance.put(SToolUtils.MSG, SUCCESS_MSG);
			return instance.toString();
		}catch (IllegalArgumentException e){
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		}catch (Exception e){
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 合同打印
	 * @param params
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "print")
	public String export(@RequestParam(required = true) String params) {
		try {
			JSONObject jsonObject = parseObject(params);
			UserSessionBean userSessionBean = getUserSessionBean();
			Integer userId = this.getSessionUserId();
			Long path = ttaSaStdHeaderServer.savePrint(jsonObject,userSessionBean,userId);
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, path).toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
		}
	}

	/**
	 *更新GM审批状态
	 * @param params
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "updateSupplementSkipStatus")
	public String updateSkipStatus(@RequestParam(required = true) String params) {
		try{
			JSONObject jsonObject = this.parseObject(params);
			TtaSaStdHeaderEntity_HI instance = ttaSaStdHeaderServer.updateSupplementSkipStatus(jsonObject,this.getUserSessionBean());
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, instance).toString();
		}catch (Exception e){
			LOGGER.error(e.getMessage(),e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}

}