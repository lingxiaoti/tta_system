package com.sie.watsons.base.ob.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.ob.model.entities.PlmDevelopmentQaDetailEntity_HI;
import com.sie.watsons.base.ob.model.entities.readonly.PlmDevelopmentQaDetailEntity_HI_RO;
import com.sie.watsons.base.ob.model.inter.IPlmDevelopmentQaDetail;
import com.sie.watsons.base.redisUtil.ResultUtils;
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
@RequestMapping("/plmDevelopmentQaDetailService")
public class PlmDevelopmentQaDetailService extends CommonAbstractService{
    private static final Logger LOGGER = LoggerFactory.getLogger(PlmDevelopmentQaDetailService.class);
    @Autowired
    private IPlmDevelopmentQaDetail plmDevelopmentQaDetailServer;

    @Override
    public IBaseCommon getBaseCommonServer(){
        return this.plmDevelopmentQaDetailServer;
    }

    /**
     * 查询资质文件明细
     *
     * @param params
     * {
     *     teamFrameworkId:框架ID
     * }
     * @param pageIndex 当前页码
     * @param pageRows 页面行数
     * @return Pagination
     */
    @RequestMapping(method = RequestMethod.POST, value = "findPlmDevelopmentQaDetailInfo")
    public String findPlmDevelopmentQaDetailInfo(@RequestParam(required = false) String params,@RequestParam(required = false,defaultValue = "1") Integer pageIndex,
                                                  @RequestParam(required = false,defaultValue = "10") Integer pageRows){
        try{
            JSONObject queryParamJSON = parseObject(params);
            Pagination<PlmDevelopmentQaDetailEntity_HI_RO> plmDevelopmentQaDetailList = plmDevelopmentQaDetailServer.findPlmDevelopmentQaDetailInfo(queryParamJSON,pageIndex,pageRows);
            ResultUtils.getLookUpValue("PLM_QA_DETAIL_STATUS");
            queryParamJSON = (JSONObject) JSON.toJSON(plmDevelopmentQaDetailList);
            queryParamJSON.put(SToolUtils.STATUS, "S");
            queryParamJSON.put(SToolUtils.MSG, SUCCESS_MSG);
            return queryParamJSON.toString();
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
     * @description   批量资质文件明细
     **/
    @RequestMapping(method = RequestMethod.POST,value = "savePlmDevelopmentQaDetailInfo")
    public String savePlmDevelopmentQaDetailInfo(@RequestParam(required = true) String params){
        try{
            JSONObject parseObject = parseObject(params);
            JSONArray jsonArray = (JSONArray) JSONArray.toJSON(plmDevelopmentQaDetailServer.savePlmDevelopmentQaDetailInfo(parseObject));
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
     * @description   批量删除资质文件明细
     **/
    @RequestMapping(method = RequestMethod.POST,value = "deletePlmDevelopmentQaDetailInfo")
    public String deletePlmDevelopmentQaDetailInfo(@RequestParam(required = true) String params){
        try{
            JSONObject parseObject = parseObject(params);
            int size = plmDevelopmentQaDetailServer.deletePlmDevelopmentQaDetailInfo(parseObject);
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