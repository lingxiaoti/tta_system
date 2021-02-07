package com.sie.watsons.base.supplement.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.entities.readonly.BaseAttachmentEntity_HI_RO;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.supplement.model.entities.TtaSideAgrtHeaderEntity_HI;
import com.sie.watsons.base.supplement.model.entities.readonly.TtaSideAgrtHeaderEntity_HI_RO;
import com.sie.watsons.base.supplier.model.entities.readonly.TtaSupplierEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;

public interface ITtaSideAgrtHeader extends IBaseCommon<TtaSideAgrtHeaderEntity_HI>{

	TtaSideAgrtHeaderEntity_HI editTtaSideAgrtHeader(JSONObject parameters,int userId) throws Exception;
	
	Pagination<TtaSideAgrtHeaderEntity_HI_RO> findTtaSideAgrtHeaderEntity(JSONObject jsonObject, Integer pageIndex, Integer pageRows);

	Pagination<TtaSupplierEntity_HI_RO> findTtaSupplierEntity(JSONObject parameters, Integer pageIndex, Integer pageRows) throws Exception;

	Pagination<BaseAttachmentEntity_HI_RO> findBaseAttachmentEntity(JSONObject parameters, Integer pageIndex, Integer pageRows) throws Exception;

	TtaSideAgrtHeaderEntity_HI submitTtaSideAgrtHeader(JSONObject parameters, int userId) throws Exception;

    TtaSideAgrtHeaderEntity_HI disSicradTtaSideAgrtHeader(JSONObject parameters, int userId) throws Exception;

    void updateStatus(JSONObject parameters, int userId) throws Exception;
}
