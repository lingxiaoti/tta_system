package com.sie.watsons.base.pos.qualificationreview.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.pos.qualificationreview.model.entities.EquPosZzscBankEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface IEquPosZzscBank extends IBaseCommon<EquPosZzscBankEntity_HI>{
    //资质审查-供应商银行信息查询
    JSONObject findZzscSupplierBankInfo(JSONObject queryParamJSON, Integer pageIndex,
                                    Integer pageRows);

    //资质审查-供应商银行信息保存
    EquPosZzscBankEntity_HI saveZzscBankInfo(JSONObject params) throws Exception;

    //资质审查-供应商银行信息删除
    String deleteZzscBankInfo(JSONObject jsonObject);
}
