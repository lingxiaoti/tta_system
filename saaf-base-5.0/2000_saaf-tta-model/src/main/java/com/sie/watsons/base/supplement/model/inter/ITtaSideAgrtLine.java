package com.sie.watsons.base.supplement.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.entities.readonly.BaseAttachmentEntity_HI_RO;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.supplement.model.entities.TtaSideAgrtHeaderEntity_HI;
import com.sie.watsons.base.supplement.model.entities.TtaSideAgrtLineEntity_HI;
import com.sie.watsons.base.supplement.model.entities.readonly.TtaSideAgrtHeaderEntity_HI_RO;
import com.sie.watsons.base.supplier.model.entities.readonly.TtaSupplierEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;

import java.util.List;

public interface ITtaSideAgrtLine extends IBaseCommon<TtaSideAgrtLineEntity_HI>{

    List<TtaSideAgrtLineEntity_HI> saveProposalSupplier(JSONObject queryParamJSON) throws Exception;

    TtaSideAgrtLineEntity_HI deleteSupplierBySideAgrtLineId(Integer id) throws Exception;
}
