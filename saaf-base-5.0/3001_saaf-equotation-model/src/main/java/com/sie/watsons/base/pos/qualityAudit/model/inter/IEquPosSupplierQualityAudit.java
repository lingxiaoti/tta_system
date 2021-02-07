package com.sie.watsons.base.pos.qualityAudit.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.pos.qualityAudit.model.entities.EquPosSupplierQualityAuditEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface IEquPosSupplierQualityAudit extends IBaseCommon<EquPosSupplierQualityAuditEntity_HI>{
    //供应商质量审核单据查询
    JSONObject findSupplierQualityAudit(JSONObject queryParamJSON, Integer pageIndex,
                                    Integer pageRows);

    //供应商质量审核单据保存
    EquPosSupplierQualityAuditEntity_HI saveSupplierQualityAudit(JSONObject params) throws Exception;

    //供应商质量审核单据删除
    String deleteSupplierQualityAudit(JSONObject jsonObject);

    //供应商质量审核单据提交
    void submitSupplierQualityAudit(JSONObject params) throws Exception;
}
