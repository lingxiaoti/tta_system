package com.sie.watsons.base.pos.archivesChange.beforeChange.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.pos.archivesChange.beforeChange.model.entities.EquPosBgqBankEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface IEquPosBgqBank extends IBaseCommon<EquPosBgqBankEntity_HI>{
    //档案变更前-供应商银行信息查询
    JSONObject findBgqSupplierBankInfo(JSONObject queryParamJSON, Integer pageIndex,
                                      Integer pageRows);

    //档案变更前-供应商银行信息保存
    EquPosBgqBankEntity_HI saveBgqBankInfo(JSONObject params) throws Exception;

    //档案变更前-供应商银行信息删除
    String deleteBgqBankInfo(JSONObject jsonObject);
}
