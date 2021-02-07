package com.sie.saaf.rule.services;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.exception.BusinessException;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.rule.model.entities.RuleExpressionEntity_HI;
import com.sie.saaf.rule.model.entities.readonly.RuleExpressionEntity_HI_RO;
import com.sie.saaf.rule.model.inter.IRuleExpression;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/ruleExpressionService")
public class RuleExpressionService extends CommonAbstractService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RuleExpressionService.class);

    @Autowired
    private IRuleExpression expressionServer;

    @Override
    public IBaseCommon getBaseCommonServer() {
        return expressionServer;
    }
    
    @RequestMapping(method = RequestMethod.POST,value="query")
    public String query(@RequestParam(required=false) String params,
    		@RequestParam(required = false,defaultValue = "1") Integer pageIndex,
    		@RequestParam(required = false,defaultValue = "10") Integer pageRows){
        try{
    		JSONObject queryParamJSON = parseObject(params);
            Pagination<RuleExpressionEntity_HI_RO> pagination = expressionServer.find(queryParamJSON,pageIndex,pageRows);

    		JSONObject results = JSONObject.parseObject(JSON.toJSONString(pagination));
    		results.put(SToolUtils.STATUS, SUCCESS_STATUS);
    		results.put(SToolUtils.MSG, "成功");
    		return results.toString();
        }catch(Exception e){
            LOGGER.error(e.getMessage(),e);
    		return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }

    @RequestMapping(method = RequestMethod.POST,value="saveorUpdate")
    public String saveorUpdate(@RequestParam(required=false) String params){
        try{
    		JSONObject queryParamJSON = parseObject(params);
            if (StringUtils.isEmpty(queryParamJSON.getString("ruleBusinessLineCode")))
                return SToolUtils.convertResultJSONObj("P","parameter error",0,null).toString();
            RuleExpressionEntity_HI instance = expressionServer.saveOrUpdate(queryParamJSON, queryParamJSON.getIntValue("operatorUserid"));
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", 1, instance).toString();
        }catch(Exception e){
            LOGGER.error(e.getMessage(),e);
    		return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }

    @RequestMapping(method = RequestMethod.POST,value="delete")
    public String delete(@RequestParam(required=false) String params){
        try{
            JSONObject queryParamJSON = parseObject(params);
            if(queryParamJSON==null || !queryParamJSON.containsKey("ruleExpId")){
                return SToolUtils.convertResultJSONObj(ERROR_STATUS, "缺少参数 ruleExpId ", 0, null).toString();
            }
            List<Serializable> ids = new ArrayList<Serializable>();
            Integer ruleExpId = queryParamJSON.getInteger("ruleExpId");
            expressionServer.delete(ruleExpId);
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", ids.size(), ids).toString();
        }catch(Exception e){
            LOGGER.error(e.getMessage(),e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }

    /**
     * 查询表达式列表
     * @param params {
     *
     * }
     * @return {
     *     "effectDate": 有效时间,
     *     "ruleBusinessLineCode": 业务线编码,
     *     "ruleExpWeight": 权重,
     *     "ruleBusinessLineName": 业务线名称,
     *     "ruleExpCode": 规则表达式编码,
     *     "ruleBusinessLineMapptype": 匹配类型,
     *     "ruleExpName": 规则表达式名称,
     *     "ruleExpId": 规则表达式Id,
     *     "ruleSimpleExp": 表达式
     * }
     * @author ZhangJun
     * @createTime 2018-12-06
     * @description 查询表达式列表
     */
    @RequestMapping(method = RequestMethod.POST, value = "findRuleExpression")
    public String findRuleExpression(@RequestParam(required = false) String params,
                                     @RequestParam(required = false,defaultValue = "1") Integer pageIndex,
                                     @RequestParam(required = false,defaultValue = "10") Integer pageRows) {
        try {
            JSONObject queryParamJSON = parseObject(params);
            Pagination<RuleExpressionEntity_HI_RO> pagination = expressionServer.findRuleExpressionPagination(queryParamJSON,pageIndex,pageRows);

            JSONObject results = JSONObject.parseObject(JSON.toJSONString(pagination));
            results.put(SToolUtils.STATUS, SUCCESS_STATUS);
            results.put(SToolUtils.MSG, "成功");
            return results.toString();
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }

    @PostMapping("/validateBusinessPoint")
    public String validateBusinessPoint(@RequestParam(name = "params",required = true) String params) {
        try {
            JSONObject parameters = this.parseObject(params);
            String ruleBusinessLineCode = parameters.getString("ruleBusinessLineCode");
            String ruleBusinessPoint = parameters.getString("ruleBusinessPoint");
            if(StringUtils.isBlank(ruleBusinessLineCode) || StringUtils.isBlank(ruleBusinessPoint)) {
                return SToolUtils.convertResultJSONObj(ERROR_STATUS, "必传参数为空", 0, null).toString();
            }
            boolean result = expressionServer.validateExpressionUnique(ruleBusinessLineCode, ruleBusinessPoint);
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功",0, result).toString();
        }catch (Exception e) {
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }

    @PostMapping("/validateRuleExpWeight")
    public String validateRuleExpWeight(@RequestParam(name = "params",required = true) String params) {
        try {
            JSONObject parameters = this.parseObject(params);
            String ruleBusinessLineCode = parameters.getString("ruleBusinessLineCode");
            Integer ruleExpWeight = parameters.getInteger("ruleExpWeight");
            if(StringUtils.isBlank(ruleBusinessLineCode) || ruleExpWeight == null) {
                return SToolUtils.convertResultJSONObj(ERROR_STATUS, "必传参数为空", 0, null).toString();
            }
            boolean result = expressionServer.validateRuleExpWeight(ruleExpWeight,ruleBusinessLineCode);
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功",0, result).toString();
        }catch (Exception e) {
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }


}
