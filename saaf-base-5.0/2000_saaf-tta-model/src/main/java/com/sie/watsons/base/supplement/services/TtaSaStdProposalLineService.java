package com.sie.watsons.base.supplement.services;

import com.alibaba.fastjson.JSON;
import com.sie.saaf.common.bean.UserSessionBean;
import com.sie.watsons.base.exclusive.model.entities.TtaSoleSupplierEntity_HI;
import com.sie.watsons.base.exclusive.model.entities.readonly.TtaSoleSupplierEntity_HI_RO;
import com.sie.watsons.base.proposal.model.entities.readonly.TtaProposalHeaderEntity_HI_RO;
import com.sie.watsons.base.supplement.model.entities.TtaSaStdProposalLineEntity_HI;
import com.sie.watsons.base.supplement.model.entities.TtaSideAgrtLineEntity_HI;
import com.sie.watsons.base.supplement.model.entities.readonly.TtaSaStdProposalLineEntity_HI_RO;
import com.sie.watsons.base.supplement.model.inter.ITtaSaStdProposalLine;

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
import java.util.List;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.model.inter.IBaseCommon;

/**
 * 补充协议PROPOSAL行信息
 */
@RestController
@RequestMapping("/ttaSaStdProposalLineService")
public class TtaSaStdProposalLineService extends CommonAbstractService{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaSaStdProposalLineService.class);

	@Autowired
	private ITtaSaStdProposalLine ttaSaStdProposalLineServer;

	@Override
	public IBaseCommon getBaseCommonServer(){
		return this.ttaSaStdProposalLineServer;
	}

	/**
	 *  查询合同拆分与合并之后的Proposal信息
	 * @param params JSON参数，查询条件的JSON格式
	 * @param pageIndex 页码
	 * @param pageRows 每页查询记录数
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findProposalVendor")
	public String findProposalVendor(@RequestParam(required = false) String params,
								 @RequestParam(required = false,defaultValue = "1") Integer pageIndex,
								 @RequestParam(required = false,defaultValue = "10") Integer pageRows) {
		try{
			JSONObject jsonObject = parseObject(params);
			Pagination<TtaSaStdProposalLineEntity_HI_RO> pagination = ttaSaStdProposalLineServer.findProposalVendor(jsonObject, pageIndex, pageRows);
			JSONObject results = JSONObject.parseObject(JSON.toJSONString(pagination));
			results.put(SToolUtils.STATUS, SUCCESS_STATUS);
			results.put(SToolUtils.MSG, "成功");
			return results.toString();
		}catch(Exception e){
			LOGGER.error(e.getMessage(),e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 保存proposal供应商的数据
	 * @param params
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST,value = "saveContractProposalVendor")
	public String saveContractProposalVendor(@RequestParam(required = true) String params){
		try{
			JSONObject queryParamJSON = parseObject(params);
			UserSessionBean sessionBean = getUserSessionBean();
			TtaSaStdProposalLineEntity_HI instance= ttaSaStdProposalLineServer.saveContractProposalVendor(queryParamJSON,sessionBean);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", 1,instance).toString();
		}catch(Exception e){
			LOGGER.error(e.getMessage(),e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}

	/**
	 *  查找数据
	 * @param params JSON参数，查询条件的JSON格式
	 * @param pageIndex 页码
	 * @param pageRows 每页查询记录数
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "find")
	public String find(@RequestParam(required = false) String params,
					   @RequestParam(required = false,defaultValue = "1") Integer pageIndex,
					   @RequestParam(required = false,defaultValue = "10") Integer pageRows) {
		try{
			JSONObject jsonObject = parseObject(params);
			Pagination<TtaSaStdProposalLineEntity_HI_RO> findList = ttaSaStdProposalLineServer.find(jsonObject, pageIndex, pageRows);
			JSONObject results = JSONObject.parseObject(JSON.toJSONString(findList));
			results.put(SToolUtils.STATUS, SUCCESS_STATUS);
			results.put(SToolUtils.MSG, "成功");
			return results.toString();
		}catch(Exception e){
			LOGGER.error(e.getMessage(),e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}

	/**
	 *删除proposal供应商数据
	 * @param params
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "deleteById")
	public String deleteById(@RequestParam(required = false) String params) {
		String result = null;
		try {
			JSONObject jsonObject = this.parseObject(params);
			TtaSaStdProposalLineEntity_HI instance = ttaSaStdProposalLineServer.delete(jsonObject);
			result = this.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, instance);
		} catch (Exception e) {
			LOGGER.error(".deleteById:{}" , e);
			result = this.convertResultJSONObj(ERROR_STATUS, ERROR_MSG, null);
		}
		LOGGER.info(".deleteById 入参信息:{},出参信息:{}", new Object[]{params, result});
		return result;
	}
}