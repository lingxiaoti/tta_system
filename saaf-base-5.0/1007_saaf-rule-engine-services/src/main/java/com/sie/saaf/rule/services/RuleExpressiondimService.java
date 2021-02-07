package com.sie.saaf.rule.services;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.rule.model.entities.RuleExpressionEntity_HI;
import com.sie.saaf.rule.model.entities.RuleExpressiondimEntity_HI;
import com.sie.saaf.rule.model.entities.readonly.RuleExpressiondimEntity_HI_RO;
import com.sie.saaf.rule.model.inter.IRuleExpression;
import com.sie.saaf.rule.model.inter.IRuleExpressiondim;
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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/ruleExpressiondimService")
public class RuleExpressiondimService extends CommonAbstractService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RuleExpressiondimService.class);

    @Autowired
    private IRuleExpressiondim expressiondimServer;

    @Autowired
    private IRuleExpression expressionServer;

    @Override
    public IBaseCommon getBaseCommonServer() {
        return expressiondimServer;
    }

    
    @RequestMapping(method = RequestMethod.POST,value="query")
    public String query(@RequestParam(required=false) String params,
    		@RequestParam(required = false,defaultValue = "1") Integer pageIndex,
    		@RequestParam(required = false,defaultValue = "10") Integer pageRows){
        try{
    		JSONObject queryParamJSON = parseObject(params);
            Pagination<RuleExpressiondimEntity_HI_RO> findList = expressiondimServer.find(queryParamJSON,pageIndex,pageRows);

    		JSONObject results = JSONObject.parseObject(JSON.toJSONString(findList));
    		results.put(SToolUtils.STATUS, SUCCESS_STATUS);
    		results.put(SToolUtils.MSG, "成功");
    		return results.toString();
        }catch(Exception e){
            LOGGER.error(e.getMessage(),e);
    		return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }


    @RequestMapping(method = RequestMethod.POST,value="queryAll")
    public String queryAll(@RequestParam(required=false) String params,
    		@RequestParam(required = false,defaultValue = "1") Integer pageIndex,
    		@RequestParam(required = false,defaultValue = "10") Integer pageRows){
        try{
    		JSONObject queryParamJSON = parseObject(params);
            Pagination<RuleExpressiondimEntity_HI_RO> pagination = expressiondimServer.find(queryParamJSON, pageIndex, pageRows);
            JSONObject results = JSONObject.parseObject(JSON.toJSONString(pagination));
            results.put(SToolUtils.STATUS, SUCCESS_STATUS);
            results.put(SToolUtils.MSG, "成功");
            return results.toString();
            /*String sql=RuleExpressiondimEntity_HI_RO.rightQuery ;
            String ruleExpCode=queryParamJSON.getString("var_equal_ruleExpCode");
            if (StringUtils.isNotBlank(ruleExpCode)){
                sql=sql.replace("#replace#","AND re.RULE_EXP_CODE='"+ruleExpCode+"'");
                queryParamJSON.remove("var_equal_ruleExpCode");
            }else {
                sql=sql.replace("#replace#","");
            }
            Pagination<RuleExpressiondimEntity_HI_RO> rightPagination = expressiondimServer.findBySql(queryParamJSON,sql,pageIndex, pageRows);
            JSONObject result = (JSONObject) JSON.toJSON(pagination);
            List<RuleExpressiondimEntity_HI_RO> list=new ArrayList<>();
            list.addAll(pagination.getData());
            list.addAll(rightPagination.getData());
            Collections.sort(list);
            result.put("count",pagination.getCount()+rightPagination.getCount());
            result.put("data", list);
            result.put("status", "S");
            return result.toString();*/
        }catch(Exception e){
            LOGGER.error(e.getMessage(),e);
    		return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }

    @RequestMapping(method = RequestMethod.POST,value="saveOrUpdate")
    public String saveOrUpdate(@RequestParam(required=false) String params){
        try{
    		JSONObject queryParamJSON = parseObject(params);
            if (StringUtils.isEmpty(queryParamJSON.getString("ruleBusinessLineCode")) )
                return SToolUtils.convertResultJSONObj("P","parameter error",0,null).toString();
            RuleExpressiondimEntity_HI instance = expressiondimServer.saveOrUpdate(queryParamJSON, queryParamJSON.getIntValue("operatorUserId"));
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", 1, instance).toString();
        }catch(Exception e){
            LOGGER.error(e.getMessage(),e);
    		return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }
    
    @RequestMapping(method = RequestMethod.POST,value="saveOrUpdateAll")
    public String saveOrUpdateAll(@RequestParam(required=false) String params){
        try{
    		JSONObject queryParamJSON = parseObject(params);
            JSONObject props = new JSONObject();
            if (queryParamJSON.containsKey("expression")) {
                LOGGER.info("businessline:{}", queryParamJSON.getString("expression"));
                props = queryParamJSON.getJSONObject("expression");
            }
            JSONArray propvals = queryParamJSON.containsKey("dimExpressions") ? queryParamJSON.getJSONArray("dimExpressions") : null;
            if (props == null && propvals == null)
                return SToolUtils.convertResultJSONObj("P","parameter error",0,null).toString();

            List<RuleExpressiondimEntity_HI> valResult = new ArrayList<>();
            RuleExpressionEntity_HI propsResult = new RuleExpressionEntity_HI();
            JSONObject result = new JSONObject();

            if (props != null) {
                propsResult = expressionServer.saveOrUpdate(props, -1);
            }
            if (propvals != null) {
                for (int i = 0; i < propvals.size(); i++) {
                    JSONObject temp = propvals.getJSONObject(i);
                    temp.put("ruleExpCode", propsResult.getRuleExpCode());
                    RuleExpressiondimEntity_HI obj = expressiondimServer.saveOrUpdate(temp, -1);
                    valResult.add(obj);
                }
            }
            result.put("expression", propsResult);
            result.put("dimExpressions", valResult);

            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", 1, result).toString();
        }catch(Exception e){
            LOGGER.error(e.getMessage(),e);
    		return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }

    @RequestMapping(method = RequestMethod.POST,value="delete")
    public String delete(@RequestParam(required=false) String params){
        try{
            JSONObject queryParamJSON = parseObject(params);
            if(queryParamJSON==null || !queryParamJSON.containsKey("ruleExpDimId")){
                return SToolUtils.convertResultJSONObj(ERROR_STATUS, "缺少参数 ruleExpDimId ", 0, null).toString();
            }
            List<Serializable> ids = new ArrayList<Serializable>();
            String[] idArr = queryParamJSON.getString("ruleExpDimId").split(",");
            for (String id : idArr) {
                ids.add(id);
            }
            expressiondimServer.deleteAll(ids);
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", ids.size(), ids).toString();
        }catch(Exception e){
            LOGGER.error(e.getMessage(),e);
    		return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }

//    @GET
//    @Path("user")
//    @Produces("text/plain")
//    public String getUser(@QueryParam("name")
//        String name) {
//        return "hello " + name;
//    }}
    
}
