package com.sie.saaf.rule.model.inter.server;


import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.rule.model.beans.DimOperatorValue;
import com.sie.saaf.rule.model.beans.DimServiceBean;
import com.sie.saaf.rule.model.beans.ExpressionDimSize;
import com.sie.saaf.rule.model.dao.*;
import com.sie.saaf.rule.model.dao.readonly.RuleExpressionFullViewDAO_HI_RO;
import com.sie.saaf.rule.model.entities.*;
import com.sie.saaf.rule.model.entities.readonly.RuleExpressionFullViewEntity_HI_RO;
import com.sie.saaf.rule.model.inter.IRuleExpressionFullView;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.HibernateHandler;
import com.yhg.hibernate.core.dao.DynamicBaseViewObject;
import com.yhg.redis.framework.JedisClusterCore;
import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisCluster;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Component("ruleExpressionFullViewServer")
public class RuleExpressionFullViewServer extends JedisClusterCore implements IRuleExpressionFullView {
    private static final Logger LOGGER = LoggerFactory.getLogger(RuleExpressionFullViewServer.class);
    private static final String REGEX_STR = "#([\\s\\S]*?)#";
    private static final String FLAG_STR = "#";
    private static final String SELECT_QUERY_SQL = "select 'Y' from dual where ";
    private static final String SQL_TYPE_BEGIN = "#SQLType_#";
    private static final String SQL_TYPE_END = "#_SQLType#";
    public static final String[] SQL_FUNCTION = {"ABS(", "CEIL(", "CEILING(", "FLOOR(", "SIGN(", "PI()", "TRUNCATE(", "ROUND(", "POW(", "POW(", "SQRT(", "EXP(", "MOD(", "CHAR_LENGTH(", "LENGTH(", "CONCAT(", "CONCAT_WS(", "CHAR_LENGTH(", "LENGTH(", "CONCAT(", "CONCAT_WS(", "INSERT(", "UPPER(", "UCAASE(", "LOWER(", "LCASE(", "LEFT(", "RIGHT(", "LPAD(", "RPAD(", "LTRIM(", "RTRIM(", "TRIM(", "TRIM(", "REPEAT(", "SPACE(", "REPLACE(", "STRCMP(", "SUBSTRING(", "MID(", "LOCATE(", "POSITION(", "INSTR(", "REVERSE(", "ELT(", "EXPORT_SET(", "FIELD(", "FIND_IN_SET(", "MAKE_SET(", "SUBSTRING_INDEX", "LOAD_FILE(", "CURDATE()", "CURRENT_DATE()", "CURTIME()", "CURRENT_TIME", "NOW()", "CURRENT_TIMESTAMP()", "LOCALTIME()", "SYSDATE()", "LOCALTIMESTAMP()", "UNIX_TIMESTAMP()", "UNIX_TIMESTAMP(", "FROM_UNIXTIME(", "UTC_DATE()", "UTC_TIME()", "MONTH(", "MONTHNAME(", "DAYNAME(", "DAYOFWEEK(", "WEEKDAY(", "WEEK(", "DAYOFYEAR(", "DAYOFMONTH(", "QUARTER(", "HOUR(", "MINUTE(", "SECOND(", "EXTRACT(", "TIME_TO_SEC(", "SEC_TO_TIME(", "TO_DAYS(", "FROM_DAYS(", "DATEDIFF(", "ADDDATE(", "ADDDATE(", "DATE_ADD(", "SUBDATE(", "SUBDATE(", "ADDTIME(", "SUBTIME(", "DATE_FORMAT(", "TIME_FORMAT(", "GET_FORMAT("};

    @Autowired
    private DynamicBaseViewObject commonDAO_HI_DY;

    @Resource(name="ruleBusinessLineDAO_HI")
    private RuleBusinessLineDAO_HI ruleBusinessLineDAO_HI;
    @Resource(name="ruleExpressionFullViewDAO_HI_RO")
    private RuleExpressionFullViewDAO_HI_RO ruleExpressionFullViewDAO_HI_RO;
    @Resource(name="ruleMappingBusinessDAO_HI")
    private RuleMappingBusinessDAO_HI ruleMappingBusinessDAO_HI;
    @Resource(name="ruleDimDAO_HI")
    private RuleDimDAO_HI ruleDimDAO_HI;
    @Resource(name="saafWebserviceInfoDAO_HI")
    private SaafWebserviceInfoDAO_HI saafWebserviceInfoDAO_HI;
    @Resource(name="saafWebserviceParamInfoDAO_HI")
    private SaafWebserviceParamInfoDAO_HI saafWebserviceParamInfoDAO_HI;


