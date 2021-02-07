package com.sie.watsons.base.ob.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.ob.model.entities.readonly.PlmProductExceptionDetailEntity_HI_RO;
import com.sie.watsons.base.ob.model.inter.IPlmProductExceptionDetail;
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
@RequestMapping("/plmProductExceptionDetailService")
public class PlmProductExceptionDetailService extends CommonAbstractService{
    private static final Logger LOGGER = LoggerFactory.getLogger(PlmProductExceptionDetailService.class);
    @Autowired
    private IPlmProductExceptionDetail plmProductExceptionDetailServer;

    @Override
    public IBaseCommon getBaseCommonServer(){
        return this.plmProductExceptionDetailServer;
    }

    /**
     * 查询项目异常明细
     *
     * @param params
     * {
     *     teamFrameworkId:框架ID
     * }
     * @param pageIndex 当前页码
     * @param pageRows 页面行数
     * @return Pagination
     */
    @RequestMapping(method = RequestMethod.POST, value = "findPlmProductExceptionDetailInfo")
    public String findPlmProductExceptionDetailInfo(@RequestParam(required = false) String params,@RequestParam(required = false,defaultValue = "1") Integer pageIndex,
                                              @RequestParam(required = false,defaultValue = "10") Integer pageRows){
        JSONObject queryParamJSON = parseObject(params);
        Pagination<PlmProductExceptionDetailEntity_HI_RO> plmProductExceptionDetailList = plmProductExceptionDetailServer.findPlmProductExceptionDetailInfo(queryParamJSON,pageIndex,pageRows);
        queryParamJSON = (JSONObject) JSON.toJSON(plmProductExceptionDetailList);
        queryParamJSON.put(SToolUtils.STATUS, "S");
        queryParamJSON.put(SToolUtils.MSG, SUCCESS_MSG);
        return queryParamJSON.toString();
    }

    /**
     * @param params
     * @return java.lang.String
     * @description   批量保存项目异常
     **/
    @RequestMapping(method = RequestMethod.POST,value = "savePlmProductExceptionDetailInfo")
    public String savePlmProductExceptionDetailInfo(@RequestParam(required = true) String params){
        try{
            JSONObject parseObject = parseObject(params);
            JSONArray jsonArray = (JSONArray) JSONArray.toJSON(plmProductExceptionDetailServer.savePlmProductExceptionDetailInfo(parseObject));
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
     * @description   批量删除项目异常
     **/
    @RequestMapping(method = RequestMethod.POST,value = "deletePlmProductExceptionDetailInfo")
    public String deletePlmProductExceptionDetailInfo(@RequestParam(required = true) String params){
        try{
            JSONObject parseObject = parseObject(params);
            int size = plmProductExceptionDetailServer.deletePlmProductExceptionDetailInfo(parseObject);
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