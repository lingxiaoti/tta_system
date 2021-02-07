package com.sie.watsons.base.proposal.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.watsons.base.proposal.model.entities.TtaTermsHEntity_HI;
import com.sie.watsons.base.proposal.model.entities.readonly.TtaTermsHEntity_HI_RO;
import com.sie.watsons.base.proposal.model.entities.readonly.TtaTermsLEntity_HI_RO;
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

import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.watsons.base.proposal.model.inter.ITtaTermsH;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/ttaTermsHService")
public class TtaTermsHService extends CommonAbstractService{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaTermsHService.class);

	@Autowired
	private ITtaTermsH ttaTermsHServer;

	@Autowired
	private ITtaContractLineReport ttaContractLineReport;

	@Override
	public IBaseCommon getBaseCommonServer(){
		return this.ttaTermsHServer;
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
			Pagination<TtaTermsHEntity_HI_RO> result = ttaTermsHServer.find(jsonObject, pageIndex, pageRows);
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
			TtaTermsHEntity_HI instance = ttaTermsHServer.saveOrUpdate(jsonObject, userId);
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
				ttaTermsHServer.delete(Integer.parseInt(pkId));
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
			JSONObject jsonObject = parseObject(params);
			TtaTermsHEntity_HI_RO instance = ttaTermsHServer.saveFindByRoId(jsonObject);
			//将数据字段前端查询转变为后台查询:TRAD_TERMS_CONTENT 查询贸易条款配置（合同模板）
			String tradTermsContent = "A".equalsIgnoreCase(instance.getInvoiceType()) ? instance.getProposalYear() :  (instance.getProposalYear() + "" + instance.getAgreementEdition());
			String termRemark = ttaContractLineReport.findDicUniqueResult("TRAD_TERMS_CONTENT", tradTermsContent);
			//将数据字段前端查询转变为后台查询:公司名称
			String companyName = ttaContractLineReport.findDicUniqueResult("PARTY_A_COMPANY", "1");//固定值
			//将数据字段前端查询转变为后台查询:合同模板目标退佣说明
			String proposalNewYearRemark = ttaContractLineReport.findDicUniqueResult("TERMS_TARGET", instance.getProposalYear());//跟年份相关
			HashMap<String, Object> dicMap = new HashMap<>();
			dicMap.put("termRemark", termRemark);
			dicMap.put("companyName", companyName);
			dicMap.put("proposalNewYearRemark", proposalNewYearRemark);
			instance.setDicMap(dicMap);
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, instance).toString();
		}catch (Exception e){
			LOGGER.error(e.getMessage(),e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 通过部门协定模块查询terms_line的数据
	 *
	 * @param params
	 *
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findTermLineDataByDeptFee")
	public String findTermLineData(String params) {
		try{
			JSONObject jsonObject = parseObject(params);
			Integer userId = this.getSessionUserId();
			List<TtaTermsLEntity_HI_RO> instance = ttaTermsHServer.saveFindTermLineDataByDeptFee(jsonObject,userId);
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, instance).toString();

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
			Pagination<TtaTermsHEntity_HI_RO> result = ttaTermsHServer.find(jsonObject, pageIndex, pageRows);
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

}