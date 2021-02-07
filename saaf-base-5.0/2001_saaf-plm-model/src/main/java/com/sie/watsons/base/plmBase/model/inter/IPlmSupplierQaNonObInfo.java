package com.sie.watsons.base.plmBase.model.inter;

import java.rmi.ServerException;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.plmBase.model.entities.PlmSupplierQaNonObInfoEntity_HI;
import com.sie.watsons.base.plmBase.model.entities.readonly.PlmSupplierQaNonObInfoEntity_HI_RO;
import com.sie.watsons.base.supplier.model.entities.readonly.TtaSupplierEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;

public interface IPlmSupplierQaNonObInfo extends
        IBaseCommon<PlmSupplierQaNonObInfoEntity_HI> {

    Pagination<PlmSupplierQaNonObInfoEntity_HI_RO> findPlmSupplierQaNonObInfoInfo(
            JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

    PlmSupplierQaNonObInfoEntity_HI savePlmSupplierQaNonObInfoInfo(
            JSONObject queryParamJSON) throws Exception;

    public Pagination<TtaSupplierEntity_HI_RO> findPlmSupplier(
            JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

    JSONObject findSupplierDetail(JSONObject param);

    Integer getProductSupplier(JSONObject param);

    /**
     * 查询供应商
     *
     * @param parameters    查询参数
     * @param pageIndex 第几页
     * @param pageRows  查几行
     * @return 供应商列表
     */
    Pagination<PlmSupplierQaNonObInfoEntity_HI_RO> findVendor(JSONObject parameters, Integer pageIndex, Integer pageRows) throws Exception;

    JSONObject getProductSupplier(String params) throws Exception;
}
