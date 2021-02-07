package com.sie.watsons.base.supplier.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.watsons.base.supplier.model.entities.readonly.TtaAbnormalSupplierBrandEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.supplier.model.entities.TtaAbnormalSupplierBrandEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface ITtaAbnormalSupplierBrand extends IBaseCommon<TtaAbnormalSupplierBrandEntity_HI>{

    int callSupplierBrandByNoExixstTTA(JSONObject jsonObject, int userId) throws Exception;

    Pagination<TtaAbnormalSupplierBrandEntity_HI_RO> find(JSONObject jsonObject, Integer pageIndex, Integer pageRows);
    void saveOrUpdate(JSONObject queryJsonParam ,int userId) throws Exception;

    void insertSupplierBrand(JSONObject jsonObject, int userId) throws Exception;
}
