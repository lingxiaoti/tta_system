package com.sie.watsons.base.pos.supplierinfo.model.inter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.pos.supplierinfo.model.entities.EquPosSupplierInfoEntity_HI;

public interface IEquPosSupplierInfo extends IBaseCommon<EquPosSupplierInfoEntity_HI>{
    //供应商基础信息查询
    JSONObject findSupplierInfo(JSONObject queryParamJSON, Integer pageIndex,
                                Integer pageRows);

    //供应商基础信息-保存
    EquPosSupplierInfoEntity_HI saveSupplierInfo(JSONObject params) throws Exception;

    //供应商档案查询
    JSONObject findSupplierFiles(JSONObject queryParamJSON, Integer pageIndex,
                                Integer pageRows);

    //供应商档案查询IT
    JSONObject findSupplierFilesIt(JSONObject queryParamJSON, Integer pageIndex,
                                Integer pageRows);

    //供应商信息报表查询(Non-trade)
    JSONObject findSupplierInfoReportForm(JSONObject queryParamJSON, Integer pageIndex,
                                   Integer pageRows);

    //供应商查询LOV
    JSONObject findSupplierLov(JSONObject queryParamJSON, Integer pageIndex,
                                Integer pageRows);

    JSONArray findSupplierScoreInfo(JSONObject paramsJONS);

    void checkSupplierCredentials();
    void checkOEMSupplierCredentials();
}