    public RuleExpressionFullViewServer() {
        super();
    }

    private static Map<String, Object> prepareMapParam(JSONObject queryParamJSON) {
        Map<String, Object> queryRuleDimMap = new HashMap<String, Object>();
        queryRuleDimMap.put("ruleBusinessLineCode", queryParamJSON.get("ruleBusinessLineCode"));
        return queryRuleDimMap;
    }

    //    private void executeWebServiceURL(){
    //        Map<Integer, Object> businessDimValueMap = new HashMap<Integer, Object>();
    //        Set<Map.Entry<Integer, Object>> entrySet = businessDimValueMap.entrySet();
    //        Iterator<Map.Entry<Integer, Object>> iterator = entrySet.iterator();
    //        while(iterator.hasNext()){
    //            Map.Entry<Integer, Object> entry = iterator.next();
    //            if(entry instanceof DimOperatorValue){
    //                continue;
    //            }else if(entry instanceof DimServiceBean){
    //                DimServiceBean entry_ = (DimServiceBean)entry;
    //                String busRequestParams = entry_.getBusRequestParams();
    //                String busResultParams = entry_.getBusResultParams();
    //                String busTargetSource = entry_.getBusTargetSource();
    //                String busTargetType = entry_.getBusTargetType();
    //            }
    //        }
    //    }

    /**
     * 获取指定表达式中的所有维度
     *
     * @param fullExpression
     * @return
     */
    public Map<String, Object> queryDimAttrFromExc(String fullExpression) {
        Map<String, Object> dimValueMap = new HashMap<String, Object>();
        Pattern p = Pattern.compile(REGEX_STR);
        Matcher m = p.matcher(fullExpression);
        while (m.find()) {
            String dimName = m.group();
            dimName = dimName.replaceAll(FLAG_STR, "");
            dimValueMap.put(dimName, null);
        }
        return dimValueMap;
    }

    /**
     * 替换表达式中的占位符
     *
     * @param fullExpression
     * @param queryBusinessParamsMap
     * @return
     */
    public String replaceHolder(String fullExpression, Map<String, Object> queryBusinessParamsMap) {
        Set<Map.Entry<String, Object>> entrySet_ = queryBusinessParamsMap.entrySet();
        Iterator<Map.Entry<String, Object>> iterator_ = entrySet_.iterator();
        while (iterator_.hasNext()) {
            Map.Entry<String, Object> entry_ = iterator_.next();
            String key = entry_.getKey();
            Object object = entry_.getValue();
            //判断object的参数类型进行不同的处理
            if (object.toString().indexOf(SQL_TYPE_BEGIN) >= 0) {
                String vlaue = object.toString();
                vlaue = vlaue.replaceAll(SQL_TYPE_BEGIN, "(");
                vlaue = vlaue.replaceAll(SQL_TYPE_END, ")");
                fullExpression = fullExpression.replaceAll(FLAG_STR + key + FLAG_STR, vlaue);
            } else {
                if (object instanceof String) {
                    fullExpression = fullExpression.replaceAll(FLAG_STR + key + FLAG_STR, "'" + object + "'");
                } else if (object instanceof Integer || object instanceof Double) {
                    fullExpression = fullExpression.replaceAll(FLAG_STR + key + FLAG_STR, object.toString());
                } else if (object instanceof Date) {
                    fullExpression = fullExpression.replaceAll(FLAG_STR + key + FLAG_STR, "'" + SToolUtils.date2String(new Date(), "yyyy-MM-dd") + "'");
                }
            }
        }
        return fullExpression;
    }

    public String replace(String fullExpression, Map<String, Object> queryBusinessParamsMap) {
        Set<Map.Entry<String, Object>> entrySet_ = queryBusinessParamsMap.entrySet();
        Iterator<Map.Entry<String, Object>> iterator_ = entrySet_.iterator();
        while (iterator_.hasNext()) {
            Map.Entry<String, Object> entry_ = iterator_.next();
            String key = entry_.getKey();
            Object object = entry_.getValue();
            fullExpression = fullExpression.replaceAll(FLAG_STR + key + FLAG_STR, Objects.toString(object));
        }
        return fullExpression;
    }

