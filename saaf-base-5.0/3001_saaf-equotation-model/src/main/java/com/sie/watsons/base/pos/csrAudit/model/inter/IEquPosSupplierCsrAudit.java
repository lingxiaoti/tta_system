package com.sie.watsons.base.pos.csrAudit.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.pos.csrAudit.model.entities.EquPosSupplierCsrAuditEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface IEquPosSupplierCsrAudit extends IBaseCommon<EquPosSupplierCsrAuditEntity_HI>{
    //供应商CSR审核单据查询
    JSONObject findSupplierCsrAudit(JSONObject queryParamJSON, Integer pageIndex,
                                    Integer pageRows);

    //供应商CSR审核单据保存
    EquPosSupplierCsrAuditEntity_HI saveSupplierCsrAudit(JSONObject params) throws Exception;

    //供应商CSR审核单据删除
    String deleteSupplierCsrAudit(JSONObject jsonObject);

    //供应商CSR审核单据提交
    void submitSupplierCsrAudit(JSONObject params) throws Exception;
}
