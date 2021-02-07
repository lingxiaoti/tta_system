package com.sie.saaf.rule.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.rule.model.entities.SaafWebserviceInfoEntity_HI;
import com.sie.saaf.rule.model.entities.SaafWebserviceParamInfoEntity_HI;
import com.sie.saaf.rule.model.entities.readonly.SaafWebserviceParamInfoEntity_HI_RO;
import com.sie.saaf.rule.model.inter.ISaafWebserviceInfoServer;
import com.sie.saaf.rule.model.inter.ISaafWebserviceParamInfoServer;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Admin on 2017/7/5.
 */
@RestController
@RequestMapping("/saafWebserviceParamInfoServices")
public class SaafWebserviceParamInfoServices extends CommonAbstractService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RuleMappingBusinessService.class);

    @Autowired
    private ISaafWebserviceInfoServer saafWebserviceInfoServer ;

    @Autowired
    private ISaafWebserviceParamInfoServer saafWebserviceParamInfoServer;

    @Override
    public IBaseCommon getBaseCommonServer() {
        return saafWebserviceParamInfoServer;
    }
    
    @RequestMapping(method = RequestMethod.POST,value="query")
    public String query(@RequestParam(required=false) String params,
    		@RequestParam(required = false,defaultValue = "1") Integer pageIndex,
    		@RequestParam(required = false,defaultValue = "10") Integer pageRows){
        try{
    		JSONObject queryParamJSON = parseObject(params);
            Pagination<SaafWebserviceParamInfoEntity_HI_RO> pagination = saafWebserviceParamInfoServer.find(queryParamJSON,pageIndex,pageRows);
    		
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
            if(queryParamJSON==null || !queryParamJSON.containsKey("id")){
                return SToolUtils.convertResultJSONObj(ERROR_STATUS, "缺少参数 id ", 0, null).toString();
            }
            List<Serializable> ids = new ArrayList<Serializable>();
            String[] idArr = queryParamJSON.getString("id").split(",");
            for (String id : idArr) {
                ids.add(id);
            }
            saafWebserviceParamInfoServer.deleteAll(ids);
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", ids.size(), ids).toString();
        }catch(Exception e){
            LOGGER.error(e.getMessage(),e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }

    @RequestMapping(method = RequestMethod.POST,value="saveOrUpdate")
    public String saveOrUpdate(@RequestParam(required=false) String params){
        try{
    		JSONObject queryParamJSON = parseObject(params);
            if (StringUtils.isBlank(queryParamJSON.getString("webserviceCode")) || StringUtils.isBlank(queryParamJSON.getString("paramCode")))
                return SToolUtils.convertResultJSONObj("P","parameter error",0,null).toString();

            Map<String, Object> map = new HashMap<>();
            map.put("webserviceCode", queryParamJSON.getString("webserviceCode"));
            map.put("paramCode", queryParamJSON.getString("paramCode"));

            if (StringUtils.isBlank(queryParamJSON.getString("paramId"))) {
                List<SaafWebserviceParamInfoEntity_HI> list = saafWebserviceParamInfoServer.findByProperty(map);
                if (list.size() > 0)
                    return SToolUtils.convertResultJSONObj("E","paramCode重复",0,"").toString();
            }

            SaafWebserviceParamInfoEntity_HI instance = saafWebserviceParamInfoServer.saveOrUpdate(queryParamJSON, queryParamJSON.getIntValue("operatorUserId"));

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
            JSONObject props = null;
            if (queryParamJSON.containsKey("services")) {
                LOGGER.info("services:{}", queryParamJSON.getString("services"));
                props = queryParamJSON.getJSONObject("services");
            }
            JSONArray propvals = queryParamJSON.containsKey("params") ? queryParamJSON.getJSONArray("params") : null;
            if (props == null && propvals == null)
                return SToolUtils.convertResultJSONObj("P","parameter error",0,null).toString();

            List<SaafWebserviceParamInfoEntity_HI> valResult = new ArrayList<>();
            SaafWebserviceInfoEntity_HI propsResult = new SaafWebserviceInfoEntity_HI();
            JSONObject result = new JSONObject();

            if (props != null) {
                List<SaafWebserviceInfoEntity_HI> list = saafWebserviceInfoServer.findByProperty("webserviceCode", queryParamJSON.getString("webserviceCode"));
                if (list.size() > 0 && queryParamJSON.getString("webserviceId") == null)
                    return SToolUtils.convertResultJSONObj("e","webserviceCode 已存在",0,"").toString();
                propsResult = saafWebserviceInfoServer.saveOrUpdate(props, queryParamJSON.getIntValue("operatorUserId"));
            }
            if (propvals != null) {
                for (int i = 0; i < propvals.size(); i++) {
                    JSONObject temp = propvals.getJSONObject(i);
                    SaafWebserviceParamInfoEntity_HI obj = saafWebserviceParamInfoServer.saveOrUpdate(temp, -1);
                    valResult.add(obj);
                }
            }
            result.put("services", propsResult);
            result.put("params", valResult);

            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", 1, result).toString();
        }catch(Exception e){
            LOGGER.error(e.getMessage(),e);
    		return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }
}
