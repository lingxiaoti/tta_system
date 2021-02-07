package com.sie.watsons.base.pos.supplierinfo.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.pos.supplierinfo.model.entities.EquPosSupplierBankEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface IEquPosSupplierBank extends IBaseCommon<EquPosSupplierBankEntity_HI>{
    //供应商银行信息查询
    JSONObject findSupplierBankInfo(JSONObject queryParamJSON, Integer pageIndex,
                                    Integer pageRows);

    //供应商银行信息-保存
    EquPosSupplierBankEntity_HI saveBankInfo(JSONObject params) throws Exception;

    //供应商银行信息-删除
    String deleteBankInfo(JSONObject jsonObject);

    //供应商银行信息查询
    JSONObject findSupplierBankReportForm(JSONObject queryParamJSON, Integer pageIndex,
                                    Integer pageRows);
}
