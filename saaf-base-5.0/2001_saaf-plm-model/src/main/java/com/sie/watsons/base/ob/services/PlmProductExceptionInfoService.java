package com.sie.watsons.base.ob.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.ob.model.entities.PlmProductExceptionInfoEntity_HI;
import com.sie.watsons.base.ob.model.entities.readonly.PlmProductExceptionInfoEntity_HI_RO;
import com.sie.watsons.base.ob.model.inter.IPlmProductExceptionInfo;
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
@RequestMapping("/plmProductExceptionInfoService")
public class PlmProductExceptionInfoService extends CommonAbstractService{
    private static final Logger LOGGER = LoggerFactory.getLogger(PlmProductExceptionInfoService.class);
    @Autowired
    private IPlmProductExceptionInfo plmProductExceptionInfoServer;

    @Override
    public IBaseCommon getBaseCommonServer(){
        return this.plmProductExceptionInfoServer;
    }

    /**
     * 查询产品异常头
     *
     * @param params
     * {
     *     teamFrameworkId:框架ID
     * }
     * @param pageIndex 当前页码
     * @param pageRows 页面行数
     * @return Pagination
     */
    @RequestMapping(method = RequestMethod.POST, value = "findPlmProductExceptionInfoInfo")
    public String findPlmProductExceptionInfoInfo(@RequestParam(required = false) String params,@RequestParam(required = false,defaultValue = "1") Integer pageIndex,
                                         @RequestParam(required = false,defaultValue = "10") Integer pageRows){

        try{
            JSONObject queryParamJSON = parseObject(params);
            Pagination<PlmProductExceptionInfoEntity_HI_RO> plmProductExceptionInfoList = plmProductExceptionInfoServer.findPlmProductExceptionInfoInfo(queryParamJSON,pageIndex,pageRows);
            ResultUtils.getLookUpValue("PLM_PRODUCT_EXEP_BILL_STATUS");
            ResultUtils.getLookUpValue("PLM_EXCEPTION_SOURCE");
            ResultUtils.getLookUpValue("PLM_EXCEPTION_CATEGORY");
            ResultUtils.getLookUpValue("PLM_TREATMENT");
            queryParamJSON = (JSONObject) JSON.toJSON(plmProductExceptionInfoList);
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
     * @description   保存产品异常头
     **/
    @RequestMapping(method = RequestMethod.POST,value = "savePlmProductExceptionInfoInfo")
    public String savePlmProductExceptionInfoInfo(@RequestParam(required = true) String params){
        try{
            JSONObject parseObject = parseObject(params);
            int userId = parseObject.getInteger("varUserId");
            JSONObject jsonObject = (JSONObject)JSONObject.toJSON(plmProductExceptionInfoServer.savePlmProductExceptionInfoInfo(parseObject));
            return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, jsonObject).toString();

        }catch (IllegalArgumentException e){
            LOGGER.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("M", e.getMessage(), 0, null).toString();
        }catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
        }
    }
}