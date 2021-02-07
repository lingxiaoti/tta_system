package com.sie.watsons.base.rule.services;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.watsons.base.questionnaire.model.entities.SaafQuestionnaireLEntity_HI;
import com.sie.watsons.base.rule.model.entities.readonly.RuleLEntity_HI_RO;
import com.sie.watsons.base.rule.model.entities.readonly.SubjectEntity_HI_RO;
import com.sie.watsons.base.rule.model.inter.IRuleH;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;

@RestController
@RequestMapping("/ruleHService")
public class RuleHService extends CommonAbstractService{
	private static final Logger LOGGER = LoggerFactory.getLogger(RuleHService.class);

	@Autowired
	private IRuleH iRuleH;

	@Override
	public IBaseCommon getBaseCommonServer(){
		return this.iRuleH;
	}
	
	/**
     * 新增头
     * @param params
     * @return
     * @throws Exception 
     */        
	@PostMapping("editRuleHeader")
	public String editRuleHeader(@RequestParam(required = false)String params)  { 
		try {
			JSONObject parameters = this.parseObject(params);
			String result = iRuleH.editRuleHeader(parameters);
			if(!StringUtils.isNumeric(result)) {
				return SToolUtils.convertResultJSONObj("E", "保存失败-"+result, 0, null).toJSONString();
			}
			return SToolUtils.convertResultJSONObj("S", "保存成功", 1, Integer.valueOf(result)).toJSONString();
		} catch (Exception e) {
			LOGGER.error("params_" + params + "_e_" + e.getMessage(),e);
			return SToolUtils.convertResultJSONObj("E", "保存失败", 0, e).toJSONString();
		}
	}
	
	/**
     * 新增行
     * @param params
     * @return
     * @throws Exception 
     */        
	@PostMapping("editRuleLine")
	public String editRuleLine(@RequestParam(required = false)String params)  { 
		try {
			List<SaafQuestionnaireLEntity_HI> paramsList = JSON.parseArray(params, SaafQuestionnaireLEntity_HI.class);
			String result = iRuleH.editRuleLine(paramsList);
			if(!StringUtils.isNumeric(result)) {
				return SToolUtils.convertResultJSONObj("E", "保存失败-"+result, 0, null).toJSONString();
			}
			return SToolUtils.convertResultJSONObj("S", "保存成功", 1, Integer.valueOf(result)).toJSONString();
		} catch (Exception e) {
			LOGGER.error("params_" + params + "_e_" + e.getMessage(),e);
			return SToolUtils.convertResultJSONObj("E", "保存失败", 0, e).toJSONString();
		}
	}
	
	@PostMapping("editSubject")
	public String editSubject(@RequestParam(required = false)String params)  { 
		try {
			List<SaafQuestionnaireLEntity_HI> paramsList = JSON.parseArray(params, SaafQuestionnaireLEntity_HI.class);
			String result = iRuleH.editSubject(paramsList);
			if(!StringUtils.isNumeric(result)) {
				return SToolUtils.convertResultJSONObj("E", "保存失败-"+result, 0, null).toJSONString();
			}
			return SToolUtils.convertResultJSONObj("S", "保存成功", 1, Integer.valueOf(result)).toJSONString();
		} catch (Exception e) {
			LOGGER.error("params_" + params + "_e_" + e.getMessage(),e);
			return SToolUtils.convertResultJSONObj("E", "保存失败", 0, e).toJSONString();
		}
	}
	
    /**
     * 删除
     * @param params
     * @return
     * @throws Exception 
     */
    @PostMapping("deleteRuleLine")
    public String deleteRuleLine(@RequestParam(required = false) String params) {
        try {
            JSONObject parameters = this.parseObject(params);
            JSONArray jsonArray = parameters.getJSONArray("ruleLineId");
            for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject jsonObject = this.parseObject(jsonArray.getString(i));
				iRuleH.deleteRuleLine(jsonObject.getInteger("ruleLineId"));
			}    
            return SToolUtils.convertResultJSONObj("S", "删除成功", 0, null).toJSONString();
        } catch (Exception e) {
            LOGGER.error("params_" + params + "_e_" + e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "删除失败", 0, e).toJSONString();
        }
    }
    
    /**
     * 删除
     * @param params
     * @return
     * @throws Exception 
     */
    @PostMapping("deleteSubject")
    public String deleteSubject(@RequestParam(required = false) String params) {
        try {
            JSONObject parameters = this.parseObject(params);
            JSONArray jsonArray = parameters.getJSONArray("subjectId");
            for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject jsonObject = this.parseObject(jsonArray.getString(i));
				iRuleH.deleteSubject(jsonObject.getInteger("subjectId"));
			}    
            return SToolUtils.convertResultJSONObj("S", "删除成功", 0, null).toJSONString();
        } catch (Exception e) {
            LOGGER.error("params_" + params + "_e_" + e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "删除失败", 0, e).toJSONString();
        }
    }
    
    /**
     * 查询行
     * @param params
     * @return
     * @throws Exception 
     */
    @PostMapping("findRuleLEntity")
    public String findRuleLEntity(@RequestParam(required = false) String params,
		@RequestParam(required = false, defaultValue = "1") Integer pageIndex,
		@RequestParam(required = false, defaultValue = "5") Integer pageRows) throws Exception {
        try {
			JSONObject parameters = this.parseObject(params);
			Pagination<RuleLEntity_HI_RO> page = iRuleH.findRuleLEntity(parameters, pageIndex, pageRows);
			JSONObject results = JSONObject
					.parseObject(JSON.toJSONString(page));
			results.put(SToolUtils.STATUS, SUCCESS_STATUS);
			results.put(SToolUtils.MSG, "成功");
			return results.toString();
		} catch (Exception e) {
			LOGGER.error("params_" + params + "_e_" + e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "查询失败", 0, e)
					.toJSONString();
		} 
    }
    
    @PostMapping("findSubjectEntity")
    public String findSubjectEntity(@RequestParam(required = false) String params,
		@RequestParam(required = false, defaultValue = "1") Integer pageIndex,
		@RequestParam(required = false, defaultValue = "5") Integer pageRows) throws Exception {
        try {
			JSONObject parameters = this.parseObject(params);
			Pagination<SubjectEntity_HI_RO> page = iRuleH.findSubjectEntity(parameters, pageIndex, pageRows);
			JSONObject results = JSONObject
					.parseObject(JSON.toJSONString(page));
			results.put(SToolUtils.STATUS, SUCCESS_STATUS);
			results.put(SToolUtils.MSG, "成功");
			return results.toString();
		} catch (Exception e) {
			LOGGER.error("params_" + params + "_e_" + e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "查询失败", 0, e)
					.toJSONString();
		} 
    }
    
}