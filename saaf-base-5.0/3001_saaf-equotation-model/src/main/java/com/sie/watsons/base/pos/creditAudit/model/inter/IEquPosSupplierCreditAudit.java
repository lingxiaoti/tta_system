package com.sie.watsons.base.pos.creditAudit.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.watsons.base.pos.creditAudit.model.entities.EquPosSupplierCreditAuditEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.pos.creditAudit.model.entities.readonly.EquPosSupplierCreditAuditEntity_HI_RO;
import com.sie.watsons.base.pos.recover.model.entities.readonly.EquPosSupplierRecoverEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;

public interface IEquPosSupplierCreditAudit extends IBaseCommon<EquPosSupplierCreditAuditEntity_HI>{

    Pagination<EquPosSupplierCreditAuditEntity_HI_RO> findEquPosCreditAuditInfo(String params, Integer pageIndex, Integer pageRows);

    Pagination<EquPosSupplierCreditAuditEntity_HI_RO> findQualificationLov(String params, Integer pageIndex, Integer pageRows);

    EquPosSupplierCreditAuditEntity_HI saveEquPosCreditAudit(JSONObject jsonObject, int userId) throws Exception;

    EquPosSupplierCreditAuditEntity_HI_RO findSupCreditAuditDatail(String params);

    void deleteSupplierCreditAudit(JSONObject jsonObject, int userId);

    EquPosSupplierCreditAuditEntity_HI updateCreditAuditApprovalCallback(JSONObject paramsObject, int userId);

    EquPosSupplierCreditAuditEntity_HI saveEquPosCreditAuditSubmit(JSONObject jsonObject, int userId);
}
