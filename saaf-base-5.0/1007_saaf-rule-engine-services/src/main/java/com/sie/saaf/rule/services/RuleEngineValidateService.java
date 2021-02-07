package com.sie.saaf.rule.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.rule.model.inter.IRuleEngineServer;
import com.sie.saaf.rule.utils.RuleMatchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 规则引擎校验服务
 *
 * @author Zhangbingshan(sam)
 * @email bszzhang@qq.com
 * @date 2019-01-21 20:48
 */
@RestController
@RequestMapping("/ruleEngineValidateService")
public class RuleEngineValidateService {

    @Autowired
    private IRuleEngineServer ruleEngineServer;

    //通过表达式编码验证规则表达式
    @PostMapping("/matchRuleExpByRuleExpCode")
    public String matchRuleExpByRuleExpCode (@RequestParam(name = "params") String params) {
        JSONObject parameters = JSONObject.parseObject(params);
        String ruleExpCode = parameters.getString("ruleExpCode");
        RuleMatchResult ruleMatchResult = ruleEngineServer.matchRuleExp(ruleExpCode, parameters);
        return JSON.toJSONString(ruleMatchResult);
    }

    //通过业务线编码及业务点验证规则表达式
    @PostMapping("/matchRuleExpByBusinessLineCodeAndPoint")
    public String matchRuleExpByBusinessLineCodeAndPoint (@RequestParam(name = "params") String params) {
        JSONObject parameters = JSONObject.parseObject(params);
        String ruleBusinessLineCode = parameters.getString("ruleBusinessLineCode");
        String ruleBusinessPoint = parameters.getString("ruleBusinessPoint");
        RuleMatchResult ruleMatchResult = ruleEngineServer.matchRuleExp(ruleBusinessLineCode, ruleBusinessPoint,parameters);
        return JSON.toJSONString(ruleMatchResult);
    }

    @PostMapping("/matchRuleAndExecuteActionByRuleExpCode")
    public String matchRuleAndExecuteActionByRuleExpCode (@RequestParam(name = "params") String params) {
        JSONObject parameters = JSONObject.parseObject(params);
        String ruleExpCode = parameters.getString("ruleExpCode");
        RuleMatchResult ruleMatchResult = ruleEngineServer.matchRuleAndExecuteAction(ruleExpCode, parameters);
        return JSON.toJSONString(ruleMatchResult);
    }

    @PostMapping("/matchRuleAndExecuteActionByBusinessLineCodeAndPoint")
    public String matchRuleAndExecuteActionByBusinessLineCodeAndPoint (@RequestParam(name = "params") String params) {
        JSONObject parameters = JSONObject.parseObject(params);
        String ruleBusinessLineCode = parameters.getString("ruleBusinessLineCode");
        String ruleBusinessPoint = parameters.getString("ruleBusinessPoint");
        RuleMatchResult ruleMatchResult = ruleEngineServer.matchRuleAndExecuteAction(ruleBusinessLineCode, ruleBusinessPoint,parameters);
        return JSON.toJSONString(ruleMatchResult);
    }

    @PostMapping("/matchRuleByBussinessLine")
    public String matchRuleByBussinessLine (@RequestParam(name = "params") String params) {
        JSONObject parameters = JSONObject.parseObject(params);
        String ruleBusinessLineCode = parameters.getString("ruleBusinessLineCode");
        List<RuleMatchResult> ruleMatchResultList = ruleEngineServer.matchRuleByBussinessLine(ruleBusinessLineCode, parameters);
        return JSON.toJSONString(ruleMatchResultList);
    }

    @PostMapping("/matchRuleAndExecuteActionByBussinessLine")
    public String matchRuleAndExecuteActionByBussinessLine (@RequestParam(name = "params") String params) {
        JSONObject parameters = JSONObject.parseObject(params);
        String ruleBusinessLineCode = parameters.getString("ruleBusinessLineCode");
        List<RuleMatchResult> ruleMatchResultList = ruleEngineServer.matchRuleAndExecuteActionByBussinessLine(ruleBusinessLineCode, parameters);
        return JSON.toJSONString(ruleMatchResultList);
    }


}
