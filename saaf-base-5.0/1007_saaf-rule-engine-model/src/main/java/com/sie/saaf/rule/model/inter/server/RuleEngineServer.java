package com.sie.saaf.rule.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.dao.readonly.DynamicSqlDao_HI_RO;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.saaf.rule.constant.RuleConstant;
import com.sie.saaf.rule.model.entities.readonly.RuleActionInstanceEntity_HI_RO;
import com.sie.saaf.rule.model.entities.readonly.RuleBusinessLineEntity_HI_RO;
import com.sie.saaf.rule.model.entities.readonly.RuleExpressionEntity_HI_RO;
import com.sie.saaf.rule.model.inter.IRuleActionInstance;
import com.sie.saaf.rule.model.inter.IRuleBusinessLine;
import com.sie.saaf.rule.model.inter.IRuleEngineServer;
import com.sie.saaf.rule.model.inter.IRuleExpression;
import com.sie.saaf.rule.utils.RuleActionResult;
import com.sie.saaf.rule.utils.RuleMatchResult;
import com.sie.saaf.transaction.invoke.RemoteServiceInvoke;
import com.yhg.transaction.compensation.beans.RedisMessageContentBean;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.locale.converters.DateLocaleConverter;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 规则引擎接口实现类
 *
 * @author: ZhangBingShan(Sam)
 * @email: bszzhang@qq.com
 * @date : 2019/1/19 17:53
 */
