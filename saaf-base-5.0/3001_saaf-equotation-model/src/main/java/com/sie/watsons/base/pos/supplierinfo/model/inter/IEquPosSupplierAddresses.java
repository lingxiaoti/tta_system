package com.sie.watsons.base.pos.supplierinfo.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.pos.supplierinfo.model.entities.EquPosSupplierAddressesEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface IEquPosSupplierAddresses extends IBaseCommon<EquPosSupplierAddressesEntity_HI>{
    //供应商地址信息查询
    JSONObject findSupplierAddressInfo(JSONObject queryParamJSON, Integer pageIndex,
                                       Integer pageRows);

    //供应商地址-保存
    EquPosSupplierAddressesEntity_HI saveSupplierAddressInfo(JSONObject params) throws Exception;

    //供应商地址-删除
    String deleteSupplierAddressInfo(JSONObject jsonObject);

    //供应商具体经营状况及现场信息报表查询(Non-trade)
    JSONObject findSupplierAddressReportForm(JSONObject queryParamJSON, Integer pageIndex,
                                       Integer pageRows);
}