    private ResultSet demo(Statement createStatement, StringBuffer fullExpressionSB) throws SQLException {
        ResultSet rs1 = createStatement.executeQuery(fullExpressionSB.toString());
        return rs1;
    }

    private List findSqlResult(final String sql){
        return (List) commonDAO_HI_DY.executeQuery(new HibernateHandler() {
            @Override
            public Object doInHibernate(Session session) {
                SQLQuery query = session.createSQLQuery(sql);
                return query.list();
            }
        });
    }

    @Override
    public Map<RuleExpressionFullViewEntity_HI_RO, Object> matchingExpResult(JSONObject queryParamJSON, Map<String, Object> queryBusinessParamsMap) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {

        List<RuleExpressionFullViewEntity_HI_RO> ruleExpressionFullViewList = findRuleExpcetionFullView(queryParamJSON);
        queryParamJSON.remove("ruleExpCode");
        LOGGER.info("ruleExpressionFullViewList:{}", JSONObject.toJSON(ruleExpressionFullViewList));

        //获取业务线规则表达式的匹配规则，维度多的优先匹配 权重优先匹配 随机匹配 组合匹配
        Map<String, Object> queryParamMap = SToolUtils.fastJsonObj2Map(queryParamJSON);
        //queryParamMap.put("currentDate", new Date());and :currentDate between  effect_date and effect_end_date
        RuleBusinessLineEntity_HI businessLineEntity_HI = (RuleBusinessLineEntity_HI) ruleBusinessLineDAO_HI.get(" from RuleBusinessLineEntity_HI where rule_business_line_code = :ruleBusinessLineCode ", queryParamMap);
        //String resultRuleExpressionCode = null; //最终匹配的结果
        String matchingType = ""; //维度多的优先匹配 权重优先匹配 随机匹配
        List<RuleExpressionFullViewEntity_HI_RO> resultRuleExpressionCodes = new ArrayList<>();
        if (null != businessLineEntity_HI) {
            matchingType = businessLineEntity_HI.getRuleBusinessLineMapptype();
            //&& (businessLineEntity_HI.getRuleBusinessLineMapptype().equalsIgnoreCase("random") || businessLineEntity_HI.getRuleBusinessLineMapptype().equalsIgnoreCase("weight"))) {
            //如果是随机 或者权重优先匹配 或者是组合，将所有的符合的表达式code放到结果集中
            resultRuleExpressionCodes = new ArrayList<RuleExpressionFullViewEntity_HI_RO>();
        }
        TreeMap<ExpressionDimSize, RuleExpressionFullViewEntity_HI_RO> expFullDimSizeMap = new TreeMap<ExpressionDimSize, RuleExpressionFullViewEntity_HI_RO>();
        for (int i = 0; i < ruleExpressionFullViewList.size(); i++) {
            RuleExpressionFullViewEntity_HI_RO entity_HI_RO = ruleExpressionFullViewList.get(i);
            String fullExpression = entity_HI_RO.getFullExp();
            //获取表达式中所有的维度
            Map<String, Object> dimValuMap = queryDimAttrFromExc(fullExpression);
            expFullDimSizeMap.put(new ExpressionDimSize(dimValuMap.size(), entity_HI_RO.getRuleExpCode()), entity_HI_RO);
        }
        Set<Map.Entry<ExpressionDimSize, RuleExpressionFullViewEntity_HI_RO>> entrySet = expFullDimSizeMap.entrySet();
        Iterator<Map.Entry<ExpressionDimSize, RuleExpressionFullViewEntity_HI_RO>> iterator = entrySet.iterator();
        Map<RuleExpressionFullViewEntity_HI_RO, Object> failMap=new ConcurrentHashMap<>();
        while (iterator.hasNext()) {
            Map.Entry<ExpressionDimSize, RuleExpressionFullViewEntity_HI_RO> entry = iterator.next();
            RuleExpressionFullViewEntity_HI_RO entity_HI_RO = entry.getValue();
            String fullExpression = entity_HI_RO.getFullExp();
            //根据配置的信息获取该表达式所需要的所有参数
            //获取维度的默认值
            if (null == queryBusinessParamsMap || queryBusinessParamsMap.size() == 0) {
                queryBusinessParamsMap = new HashMap<String, Object>();
            }
            findBusinessLineAllDim(queryParamJSON, queryBusinessParamsMap);


            //替换表达式中的占位符
            fullExpression = replaceHolder(fullExpression, queryBusinessParamsMap);
            //开始匹配表达式
            StringBuffer fullExpressionSB = new StringBuffer(SELECT_QUERY_SQL);
            fullExpressionSB.append(fullExpression);
            LOGGER.info("queryBusinessParamsMap:{}", queryBusinessParamsMap);
            LOGGER.info("fullExpression:{}", fullExpressionSB);
            try {
                List queryResult=findSqlResult(fullExpressionSB.toString());
                //如果执行结果为true
                if (queryResult.size()>0) {
                    //resultRuleExpressionCode = entity_HI_RO.getRuleExpCode();
                    if (matchingType.equalsIgnoreCase("dimSize")) {
                        //如果是维度多的优先匹配，匹配到结果直接退出循环
                        //resultRuleExpressionCode = entity_HI_RO.getRuleExpCode();
                        if (null == resultRuleExpressionCodes) {
                            resultRuleExpressionCodes = new ArrayList<RuleExpressionFullViewEntity_HI_RO>();
                        }
                        resultRuleExpressionCodes.add(entity_HI_RO);
                        break;
                    } else {
                        if (null == resultRuleExpressionCodes) {
                            resultRuleExpressionCodes = new ArrayList<RuleExpressionFullViewEntity_HI_RO>();
                        }
                        resultRuleExpressionCodes.add(entity_HI_RO);
                    }
                }else {
                    failMap.put(entity_HI_RO,new JSONObject().fluentPut("msg","规则不匹配"));
                }
            } catch (Exception sqle) {
                LOGGER.error(sqle.getMessage(), sqle);
                failMap.put(entity_HI_RO,new JSONObject().fluentPut("msg","规则不匹配"));
            }
        }
        if (matchingType.equalsIgnoreCase("dimSize")) {
            Map<RuleExpressionFullViewEntity_HI_RO, Object> result= matchingResult(resultRuleExpressionCodes);
            result.putAll(failMap);
            return result;
        }
        if (resultRuleExpressionCodes.size() > 0 && resultRuleExpressionCodes.size() == 1) {
            //resultRuleExpressionCode = resultRuleExpressionCodes.get(0).getRuleExpCode();

        } else {
            if (matchingType.equalsIgnoreCase("random")) {
                //随机产生一个匹配结果
                int index = (int)(Math.random() * resultRuleExpressionCodes.size());
                //resultRuleExpressionCode = resultRuleExpressionCodes.get(index).getRuleExpCode();
                RuleExpressionFullViewEntity_HI_RO resultRuleExpression = resultRuleExpressionCodes.get(index);
                resultRuleExpressionCodes.clear();
                resultRuleExpressionCodes.add(resultRuleExpression);
            } else if (matchingType.equalsIgnoreCase("weight")) {
                int expWeigth = 0;
                List<RuleExpressionFullViewEntity_HI_RO> ruleArray=new ArrayList<>();
                ruleArray.addAll(resultRuleExpressionCodes);
                resultRuleExpressionCodes.clear();
                for (int i = 0; i < ruleArray.size(); i++) {
                    RuleExpressionFullViewEntity_HI_RO temp = ruleArray.get(i);
                    if (i == 0) {
                        expWeigth = temp.getRuleExpWeigth();
                        resultRuleExpressionCodes.add(temp);
                    } else {
                        if (expWeigth <= temp.getRuleExpWeigth()) {
                            expWeigth = temp.getRuleExpWeigth();
                            resultRuleExpressionCodes.clear();
                            resultRuleExpressionCodes.add(temp);
                        }
                    }
                }
            } else if (matchingType.equalsIgnoreCase("all")) {

            }
        }
        Map<RuleExpressionFullViewEntity_HI_RO, Object> result=matchingResult(resultRuleExpressionCodes);
        result.putAll(failMap);
        return result;
    }