@Component("ruleEngineServer")
public class RuleEngineServer implements IRuleEngineServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(RuleEngineServer.class);

    @Autowired
    private IRuleExpression ruleExpressionServer;

    @Autowired
    private IRuleBusinessLine ruleBusinessLineServer;

    @Autowired
    private IRuleActionInstance ruleActionInstanceServer;

    @Autowired
    private DynamicSqlDao_HI_RO dynamicSqlDao_hi_ro;

    private Logger logger = LoggerFactory.getLogger(getClass());
    //用于执行数据公式的类
    private ScriptEngine jse = new ScriptEngineManager().getEngineByName("JavaScript");

    /**
     * 通过表达式编码获取相关的表达式对象，仅供内部使用，提高代码复用性
     *
     * @param ruleExpCode 规则编码
     * @return 规则表达式对象
     * @throws Exception 抛出运行异常，主要检查规则表达式编码是否有传入，是否可以获取到表达式
     */
    private RuleExpressionEntity_HI_RO getRuleExpression(String ruleExpCode) throws Exception {
        if (StringUtils.isBlank(ruleExpCode)) {
            throw new RuntimeException("传入表达式编码为空");
        }
        JSONObject conditonMap = new JSONObject();
        conditonMap.put("ruleExpCode", ruleExpCode);
        List<RuleExpressionEntity_HI_RO> ruleExpressionList = ruleExpressionServer.getRuleExpressionByCondition(conditonMap);
        if (ruleExpressionList.size() != 1) {
            throw new RuntimeException("无法通过编码：" + ruleExpCode + "获取到表达式，请检查表达式编码是否正确");
        }
        return ruleExpressionList.get(0);
    }

    /**
     * 通过业务点和业务线来获取特定的表达式，仅供内部使用
     *
     * @param ruleBusinessLineCode 业务线编码
     * @param ruleBusinessPoint    业务点
     * @return 表达式对象
     * @throws Exception 抛出运行异常，主要检查业务线和业务点是否有传入，是否可以获取到表达式
     */
    private RuleExpressionEntity_HI_RO getRuleExpression(String ruleBusinessLineCode, String ruleBusinessPoint) throws Exception {
        if (StringUtils.isBlank(ruleBusinessLineCode)) {
            throw new RuntimeException("传入业务线编码为空");
        }
        if (StringUtils.isBlank(ruleBusinessPoint)) {
            throw new RuntimeException("传入业务点为空");
        }
        JSONObject conditonMap = new JSONObject();
        conditonMap.put("ruleBusinessLineCode", ruleBusinessLineCode);
        conditonMap.put("ruleBusinessPoint", ruleBusinessPoint);
        List<RuleExpressionEntity_HI_RO> ruleExpressionList = ruleExpressionServer.getRuleExpressionByCondition(conditonMap);
        if (ruleExpressionList.size() != 1) {
            throw new RuntimeException("无法通过业务线编码：" + ruleBusinessLineCode + "业务点：" + ruleBusinessPoint + ",获取到表达式，请检查表达式编码是否正确");
        }
        return ruleExpressionList.get(0);
    }

    /**
     * 验证表达式，及执行表达式需要的参数，，仅供类内部使用
     *
     * @param ruleExpression 规则表达式
     * @param parameters     表达式输入参数
     * @return 执行表达式sql需要的map
     * @throws Exception 当参数验证不成功时回抛出相关的运行时异常
     */
    private Map<String, Object> validateRuleExpresspionAndParams(RuleExpressionEntity_HI_RO ruleExpression, JSONObject parameters) throws Exception {

        if (ruleExpression == null) {
            throw new RuntimeException("传入表达式对象为空");
        }
        String ruleExp = ruleExpression.getRuleExp();
        if (StringUtils.isBlank(ruleExp)) {
            throw new RuntimeException("无法获取到执行的sql，请检查表达式编码是否正确");
        }
        String ruleExpParams = ruleExpression.getRuleExpParams();
        //匹配必传参数并将参数放进map中，作为sql执行的条件
        Map<String, Object> conditionMap = new HashMap<>();
        if (StringUtils.isNotBlank(ruleExpParams) && null != parameters) {
            String[] paramsArray = ruleExpParams.split(",");
            boolean jdugeParams = true;
            String missParam = "";
            for (String param : paramsArray) {
                Object paramObj = parameters.get(param);
                if (paramObj == null) {
                    jdugeParams = false;
                    missParam = param;
                    break;
                }
                //如果是日期类型的话，需要特殊处理
                String paramValue;
                if (paramObj instanceof Date) {
                    paramValue = JSON.toJSONStringWithDateFormat(paramObj, "yyyy-MM-dd HH:mm:ss");
                } else {
                    paramValue = parameters.getString(param);
                }
                conditionMap.put(param, paramValue);
            }
            if (!jdugeParams) {
                throw new RuntimeException("缺少参数：" + missParam);
            }
        }
        return conditionMap;
    }

    /**
     * 用于匹配规则是否符合预期的方法， 供内部类使用
     *
     * @param ruleExpression 规则表达式对象
     * @param parameters     传入的参数，如果没有参数可为空
     * @return RuleMatchResult
     * @throws Exception 执行表达式的sql不成功是，抛出异常
     */
    private RuleMatchResult matchRuleExp(RuleExpressionEntity_HI_RO ruleExpression, JSONObject parameters) throws Exception {
        RuleMatchResult ruleMatchResult = new RuleMatchResult();
        Map<String, Object> conditionMap = validateRuleExpresspionAndParams(ruleExpression, parameters);

        //设置默认返回信息为不成，如果成功时在后面重新赋值处理
        ruleMatchResult.setStatus(false);
        ruleMatchResult.setMsg("规则表达式匹配不成功");
        ruleMatchResult.setRuleExpCode(ruleExpression.getRuleExpCode());
        //执行sql,获取相关的规则
        String ruleExp = ruleExpression.getRuleExp().replaceAll("\\[", ":").replaceAll("]", " ");
        List<Map<String, Object>> list = dynamicSqlDao_hi_ro.executeSql(ruleExp, conditionMap);
        if (list.size() == 1) {
            String result = (String) list.get(0).get("result");
            if ("Y".equalsIgnoreCase(result)) {
                ruleMatchResult.setStatus(true);
                ruleMatchResult.setMsg(ruleExpression.getRuleReturnMsg());
            }
        }
        //匹配成功后，确认是否需要返回值，执行数学表达式，执行相关服务实例
        if (ruleMatchResult.getStatus()) {
            //执行服务实例
            if ("Y".equalsIgnoreCase(ruleExpression.getRuleUseAction())) {
                ruleMatchResult.setRuleUseAction(true);
            }
            //返回具体值
            if ("Y".equalsIgnoreCase(ruleExpression.getRuleUseVaule())) {
                ruleMatchResult.setRuleValue(ruleExpression.getRuleValue());
            }
            //执行数学公式
            if ("Y".equalsIgnoreCase(ruleExpression.getRuleUseFormula())) {
                String ruleFormulaStaticValue = ruleExpression.getRuleFormulaStaticValue();
                parameters.putAll(JSON.parseObject(ruleFormulaStaticValue));
                String s = formulaResolveAndCalculate(ruleExpression.getRuleFormula(), parameters);
                ruleMatchResult.setRuleFormulaReturnValue(new BigDecimal(s));
            }

        }
        return ruleMatchResult;
    }


    /**
     * 匹配完成表达式后的执行动作,内部使用
     *
     * @param ruleActionInstanceList 需要执行服务的对象集合
     * @param parameters  执行服务实例需要的动态参数必须包括
     * @return List<RuleActionResult> 返回执行动作的结果，这里需要注意执行动作中某个参数验证不成功，不影响其他执行结果的运行。
     * 同步时有返回信息，异步时没有返回信息
     * @throws Exception 查询规则表达式时抛出相关的sql异常
     */
    private List<RuleActionResult> executeAction(List<RuleActionInstanceEntity_HI_RO> ruleActionInstanceList, JSONObject parameters) throws Exception {

        //执行动作返回的结果集
        List<RuleActionResult> ruleActionResultList = new ArrayList<>();
        if (ruleActionInstanceList.size() == 0) return ruleActionResultList;
        //验证相关的参数并执行相关的操作
        for (RuleActionInstanceEntity_HI_RO instance : ruleActionInstanceList) {
            //定义动作执行的返回结果对象
            RuleActionResult ruleActionResult = new RuleActionResult();
            ruleActionResult.setActionInstanceCode(instance.getActionInstanceCode());
            ruleActionResult.setExecuteType(instance.getActionExecuteType());
            ruleActionResult.setStatus(true);
            //静态参数值输入parameter中
            String actionStaticValue = instance.getActionStaticValue();
            if (StringUtils.isNotBlank(actionStaticValue)) {
                JSONObject staticParams = JSONObject.parseObject(actionStaticValue);
                parameters.putAll(staticParams);
            }
            //动态参数校验 (全部放在具体执行的服务中去判断)
            /*String actionDynamicParamStr = instance.getActionDynamicParam();
            if (StringUtils.isNotBlank(actionDynamicParamStr)) {
                String[] dynamicParams = actionDynamicParamStr.split(",");
                for (String dynamicParam : dynamicParams) {
                    Object object = parameters.get(dynamicParam);
                    if (null == object) {
                        ruleActionResult.setStatus(false);
                        ruleActionResult.setMsg("表达式编码：" + ruleExpCode + ",服务实例：" + JSONObject.toJSONString(instance) + "，确少动态参数：" + dynamicParam);
                        ruleActionResultList.add(ruleActionResult);
                        logger.error("表达式编码：" + ruleExpCode + ",服务实例：" + JSONObject.toJSONString(instance) + "确少动态参数：" + dynamicParam);
                    }
                }
            }*/
            //如果参数验证不成功，跳出此次循环
//            if (!ruleActionResult.getStatus()) continue;
            //执行数学表达(执行服务前)，如果不成功，跳出此次循环
            if (RuleConstant.RULE_ACTION_FORMULA_BEFORE.equalsIgnoreCase(instance.getActionExecuteFormula())) {
                ruleActionResult = formulaExecuteInActionInstance(instance, parameters);
                if (!ruleActionResult.getStatus()) continue;
            }
            //执行服务中，需要在服务中调用
            if (RuleConstant.RULE_ACTION_FORMULA_IN.equalsIgnoreCase(instance.getActionExecuteFormula())) {
                //设置时间类型为空，防止转换出错
                instance.setCreationDate(null);
                instance.setLastUpdateDate(null);
                parameters.put(RuleConstant.RULE_ACTION_INSTANCE, instance);
            }
            //执行相关服务,判断执行同步请求还是异步执行请求
            if ("Y".equalsIgnoreCase(instance.getActionExecuteType())) {
                JSONObject urlResturnMsg = SaafToolUtils.preaseServiceResultJSON(instance.getActionUrl(), new JSONObject().fluentPut("params", parameters));
                ruleActionResult.setMsg(JSON.toJSONString(urlResturnMsg));
            } else {
                RedisMessageContentBean bean = new RedisMessageContentBean();
                bean.setRequestURL(instance.getActionUrl());
//                bean.setQueueName("Transaction_Consistency_Queue_zbs");
                bean.setMessageBody(new JSONObject().fluentPut("params", parameters).toJSONString());
                RemoteServiceInvoke.sendMessageBody2Redis(bean);
            }
            ruleActionResultList.add(ruleActionResult);
        }
        return ruleActionResultList;
    }


    @Override
    public RuleMatchResult matchRuleExp(String ruleBusinessLineCode, String ruleBusinessPoint, JSONObject parameters) {
        try {
            RuleExpressionEntity_HI_RO ruleExpression = getRuleExpression(ruleBusinessLineCode, ruleBusinessPoint);
            return matchRuleExp(ruleExpression, parameters);
        } catch (Exception e) {
            RuleMatchResult ruleMatchResult = new RuleMatchResult();
            ruleMatchResult.setStatus(false);
            ruleMatchResult.setMsg(e.getMessage());
            logger.error("ruleBusinessLineCode_" + ruleBusinessLineCode + ",ruleBusinessPoint_" + ruleBusinessPoint + "parameters_" + parameters.toJSONString() + ",e_" + e.getMessage(), e);
            return ruleMatchResult;
        }
    }

    @Override
    public RuleMatchResult matchRuleExp(String ruleExpCode, JSONObject parameters) {
        try {
            RuleExpressionEntity_HI_RO ruleExpression = getRuleExpression(ruleExpCode);
            return matchRuleExp(ruleExpression, parameters);
        } catch (Exception e) {
            RuleMatchResult ruleMatchResult = new RuleMatchResult();
            ruleMatchResult.setStatus(false);
            ruleMatchResult.setMsg(e.getMessage());
            logger.error("ruleExpCode" + ruleExpCode + "parameters_" + parameters.toJSONString() + ",e_" + e.getMessage(), e);
            return ruleMatchResult;
        }
    }

    /**
     * 匹配规则后的执行动作，一个规则中可能有多个动作，每个动作都需要验证相关的动态参数，某条动作验证不成功不影响其他动作的执行,错误以日志形式输出
     * 存在同步和异步执行，异步执行调用Mq需要开启事务
     *
     * @param parameters  放置动态参数的集合
     * @param matchResult 规则引擎匹配返回的结果集合
     * @return RuleMatchResult，里面放置了规则匹配的结果，如果匹配不上或查询执行动作报错时，其执行动结果作集合为null
     */
    @Override
    //@TransMessageProvider(desc = "规则引擎执行动作")
    public RuleMatchResult saveForExecuteAction(JSONObject parameters, RuleMatchResult matchResult) {
        try {
            if (matchResult.getStatus() && matchResult.isRuleUseAction()) {
                List<RuleActionInstanceEntity_HI_RO> ruleActionInstanceList = matchResult.getRuleActionList();
                if (matchResult.getRuleActionResultList() == null) {
                    //搜索查询规则表达式中的执行动作
                    ruleActionInstanceList = ruleActionInstanceServer.findRuleActionInstanceList(matchResult.getRuleExpCode());
                    matchResult.setRuleActionList(ruleActionInstanceList);
                }
                List<RuleActionResult> ruleActionResultList = executeAction(ruleActionInstanceList, parameters);
                matchResult.setRuleActionResultList(ruleActionResultList);
                return matchResult;
            }
            return matchResult;
        } catch (Exception e) {
            logger.error("parameters_" + parameters.toJSONString() + ",e_" + e.getMessage(), e);
            return matchResult;
        }
    }

    /**
     * 匹配并执行结果，所有的报错信息均输入在错误日志当中
     *
     * @param ruleExpCode 表达式编码
     * @param parameters  验证表达式及执行动作必要的参数map
     * @return RuleMatchResult，里面放置了规则匹配的结果，如果匹配不上（包含匹配时报错）/查询执行动作报错时，其执行动结果作集合为null
     */
    @Override
    //@TransMessageProvider(desc = "规则引擎执行动作")
    public RuleMatchResult matchRuleAndExecuteAction(String ruleExpCode, JSONObject parameters) {
        RuleMatchResult ruleMatchResult = matchRuleExp(ruleExpCode, parameters);
        return saveForExecuteAction(parameters, ruleMatchResult);
    }

    /**
     * 通过业务线编码及业务点匹配并执行规则
     *
     * @param ruleBusinessLineCode 业务线
     * @param ruleBusinessPoint    业务点
     * @param parameters           参数
     * @return RuleMatchResult
     */
    @Override
    //@TransMessageProvider(desc = "规则引擎执行动作")
    public RuleMatchResult matchRuleAndExecuteAction(String ruleBusinessLineCode, String ruleBusinessPoint, JSONObject parameters) {
        RuleMatchResult ruleMatchResult = matchRuleExp(ruleBusinessLineCode, ruleBusinessPoint, parameters);
        return saveForExecuteAction(parameters, ruleMatchResult);
    }

    /**
     * 通过业务线匹配规则表达式，业务线中会设置命中匹配成功表达式的规则，通过规则来执行相关的动作
     *
     * @param ruleBusinessLineCode 业务线编码
     * @param parameters           匹配规则需要的参数
     * @return List<RuleMatchResult> 当报错/业务线没有相关的规则表达式返回空集合。
     * 权重模式下仅会返回一条规则，一旦在执行中有报错/没有匹配到规则时，返回空集合
     * 全部匹配模式会会将所有的规则匹配结果返回。一旦在执行中有报错，返回空集合
     * 当命中规则匹配不中时，也是返回空集合，命中规则目前有三种：全部，权重，业务点（有业务点的节点不在此处处理）
     * 业务点匹配规则，通过业务点直接匹配，如果匹配成功返回一条数据，如果不成功或参数传入报错，返回空集合
     */
    @Override
    public List<RuleMatchResult> matchRuleByBussinessLine(String ruleBusinessLineCode, JSONObject parameters) {
        List<RuleMatchResult> ruleMatchResultList = new ArrayList<>();
        if (StringUtils.isBlank(ruleBusinessLineCode)) {
            logger.error("业务线编码输入为空");
            return ruleMatchResultList;
        }
        //查询业务线的表达式命中规则及相关的表达式,分开查询主要是为了防止数据表过大导致连接冗余查询，效率底
        JSONObject conditonMap = new JSONObject();
        conditonMap.put("ruleBusinessLineCode", ruleBusinessLineCode);

        //查询业务线对象
        List<RuleBusinessLineEntity_HI_RO> ruleBusinessLineList;
        try {
            ruleBusinessLineList = ruleBusinessLineServer.findRuleBusinessLineList(conditonMap);
        } catch (Exception e) {
            logger.error("查询业务线对象失败，ruleBusinessLineCode：" + ruleBusinessLineCode + "e_" + e.getMessage(), e);
            return ruleMatchResultList;
        }

        if (ruleBusinessLineList.size() != 1) {
            logger.error("未找到该业务线，业务线编码：" + ruleBusinessLineCode);
            return ruleMatchResultList;
        }

        RuleBusinessLineEntity_HI_RO ruleBusinessLineEntity_hi = ruleBusinessLineList.get(0);
        //获取业务线中规则的命中方式，主要是分权重和全部匹配
        String ruleBusinessLineMapptype = ruleBusinessLineEntity_hi.getRuleBusinessLineMapptype();
        if (StringUtils.isBlank(ruleBusinessLineMapptype)) {
            logger.error("命中规则为空，业务编码：" + ruleBusinessLineCode);
            return ruleMatchResultList;
        }
        //如果是业务点匹配，直接查找规则表达式
        if ("businessPoint".equalsIgnoreCase(ruleBusinessLineMapptype)) {
            String businessPoint = parameters.getString(RuleConstant.RULE_BUSINESS_POINT);
            RuleMatchResult ruleMatchResult = matchRuleExp(ruleBusinessLineCode, businessPoint, parameters);
            if (ruleMatchResult.getStatus()) {
                ruleMatchResultList.add(ruleMatchResult);
            }
            return ruleMatchResultList;
        }
        //通过业务线编码查询业务线中的规则
        JSONObject conditonJson = new JSONObject();
        conditonJson.put("ruleBusinessLineCode", ruleBusinessLineCode);
        List<RuleExpressionEntity_HI_RO> ruleExpressionList;
        try {
            ruleExpressionList = ruleExpressionServer.getRuleExpressionByCondition(conditonJson);
        } catch (Exception e) {
            logger.error("数据库获取表达式失败，ruleBusinessLineCode：" + ruleBusinessLineCode + ",e_" + e.getMessage(), e);
            return ruleMatchResultList;
        }
        //全匹配
        if ("all".equalsIgnoreCase(ruleBusinessLineMapptype)) {
            for (RuleExpressionEntity_HI_RO ruleExpressionEntity_hi_ro : ruleExpressionList) {
                try {
                    RuleMatchResult ruleMatchResult = matchRuleExp(ruleExpressionEntity_hi_ro, parameters);
                    ruleMatchResultList.add(ruleMatchResult);
                } catch (Exception e) {
                    //如果匹配的规则报错，则所有的规则都不生效
                    ruleMatchResultList.clear();
                    logger.error("ruleExpression:" + JSON.toJSONString(ruleExpressionEntity_hi_ro) + "parameters_" + parameters.toJSONString() + ",e_" + e.getMessage(), e);
                    break;
                }

            }
            return ruleMatchResultList;
        }
        //权重匹配（这里需要修改，匹配通过的权重最高的才行）；
        if ("weight".equalsIgnoreCase(ruleBusinessLineMapptype)) {
            //需要获取所有匹配的表达式，进行匹配将匹配成功统一放到集合中，留待后续帅选权重最高的一个
            int ruleExpWeiht = 0;
            for (RuleExpressionEntity_HI_RO expression : ruleExpressionList) {
                try {
                    RuleMatchResult ruleMatchResult = matchRuleExp(expression, parameters);
                    if (ruleMatchResult.getStatus()) {
                        if (ruleExpWeiht < expression.getRuleExpWeight()) {
                            ruleExpWeiht = expression.getRuleExpWeight();
                            if (ruleMatchResultList.size() == 0) {
                                ruleMatchResultList.add(ruleMatchResult);
                            } else {
                                ruleMatchResultList.set(0, ruleMatchResult);
                            }
                        }
                    }
                } catch (Exception e) {
                    //一旦规则匹配有异常，所有都不通过，因为这是权重匹配
                    logger.error("ruleExpression:" + JSON.toJSONString(expression) + "parameters_" + parameters.toJSONString() + ",e_" + e.getMessage(), e);
                    ruleMatchResultList.clear();
                    return ruleMatchResultList;
                }
            }
        }
        return ruleMatchResultList;

    }

    /**
     * 通过业务编码执行相关的动作
     *
     * @param ruleMatchResultList 匹配表达式结果结合
     * @return List<RuleMatchResult>
     */
    @Override
    //@TransMessageProvider(desc = "规则引擎执行动作")
    public List<RuleMatchResult> executeActionByBussinessLine(JSONObject parameters, List<RuleMatchResult> ruleMatchResultList) {
        for (int i = 0; i < ruleMatchResultList.size(); i++) {
            RuleMatchResult ruleMatchResult = ruleMatchResultList.get(i);
            if (ruleMatchResult.getStatus()) {
                ruleMatchResult = saveForExecuteAction(parameters, ruleMatchResult);
                ruleMatchResultList.set(i, ruleMatchResult);
            }
        }
        return ruleMatchResultList;
    }

    /**
     * 通过业务编码执行相关的动作
     *
     * @param ruleBusinessLineCode 业务线编码
     * @param parameters           参数map
     * @return List<RuleMatchResult>
     */
    @Override
    //@TransMessageProvider(desc = "规则引擎执行动作")
    public List<RuleMatchResult> matchRuleAndExecuteActionByBussinessLine(String ruleBusinessLineCode, JSONObject parameters) {
        List<RuleMatchResult> ruleMatchResultList = matchRuleByBussinessLine(ruleBusinessLineCode, parameters,true);
        return executeActionByBussinessLine(parameters, ruleMatchResultList);
    }

    /**
     * 规则执行实例数学公式 返回相关的结果
     *
     * @param ruleActionInstance 执行实例
     * @param parameter          相关的参数，并将运算成功的参数放置到其中
     * @return 执行成功的实例
     */
    private RuleActionResult formulaExecuteInActionInstance(RuleActionInstanceEntity_HI_RO ruleActionInstance, JSONObject parameter) throws Exception {
        logger.info("开始执行服务实例数学表达式：服务实例_{},参数_{}", JSON.toJSONString(ruleActionInstance), parameter.toJSONString());
        RuleActionResult ruleActionResult = new RuleActionResult();
        ruleActionResult.setStatus(false);
        if (ruleActionInstance == null) {
            throw new RuntimeException("ruleActionInstance传入为空");
        }
        String actionFormula = ruleActionInstance.getActionFormula();
        if (StringUtils.isBlank(actionFormula)) {
            throw new RuntimeException("actionFormula 为空");
        }
        String actionReturnParamType = ruleActionInstance.getActionReturnParamType();
        String s = formulaResolveAndCalculate(actionFormula, parameter);
        Object value = null;
        if ("int".equalsIgnoreCase(actionReturnParamType) || "integer".equalsIgnoreCase(actionReturnParamType)) {
            if (s.contains(".")) {
                throw new RuntimeException("返回结果为包含小数，但返回类型要求是整型：返回结果：" + s);
            }
            value = Integer.valueOf(s);
        }
        if ("double".equalsIgnoreCase(actionReturnParamType)) {
            value = Double.valueOf(s);
        }
        if ("bigDecimal".equalsIgnoreCase(actionReturnParamType)) {
            value = new BigDecimal(s);
        }
        if (value == null) {
            throw new RuntimeException("无法匹配到返回结果的数据类型，仅支持：int,double,integer,bigdecimal(可忽略大小写)");
        }
        parameter.put(ruleActionInstance.getActionReturnParam(), value);
        ruleActionResult.setStatus(true);
        logger.info("执行服务实例数学表达式成功：参数_{}" + parameter.toJSONString());
        return ruleActionResult;
    }

    /**
     * 业务端调用规则引擎执行数学公式
     *
     * @param parameter 里面存放了相关的参数及执行动作实例对象
     * @return RuleActionResult 执行是否成功，参数已经放置在parameter中
     * @throws Exception 如果不成功会抛出相关的异常
     */
    @Override
    public RuleActionResult formulaExecuteInAction(JSONObject parameter) throws Exception {
        RuleActionResult ruleActionResult = new RuleActionResult();
        if (parameter == null) {
            ruleActionResult.setMsg("parameter传入为空");
            ruleActionResult.setStatus(false);
            return ruleActionResult;
        }

        Object instanceObj = parameter.get(RuleConstant.RULE_ACTION_INSTANCE);
        if (null == instanceObj) {
            ruleActionResult.setStatus(false);
            ruleActionResult.setMsg("获取执行动作实例对象失败");
            return ruleActionResult;
        }
        RuleActionInstanceEntity_HI_RO ruleActionInstance = new RuleActionInstanceEntity_HI_RO();
        ConvertUtils.register(new DateLocaleConverter(),Date.class);
        BeanUtils.populate(ruleActionInstance, (JSONObject) instanceObj);
        return formulaExecuteInActionInstance(ruleActionInstance, parameter);
    }

    /**
     * 业务线来验证规则引擎，返回带动作的返回结果
     *
     * @param ruleBusinessLineCode 业务编码
     * @param parameters           匹配规则需要的参数
     * @param findAction           是否查询动作，放置到结果集合中
     * @return
     * @throws Exception
     */
    @Override
    public List<RuleMatchResult> matchRuleByBussinessLine(String ruleBusinessLineCode, JSONObject parameters, boolean findAction) {
        List<RuleMatchResult> ruleMatchResultList = matchRuleByBussinessLine(ruleBusinessLineCode, parameters);
        if(findAction) {
            for (RuleMatchResult ruleMatchResult : ruleMatchResultList) {
                if(ruleMatchResult.getStatus()&&ruleMatchResult.isRuleUseAction()) {
                    try {
                    List<RuleActionInstanceEntity_HI_RO> ruleActionInstanceList = ruleActionInstanceServer.findRuleActionInstanceList(ruleMatchResult.getRuleExpCode());
                    ruleMatchResult.setRuleActionList(ruleActionInstanceList);
                    }catch (Exception e) {
                        logger.error("业务线匹配规则引擎，查询动作实例失败，{}",ruleMatchResult.getRuleExpCode());
                        ruleMatchResultList.clear();
                        return ruleMatchResultList;
                    }
                }
            }
        }
        return ruleMatchResultList;
    }

    /**
     * 数学公式的解析与运算
     *
     * @param formala   数学公式，变量用[] 包裹
     * @param parameter 存放数学公式变量具体值的jsonObj
     * @return 字符形式的数字
     * @throws Exception 执行数学公式失败抛出异常及正则表达式的异常,及必传参数异常
     */
    private String formulaResolveAndCalculate(String formala, JSONObject parameter) throws Exception {
        //正则表达式
        String regex = "\\[\\s*([^\\[\\]]+?)\\s*\\]";
        Pattern compile = Pattern.compile(regex);
        Matcher matcher = compile.matcher(formala);
        while (matcher.find()) {
            String param = parameter.getString(matcher.group(1));
            if (StringUtils.isBlank(param)) {
                throw new RuntimeException("执行数学公式，必传参数未传入：" + param);
            }
            formala = formala.replaceAll("\\[" + matcher.group(1) + "]", param);
        }
        Object eval = jse.eval(formala);
        return String.valueOf(eval);
    }

    public static void main(String[] args) {
        RuleEngineServer ruleEngineServer = new RuleEngineServer();
        RuleActionInstanceEntity_HI_RO ruleActionInstance = new RuleActionInstanceEntity_HI_RO();
        ruleActionInstance.setActionFormula("[oldProduct]*[times]");
        ruleActionInstance.setActionDynamicParam("memberId,oldProduct,test");
        ruleActionInstance.setActionStaticParam("times,hehe");
        ruleActionInstance.setActionReturnParam("newPrice");
        ruleActionInstance.setActionReturnParamType("Double");
        JSONObject parameter = new JSONObject();
        parameter.put("oldProduct", "2.8");
        parameter.put("times", "2");
        parameter.put("memberId", "45");
        //设置时间类型为空，防止转换出错
        ruleActionInstance.setCreationDate(null);
        ruleActionInstance.setLastUpdateDate(null);
        parameter.put(RuleConstant.RULE_ACTION_INSTANCE, ruleActionInstance);
        parameter.put("big", new BigDecimal(8.9));
        String big = parameter.getString("big");
//        System.out.println(big);
        parameter = JSON.parseObject(parameter.toJSONString());
//        Object actionInstanceObj = parameter.get("actionInstance");
//        System.out.println(actionInstanceObj.getClass().getSimpleName());

        try {
//            BeanUtils.populate(ruleActionInstanceEntity_hi_ro,(JSONObject)actionInstanceObj);
//            System.out.println(JSON.toJSONString(ruleActionInstanceEntity_hi_ro));
            ruleEngineServer.formulaExecuteInAction(parameter);
//            System.out.println("========");
//            String s = ruleEngineServer.formulaResolveAndCalculate(ruleActionInstance.getActionFormula(), parameter);
//            System.out.println(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}