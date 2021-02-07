package com.sie.watsons.base.pos.archivesChange.afterChange.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.watsons.base.pos.archivesChange.afterChange.model.entities.EquPosBgBankEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface IEquPosBgBank extends IBaseCommon<EquPosBgBankEntity_HI>{
    //档案变更后-供应商银行信息查询
    JSONObject findBgSupplierBankInfo(JSONObject queryParamJSON, Integer pageIndex,
                                        Integer pageRows);

    //档案变更后-供应商银行信息保存
    EquPosBgBankEntity_HI saveBgBankInfo(JSONObject params) throws Exception;

    //档案变更后-供应商银行信息删除
    String deleteBgBankInfo(JSONObject jsonObject);
}