    private Map<RuleExpressionFullViewEntity_HI_RO, Object> matchingResult(List<RuleExpressionFullViewEntity_HI_RO> resultRuleExpressionCodes) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        //开始匹配业务处理的表达式
        Map<RuleExpressionFullViewEntity_HI_RO, Object> businessDimValueMap = new HashMap<RuleExpressionFullViewEntity_HI_RO, Object>();
        for (int k = 0; k < resultRuleExpressionCodes.size(); k++) {
            RuleExpressionFullViewEntity_HI_RO entity_HI_RO = resultRuleExpressionCodes.get(k);
            Map<String, Object> tmpParamMap = new HashMap<String, Object>();
            tmpParamMap.put("ruleExcCode", entity_HI_RO.getRuleExpCode());
            List<RuleMappingBusinessEntity_HI> ruleMappingBusiness = ruleMappingBusinessDAO_HI.findList(" from RuleMappingBusinessEntity_HI where rule_exc_code = :ruleExcCode and now() between  coalesce(effect_date,current_date()-1) and coalesce(effect_end_date,current_date()+1)", tmpParamMap);
            List<DimOperatorValue> dimOperatorValues = new ArrayList<>();
            List<DimServiceBean> dimServiceBeans = new ArrayList<>();
            for (int i = 0; i < ruleMappingBusiness.size(); i++) {
                //RuleExpressionFullViewEntity_HI_RO entity_HI_RO_ = (RuleExpressionFullViewEntity_HI_RO) BeanUtils.cloneBean(entity_HI_RO);
                RuleExpressionFullViewEntity_HI_RO entity_HI_RO_ = JSONObject.parseObject(JSONObject.toJSONString(entity_HI_RO), RuleExpressionFullViewEntity_HI_RO.class);
                RuleMappingBusinessEntity_HI businessEntity_HI = ruleMappingBusiness.get(i);
                String busParam = businessEntity_HI.getRuleBusParam();
                String ruleBusResultDim = businessEntity_HI.getRuleBusResultDim();
                Integer ruleMappBusId = businessEntity_HI.getRuleMappBusId();
                entity_HI_RO_.setIndex(ruleMappBusId);
                String dimName = businessEntity_HI.getRuleBusDim();
                String busTargetType = businessEntity_HI.getRuleBusTargetType();
                String busTargetSource = businessEntity_HI.getRuleBusTargetSource();
                if (null != dimName && !"".equals(dimName.trim()) && "confirmed".equals(busTargetType.trim())) { // && 0 != dimValue && (null != dimOperator && !"".equals(dimOperator))) {
                    dimOperatorValues.add(new DimOperatorValue(dimName, businessEntity_HI.getRuleBusDimOperator(), businessEntity_HI.getRuleBusDimValue()));
                } else if (null != busTargetType && "serviceURL".equals(busTargetType.trim()) && null != busTargetSource && !"".equals(busTargetSource)) {
                    Map<String,Object> map=new HashMap<>();
                    map.put("webserviceCode",busTargetSource);
                    map.put("businessLineCode",entity_HI_RO.getRuleBusinessLineCode());
                    List<SaafWebserviceInfoEntity_HI> servicesList=saafWebserviceInfoDAO_HI.findByProperty(map);
                    if (servicesList.size()==0){
                        LOGGER.error("未找到对应服务配置[webserviceCode:{}]",busTargetSource);
                        continue;
                    }
                    List<SaafWebserviceParamInfoEntity_HI> paramsList=saafWebserviceParamInfoDAO_HI.findByProperty("webserviceCode",servicesList.get(0).getWebserviceCode());
                    dimServiceBeans.add(new DimServiceBean(busTargetType, busTargetSource,servicesList.get(0).getWebserviceUrl() ,paramsList, ruleBusResultDim));
                }
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("dimOperatorValues", dimOperatorValues);
            jsonObject.put("dimServiceBeans", dimServiceBeans);
            businessDimValueMap.put(entity_HI_RO, jsonObject);
        }
        return businessDimValueMap;
    }

