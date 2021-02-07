package com.sie.watsons.base.pos.creditAudit.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.pos.creditAudit.model.entities.EquPosCreditAuditChangeHEntity_HI;
import com.sie.watsons.base.pos.creditAudit.model.entities.readonly.EquPosCreditAuditChangeEntity_HI_RO;
import com.sie.watsons.base.pos.creditAudit.model.entities.readonly.EquPosCreditAuditChangeHEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;

public interface IEquPosCreditAuditChangeH extends IBaseCommon<EquPosCreditAuditChangeHEntity_HI>{

    Pagination<EquPosCreditAuditChangeHEntity_HI_RO> findEquPosCreditAuditChangeInfo(String params, Integer pageIndex, Integer pageRows);

    JSONObject saveEquPosCreditAuditChange(JSONObject jsonObject, int userId) throws Exception;

    EquPosCreditAuditChangeHEntity_HI_RO findSupCreditAuditChangeDatail(String params);

    Pagination<EquPosCreditAuditChangeEntity_HI_RO> findEquPosCreditAuditChangeL(String params, Integer pageIndex, Integer pageRows);

    JSONObject saveImportChange(JSONObject jsonObject, int userId);

    String approveEquPosCreditAuditChange(JSONObject jsonObject, int userId);

    Integer deleteCreditAuditChange(JSONObject jsonObject, int userId);

    void deleteCreditAuditLine(JSONObject jsonObject, int userId);

    EquPosCreditAuditChangeHEntity_HI updateCreditAuditChangeCallback(JSONObject paramsObject, int userId);

    JSONObject saveEquPosCreditAuditChangeSubmit(JSONObject jsonObject, int userId);
}
