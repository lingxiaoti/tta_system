package com.sie.watsons.base.supplement.model.inter;


import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.entities.BaseAttachmentEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.supplement.model.entities.TtaAttachmentDownloadEntity_HI;
import com.sie.watsons.base.supplement.model.entities.readonly.TtaAttachmentDownloadEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;

public interface ITtaAttachmentDownload extends IBaseCommon<TtaAttachmentDownloadEntity_HI> {

    TtaAttachmentDownloadEntity_HI ttaAchmentDownload(BaseAttachmentEntity_HI baseAttachmentEntity_hi,int userId) throws Exception;

    Pagination<TtaAttachmentDownloadEntity_HI_RO> findAttachmentDownloadPagination(JSONObject jsonObject, Integer pageIndex, Integer pageRows);
}