    /**
     * 获取当前业务线的所有维度
     *
     * @param queryParamJSON
     * @return
     */
    public List<RuleDimEntity_HI> findBusinessLineAllDim(JSONObject queryParamJSON) {
        Map<String, Object> queryRuleDimMap = prepareMapParam(queryParamJSON);
        List<RuleDimEntity_HI> findList =ruleDimDAO_HI.findList(" from RuleDimEntity_HI where rule_business_line_code = :ruleBusinessLineCode and now() between  coalesce(effect_date,current_date()-1) and coalesce(effect_end_date,current_date()+1)", queryRuleDimMap);
        return findList;
    }

    private Map<String, Object> findBusinessLineAllDim(JSONObject queryParamJSON, Map<String, Object> queryBusinessParamsMap) {
        List<RuleDimEntity_HI> findList = findBusinessLineAllDim(queryParamJSON);
        for (int i = 0; i < findList.size(); i++) {
            RuleDimEntity_HI dimEntity_HI = findList.get(i);
            String dimCode = dimEntity_HI.getRuleDimCode();
            if (queryBusinessParamsMap.containsKey(dimCode)) {
                continue;
            }
            Object defaultValue = dimEntity_HI.getRuleDimDefaultValue();
            String dataType = dimEntity_HI.getRuleDimDataType();
            String dimValueFrom = dimEntity_HI.getRuleDimValueFrom();
            String dimTargetSource = dimEntity_HI.getRuleDimTargetSource();
            if (null != dimValueFrom && dimTargetSource != null) {
                if ("redis".equalsIgnoreCase(dimValueFrom.trim())) {
                    //从redis中利用 dimTargetSource做为key获取一个值给到 defaultValue
                    defaultValue = get(replaceHolder(dimTargetSource, queryBusinessParamsMap));
                    //queryBusinessParamsMap.put(dimCode, get(dimTargetSource));
                } else if ("webservice".equalsIgnoreCase(dimValueFrom.trim())) {
                    //TODO 从服务中利用 dimTargetSource做为请求的服务源头 获取一个值给到 defaultValue


                } else if ("sql".equalsIgnoreCase(dimValueFrom.trim())) {
                    //从sql中利用dimTargetSource做为请求的sql语句 获取一个值给到 defaultValue

                    defaultValue = replaceHolder(dimTargetSource, queryBusinessParamsMap);
                    dataType = "SQLType";
                    //queryBusinessParamsMap.put(dimCode, "(" + dimTargetSource + ")");
                }
                if (defaultValue==null)
                    defaultValue=dimEntity_HI.getRuleDimDefaultValue();
                if (null != dataType) {
                    if ("Integer".equalsIgnoreCase(dataType)) {
                        defaultValue = defaultValue == null ? 0 : Integer.parseInt((String) defaultValue);
                    } else if ("Date".equalsIgnoreCase(dataType)) {
                        defaultValue = (Date) defaultValue;
                    } else if ("String".equalsIgnoreCase(dataType)) {
                        defaultValue = String.valueOf(defaultValue);
                    } else if ("Double".equalsIgnoreCase(dataType)) {
                        defaultValue = defaultValue == null ? 0 : Double.valueOf((String) defaultValue);
                    } else if ("Float".equalsIgnoreCase(dataType)) {
                        defaultValue = defaultValue == null ? 0 : Float.valueOf((String) defaultValue);
                    } else if ("SQLType".equals(dataType)) {
                        //Map<String, Object> childSQLDimMap = queryDimAttrFromExc((String)defaultValue);
                        defaultValue = SQL_TYPE_BEGIN + defaultValue + SQL_TYPE_END;
                    }
                }
                queryBusinessParamsMap.put(dimCode, defaultValue);
            }
        }
        return queryBusinessParamsMap;
    }

