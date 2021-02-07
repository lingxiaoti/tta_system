package com.sie.watsons.base.product.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.product.model.entities.readonly.PlmProductBpmUserEntity_HI_RO;
import com.sie.watsons.base.product.model.entities.readonly.PlmProductObLicenseEntity_HI_RO;
import com.sie.watsons.base.product.model.inter.IPlmProductBpmUser;
import com.sie.watsons.base.product.model.inter.IPlmProductObLicense;
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
@RequestMapping("/plmProductObLicenseService")
public class PlmProductObLicenseService extends CommonAbstractService {
    private static final Logger LOGGER = LoggerFactory
            .getLogger(PlmProductObLicenseService.class);

    @Autowired
    private IPlmProductObLicense plmProductObLicenseServer;

    @Override
    public IBaseCommon getBaseCommonServer() {
        return this.plmProductObLicenseServer;
    }

    @RequestMapping(method = RequestMethod.POST, value = "findByCondition")
    public String findObLicenseByCondition(@RequestParam(required = true) String params,
                                         @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
                                         @RequestParam(required = false, defaultValue = "10") Integer pageRows) {
        try {
            JSONObject param = parseObject(params);
            JSONObject queryParamJSON = parseObject(params);
            Pagination<PlmProductObLicenseEntity_HI_RO> results = plmProductObLicenseServer.findObLicenseByCondition(queryParamJSON, pageIndex, pageRows);
            queryParamJSON = (JSONObject) JSON.toJSON(results);
            queryParamJSON.put(SToolUtils.STATUS, "S");
            queryParamJSON.put(SToolUtils.MSG, SUCCESS_MSG);
            return queryParamJSON.toString();

        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS,
                    e.getMessage(), 0, null).toString();
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "saveProductObLicense")
    public String saveProductObLicense(@RequestParam(required = true) String params) {
        try {
                JSONObject jsonObject= parseObject(params);
               String result = plmProductObLicenseServer.saveProductObLicense(jsonObject);

            return SToolUtils.convertResultJSONObj("S", "保存成功", 1, result).toJSONString();
        } catch (Exception e) {
            return getException(e,LOGGER);
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "saveProductObLicenseByObId")
    public String saveProductObLicenseByObId(@RequestParam(required = true) String params) {
        try {
            JSONObject jsonObject= JSONObject.parseObject(params);
            String result = plmProductObLicenseServer.saveProductObLicenseByObId(jsonObject);
            return SToolUtils.convertResultJSONObj("S", "保存成功", 1, result).toJSONString();
        } catch (Exception e) {
            return getException(e,LOGGER);
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "saveProductObLicenseByNewFile")
    public String saveProductObLicenseByNewFile(@RequestParam(required = true) String params) {
        try {
            JSONObject jsonObject= JSONObject.parseObject(params);
            SaafToolUtils.validateJsonParms(jsonObject, "plmSupplierQaNonObInfoId","newLines");
            String result = plmProductObLicenseServer.saveProductObLicenseByNewFile(jsonObject);
            return SToolUtils.convertResultJSONObj("S", "保存成功", 1, result).toJSONString();
        } catch (Exception e) {
            return getException(e,LOGGER);
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "saveProductObLicenseByTime")
    public String saveProductObLicenseByTime() {
        try {
            String result = plmProductObLicenseServer.saveProductObLicenseByTime();
            return SToolUtils.convertResultJSONObj("S", "保存成功", 1, result).toJSONString();
        } catch (Exception e) {
            return getException(e,LOGGER);
        }
    }



}