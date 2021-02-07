package com.sie.watsons.base.proposal.services;

import com.alibaba.fastjson.JSON;
import com.sie.watsons.base.proposal.model.entities.TtaDeptFeeLineEntity_HI;
import com.sie.watsons.base.proposal.model.entities.readonly.TtaDeptFeeLineEntity_HI_RO;
import com.sie.watsons.base.proposal.model.inter.ITtaDeptFeeLine;

import com.alibaba.fastjson.JSONObject;
import com.sie.watsons.report.model.inter.ITtaContractLineReport;
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
import java.util.Map;

import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.model.inter.IBaseCommon;

@RestController
@RequestMapping("/ttaDeptFeeLineService")
public class TtaDeptFeeLineService extends CommonAbstractService{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaDeptFeeLineService.class);

	@Autowired
	private ITtaDeptFeeLine ttaDeptFeeLineServer;

	@Override
	public IBaseCommon getBaseCommonServer(){
		return this.ttaDeptFeeLineServer;
	}

	@Autowired
	private ITtaContractLineReport ttaContractLineReport;

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
			Pagination<TtaDeptFeeLineEntity_HI_RO> result = ttaDeptFeeLineServer.find(jsonObject, pageIndex, pageRows);
			System.out.print("zidong2============");
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
	 *
	 * @return
	 * @description 查找往年的部门协定标准信息
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findDeptFeeLFindByOldYear")
	public String findDeptFeeLFindByOldYear(@RequestParam(required = false) String params) {

		try{
			JSONObject jsonObject = JSON.parseObject(params);
			JSONObject instance = ttaDeptFeeLineServer.findDeptFeeLFindByOldYear(jsonObject);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "查询成功", instance.size(), instance).toString();
		}catch (Exception e){
			LOGGER.error(e.getMessage(),e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS,"服务异常," + e.getMessage(), 0, null).toString();
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
			TtaDeptFeeLineEntity_HI instance = ttaDeptFeeLineServer.saveOrUpdate(jsonObject, userId);
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
				ttaDeptFeeLineServer.delete(Integer.parseInt(pkId));
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
			TtaDeptFeeLineEntity_HI_RO instance = ttaDeptFeeLineServer.findByRoId(jsonObject);
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, instance).toString();

		}catch (Exception e){
			LOGGER.error(e.getMessage(),e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}

	/**
	 *
	 * @param params
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findDeptFeeLineReport")
	public String findDeptFeeLineReport(@RequestParam(required = false) String params) {
		try{
			JSONObject jsonObject = this.parseObject(params);
			JSONObject instance = ttaDeptFeeLineServer.findDeptFeeLineReport(jsonObject);
			String companyNameDic = ttaContractLineReport.findDicUniqueResult("PARTY_A_COMPANY", "1");//固定值
			instance.put("companyNameDic", companyNameDic);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", instance.size(), instance).toString();
		}catch (Exception e){
			LOGGER.error(e.getMessage(),e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "findDeptFeeShowHideByProposal")
	public String findDeptFeeShowHideByProposal(@RequestParam(required = false) String params) {
		try{
			JSONObject jsonObject = this.parseObject(params);
			JSONObject instance = ttaDeptFeeLineServer.findDeptFeeShowHideByProposal(jsonObject);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "查询成功", instance.size(), instance).toString();
		}catch (Exception e){
			LOGGER.error(e.getMessage(),e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * @param params    {
	 *                  }
	 * @param pageIndex
	 * @param pageRows
	 * @return
	 * @description 查询列表（带分页 字典）
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findLov")
	public String findLov(@RequestParam(required = false) String params,
						  @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
						  @RequestParam(required = false, defaultValue = "10") Integer pageRows) {
		try {

			JSONObject jsonObject = new JSONObject();
			if (StringUtils.isNotBlank(params)) {
				jsonObject = JSON.parseObject(params);
			}
			Pagination<TtaDeptFeeLineEntity_HI_RO> result = ttaDeptFeeLineServer.find(jsonObject, pageIndex, pageRows);
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
	 * 查找部门的标准值和单位
	 * @param params
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "ttaDeptFeeLFindSearchPromotionCost")
	public String ttaDeptFeeLFindSearchPromotionCost(@RequestParam(required = false) String params) {
		try{
			JSONObject jsonObject = JSON.parseObject(params);
			List<Map<String,Object>> mapList = ttaDeptFeeLineServer.ttaDeptFeeLFindSearchPromotionCost(jsonObject);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "查询成功", mapList.size(), mapList).toString();
		}catch (Exception e){
			LOGGER.error(e.getMessage(),e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * @param params
	 * @description 删除部门协定标准 促销陈列服务费和宣传单费用数据
	 */
	@RequestMapping(method = RequestMethod.POST, value = "deleteDeptFeeByProposalAndDmFlyer")
	public String deleteDeptFeeByProposalAndDmFlyer(@RequestParam(required = false) String params) {
		try {
			if (StringUtils.isBlank(params)) {
				return SToolUtils.convertResultJSONObj("F", "缺少参数 proposalId", 0, null).toString();
			}
			JSONObject jsonObject = JSON.parseObject(params);
			ttaDeptFeeLineServer.deleteDeptFeeByProposalAndDmFlyer(jsonObject);
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, null).toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
		}
	}

}