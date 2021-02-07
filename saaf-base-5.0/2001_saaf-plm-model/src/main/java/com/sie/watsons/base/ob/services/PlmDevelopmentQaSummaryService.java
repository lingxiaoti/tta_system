package com.sie.watsons.base.ob.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.ob.model.entities.PlmDevelopmentQaSummaryEntity_HI;
import com.sie.watsons.base.ob.model.entities.readonly.PlmDevelopmentQaSummaryEntity_HI_RO;
import com.sie.watsons.base.ob.model.inter.IPlmDevelopmentQaSummary;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/plmDevelopmentQaSummaryService")
public class PlmDevelopmentQaSummaryService extends CommonAbstractService{
    private static final Logger LOGGER = LoggerFactory.getLogger(PlmDevelopmentQaSummaryService.class);
    @Autowired
    private IPlmDevelopmentQaSummary plmDevelopmentQaSummaryServer;

    @Override
    public IBaseCommon getBaseCommonServer(){
        return this.plmDevelopmentQaSummaryServer;
    }

    /**
     * 查询资质文件汇总表
     *
     * @param params
     * {
     *     teamFrameworkId:框架ID
     * }
     * @param pageIndex 当前页码
     * @param pageRows 页面行数
     * @return Pagination
     */
    @RequestMapping(method = RequestMethod.POST, value = "findPlmDevelopmentQaSummaryInfo")
    public String findPlmDevelopmentQaSummaryInfo(@RequestParam(required = false) String params,@RequestParam(required = false,defaultValue = "1") Integer pageIndex,
                                                  @RequestParam(required = false,defaultValue = "10") Integer pageRows){
        JSONObject queryParamJSON = parseObject(params);
        Pagination<PlmDevelopmentQaSummaryEntity_HI_RO> plmDevelopmentQaSummaryList = plmDevelopmentQaSummaryServer.findPlmDevelopmentQaSummaryInfo(queryParamJSON,pageIndex,pageRows);
        queryParamJSON = (JSONObject) JSON.toJSON(plmDevelopmentQaSummaryList);
        queryParamJSON.put(SToolUtils.STATUS, "S");
        queryParamJSON.put(SToolUtils.MSG, SUCCESS_MSG);
        return queryParamJSON.toString();
    }

    /**
     * @param params
     * @return java.lang.String
     * @description   批量保存资质文件汇总数据
     **/
    @RequestMapping(method = RequestMethod.POST,value = "savePlmDevelopmentQaSummaryInfo")
    public String savePlmDevelopmentQaSummaryInfo(@RequestParam(required = true) String params){
        try{
            JSONObject parseObject = parseObject(params);
            JSONArray jsonArray = (JSONArray) JSONArray.toJSON(plmDevelopmentQaSummaryServer.savePlmDevelopmentQaSummaryInfo(parseObject));
            return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, jsonArray).toString();

        }catch (IllegalArgumentException e){
            LOGGER.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("M", e.getMessage(), 0, null).toString();
        }catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
        }
    }

    /**
     * @param params
     * @return java.lang.String
     * @description   批量删除资质文件汇总数据
     **/
    @RequestMapping(method = RequestMethod.POST,value = "deletePlmDevelopmentQaSummaryInfo")
    public String deletePlmDevelopmentQaSummaryInfo(@RequestParam(required = true) String params){
        try{
            JSONObject parseObject = parseObject(params);
            int size = plmDevelopmentQaSummaryServer.deletePlmDevelopmentQaSummaryInfo(parseObject);
            return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, size).toString();
        }catch (IllegalArgumentException e){
            LOGGER.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("M", e.getMessage(), 0, null).toString();
        }catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
        }
    }
}