    /**
     * 根据业务线获取完整的规则表达式
     *
     * @param queryParamJSON
     * @return
     */
    public List<RuleExpressionFullViewEntity_HI_RO> findRuleExpcetionFullView(JSONObject queryParamJSON) {
        Map<String, Object> queryParamMap = SToolUtils.fastJsonObj2Map(queryParamJSON);
        String sql=RuleExpressionFullViewEntity_HI_RO.QUERY_SQL;
        if (queryParamJSON.get("ruleExpCode")!=null)
            sql=RuleExpressionFullViewEntity_HI_RO.query;
        else
            queryParamMap.remove("ruleExpCode");
        //获取符合业务线的所有的规则维度表达式
        List<RuleExpressionFullViewEntity_HI_RO> findListResult = ruleExpressionFullViewDAO_HI_RO.findList(sql, queryParamMap);
        LOGGER.info("sql:{},param:{}",sql,queryParamJSON.toJSONString());
        LOGGER.info("result:{}",JSONObject.toJSON(findListResult));
        LOGGER.info("size:{}",findListResult.size());
        //组装规则维度表达式变成一个完整的规则表达式
        Map<RuleExpressionFullViewEntity_HI_RO, Map<String, String>> expectionCodeMap = new HashMap<RuleExpressionFullViewEntity_HI_RO, Map<String, String>>();
        for (int i = 0; i < findListResult.size(); i++) {
            RuleExpressionFullViewEntity_HI_RO entity_HI_RO = findListResult.get(i);
            if (null != entity_HI_RO.getRuleDimValue() && "ALL".equalsIgnoreCase(entity_HI_RO.getRuleDimValue())) {
                entity_HI_RO.setAttributeValue("1 = 1");
            }
            Map<String, String> ruleDimMap = expectionCodeMap.get(entity_HI_RO);
            if (null == ruleDimMap) {
                ruleDimMap = new HashMap<String, String>();
                expectionCodeMap.put(entity_HI_RO, ruleDimMap);
            }
            //String id = entity_HI_RO.getPlaceholder();
            String id = entity_HI_RO.getRuleOrderId();
            String attributeValue = entity_HI_RO.getAttributeValue();

            if (attributeValue == null) {
                ruleDimMap.put(id, attributeValue);
                continue;
            }
            if ("in".equalsIgnoreCase(entity_HI_RO.getRuleDimOperators())) {
                attributeValue = "( #" + entity_HI_RO.getRuleDimCode() + "#  in " + getSqlInExpress(entity_HI_RO.getRuleDimValue()) +")";
            }else {
                attributeValue=   "( #" + entity_HI_RO.getRuleDimCode() + "# " +getSingleQuoteStr(entity_HI_RO.getRuleDimValue()) + getSqlInExpress(entity_HI_RO.getRuleDimValue())+")";
            }

            if (attributeValue != null && attributeValue.indexOf("between##and") >= 0) {
                String[] split = attributeValue.split(" ");
                if (split.length == 3 && split[2].split("##").length == 2)
                    attributeValue = "(" + split[0] + " " + split[1].split("##")[0] + " '" + split[2].split("##")[0] + "' " + split[1].split("##")[1] + " '" + split[2].split("##")[1] + "')";
            }
            //#sessionPrice# between##and 9##50
            ruleDimMap.put(id, attributeValue);
        }
        Set<Map.Entry<RuleExpressionFullViewEntity_HI_RO, Map<String, String>>> entrySet = expectionCodeMap.entrySet();
        Iterator<Map.Entry<RuleExpressionFullViewEntity_HI_RO, Map<String, String>>> iterator = entrySet.iterator();
        findListResult.clear();
        while (iterator.hasNext()) {
            Map.Entry<RuleExpressionFullViewEntity_HI_RO, Map<String, String>> entry = iterator.next();
            RuleExpressionFullViewEntity_HI_RO key = entry.getKey();
            Map<String, String> map = entry.getValue();
            String fullExpression = key.getFullExp();
            Set<Map.Entry<String, String>> set = map.entrySet();
            Iterator<Map.Entry<String, String>> iterator_ = set.iterator();
            while (iterator_.hasNext()) {
                Map.Entry<String, String> next = iterator_.next();
                LOGGER.info(next.getKey()+":"+next.getValue());
                String string = next.getKey();
                String value = next.getValue();
                if (value!=null)
                    fullExpression = fullExpression.replaceAll(string, value);
            }
            key.setFullExp(fullExpression);
            findListResult.add(key);
        }
        //返回所有符合规则业务线的规则完整的表达式
        return findListResult;
    }

