package com.sie.saaf.rule.services;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.rule.model.entities.SaafWebserviceInfoEntity_HI;
import com.sie.saaf.rule.model.entities.readonly.SaafWebserviceInfoEntity_HI_RO;
import com.sie.saaf.rule.model.inter.ISaafWebserviceInfoServer;
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

/**
 * Created by Admin on 2017/7/5.
 */
@RestController
@RequestMapping("/saafWebserviceInfoServices")
public class SaafWebserviceInfoServices extends CommonAbstractService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RuleMappingBusinessService.class);

    @Autowired
    private ISaafWebserviceInfoServer saafWebserviceInfoServer;

    @Override
    public IBaseCommon getBaseCommonServer() {
        return saafWebserviceInfoServer;
    }

    @RequestMapping(method = RequestMethod.POST,value="query")
    public String query(@RequestParam(required=false) String params,
    		@RequestParam(required = false,defaultValue = "1") Integer pageIndex,
    		@RequestParam(required = false,defaultValue = "10") Integer pageRows){
        try{
    		JSONObject queryParamJSON = parseObject(params);
            Pagination<SaafWebserviceInfoEntity_HI_RO> pagination = saafWebserviceInfoServer.find(queryParamJSON,pageIndex,pageRows);

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
            if (StringUtils.isBlank(queryParamJSON.getString("webserviceUrl")))
                return SToolUtils.convertResultJSONObj("P","parameter error",0,null).toString();

            if (StringUtils.isBlank(queryParamJSON.getString("webserviceId"))) {
                List<SaafWebserviceInfoEntity_HI> list = saafWebserviceInfoServer.findByProperty("webserviceCode", queryParamJSON.getString("webserviceCode"));
                if (list.size() > 0)
                    return SToolUtils.convertResultJSONObj("E","webserviceCode 已存在",0,"").toString();
            }
            SaafWebserviceInfoEntity_HI instance = saafWebserviceInfoServer.saveOrUpdate(queryParamJSON, queryParamJSON.getIntValue("operatorUserId"));

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
            if(queryParamJSON==null || !queryParamJSON.containsKey("id")){
                return SToolUtils.convertResultJSONObj(ERROR_STATUS, "缺少参数 id ", 0, null).toString();
            }
            List<Serializable> ids = new ArrayList<Serializable>();
            String[] idArr = queryParamJSON.getString("id").split(",");
            for (String id : idArr) {
                ids.add(id);
            }
            saafWebserviceInfoServer.deleteAll(ids);
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", ids.size(), ids).toString();
        }catch(Exception e){
            LOGGER.error(e.getMessage(),e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }


}
