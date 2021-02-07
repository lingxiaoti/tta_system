package com.sie.watsons.base.pos.supplierinfo.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.pos.supplierinfo.model.entities.EquPosCredentialsAttachmentEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface IEquPosCredentialsAttachment extends IBaseCommon<EquPosCredentialsAttachmentEntity_HI>{
    //供应商资质文件查询
    JSONObject findCredentialsAttachmentInfo(JSONObject queryParamJSON, Integer pageIndex,
                                Integer pageRows);

    //供应商资质文件-保存
    EquPosCredentialsAttachmentEntity_HI saveCredentialsAttachmentInfo(JSONObject params) throws Exception;

    //供应商资质文件-删除
    String deleteCredentialsAttachmentInfo(JSONObject jsonObject);

    //供应商资质文件报表查询(Non-trade)
    JSONObject findCredentialsAttachmentReportForm(JSONObject queryParamJSON, Integer pageIndex,
                                             Integer pageRows);
}