    public boolean isSqlExpress(String express) {
        if (StringUtils.isBlank(express))
            return false;
        for (String str : SQL_FUNCTION) {
            if (express.toUpperCase().indexOf(str) > -1)
                return true;
        }
        return false;
    }

    public String getSingleQuoteStr(String str) {
        if (str == null)
            return str;
        if (str.indexOf("'") == -1)
            return "'" + str + "'";
        return str;
    }

    public String getSqlInExpress(String str) {
        if (str == null)
            return str;
        StringBuilder sb = new StringBuilder("(");
        String[] strs = str.split(",");
        for (String string : strs) {
            sb.append(getSingleQuoteStr(string));
            sb.append(",");
        }
        return sb.substring(0, sb.lastIndexOf(",")) + ")";
    }


    public String findRuleExpressionFullViewInfo(JSONObject queryParamJSON) {
        List<RuleExpressionFullViewEntity_HI_RO> findListResult = findRuleExpcetionFullView(queryParamJSON);
        JSONObject resultStr = SToolUtils.convertResultJSONObj("S", "", findListResult.size(), findListResult);
        return resultStr.toString();
    }

    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {


    }

    @Override
    @Resource(name = "jedisCluster")
    public void setJedisCluster(JedisCluster jedisCluster) {
        super.setJedisCluster(jedisCluster);
    }
}
