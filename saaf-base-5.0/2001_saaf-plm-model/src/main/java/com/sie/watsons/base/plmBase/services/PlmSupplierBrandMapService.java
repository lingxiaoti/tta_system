package com.sie.watsons.base.plmBase.services;

import com.alibaba.fastjson.JSON;
import com.sie.saaf.common.bean.UserSessionBean;
import com.sie.watsons.base.plmBase.model.entities.readonly.PlmSupplierBrandMapEntity_HI_RO;
import com.sie.watsons.base.plmBase.model.inter.IPlmSupplierBrandMap;

import com.alibaba.fastjson.JSONObject;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.model.inter.IBaseCommon;

@RestController
@RequestMapping("/plmSupplierBrandMapService")
public class PlmSupplierBrandMapService extends CommonAbstractService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PlmSupplierBrandMapService.class);

    @Autowired
    private IPlmSupplierBrandMap plmSupplierBrandMapServer;

    @Override
    public IBaseCommon getBaseCommonServer() {
        return this.plmSupplierBrandMapServer;
    }


    @RequestMapping(method = RequestMethod.POST, value = "findSupplierBrandMap")
    public String findSupplierBrandMap(@RequestParam(required = false) String params, @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
                                       @RequestParam(required = false, defaultValue = "10") Integer pageRows) {
        JSONObject queryParamJSON = parseObject(params);
        Pagination<PlmSupplierBrandMapEntity_HI_RO> dataList = plmSupplierBrandMapServer.findSupplierBrandMap(queryParamJSON, pageIndex, pageRows);
        queryParamJSON = (JSONObject) JSON.toJSON(dataList);
        queryParamJSON.put(SToolUtils.STATUS, "S");
        queryParamJSON.put(SToolUtils.MSG, SUCCESS_MSG);
        return queryParamJSON.toString();
    }

    @RequestMapping(method = RequestMethod.POST, value = "findDistinctSupplierBrandMap")
    public String findDistinctSupplierBrandMap(@RequestParam(required = false) String params,
                                             @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
                                             @RequestParam(required = false, defaultValue = "10") Integer pageRows) {
        JSONObject queryParamJSON = parseObject(params);
        UserSessionBean userSessionBean = getUserSessionBean();
        queryParamJSON.put("vendorCodeList", userSessionBean.getVendorCodes());
        Pagination<PlmSupplierBrandMapEntity_HI_RO> dataList = plmSupplierBrandMapServer.findDistinctSupplierBrandMap(queryParamJSON, pageIndex, pageRows);
        queryParamJSON = (JSONObject) JSON.toJSON(dataList);
        queryParamJSON.put(SToolUtils.STATUS, "S");
        queryParamJSON.put(SToolUtils.MSG, SUCCESS_MSG);
        return queryParamJSON.toString();
    }


}