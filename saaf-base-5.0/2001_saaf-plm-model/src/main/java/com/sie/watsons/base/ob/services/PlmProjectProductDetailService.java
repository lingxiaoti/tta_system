package com.sie.watsons.base.ob.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.ob.model.entities.PlmProjectProductDetailEntity_HI;
import com.sie.watsons.base.ob.model.entities.readonly.PlmProjectProductDetailEntity_HI_RO;
import com.sie.watsons.base.ob.model.inter.IPlmProjectProductDetail;
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
@RequestMapping("/plmProjectProductDetailService")
public class PlmProjectProductDetailService extends CommonAbstractService{
    private static final Logger LOGGER = LoggerFactory.getLogger(PlmProjectProductDetailService.class);
    @Autowired
    private IPlmProjectProductDetail plmProjectProductDetailServer;

    @Override
    public IBaseCommon getBaseCommonServer(){
        return this.plmProjectProductDetailServer;
    }

    /**
     * 查询项目产品明细
     *
     * @param params
     * {
     *     teamFrameworkId:框架ID
     * }
     * @param pageIndex 当前页码
     * @param pageRows 页面行数
     * @return Pagination
     */
    @RequestMapping(method = RequestMethod.POST, value = "findPlmProjectProductDetailInfo")
    public String findPlmProjectProductDetailInfo(@RequestParam(required = false) String params,@RequestParam(required = false,defaultValue = "1") Integer pageIndex,
                                         @RequestParam(required = false,defaultValue = "10") Integer pageRows){
        try{
            JSONObject queryParamJSON = parseObject(params);
            Pagination<PlmProjectProductDetailEntity_HI_RO> plmProjectProductDetailList = plmProjectProductDetailServer.findPlmProjectProductDetailInfo(queryParamJSON,pageIndex,pageRows);
            ResultUtils.getLookUpValue("PLM_OB_PRODUCT_BILL_STATUS");
            ResultUtils.getLookUpValue("PLM_PROJECT_PRODUCT_CATEGORY");
            queryParamJSON = (JSONObject) JSON.toJSON(plmProjectProductDetailList);
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
     * @description   批量保存项目产品明细
     **/
    @RequestMapping(method = RequestMethod.POST,value = "savePlmProjectProductDetailInfo")
    public String savePlmProjectProductDetailInfo(@RequestParam(required = true) String params){
        try{
            JSONObject parseObject = parseObject(params);
            JSONArray jsonArray = (JSONArray) JSONArray.toJSON(plmProjectProductDetailServer.savePlmProjectProductDetailInfo(parseObject));
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
     * @description   批量删除项目产品明细
     **/
    @RequestMapping(method = RequestMethod.POST,value = "deletePlmProjectProductDetailInfo")
    public String deletePlmProjectProductDetailInfo(@RequestParam(required = true) String params){
        try{
            JSONObject parseObject = parseObject(params);
            int size = plmProjectProductDetailServer.deletePlmProjectProductDetailInfo(parseObject);
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