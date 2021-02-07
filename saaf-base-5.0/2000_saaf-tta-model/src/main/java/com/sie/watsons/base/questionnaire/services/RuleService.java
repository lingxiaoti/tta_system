package com.sie.watsons.base.questionnaire.services;

import java.util.List;

import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.proposal.utils.Util;
import com.sie.watsons.base.questionnaire.model.entities.RuleEntity_HI;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.watsons.base.questionnaire.model.entities.readonly.RulePerson_HI_RO;
import com.sie.watsons.base.questionnaire.model.inter.IRule;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;


/**
 * 问卷调查
 * @author zhangganghui
 */
@RestController
@RequestMapping("/ruleService")
public class RuleService extends CommonAbstractService {
    private static final Logger log = LoggerFactory.getLogger(RuleService.class);
    
    @Autowired
    private IRule iRule;
    
    @Override
    public IBaseCommon<?> getBaseCommonServer() {
        return null;
    }
    
    /**
     * 规则新增与编辑
     * @param params
     * @return
     * @throws Exception 
     */
	@RequestMapping(method = RequestMethod.POST, value = "saveRule")
	public String saveRule(@RequestParam(required = false)String params)  {

		try {
			JSONObject paramsJSON = parseObject(params);
			if (Util.isIllegalSqlChar(paramsJSON.getString("sqlValues"))){
				return SToolUtils.convertResultJSONObj(ERROR_STATUS, "含有非法sql语句！", 0, null).toString();
			}
			RuleEntity_HI result = iRule.saveRule(paramsJSON,paramsJSON.getInteger("varUserId"));
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 1, result).toString();
		} catch (IllegalArgumentException e) {
			log.warn("新增&修改规则信息参数异常:{}--{}", e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			log.warn("新增&修改规则信息异常:{}--{}", e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, "服务异常", 0, null).toString();
		}
	}
	
	/**
     * 删除规则
     * @param params
     * @return
     * @throws Exception 
     */
    @PostMapping("deleteRule")
    public String deleteRule(@RequestParam(required = false) String params) {
        try {
            JSONObject parameters = this.parseObject(params);
            JSONArray jsonArray = parameters.getJSONArray("ruleIds");
            for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject jsonObject = this.parseObject(jsonArray.getString(i));
				SaafToolUtils.validateJsonParms(jsonObject,"ruleId");
				iRule.deleteRule(jsonObject.getInteger("ruleId"));	
			}    
            return SToolUtils.convertResultJSONObj("S", "删除成功", 0, null).toJSONString();
        } catch (Exception e) {
        	log.error("params_" + params + "_e_" + e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "删除失败", 0, e).toJSONString();
        }
    }
    
    /**
     * 查询规则
     * @param params
     * @return
     * @throws Exception 
     */
    @PostMapping("queryRulePag")
    public String queryRulePag(@RequestParam(required = false) String params,
		@RequestParam(required = false, defaultValue = "1") Integer pageIndex,
		@RequestParam(required = false, defaultValue = "5") Integer pageRows) throws Exception {
        try {
			JSONObject parameters = this.parseObject(params);
			Pagination<RulePerson_HI_RO> page = iRule.queryRulePage(parameters, pageIndex, pageRows);
			JSONObject results = JSONObject
					.parseObject(JSON.toJSONString(page));
			results.put(SToolUtils.STATUS, SUCCESS_STATUS);
			results.put(SToolUtils.MSG, "成功");
			return results.toString();
		} catch (Exception e) {
			log.error("params_" + params + "_e_" + e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "查询失败", 0, e)
					.toJSONString();
		} 
    }
    
    /**
     * 查询规则
     * @param params
     * @return
     * @throws Exception 
     */
    @PostMapping("queryRuleList")
    public String queryRuleList(@RequestParam(required = false) String params) throws Exception {
        try {
			JSONObject parameters = this.parseObject(params);
			List<RulePerson_HI_RO> page = iRule.queryRuleList(parameters);
			JSONObject results = JSONObject
					.parseObject(JSON.toJSONString(page));
			results.put(SToolUtils.STATUS, SUCCESS_STATUS);
			results.put(SToolUtils.MSG, "成功");
			return results.toString();
		} catch (Exception e) {
			log.error("params_" + params + "_e_" + e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "查询失败", 0, e)
					.toJSONString();
		} 
    }
    
    /**
     * 查询规则
     * @param params
     * @return
     * @throws Exception 
     */
    @PostMapping("queryRule")
    public String queryRule(@RequestParam(required = false) String params) throws Exception {
        try {
			JSONObject parameters = this.parseObject(params);
			RulePerson_HI_RO page = iRule.queryRule(parameters);
			JSONObject results = JSONObject
					.parseObject(JSON.toJSONString(page));
			results.put(SToolUtils.STATUS, SUCCESS_STATUS);
			results.put(SToolUtils.MSG, "成功");
			return results.toString();
		} catch (Exception e) {
			log.error("params_" + params + "_e_" + e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "查询失败", 0, e)
					.toJSONString();
		} 
    }
	
}
