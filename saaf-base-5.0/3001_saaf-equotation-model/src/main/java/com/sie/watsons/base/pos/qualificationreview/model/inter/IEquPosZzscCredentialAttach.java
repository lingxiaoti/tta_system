package com.sie.watsons.base.pos.qualificationreview.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.pos.qualificationreview.model.entities.EquPosZzscCredentialAttachEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface IEquPosZzscCredentialAttach extends IBaseCommon<EquPosZzscCredentialAttachEntity_HI>{
    //资质审查-供应商资质文件查询
    JSONObject findZzscCredentialsAttachmentInfo(JSONObject queryParamJSON, Integer pageIndex,
                                             Integer pageRows);

    //资质审查-供应商资质文件保存
    EquPosZzscCredentialAttachEntity_HI saveZzscCredentialsAttachmentInfo(JSONObject params) throws Exception;

    //资质审查-供应商资质文件删除
    String deleteZzscCredentialsAttachmentInfo(JSONObject jsonObject);
}
