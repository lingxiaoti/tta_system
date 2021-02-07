package com.sie.watsons.base.pos.archivesChange.beforeChange.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.pos.archivesChange.beforeChange.model.entities.EquPosBgqCredentialAttachEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface IEquPosBgqCredentialAttach extends IBaseCommon<EquPosBgqCredentialAttachEntity_HI>{
    //档案变更前-供应商资质文件查询
    JSONObject findBgqCredentialsAttachmentInfo(JSONObject queryParamJSON, Integer pageIndex,
                                               Integer pageRows);

    //档案变更前-供应商资质文件保存
    EquPosBgqCredentialAttachEntity_HI saveBgqCredentialsAttachmentInfo(JSONObject params) throws Exception;

    //档案变更前-供应商资质文件删除
    String deleteBgqCredentialsAttachmentInfo(JSONObject jsonObject);
}
