package com.sie.saaf.rule.services;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.rule.model.entities.PageModelGroupDetailEntity_HI;
import com.sie.saaf.rule.model.entities.readonly.PageModelGroupDetailEntity_HI_RO;
import com.sie.saaf.rule.model.inter.IPageModelGroupDetailServer;
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
 * Created by Administrator on Fri Jul 07 17:28:51 CST 2017
 */
@RestController
@RequestMapping("/pageModelGroupDetailService")
public class PageModelGroupDetailService extends CommonAbstractService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PageModelGroupDetailService.class);

    @Autowired
    private IPageModelGroupDetailServer pageModelGroupDetailServer;

    @Override
    public IBaseCommon getBaseCommonServer() {
        return pageModelGroupDetailServer;
    }

    @RequestMapping(method = RequestMethod.POST,value="query")
    public String query(@RequestParam(required=false) String params,
    		@RequestParam(required = false,defaultValue = "1") Integer pageIndex,
    		@RequestParam(required = false,defaultValue = "10") Integer pageRows){
        try{
    		JSONObject queryParamJSON = parseObject(params);

            Pagination<PageModelGroupDetailEntity_HI_RO> pagination = pageModelGroupDetailServer.find(queryParamJSON, pageIndex, pageRows);
            JSONObject result = JSONObject.parseObject(JSON.toJSONString(pagination));
            result.put("data", pagination.getData());
            result.put("status", "S");
            return result.toString();

        }catch(Exception e){
            LOGGER.error(e.getMessage(),e);
    		return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }

    @RequestMapping(method = RequestMethod.POST,value="saveOrUpdate")
    public String saveOrUpdate(@RequestParam(required=false) String params){
        try{
            if (StringUtils.isBlank(params))
                return SToolUtils.convertResultJSONObj("P","parameter error",0,null).toString();

    		JSONObject queryParamJSON = parseObject(params);
            PageModelGroupDetailEntity_HI instance = pageModelGroupDetailServer.saveOrUpdate(queryParamJSON, queryParamJSON.getIntValue("operatorUserId"));
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
            if(queryParamJSON==null || !queryParamJSON.containsKey("id")){
                return SToolUtils.convertResultJSONObj(ERROR_STATUS, "缺少参数 id ", 0, null).toString();
            }

            List<Serializable> ids = new ArrayList<Serializable>();
            String[] idArr = queryParamJSON.getString("id").split(",");
            for(String s : idArr){
                ids.add(s);
            }

            pageModelGroupDetailServer.deleteAll(ids);

            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", ids.size(), ids).toString();
        }catch(Exception e){
            LOGGER.error(e.getMessage(),e);
    		return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }

}
