package com.sie.saaf.rule.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.rule.model.entities.RuleBusinessLineEntity_HI;
import com.sie.saaf.rule.model.entities.RuleDimEntity_HI;
import com.sie.saaf.rule.model.entities.readonly.RuleDimEntity_HI_RO;
import com.sie.saaf.rule.model.inter.IRuleBusinessLine;
import com.sie.saaf.rule.model.inter.IRuleDim;
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
@RequestMapping("/ruleDimService")
public class RuleDimService extends CommonAbstractService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RuleDimService.class);

    @Autowired
    private IRuleDim ruleDimServer ;

    @Autowired
    private IRuleBusinessLine ruleBusinessLineServer;

    @Override
    public IBaseCommon getBaseCommonServer() {
        return ruleBusinessLineServer;
    }

    @RequestMapping(method = RequestMethod.POST,value="query")
    public String query(@RequestParam(required=false) String params,
    		@RequestParam(required = false,defaultValue = "1") Integer pageIndex,
    		@RequestParam(required = false,defaultValue = "10") Integer pageRows){
        try{
    		JSONObject queryParamJSON = parseObject(params);
            Pagination<RuleDimEntity_HI_RO> pagination = ruleDimServer.find(queryParamJSON,pageIndex,pageRows);

    		JSONObject results = JSONObject.parseObject(JSON.toJSONString(pagination));
    		results.put(SToolUtils.STATUS, SUCCESS_STATUS);
    		results.put(SToolUtils.MSG, "成功");
    		return results.toString();
        }catch(Exception e){
            LOGGER.error(e.getMessage(),e);
    		return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }

    @RequestMapping(method = RequestMethod.POST,value="delete")
    public String delete(@RequestParam(required=false) String params){
        try{
            JSONObject queryParamJSON = parseObject(params);
            if(queryParamJSON==null || !queryParamJSON.containsKey("ruleDimId")){
                return SToolUtils.convertResultJSONObj(ERROR_STATUS, "缺少参数 ruleDimId ", 0, null).toString();
            }
            List<Serializable> ids = new ArrayList<Serializable>();
            String[] idArr = queryParamJSON.getString("ruleDimId").split(",");
            for (String id : idArr) {
                ids.add(id);
            }
            ruleDimServer.deleteAll(ids);
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", ids.size(), ids).toString();
        }catch(Exception e){
            LOGGER.error(e.getMessage(),e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }

    @RequestMapping(method = RequestMethod.POST,value="saveOrUpdateDim")
    public String saveOrUpdateDim(@RequestParam(required=false) String params){
        try{
    		JSONObject queryParamJSON = parseObject(params);

            if (StringUtils.isBlank(queryParamJSON.getString("ruleBusinessLineCode")) )
                return SToolUtils.convertResultJSONObj("P","parameter error",0,null).toString();
            if (queryParamJSON.get("ruleDimCode")!=null){
                List<RuleDimEntity_HI> list= ruleDimServer.findByProperty(new JSONObject().fluentPut("ruleDimCode",queryParamJSON.getString("ruleDimCode")).fluentPut("ruleBusinessLineCode",queryParamJSON.getString("ruleBusinessLineCode")));
                if (list.size()>0 && queryParamJSON.get("ruleDimId")==null)
                    return SToolUtils.convertResultJSONObj("E","维度编码重复",0,null).toString();
                else  if (list.size()>0 && queryParamJSON.get("ruleDimId")!=null && list.get(0).getRuleDimId().equals(queryParamJSON.getIntValue("ruleDimId"))==false)
                    return SToolUtils.convertResultJSONObj("E","维度编码重复",0,null).toString();
            }

            RuleDimEntity_HI instance = ruleDimServer.saveOrUpdate(queryParamJSON, queryParamJSON.getIntValue("operatorUserId"));
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", 1, instance).toString();
        }catch(Exception e){
            LOGGER.error(e.getMessage(),e);
    		return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }


    @RequestMapping(method = RequestMethod.POST,value="saveOrUpdate")
    public String saveOrUpdate(@RequestParam(required=false) String params){
        try{
    		JSONObject queryParamJSON = parseObject(params);
            JSONObject props = null;
            if (queryParamJSON.containsKey("businessline")) {
                LOGGER.info("businessline:{}", queryParamJSON.getString("businessline"));
                props = queryParamJSON.getJSONObject("businessline");
            }
            JSONArray propvals = queryParamJSON.containsKey("dims") ? queryParamJSON.getJSONArray("dims") : null;
            if (props == null && propvals == null)
                return SToolUtils.convertResultJSONObj("P","parameter error",0,null).toString();

            List<RuleDimEntity_HI> valResult = new ArrayList<>();
            RuleBusinessLineEntity_HI propsResult = new RuleBusinessLineEntity_HI();
            JSONObject result = new JSONObject();

            if (props != null) {
                propsResult = ruleBusinessLineServer.saveOrUpdate(props, queryParamJSON.getIntValue("operatorUserId"));
            }
            if (propvals != null) {
                for (int i = 0; i < propvals.size(); i++) {
                    JSONObject temp = propvals.getJSONObject(i);
                    if (StringUtils.isBlank(temp.getString("ruleBusinessLineCode")))
                        temp.put("ruleBusinessLineCode", propsResult.getRuleBusinessLineCode());
                    RuleDimEntity_HI obj = ruleDimServer.saveOrUpdate(temp, queryParamJSON.getIntValue("operatorUserId"));
                    valResult.add(obj);
                }
            }
            result.put("businessline", propsResult);
            result.put("dims", valResult);
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", 1, result).toString();
        }catch(Exception e){
            LOGGER.error(e.getMessage(),e);
    		return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }

}
