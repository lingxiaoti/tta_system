package com.sie.watsons.base.pos.archivesChange.afterChange.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.watsons.base.pos.archivesChange.afterChange.model.entities.EquPosBgCredentialAttachEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface IEquPosBgCredentialAttach extends IBaseCommon<EquPosBgCredentialAttachEntity_HI>{
    //档案变更后-供应商资质文件查询
    JSONObject findBgCredentialsAttachmentInfo(JSONObject queryParamJSON, Integer pageIndex,
                                                 Integer pageRows);

    //档案变更后-供应商资质文件保存
    EquPosBgCredentialAttachEntity_HI saveBgCredentialsAttachmentInfo(JSONObject params) throws Exception;

    //档案变更后-供应商资质文件删除
    String deleteBgCredentialsAttachmentInfo(JSONObject jsonObject);
}
