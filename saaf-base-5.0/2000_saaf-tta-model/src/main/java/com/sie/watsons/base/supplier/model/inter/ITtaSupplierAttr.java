package com.sie.watsons.base.supplier.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.entities.readonly.BaseAttachmentEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.supplier.model.entities.TtaSupplierAttrEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface ITtaSupplierAttr extends IBaseCommon<TtaSupplierAttrEntity_HI>{

    Pagination<BaseAttachmentEntity_HI_RO> findBaseAttachmentEntity(JSONObject parameters, Integer pageIndex, Integer pageRows);

}
