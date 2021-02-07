package com.sie.watsons.base.withdrawal.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.watsons.base.withdrawal.model.entities.readonly.TtaSplitBrandDetailEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.withdrawal.model.entities.TtaSplitBrandDetailEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface ITtaSplitBrandDetail extends IBaseCommon<TtaSplitBrandDetailEntity_HI>{

    List<TtaSplitBrandDetailEntity_HI_RO> findSplitBrandList(String oldproposalYear, String vendorNbr) throws Exception;

    List<TtaSplitBrandDetailEntity_HI_RO> findSplitBrandByNotSourceSupplier(String oldproposalYear, String vendorNbr) throws Exception;

    TtaSplitBrandDetailEntity_HI_RO findSplitBrandByMoney(String oldproposalYear, String vendorNbr) throws Exception;

    TtaSplitBrandDetailEntity_HI_RO findSplitBrandByFcsPurchaseAndFcsSales(String oldproposalYear, String vendorNbr) throws Exception;
}
