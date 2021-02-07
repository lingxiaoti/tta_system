package com.sie.saaf.rule.model.inter;


import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.rule.utils.RuleActionResult;
import com.sie.saaf.rule.utils.RuleMatchResult;

import java.util.List;

/**
* 执行规则服务的接口类,（注意：规则引擎方法中，均不能抛出异常，避免影响正常业务）
* @author: ZhangBingShan(Sam)
* @email: bszzhang@qq.com
* @date : 2019/1/19 17:53      
*/
public interface IRuleEngineServer {

    /**
     * 通过业务线和业务点来匹配表达式，不能抛出异常，在代码中处理
     *
     * @param ruleBusinessLineCode 业务线
     * @param ruleBusinessPoint    业务点
     * @param parameters           参数
     * @return
     */
    RuleMatchResult matchRuleExp(String ruleBusinessLineCode, String ruleBusinessPoint, JSONObject parameters);

    /**
     * 通过规则编码匹配规则表达式，该方法是对外提供的方法，不抛出异常，在代码处理
     *
     * @param ruleExpCode 表达式编码
     * @param parameters
     * @return
     */
    RuleMatchResult matchRuleExp(String ruleExpCode, JSONObject parameters);

    /**
     * 匹配规则后的执行动作，一个规则中可能有多个动作，每个动作都需要验证相关的动态参数，某条动作验证不成功不影响其他动作的执行
     * 存在同步和异步执行，异步执行调用Mq需要开启事务
     * @param parameters 放置动态参数的集合
     * @param matchResult 规则引擎匹配返回的结果集合
     * @return 规则匹配成功，返回相关的执行结果集合，如果匹配不成功/查询动作实例失败，返回null;
     */
    RuleMatchResult saveForExecuteAction(JSONObject parameters, RuleMatchResult matchResult);

    /**
     * 匹配并执行结果，所有的报错信息均输入在错误日志当中
     * @param ruleExpCode
     * @param parameters
     * @return RuleMatchResult，里面放置了规则匹配的结果，如果匹配不上（包含匹配时报错）/查询执行动作报错时，其执行动结果作集合为null
     */
    RuleMatchResult matchRuleAndExecuteAction(String ruleExpCode, JSONObject parameters);

    /**
     * 通过业务线编码及业务点匹配并执行规则
     * @param ruleBusinessLineCode 业务线
     * @param ruleBusinessPoint 业务点
     * @param parameters 参数
     * @return RuleMatchResult
     */
    RuleMatchResult matchRuleAndExecuteAction(String ruleBusinessLineCode, String ruleBusinessPoint, JSONObject parameters);

    /**
     * 通过业务线匹配规则表达式，业务线中会设置命中匹配成功表达式的规则，通过规则来执行相关的动作
     * @param ruleBusinessLineCode 业务线编码
     * @param parameters 匹配规则需要的参数
     * @return List<RuleMatchResult> 当报错/业务线没有相关的规则表达式返回空集合。
     * 权重模式下仅会返回一条规则，一旦在执行中有报错/没有匹配到规则时，返回空集合
     * 全部匹配模式会会将所有的规则匹配结果返回。一旦在执行中有报错，返回空集合
     */
    List<RuleMatchResult> matchRuleByBussinessLine(String ruleBusinessLineCode, JSONObject parameters);

    /**
     * 通过业务编码执行相关的动作
     * @param parameters 执行动作必传参数
     * @param ruleMatchResultList
     * @return
     */
    List<RuleMatchResult> executeActionByBussinessLine(JSONObject parameters, List<RuleMatchResult> ruleMatchResultList);

    /**
     * 通过业务线编码匹配表达式并执行相关动作
     * @param ruleBusinessLineCode
     * @param parameters
     * @return
     */
    List<RuleMatchResult> matchRuleAndExecuteActionByBussinessLine(String ruleBusinessLineCode, JSONObject parameters);

    /**
     * 业务端调用规则引擎执行数学公式
     * @param parameter 里面存放了相关的参数及执行动作实例对象
     * @return RuleActionResult 执行是否成功，生成的新参数已经放置在parameter中
     * @throws Exception 如果不成功会抛出相关的异常
     */
    RuleActionResult formulaExecuteInAction(JSONObject parameter) throws Exception;

    /**
     * 业务线来验证规则引擎，返回带动作的返回结果
     * @param ruleBusinessLineCode 业务编码
     * @param parameters 匹配规则需要的参数
     * @param findAction 是否查询动作，放置到结果集合中
     * @return 如果报错，返回空集合，没有匹配成功也返回空集合
     * @throws Exception
     */
    List<RuleMatchResult> matchRuleByBussinessLine(String ruleBusinessLineCode, JSONObject parameters,boolean findAction);

}
