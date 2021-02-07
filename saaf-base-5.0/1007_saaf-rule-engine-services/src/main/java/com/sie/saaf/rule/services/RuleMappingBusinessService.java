package com.sie.saaf.rule.services;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.rule.model.entities.RuleMappingBusinessEntity_HI;
import com.sie.saaf.rule.model.entities.readonly.RuleMappingBusinessEntity_HI_RO;
import com.sie.saaf.rule.model.inter.IRuleMappingBusiness;
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
@RequestMapping("/ruleMappingBusinessService")
public class RuleMappingBusinessService extends CommonAbstractService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RuleMappingBusinessService.class);

    @Autowired
    private IRuleMappingBusiness ruleMappingBusinessServer;

    @Override
    public IBaseCommon getBaseCommonServer() {
        return ruleMappingBusinessServer;
    }

    @RequestMapping(method = RequestMethod.POST,value="query")
    public String query(@RequestParam(required=false) String params,
    		@RequestParam(required = false,defaultValue = "1") Integer pageIndex,
    		@RequestParam(required = false,defaultValue = "10") Integer pageRows){
        try{
    		JSONObject queryParamJSON = parseObject(params);
            Pagination<RuleMappingBusinessEntity_HI_RO> pagination = ruleMappingBusinessServer.find(queryParamJSON,pageIndex,pageRows);

    		JSONObject results = JSONObject.parseObject(JSON.toJSONString(pagination));
    		results.put(SToolUtils.STATUS, SUCCESS_STATUS);
    		results.put(SToolUtils.MSG, "成功");
    		return results.toString();
        }catch(Exception e){
            LOGGER.error(e.getMessage(),e);
    		return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }
    
    @RequestMapping(method = RequestMethod.POST,value="saveOrUpdate")
    public String saveOrUpdate(@RequestParam(required=false) String params){
        try{
    		JSONObject queryParamJSON = parseObject(params);
            if (StringUtils.isEmpty(queryParamJSON.getString("ruleExcCode")) && StringUtils.isBlank(queryParamJSON.getString("ruleMappBusId")))
                return SToolUtils.convertResultJSONObj("P","parameter error",0,null).toString();
            RuleMappingBusinessEntity_HI instance = ruleMappingBusinessServer.saveOrUpdate(queryParamJSON, queryParamJSON.getIntValue("operatorUserId"));
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", 0, null).toString();
        }catch(Exception e){
            LOGGER.error(e.getMessage(),e);
    		return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }

    @RequestMapping(method = RequestMethod.POST,value="delete")
    public String delete(@RequestParam(required=false) String params){
        try{
            JSONObject queryParamJSON = parseObject(params);
            if(queryParamJSON==null || !queryParamJSON.containsKey("ruleMappBusId")){
                return SToolUtils.convertResultJSONObj(ERROR_STATUS, "缺少参数 ruleMappBusId ", 0, null).toString();
            }
            List<Serializable> ids = new ArrayList<Serializable>();
            String[] idArr = queryParamJSON.getString("ruleMappBusId").split(",");
            for (String id : idArr) {
                ids.add(id);
            }
            ruleMappingBusinessServer.deleteAll(ids);
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", ids.size(), ids).toString();
        }catch(Exception e){
            LOGGER.error(e.getMessage(),e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }
}
